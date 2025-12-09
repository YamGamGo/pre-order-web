// src/page/Homepage.js
"use client";

import { useEffect, useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { logoutJwt, loginWithKakao } from "../api/authService.js";
import { getUserProfile } from "../api/userService.js";
import { getProductInformation } from "../api/productService.js";
import IphoneFeature from "../components/IphoneFeature.js";
import ScrollIndicator from "../components/ScrollIndicator.js"; 

// 이미지 import
import black from "../assets/img/black.jpg";
import camera from "../assets/img/camera.jpg";
import better from "../assets/img/better.jpg";
import a18pro from "../assets/img/a18pro.jpg";
import ios18 from "../assets/img/ios18.jpg";

const useScrollAnimation = () => {
    const [visibleElements, setVisibleElements] = useState(new Set());

    useEffect(() => {
        const observer = new IntersectionObserver(
            (entries) => {
                entries.forEach((entry) => {
                    if (entry.isIntersecting) {
                        setVisibleElements((prev) => new Set([...prev, entry.target.dataset.animate]));
                    }
                });
            },
            { threshold: 0.1, rootMargin: "0px 0px -50px 0px" }
        );

        const elements = document.querySelectorAll("[data-animate]");
        elements.forEach((el) => observer.observe(el));

        return () => observer.disconnect();
    }, []);

    return visibleElements;
};

const useParallax = () => {
    const [scrollY, setScrollY] = useState(0);

    useEffect(() => {
        const handleScroll = () => setScrollY(window.scrollY);
        window.addEventListener("scroll", handleScroll);
        return () => window.removeEventListener("scroll", handleScroll);
    }, []);

    return scrollY;
};

const HomePage = () => {
    const [product, setProduct] = useState(null);
    const [me, setMe] = useState(null);
    const navigate = useNavigate();
    const location = useLocation();
    const visibleElements = useScrollAnimation();
    const scrollY = useParallax();

    const fetchMe = async () => {
        try {
            const data = await getUserProfile();
            setMe(data);
        } catch (err) {
            console.error(err);
            setMe({ loggedIn: false, nickname: null, profileImage: null });
        }
    };

    useEffect(() => {
        getProductInformation()
            .then((data) => setProduct(data))
            .catch((err) => console.error(err));

        fetchMe();
    }, [location]);

    const handleBuy = () => {
        navigate("/reservation/1");
    };

    const navigateToKakaoLogin = () => {
        loginWithKakao();
    };

    const handleLogout = async () => {
        try {
            await logoutJwt();
        } catch (e) {
            console.error(e);
        } finally {
            setMe({ loggedIn: false, nickname: null, profileImage: null });
        }
    };

    const renderRightTop = () => {
        if (!me) return null;
        if (!me.loggedIn) {
            return (
                <button
                    onClick={navigateToKakaoLogin}
                    className="fixed top-6 right-6 z-50 rounded-full bg-yellow-400 px-6 py-3 font-semibold text-black shadow-lg transition-transform duration-300 hover:scale-105 hover:bg-yellow-500 hover:shadow-[0_15px_30px_rgba(251,191,36,0.4),0_0_25px_rgba(251,191,36,0.2)]"
                >
                    카카오로 로그인하기
                </button>
            );
        }
        return (
            <div className="fixed top-6 right-6 z-50 flex items-center gap-4">
                {me.profileImage && (
                    <img
                        src={me.profileImage || "/placeholder.svg"}
                        alt="프로필"
                        className="h-10 w-10 rounded-full border-2 border-white/20"
                    />
                )}
                <span className="text-sm font-medium text-white">{me.nickname ?? "회원"}님 환영합니다.</span>
                <button
                    onClick={handleLogout}
                    className="rounded-full border border-white/30 px-4 py-2 text-sm text-white transition-all duration-200 hover:border-white/50 hover:bg-white/10"
                >
                    로그아웃
                </button>
            </div>
        );
    };

    return (
        <div className="min-h-screen bg-black text-white">
            {renderRightTop()}

            {/* Hero Section */}
            <header
                className="relative flex min-h-screen items-center justify-center overflow-hidden bg-gradient-to-b from-gray-900 to-black"
                style={{ transform: `translateY(${scrollY * 0.5}px)` }}
            >
                <div
                    className="absolute inset-0 opacity-10"
                    style={{
                        backgroundImage:
                            "radial-gradient(circle at 25% 25%, rgba(59,130,246,0.1) 0%, transparent 50%)",
                        transform: `translateY(${scrollY * 0.3}px)`,
                    }}
                />

                <div className="relative z-10 mx-auto max-w-4xl px-6 text-center">
                    <div className="space-y-8">
                        <div className="space-y-4">
                            <p
                                className={`text-lg font-light tracking-wide text-gray-300 md:text-xl ${visibleElements.has("hero-subtitle")
                                        ? "animate-fade-in delay-100"
                                        : "opacity-0"
                                    }`}
                                data-animate="hero-subtitle"
                            >
                                iPhone 16 Pro 사전예약
                            </p>
                            <h1
                                className={`text-4xl font-thin leading-tight tracking-tight text-white md:text-7xl ${visibleElements.has("hero-title")
                                        ? "animate-fade-in delay-200"
                                        : "opacity-0"
                                    }`}
                                data-animate="hero-title"
                            >
                                지금 바로
                                <br />
                                <span className="font-light">사전 예약 하세요.</span>
                            </h1>
                        </div>

                        {product ? (
                            <div className="py-8">
                                <div
                                    className={`inline-block rounded-2xl border border-white/10 bg-white/5 px-8 py-6 backdrop-blur-sm ${visibleElements.has("product-card")
                                            ? "animate-scale-up delay-300"
                                            : "opacity-0"
                                        }`}
                                    data-animate="product-card"
                                >
                                    <p className="text-xl font-light text-white md:text-2xl">
                                        <span className="font-medium">{product.name}</span>
                                    </p>
                                    <p className="mt-2 text-lg text-gray-300 md:text-xl">
                                        {Number(product.price).toLocaleString()} 원
                                    </p>
                                </div>
                            </div>
                        ) : (
                            <p className="text-gray-400">No product available.</p>
                        )}

                        <div className="pt-8">
                            <button
                                onClick={handleBuy}
                                className={`rounded-full bg-blue-600 px-12 py-4 text-lg font-medium text-white shadow-xl transition-transform duration-300 hover:scale-105 hover:bg-blue-700 hover:shadow-[0_20px_40px_rgba(59,130,246,0.4),0_0_30px_rgba(59,130,246,0.3)] ${visibleElements.has("cta-button")
                                        ? "animate-fade-in delay-400"
                                        : "opacity-0"
                                    }`}
                                data-animate="cta-button"
                            >
                                사전예약 하기
                            </button>
                        </div>
                    </div>
                </div>

                {/* ✅ 스크롤 인디케이터 컴포넌트 */}
                <ScrollIndicator />
            </header>

            {/* Features Section */}
            <section className="relative z-10 bg-black py-24">
                <div className="mx-auto max-w-6xl px-6 space-y-32">
                    <IphoneFeature
                        title="디스플레이"
                        year="2024"
                        text="Super Retina XDR 디스플레이로 놀라운 색상과 명암비를 제공합니다."
                        img={black}
                        reverse={true}
                        animateKeys={{ text: "display-text", image: "display-image" }}
                    />

                    <IphoneFeature
                        title="카메라"
                        year="2024"
                        text="향상된 카메라 기능으로 저조도에서도 뛰어난 사진을 촬영할 수 있습니다."
                        img={camera}
                        animateKeys={{ text: "camera-text", image: "camera-image" }}
                    />

                    <IphoneFeature
                        title="배터리 수명"
                        year="2024"
                        text="더 긴 배터리 수명으로 하루 종일 지속됩니다."
                        img={better}
                        reverse={true}
                        animateKeys={{ text: "battery-text", image: "battery-image" }}
                    />

                    <IphoneFeature
                        title="프로세서"
                        year="2024"
                        text="강력한 A18 칩으로 뛰어난 성능을 경험하세요."
                        img={a18pro}
                        animateKeys={{ text: "processor-text", image: "processor-image" }}
                    />

                    <IphoneFeature
                        title="소프트웨어"
                        year="2024"
                        text="iOS 18로 최신 기능과 보안을 경험하세요."
                        img={ios18}
                        reverse={true}
                        animateKeys={{ text: "software-text", image: "software-image" }}
                    />
                </div>
            </section>
        </div>
    );
};

export default HomePage;
