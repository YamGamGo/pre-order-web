
import React from 'react';

export default function KakaoLoginPage() {
    const handleKakaoLogin = () => {
        window.location.href = '/oauth/kakao/login';
    };

    return (
        <div
            style={{
                minHeight: '100vh',
                backgroundColor: 'black',
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                padding: '1rem',
                fontFamily:
                    "'Noto Sans KR', 'Malgun Gothic', 'Apple SD Gothic Neo', 'Nanum Gothic', sans-serif",
            }}
        >
            <div
                style={{
                    position: 'relative',
                    background:
                        'linear-gradient(135deg, #1a202c 0%, #2d3748 50%, #ffb800 100%)',
                    borderRadius: '24px',
                    padding: '3rem 6rem',
                    boxShadow: '0 20px 40px rgba(255, 184, 0, 0.4)',
                    overflow: 'hidden',
                }}
            >
                <div
                    style={{
                        position: 'absolute',
                        inset: 0,
                        background:
                            'linear-gradient(135deg, rgba(255, 184, 0, 0.1), transparent 70%)',
                        filter: 'blur(20px)',
                        borderRadius: '24px',
                        pointerEvents: 'none',
                    }}
                ></div>

                <button
                    onClick={handleKakaoLogin}
                    style={{
                        position: 'relative',
                        display: 'inline-flex',
                        alignItems: 'center',
                        justifyContent: 'center',
                        padding: '1.5rem 4rem',
                        fontSize: '1.5rem',
                        fontWeight: '700',
                        color: 'black',
                        backgroundColor: '#FEE500',
                        border: 'none',
                        borderRadius: '16px',
                        cursor: 'pointer',
                        boxShadow: '0 8px 15px rgba(254, 229, 0, 0.6)',
                        transition: 'all 0.3s ease',
                    }}
                    onMouseEnter={e => {
                        e.currentTarget.style.transform = 'scale(1.05)';
                        e.currentTarget.style.boxShadow = '0 12px 30px rgba(254, 229, 0, 0.8)';
                    }}
                    onMouseLeave={e => {
                        e.currentTarget.style.transform = 'scale(1)';
                        e.currentTarget.style.boxShadow = '0 8px 15px rgba(254, 229, 0, 0.6)';
                    }}
                >
                    카카오로 로그인
                </button>
            </div>
        </div>
    );
}
