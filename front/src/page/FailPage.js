import React from 'react';
import { useNavigate } from 'react-router-dom';

function FailPage() {
    const navigate = useNavigate();

    return (
        <div>
            <h1>결제가 실패했습니다</h1>
            <p>결제 중 문제가 발생했습니다.</p>
            <button onClick={() => navigate('/')}>홈으로 돌아가기</button>
        </div>
    );
}

export default FailPage;