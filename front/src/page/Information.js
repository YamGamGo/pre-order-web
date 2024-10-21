import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';  /* 페이지 이동을 위한 useNavigate 훅 */

function Information() {
    const [product, setProduct] = useState(null);  // 단일 상품을 저장할 state
    const navigate = useNavigate();

    useEffect(() => {
        axios.get('/api/information')
            .then((res) => setProduct(res.data))
            .catch((err) => console.error(err));
    }, []);

    return (
        <div>
            <h1>여기는 추가 정보 페이지입니다</h1>
            <h2>상품 정보</h2>
            {product ? (  // 상품이 있을 때만 렌더링
                <div>
                    <strong>{product.name}</strong>: {product.price.toLocaleString()} 원
                    <button onClick={() => navigate(`/reservation/${product.id}`)}>구매하기</button> {/* navigate로 경로 이동 */}
                </div>
            ) : (
                <p>No product available.</p>
            )}
        </div>
    );
}

export default Information;










