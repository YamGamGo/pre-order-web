import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import '../assets/styles.css';

function SuccessPage() {
    const location = useLocation();
    const [reservationData, setReservationData] = useState(null);
    const [error, setError] = useState(null);

    useEffect(() => {
        const queryParams = new URLSearchParams(location.search);
        const id = queryParams.get('id');
        const pg_token = queryParams.get('pg_token');

        const handleSuccess = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/payment/approve?id=${id}&pg_token=${pg_token}`);
                if (!response.ok) {
                    throw new Error('결제 성공 처리 실패');
                }
                const data = await response.json();
                console.log('결제 성공:', data);
            } catch (error) {
                console.error('결제 성공 처리 중 오류 발생:', error);
            }
        };

        const fetchReservationData = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/payment/reservation/${id}`);
                if (!response.ok) {
                    throw new Error('예약 정보 조회 실패');
                }
                const data = await response.json();
                setReservationData(data);
            } catch (error) {
                setError(error.message);
                console.error('예약 정보 조회 중 오류 발생:', error);
            }
        };

        handleSuccess();
        fetchReservationData();
    }, [location.search]);

    return (
        <div className="success-container">
            <h1 className="success-title">결제가 완료되었습니다!</h1>
            <p className="success-message">결제가 성공적으로 처리되었습니다.</p>
            {error && <p className="error-message">오류 발생: {error}</p>}
            {reservationData && (
                <div className="reservation-info">
                    <h2>예약 정보</h2>
                    <p>이름: {reservationData.name}</p>
                    <p>전화번호: {reservationData.number}</p>
                    <p>이메일: {reservationData.email}</p>
                    <p>주소: {reservationData.address}</p>
                </div>
            )}
            <button className="continue-button" onClick={() => window.location.href = '/'}>홈으로 돌아가기</button>
        </div>
    );
}

export default SuccessPage;

