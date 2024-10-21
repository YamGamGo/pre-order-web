import React, { useEffect } from 'react';
import { useLocation } from 'react-router-dom';

function SuccessPage() {
    const location = useLocation();
    
    useEffect(() => {
        const queryParams = new URLSearchParams(location.search);
        const id = queryParams.get('id');
        const pg_token = queryParams.get('pg_token');

        // 결제 성공 처리 API 호출
        const handleSuccess = async () => {
            try {
                console.log(`http://localhost:8080/api/kakao/approve?id=${id}&pg_token=${pg_token}`);
                const response = await fetch(`http://localhost:8080/api/kakao/approve?id=${id}&pg_token=${pg_token}`);
                if (!response.ok) {
                    throw new Error('결제 성공 처리 실패');
                }
                const data = await response.json();
                console.log('결제 성공:', data);
            } catch (error) {
                console.error('결제 성공 처리 중 오류 발생:', error);
            }
        };

        handleSuccess();
    }, [location.search]);

    return (
        <div>
            <h1>결제가 완료되었습니다!</h1>
            <p>결제가 성공적으로 처리되었습니다.</p>
        </div>
    );
}

export default SuccessPage;


