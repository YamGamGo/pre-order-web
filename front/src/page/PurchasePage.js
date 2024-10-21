import React, { useState } from 'react';
import axios from 'axios';

function PurchasePage() {
    const [email, setEmail] = useState(''); 
    const [address, setAddress] = useState(''); 
    const [number, setNumber] = useState(''); 
    const [name, setName] = useState(''); 
    const handleBuy = async () => {
        try {
            const response = await axios.post('http://localhost:8080/api/kakao/ready', {
                email,
                address,
                number,
                name,
                fail_url: 'http://localhost:3000/fail' 
            });

            if (response.data && response.data.next_redirect_pc_url) {
                window.location.href = response.data.next_redirect_pc_url; 
            } else {
                throw new Error('Redirect URL not found in response.');
            }
        } catch (error) {
            console.error('결제 준비 실패:', error);
            alert('결제 준비 실패');
        }
    };

    return (
        <div>
            <h1>구매 페이지</h1>
            <label>
                이메일 주소:
                <input
                    type="email"
                    placeholder="이메일 입력"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />
                <br />
                주소:
                <input
                    type="text"
                    placeholder="주소 입력"
                    value={address}
                    onChange={(e) => setAddress(e.target.value)}
                />
                <br />
                번호:
                <input
                    type="text"
                    placeholder="번호 입력"
                    value={number}
                    onChange={(e) => setNumber(e.target.value)}
                />
                <br />
                이름:
                <input
                    type="text"
                    placeholder="이름 입력"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                />
            </label>
            <button onClick={handleBuy}>카카오페이로 결제하기</button>
        </div>
    );
}

export default PurchasePage;




