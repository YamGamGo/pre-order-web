import api from "./axiosInstance";

/**
 * 상품 정보 조회
 */
export async function getProductInformation() {
    const res = await api.get("/api/product");
    return res.data;
}

/**
 * 예약 생성
 */
export async function createReservation(reservationData) {
    const res = await api.post("/api/product/reservation", reservationData);
    return res.data;
}
