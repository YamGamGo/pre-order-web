import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function Information() {
    const [product, setProduct] = useState(null);  // 단일 상품을 저장할 state
    const navigate = useNavigate();

    useEffect(() => {
        axios.get('/api/information')
            .then((res) => setProduct(res.data))
            .catch((err) => console.error(err));
    }, []);

    return (
        <div style={{ backgroundColor: 'black', color: 'white', minHeight: '100vh', padding: '20px' }}>
            {product ? (  // 상품이 있을 때만 렌더링
                <div>
                    <strong>{product.name}</strong>: {product.price.toLocaleString()} 원
                    <button onClick={() => navigate(`/reservation/${product.id}`)}>구매하기</button>
                </div>
            ) : (
                <p>No product available.</p>
            )}
            <section className="page-section" id="about">
                <div className="container">
                    <div className="text-center">
                        <h2 className="section-heading text-uppercase">아이폰 16pro</h2>
                    </div>
                    <ul className="timeline">
                        <li>
                            <div className="timeline-image">
                                <img className="rounded-circle img-fluid" src={require('../assets/img/black.jpg')} alt="..." />
                            </div>
                            <div className="timeline-panel">
                                <div className="timeline-heading">
                                    <h4 style={{ color: 'white' }}>2024</h4>
                                    <h4 className="subheading" style={{ color: 'white' }}>디스플레이</h4>
                                </div>
                                <div className="timeline-body">
                                    <p style={{ color: 'white' }}>Super Retina XDR 디스플레이
                                        15.9cm(대각선) 전면 화면 OLED 디스플레이
                                        2622 x 1206 픽셀 해상도(460ppi)
                                        iPhone 16 Pro 디스플레이는 모서리가 둥근 형태로, 기기의 아름다운 곡면 디자인을 반영합니다. 이 모서리는 기기의 전체적인 모양인 직사각형 내부에 위치합니다. 직사각형 기준으로 측정했을 때, 화면은 대각선 길이 기준 15.93cm입니다(실제로 보이는 영역은 이보다 좁음).</p>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div className="timeline-panel">
                                <div className="timeline-heading">
                                    <h4 style={{ color: 'white' }}>A18 Pro 칩</h4>
                                </div>
                                <div className="timeline-body">
                                    <p style={{ color: 'white' }}>새로운 6코어 CPU(성능 코어 2개 및 효율 코어 4개), 새로운 6코어 GPU, 새로운 16코어 Neural Engine이 탑재되어 더욱 뛰어난 성능을 제공합니다.</p>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div className="timeline-panel">
                                <div className="timeline-heading">
                                    <h4 style={{ color: 'white' }}>프로 카메라 시스템</h4>
                                </div>
                                <div className="timeline-body">
                                    <p style={{ color: 'white' }}>
                                        48MP Fusion: 24mm, ƒ/1.78 조리개, 2세대 센서 시프트 광학 이미지 흔들림 보정(OIS), 100% Focus Pixels, 초고해상도 사진 지원(24MP 및 48MP)<br />
                                        12MP 2배 망원: 48mm, f/1.78 조리개, 2세대 센서 시프트 광학 이미지 흔들림 보정(OIS), 100% Focus Pixels<br />
                                        48MP 울트라 와이드: 13mm, ƒ/2.2 조리개 및 120° 시야각, 하이브리드 Focus Pixels, 초고해상도 사진(48MP)<br />
                                        12MP 5배 망원: 120mm, ƒ/2.8 조리개 및 20° 시야각, 100% Focus Pixels, 7매(Seven-element) 렌즈, 3D 센서 시프트 광학 이미지 흔들림 보정(OIS) 및 오토포커스, 테트라프리즘 디자인<br />
                                        5배 광학 줌인, 2배 광학 줌아웃, 10배 광학 줌 범위<br />
                                        최대 25배 디지털 줌<br />
                                        <strong>카메라 컨트롤:</strong><br />
                                        맞춤 설정 가능한 기본 렌즈(Fusion)<br />
                                        사파이어 크리스털 렌즈 커버<br />
                                        적응형 True Tone 플래시<br />
                                        Photonic Engine<br />
                                        Deep Fusion<br />
                                        스마트 HDR 5<br />
                                        초점 및 심도 제어 기능을 지원하는 한 차원 높은 인물 사진<br />
                                        6가지 효과의 인물 사진 조명<br />
                                        야간 모드<br />
                                        LiDAR 스캐너를 활용한 야간 모드 인물 사진<br />
                                        파노라마(최대 63MP)<br />
                                        최신 세대 사진 스타일<br />
                                        공간 사진<br />
                                        48MP 접사 사진<br />
                                        Apple ProRAW<br />
                                        사진 및 Live Photo 촬영 시 넓은 색영역 포착<br />
                                        렌즈 보정(울트라 와이드)<br />
                                        첨단 적목 보정<br />
                                        자동 흔들림 보정<br />
                                        고속 연사 모드<br />
                                        사진 위치 표시 기능<br />
                                        촬영 이미지 포맷: HEIF, JPEG, DNG
                                    </p>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
            </section>
        </div>
    );
}

export default Information;


















