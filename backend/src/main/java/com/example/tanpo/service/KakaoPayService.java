package com.example.tanpo.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.example.tanpo.entity.ReservationEntity;
import com.example.tanpo.repository.ReservationRepository;

import java.util.Optional;

@Service
public class KakaoPayService {

    @Value("${kakao.admin.key}")
    private String adminKey;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Transactional
    public String ready(ReservationEntity reservationEntity) {
        RestTemplate restTemplate = new RestTemplate();

        // 결제 정보를 DB에 저장
        ReservationEntity savedReservationEntity = reservationRepository.save(reservationEntity);
        Long id = savedReservationEntity.getId();  // DB에 저장된 구매 ID

        // KakaoPay 결제 준비 API 호출
        return readyKaKao(savedReservationEntity, id);
    }

    public String readyKaKao(ReservationEntity savedReservationEntity, Long id) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "KakaoAK " + adminKey);

        // 요청 파라미터 설정
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cid", "TC0ONETIME");
        params.add("partner_order_id", "1001");
        params.add("partner_user_id", "user001");
        params.add("item_name", "푸바오");
        params.add("quantity", "1");
        params.add("total_amount", "77777");
        params.add("tax_free_amount", "0");
        params.add("approval_url", "http://localhost:3000/success?id=" + id);  // 결제 성공 리다이렉트 URL
        params.add("cancel_url", "http://localhost:3000/cancel");  // 결제 취소 리다이렉트 URL
        params.add("fail_url", "http://localhost:3000/fail");  // 결제 실패 리다이렉트 URL

        // HTTP 요청 생성
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        try {
            // KakaoPay 결제 준비 API 호출
            ResponseEntity<String> response = restTemplate.postForEntity(
                    "https://kapi.kakao.com/v1/payment/ready",
                    requestEntity,
                    String.class
            );

            // 응답에서 tid와 리다이렉트 URL 추출
            String responseBody = response.getBody();
            String tid = extractTidFromResponse(responseBody);  // 결제 tid 추출
            String nextRedirectUrl = extractNextRedirectUrlFromResponse(responseBody);  // 리다이렉트 URL 추출

            // DB에 tid와 결제 상태 업데이트
            savedReservationEntity.setTid(tid);
            savedReservationEntity.setStatus("READY");
            reservationRepository.save(savedReservationEntity);

            // 응답으로 tid와 리다이렉트 URL을 JSON 형식으로 반환
            return "{\"tid\":\"" + tid + "\",\"next_redirect_pc_url\":\"" + nextRedirectUrl + "\"}";
        } catch (HttpClientErrorException e) {
            // HTTP 오류 처리
            throw new RuntimeException("KakaoPay 준비 과정에서 오류가 발생: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            // 예외 처리
            throw new RuntimeException("오류 발생: " + e.getMessage());
        }
    }

    @Transactional
    public String approve(String pgToken, Long id) {
        RestTemplate restTemplate = new RestTemplate();

        // DB에서 구매 정보 조회
        Optional<ReservationEntity> purchaseOpt = getPurchaseById(id);
        if (purchaseOpt.isEmpty()) {
            throw new IllegalArgumentException("구매 정보를 찾을 수 없음: " + id);
        }
        ReservationEntity reservationEntity = purchaseOpt.get();

        // paid를 통해 결제 상태 확인
        if ("PAID".equals(reservationEntity.getStatus())) {
            return "{\"message\":\"이 구매는 이미 완료됨.\"}";  // 이미 결제가 완료된 경우
        }

        String tid = reservationEntity.getTid();  // DB에서 조회한 tid

        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "KakaoAK " + adminKey);  // Kakao Admin Key 추가

        // 결제 승인 요청 파라미터 설정
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cid", "TC0ONETIME");
        params.add("tid", tid);  // 결제 tid
        params.add("partner_order_id", "1001");
        params.add("partner_user_id", "user001");
        params.add("pg_token", pgToken);  // 결제 승인 토큰

        // HTTP 요청 생성
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        try {
            // KakaoPay 결제 승인 API 호출
            ResponseEntity<String> response = restTemplate.postForEntity(
                    "https://kapi.kakao.com/v1/payment/approve",
                    requestEntity,
                    String.class
            );

            // response 뭐가 들어있는지 확인
            //

            // todo response 체크 해서 실제로 결제가 성공 되었는지 확인


            // todo : 실패했으면 실패하면 failed 업데이트하고 사용자가 이후에는 상품 예약 과정은 스킵하고 결제만 바로 다시시도할 수 있도록 구현 필요

            // 결제가 완료되면 DB에서 상태를 paid로 업데이트
            reservationEntity.setStatus("PAID");
            reservationRepository.save(reservationEntity);

            // RabbitMQ를 통해 이메일 전송 메시지를 전송
            rabbitTemplate.convertAndSend("paymentQueue", createEmailMessage(reservationEntity));

            // 리다이렉트 URL 생성
            String successUrl = "http://localhost:3000/success?id=" + id;

            // 결제 승인 응답 반환 (여기서 리다이렉트 URL을 포함)
            return "{\"successUrl\":\"" + successUrl + "\", \"responseBody\":" + response.getBody() + "}";
        } catch (HttpClientErrorException e) {
            // HTTP 오류 처리
            throw new RuntimeException("KakaoPay 승인 과정에서 오류가 발생: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            // 예외 처리
            throw new RuntimeException("예상치 못한 오류가 발생: " + e.getMessage());
        }
    }

    // 메소드 접근 제어자 변경
    public Optional<ReservationEntity> getPurchaseById(Long id) {
        return reservationRepository.findById(id);  // ID로 PurchaseEntity 조회
    }

    private String createEmailMessage(ReservationEntity reservationEntity) {
        JsonObject emailMessage = new JsonObject();
        emailMessage.addProperty("email", reservationEntity.getEmail());  // 이메일 주소
        emailMessage.addProperty("subject", "결제가 완료 되었습니다.");  // 메일 제목
        emailMessage.addProperty("body", "구매 ID: " + reservationEntity.getId() + " 결제가 성공적으로 마무리 되었습니다.");  // 메일 본문
        return emailMessage.toString();  // JSON 형태로 반환
    }

    private String extractTidFromResponse(String responseBody) {
        JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
        return jsonResponse.get("tid").getAsString();  // tid 추출
    }

    private String extractNextRedirectUrlFromResponse(String responseBody) {
        JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
        return jsonResponse.get("next_redirect_pc_url").getAsString();  // 리다이렉트 URL 추출
    }
}



