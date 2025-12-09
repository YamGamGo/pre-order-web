import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import TimelineItem from '../components/TimelineItem';

import black from '../assets/img/black.jpg';

function Information() {
    const [product, setProduct] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        axios.get('/api/information')
            .then((res) => setProduct(res.data))
            .catch((err) => console.error(err));
    }, []);

    return (
        <div style={{ backgroundColor: 'black', color: 'white', minHeight: '100vh', padding: '20px' }}>
            {product ? (
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
                        <h2 className="section-heading text-uppercase">아이폰 16 Pro</h2>
                    </div>
                    <ul className="timeline">
                        <TimelineItem
                            year="2024"
                            title="디스플레이"
                            description={`Super Retina XDR 디스플레이
                                        15.9cm(대각선) 전면 화면 OLED 디스플레이
                                        2622 x 1206 픽셀 해상도(460ppi)
                                        iPhone 16 Pro 디스플레이는 모서리가 둥근 형태로, 기기의 아름다운 곡면 디자인을 반영합니다. 
                                        이 모서리는 기기의 전체적인 모양인 직사각형 내부에 위치합니다. 직사각형 기준으로 측정했을 때, 
                                        화면은 대각선 길이 기준 15.93cm입니다(실제로 보이는 영역은 이보다 좁음).`}
                            image={black}
                        />
                        <TimelineItem
                            title="A18 Pro 칩"
                            description="새로운 6코어 CPU(성능 코어 2개 및 효율 코어 4개), 새로운 6코어 GPU, 새로운 16코어 Neural Engine이 탑재되어 더욱 뛰어난 성능을 제공합니다."
                        />
                        <TimelineItem
                            title="프로 카메라 시스템"
                            description={`48MP Fusion: 24mm, ƒ/1.78 조리개, 2세대 센서 시프트 광학 이미지 흔들림 보정(OIS), 100% Focus Pixels, 초고해상도 사진 지원(24MP 및 48MP)
                                        12MP 2배 망원: 48mm, f/1.78 조리개, 2세대 센서 시프트 광학 이미지 흔들림 보정(OIS), 100% Focus Pixels
                                        48MP 울트라 와이드: 13mm, ƒ/2.2 조리개 및 120° 시야각, 하이브리드 Focus Pixels, 초고해상도 사진(48MP)
                                        12MP 5배 망원: 120mm, ƒ/2.8 조리개 및 20° 시야각, 100% Focus Pixels, 7매 렌즈, 3D 센서 시프트 광학 이미지 흔들림 보정(OIS) 및 오토포커스, 테트라프리즘 디자인
                                        5배 광학 줌인, 2배 광학 줌아웃, 10배 광학 줌 범위
                                        최대 25배 디지털 줌
                                        카메라 컨트롤, Photonic Engine, Deep Fusion, 스마트 HDR 5, 야간 모드, 공간 사진, Apple ProRAW, HEIF, JPEG, DNG 지원 등`}
                        />
                    </ul>
                </div>
            </section>
        </div>
    );
}

export default Information;