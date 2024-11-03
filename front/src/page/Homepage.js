import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import '../assets/styles.css';

const Homepage = () => {
    const [product, setProduct] = useState(null);  
    const navigate = useNavigate();  

    useEffect(() => {
        axios.get('/api/information')
            .then((res) => setProduct(res.data))
            .catch((err) => console.error(err));
    }, []);

    const handleBuy = () => {
        navigate('/reservation/1'); 
    };

    return (
        <div>
            <header className="masthead">
                <div className="container">
                    <div className="masthead-subheading">iPhone 16 Pro 사전예약</div>
                    <div className="masthead-heading text-uppercase">지금 바로 사전 예약 하세요.</div>
                    {product ? (
                        <div className="product-info">
                            <strong>{product.name}</strong>: {product.price.toLocaleString()} 원
                        </div>
                    ) : (
                        <p>No product available.</p>
                    )}
                    <button className="btn btn-primary btn-xl text-uppercase" onClick={handleBuy}>사전예약 하기</button>
                </div>
            </header>
            <div style={{ backgroundColor: 'black', color: 'white', minHeight: '100vh', padding: '20px' }}>
                <section className="page-section" id="about">
                    <div className="container">
                        <div className="text-center">
                            <h2 className="section-heading text-uppercase">아이폰 16 Pro</h2>
                        </div>
                        <ul className="timeline">
                            <li>
                                <div className="timeline-image">
                                    <img className="rounded-circle img-fluid" style={{ width: '230px', height: '159px' }} src={require('../assets/img/black.jpg')} alt="..." />
                                </div>
                                <div className="timeline-panel">
                                    <div className="timeline-heading">
                                        <h4 style={{ color: 'white' }}>2024</h4>
                                        <h4 className="subheading" style={{ color: 'white' }}>디스플레이</h4>
                                    </div>
                                    <div className="timeline-body">
                                        <p style={{ color: 'white' }}>Super Retina XDR 디스플레이로 놀라운 색상과 명암비를 제공합니다.</p>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div className="timeline-image">
                                    <img className="rounded-circle img-fluid" style={{ width: '230px', height: '159px' }} src={require('../assets/img/camera.jpg')} alt="..." />
                                </div>
                                <div className="timeline-panel">
                                    <div className="timeline-heading">
                                        <h4 style={{ color: 'white' }}>2024</h4>
                                        <h4 className="subheading" style={{ color: 'white' }}>카메라</h4>
                                    </div>
                                    <div className="timeline-body">
                                        <p style={{ color: 'white' }}>향상된 카메라 기능으로 저조도에서도 뛰어난 사진을 촬영할 수 있습니다.</p>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div className="timeline-image">
                                    <img className="rounded-circle img-fluid" style={{ width: '230px', height: '159px' }} src={require('../assets/img/better.jpg')} alt="..." />
                                </div>
                                <div className="timeline-panel">
                                    <div className="timeline-heading">
                                        <h4 style={{ color: 'white' }}>2024</h4>
                                        <h4 className="subheading" style={{ color: 'white' }}>배터리 수명</h4>
                                    </div>
                                    <div className="timeline-body">
                                        <p style={{ color: 'white' }}>더 긴 배터리 수명으로 하루 종일 지속됩니다.</p>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div className="timeline-image">
                                    <img className="rounded-circle img-fluid" style={{ width: '230px', height: '159px' }} src={require('../assets/img/a18pro.jpg')} alt="..." />
                                </div>
                                <div className="timeline-panel">
                                    <div className="timeline-heading">
                                        <h4 style={{ color: 'white' }}>2024</h4>
                                        <h4 className="subheading" style={{ color: 'white' }}>프로세서</h4>
                                    </div>
                                    <div className="timeline-body">
                                        <p style={{ color: 'white' }}>강력한 A18 칩으로 뛰어난 성능을 경험하세요.</p>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div className="timeline-image">
                                    <img className="rounded-circle img-fluid" style={{ width: '230px', height: '159px' }} src={require('../assets/img/ios18.jpg')} alt="..." />
                                </div>
                                <div className="timeline-panel">
                                    <div className="timeline-heading">
                                        <h4 style={{ color: 'white' }}>2024</h4>
                                        <h4 className="subheading" style={{ color: 'white' }}>소프트웨어</h4>
                                    </div>
                                    <div className="timeline-body">
                                        <p style={{ color: 'white' }}>iOS 18로 최신 기능과 보안을 경험하세요.</p>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </section>
            </div>
        </div>
    );
};

export default Homepage;










