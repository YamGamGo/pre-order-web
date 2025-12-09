import api from "./axiosInstance";

/**
 * 카카오페이 결제 준비
 */
export async function readyPayment(reservationData) {
    const res = await api.post("/api/payment/ready", reservationData);
    return res.data;
}

/**
 * 결제 승인 요청
 */
export async function approvePayment(id, pgToken) {
    const res = await api.get(`/api/payment/approve?id=${id}&pg_token=${pgToken}`);
    return res.data;
}

/**
 * 예약 정보 조회
 */
export async function getReservation(id) {
    const res = await api.get(`/api/payment/reservation/${id}`);
    return res.data;
}
