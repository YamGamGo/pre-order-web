import React, { useState } from 'react';
import axios from 'axios';

function PurchasePage() {
    const [email, setEmail] = useState('');
    const [address, setAddress] = useState('');
    const [number, setNumber] = useState('');
    const [name, setName] = useState('');

    const handleBuy = async () => {
        try {
            const response = await axios.post('http://localhost:8080/api/payment/ready', {
                email,
                address,
                number,
                name,
                fail_url: 'http://localhost:3000/fail'
            });

            // 성공적인 응답 처리
            if (response.data && response.data.next_redirect_pc_url) {
                // 리디렉션
                window.location.href = response.data.next_redirect_pc_url;
            } else {
                throw new Error('Redirect URL not found in response.');
            }
        } catch (error) {
            // 에러 처리
            console.error('결제 준비 실패:', error);
            alert('결제 준비 실패: ' + (error.response?.data?.message || '서버 오류'));
        }
    };

    return (
        <div>
            <section className="page-section" id="contact">
                <div className="container">
                    <div className="text-center">
                        <h2 className="section-heading text-uppercase">사전구매 예약</h2>
                        <br />
                    </div>
                    <form id="contactForm" onSubmit={(e) => { e.preventDefault(); handleBuy(); }}>
                        <div className="row align-items-stretch mb-5">
                            <div className="col-md-6">
                                <div className="form-group">
                                    <input
                                        className="form-control"
                                        id="name"
                                        type="text"
                                        placeholder="이름 *"
                                        value={name}
                                        onChange={(e) => setName(e.target.value)}
                                        required
                                    />
                                    <div className="invalid-feedback">이름을 입력하세요.</div>
                                </div>
                                <div className="form-group">
                                    <input
                                        className="form-control"
                                        id="email"
                                        type="email"
                                        placeholder="이메일 *"
                                        value={email}
                                        onChange={(e) => setEmail(e.target.value)}
                                        required
                                    />
                                    <div className="invalid-feedback">이메일을 입력하세요.</div>
                                    <div className="invalid-feedback">유효한 이메일이 아닙니다.</div>
                                </div>
                                <div className="form-group mb-md-0">
                                    <input
                                        className="form-control"
                                        id="phone"
                                        type="tel"
                                        placeholder="전화번호 *"
                                        value={number}
                                        onChange={(e) => setNumber(e.target.value)}
                                        required
                                    />
                                    <div className="invalid-feedback">전화번호를 입력하세요.</div>
                                </div>
                            </div>
                            <div className="col-md-6">
                                <div className="form-group form-group-textarea mb-md-0">
                                    <input
                                        className="form-control"
                                        id="address"
                                        type="text"
                                        placeholder="주소 *"
                                        value={address}
                                        onChange={(e) => setAddress(e.target.value)}
                                        required
                                    />
                                    <div className="invalid-feedback">주소를 입력하세요.</div>
                                </div>
                            </div>
                        </div>
                        <div className="text-center">
                            <button className="btn btn-primary btn-xl text-uppercase" type="submit">구매하기</button>
                        </div>
                    </form>
                </div>
            </section>
        </div>
    );
}

export default PurchasePage;
