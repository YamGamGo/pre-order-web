"use client"

import { useEffect, useState } from "react"
import { useNavigate, useLocation } from "react-router-dom"
import api from "../api/axiosInstance" // axios 인스턴스 (withCredentials: true 포함)

const useScrollAnimation = () => {
    const [visibleElements, setVisibleElements] = useState(new Set())

    useEffect(() => {
        const observer = new IntersectionObserver(
            (entries) => {
                entries.forEach((entry) => {
                    if (entry.isIntersecting) {
                        setVisibleElements((prev) => new Set([...prev, entry.target.dataset.animate]))
                    }
                })
            },
            { threshold: 0.1, rootMargin: "0px 0px -50px 0px" },
        )

        const elements = document.querySelectorAll("[data-animate]")
        elements.forEach((el) => observer.observe(el))

        return () => observer.disconnect()
    }, [])

    return visibleElements
}

const useParallax = () => {
    const [scrollY, setScrollY] = useState(0)

    useEffect(() => {
        const handleScroll = () => setScrollY(window.scrollY)
        window.addEventListener("scroll", handleScroll)
        return () => window.removeEventListener("scroll", handleScroll)
    }, [])

    return scrollY
}

const Homepage = () => {
    const [product, setProduct] = useState(null)
    const [me, setMe] = useState(null)
    const navigate = useNavigate()
    const location = useLocation()
    const visibleElements = useScrollAnimation()
    const scrollY = useParallax() // Added parallax scroll tracking

    const fetchMe = async () => {
        try {
            const res = await api.get("/api/kakao/me", { withCredentials: true })
            setMe(res.data)
        } catch (err) {
            console.error(err)
            setMe({ loggedIn: false, nickname: null, profileImage: null })
        }
    }

    useEffect(() => {
        api
            .get("/api/information", { withCredentials: true })
            .then((res) => setProduct(res.data))
            .catch((err) => console.error(err))

        fetchMe()
    }, [location])

    const handleBuy = () => {
        navigate("/reservation/1")
    }

    const navigateToKakaoLogin = () => {
        window.location.href = "/oauth/kakao/login"
    }

    const handleLogout = async () => {
        try {
            await api.post("/api/kakao/logout", null, { withCredentials: true })
        } catch (e) {
            console.error(e)
        } finally {
            setMe({ loggedIn: false, nickname: null, profileImage: null })
        }
    }

    const renderRightTop = () => {
        if (!me) return null

        if (!me.loggedIn) {
            return (
                <button
                    onClick={navigateToKakaoLogin}
                    className="btn-kakao fixed top-6 right-6 bg-yellow-400 hover:bg-yellow-500 text-black font-semibold px-6 py-3 rounded-full z-50 shadow-lg"
                >
                    카카오로 로그인하기
                </button>
            )
        }

        return (
            <div className="fixed top-6 right-6 flex items-center gap-4 z-50">
                {me.profileImage && (
                    <img
                        src={me.profileImage || "/placeholder.svg"}
                        alt="프로필"
                        className="w-10 h-10 rounded-full border-2 border-white/20"
                    />
                )}
                <span className="text-white font-medium text-sm">{me.nickname ?? "회원"}님 환영합니다.</span>
                <button
                    onClick={handleLogout}
                    className="bg-transparent hover:bg-white/10 text-white border border-white/30 hover:border-white/50 rounded-full px-4 py-2 text-sm transition-all duration-200"
                >
                    로그아웃
                </button>
            </div>
        )
    }

    return (
        <div className="min-h-screen bg-black text-white">
            <style>{`
        .animate-fade-in {
           opacity: 0;
            transform: translateY(60px);
          transition: all 1s cubic-bezier(0.25, 0.46, 0.45, 0.94);
        }
        .animate-fade-in.visible {
          opacity: 1;
          transform: translateY(0);
        }
        .animate-slide-left {
          opacity: 0;
          transform: translateY(60px) translateX(-30px);
          transition: all 1s cubic-bezier(0.25, 0.46, 0.45, 0.94);
        }
        .animate-slide-left.visible {
          opacity: 1;
          transform: translateY(0) translateX(0);
        }
        .animate-slide-right {
          opacity: 0;
          transform: translateY(60px) translateX(30px);
          transition: all 1s cubic-bezier(0.25, 0.46, 0.45, 0.94);
        }
        .animate-slide-right.visible {
          opacity: 1;
          transform: translateY(0) translateX(0);
        }
        .animate-scale {
          opacity: 0;
          transform: translateY(40px) scale(0.95);
          transition: all 1s cubic-bezier(0.25, 0.46, 0.45, 0.94);
        }
        .animate-scale.visible {
          opacity: 1;
          transform: translateY(0) scale(1);
        }
        
        .animate-delay-100 {
          transition-delay: 0.1s;
        }
        .animate-delay-200 {
          transition-delay: 0.2s;
        }
        .animate-delay-300 {
          transition-delay: 0.3s;
        }
        .animate-delay-400 {
          transition-delay: 0.4s;
        }
        .animate-delay-500 {
          transition-delay: 0.5s;
        }

        .btn-premium {
          transition: all 0.3s cubic-bezier(0.25, 0.46, 0.45, 0.94);
        }
        .btn-premium:hover {
          transform: scale(1.05);
          box-shadow: 0 20px 40px rgba(59, 130, 246, 0.4), 0 0 30px rgba(59, 130, 246, 0.3);
        }
        
        .btn-kakao {
          transition: all 0.3s cubic-bezier(0.25, 0.46, 0.45, 0.94);
        }
        .btn-kakao:hover {
          transform: scale(1.05);
          box-shadow: 0 15px 30px rgba(251, 191, 36, 0.4), 0 0 25px rgba(251, 191, 36, 0.2);
        }

        .image-hover {
          transition: all 0.4s cubic-bezier(0.25, 0.46, 0.45, 0.94);
        }
        .image-hover:hover {
          transform: scale(1.08);
          box-shadow: 0 30px 60px rgba(0, 0, 0, 0.6), 0 0 40px rgba(255, 255, 255, 0.1);
        }

        .scroll-indicator {
          animation: bounce 2s infinite;
        }
        @keyframes bounce {
          0%, 20%, 50%, 80%, 100% {
            transform: translateY(0);
          }
          40% {
            transform: translateY(-10px);
          }
          60% {
            transform: translateY(-5px);
          }
        }

        @media (max-width: 768px) {
          .hero-title {
            font-size: 2.5rem;
            line-height: 1.1;
          }
          .hero-subtitle {
            font-size: 1rem;
          }
          .section-title {
            font-size: 2rem;
          }
          .feature-title {
            font-size: 1.75rem;
          }
          .feature-text {
            font-size: 1rem;
          }
        }
      `}</style>

            {renderRightTop()}

            <header
                className="relative min-h-screen flex items-center justify-center bg-gradient-to-b from-gray-900 to-black overflow-hidden"
                style={{
                    transform: `translateY(${scrollY * 0.5}px)`,
                }}
            >
                <div
                    className="absolute inset-0 opacity-10"
                    style={{
                        backgroundImage: "radial-gradient(circle at 25% 25%, rgba(59, 130, 246, 0.1) 0%, transparent 50%)",
                        transform: `translateY(${scrollY * 0.3}px)`,
                    }}
                />

                <div className="max-w-4xl mx-auto px-6 text-center relative z-10">
                    <div className="space-y-8">
                        <div className="space-y-4">
                            <p
                                className={`hero-subtitle text-lg md:text-xl font-light text-gray-300 tracking-wide animate-fade-in animate-delay-100 ${visibleElements.has("hero-subtitle") ? "visible" : ""}`}
                                data-animate="hero-subtitle"
                            >
                                iPhone 16 Pro 사전예약
                            </p>
                            <h1
                                className={`hero-title text-4xl md:text-7xl font-thin tracking-tight text-white leading-tight animate-fade-in animate-delay-200 ${visibleElements.has("hero-title") ? "visible" : ""}`}
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
                                    className={`inline-block bg-white/5 backdrop-blur-sm rounded-2xl px-8 py-6 border border-white/10 animate-scale animate-delay-300 ${visibleElements.has("product-card") ? "visible" : ""}`}
                                    data-animate="product-card"
                                >
                                    <p className="text-xl md:text-2xl font-light text-white">
                                        <span className="font-medium">{product.name}</span>
                                    </p>
                                    <p className="text-lg md:text-xl text-gray-300 mt-2">{Number(product.price).toLocaleString()} 원</p>
                                </div>
                            </div>
                        ) : (
                            <p className="text-gray-400">No product available.</p>
                        )}

                        <div className="pt-8">
                            <button
                                onClick={handleBuy}
                                className={`btn-premium bg-blue-600 hover:bg-blue-700 text-white font-medium px-12 py-4 rounded-full text-lg shadow-xl animate-fade-in animate-delay-400 ${visibleElements.has("cta-button") ? "visible" : ""}`}
                                data-animate="cta-button"
                            >
                                사전예약 하기
                            </button>
                        </div>
                    </div>
                </div>

                <div className="absolute bottom-8 left-1/2 transform -translate-x-1/2">
                    <div className="scroll-indicator text-white/60 text-center">
                        <div className="w-6 h-10 border-2 border-white/30 rounded-full flex justify-center">
                            <div className="w-1 h-3 bg-white/60 rounded-full mt-2 animate-pulse"></div>
                        </div>
                        <p className="text-xs mt-2 font-light">스크롤</p>
                    </div>
                </div>
            </header>

            <section className="py-24 bg-black relative z-10">
                <div className="max-w-6xl mx-auto px-6">
                    <div
                        className={`text-center mb-20 animate-fade-in animate-delay-100 ${visibleElements.has("section-header") ? "visible" : ""}`}
                        data-animate="section-header"
                    >
                        <h2 className="section-title text-3xl md:text-5xl font-thin text-white mb-4">아이폰 16 Pro</h2>
                        <p className="text-lg md:text-xl text-gray-400 font-light">혁신적인 기술의 완성</p>
                    </div>

                    <div className="space-y-32">
                        {/* Display Feature */}
                        <div className="flex flex-col lg:flex-row items-center gap-16">
                            <div
                                className={`flex-1 order-2 lg:order-1 animate-slide-left animate-delay-100 ${visibleElements.has("display-text") ? "visible" : ""}`}
                                data-animate="display-text"
                            >
                                <div className="space-y-6">
                                    <div className="space-y-2">
                                        <p className="text-sm font-medium text-blue-400 tracking-wider uppercase">2024</p>
                                        <h3 className="feature-title text-2xl md:text-3xl font-light text-white">디스플레이</h3>
                                    </div>
                                    <p className="feature-text text-base md:text-lg text-gray-300 leading-relaxed font-light">
                                        Super Retina XDR 디스플레이로 놀라운 색상과 명암비를 제공합니다.
                                    </p>
                                </div>
                            </div>
                            <div
                                className={`flex-1 order-1 lg:order-2 animate-slide-right animate-delay-200 ${visibleElements.has("display-image") ? "visible" : ""}`}
                                data-animate="display-image"
                            >
                                <div className="relative">
                                    <img
                                        className="image-hover w-64 h-64 object-cover rounded-3xl shadow-2xl"
                                        src={require("../assets/img/black.jpg") || "/placeholder.svg"}
                                        alt="디스플레이"
                                    />
                                </div>
                            </div>
                        </div>

                        {/* Camera Feature */}
                        <div className="flex flex-col lg:flex-row items-center gap-16">
                            <div
                                className={`flex-1 animate-slide-left animate-delay-100 ${visibleElements.has("camera-image") ? "visible" : ""}`}
                                data-animate="camera-image"
                            >
                                <div className="relative">
                                    <img
                                        className="image-hover w-64 h-64 object-cover rounded-3xl shadow-2xl"
                                        src={require("../assets/img/camera.jpg") || "/placeholder.svg"}
                                        alt="카메라"
                                    />
                                </div>
                            </div>
                            <div
                                className={`flex-1 animate-slide-right animate-delay-200 ${visibleElements.has("camera-text") ? "visible" : ""}`}
                                data-animate="camera-text"
                            >
                                <div className="space-y-6">
                                    <div className="space-y-2">
                                        <p className="text-sm font-medium text-blue-400 tracking-wider uppercase">2024</p>
                                        <h3 className="feature-title text-2xl md:text-3xl font-light text-white">카메라</h3>
                                    </div>
                                    <p className="feature-text text-base md:text-lg text-gray-300 leading-relaxed font-light">
                                        향상된 카메라 기능으로 저조도에서도 뛰어난 사진을 촬영할 수 있습니다.
                                    </p>
                                </div>
                            </div>
                        </div>

                        {/* Battery Feature */}
                        <div className="flex flex-col lg:flex-row items-center gap-16">
                            <div
                                className={`flex-1 order-2 lg:order-1 animate-slide-left animate-delay-100 ${visibleElements.has("battery-text") ? "visible" : ""}`}
                                data-animate="battery-text"
                            >
                                <div className="space-y-6">
                                    <div className="space-y-2">
                                        <p className="text-sm font-medium text-blue-400 tracking-wider uppercase">2024</p>
                                        <h3 className="feature-title text-2xl md:text-3xl font-light text-white">배터리 수명</h3>
                                    </div>
                                    <p className="feature-text text-base md:text-lg text-gray-300 leading-relaxed font-light">
                                        더 긴 배터리 수명으로 하루 종일 지속됩니다.
                                    </p>
                                </div>
                            </div>
                            <div
                                className={`flex-1 order-1 lg:order-2 animate-slide-right animate-delay-200 ${visibleElements.has("battery-image") ? "visible" : ""}`}
                                data-animate="battery-image"
                            >
                                <div className="relative">
                                    <img
                                        className="image-hover w-64 h-64 object-cover rounded-3xl shadow-2xl"
                                        src={require("../assets/img/better.jpg") || "/placeholder.svg"}
                                        alt="배터리 수명"
                                    />
                                </div>
                            </div>
                        </div>

                        {/* Processor Feature */}
                        <div className="flex flex-col lg:flex-row items-center gap-16">
                            <div
                                className={`flex-1 animate-slide-left animate-delay-100 ${visibleElements.has("processor-image") ? "visible" : ""}`}
                                data-animate="processor-image"
                            >
                                <div className="relative">
                                    <img
                                        className="image-hover w-64 h-64 object-cover rounded-3xl shadow-2xl"
                                        src={require("../assets/img/a18pro.jpg") || "/placeholder.svg"}
                                        alt="프로세서"
                                    />
                                </div>
                            </div>
                            <div
                                className={`flex-1 animate-slide-right animate-delay-200 ${visibleElements.has("processor-text") ? "visible" : ""}`}
                                data-animate="processor-text"
                            >
                                <div className="space-y-6">
                                    <div className="space-y-2">
                                        <p className="text-sm font-medium text-blue-400 tracking-wider uppercase">2024</p>
                                        <h3 className="feature-title text-2xl md:text-3xl font-light text-white">프로세서</h3>
                                    </div>
                                    <p className="feature-text text-base md:text-lg text-gray-300 leading-relaxed font-light">
                                        강력한 A18 칩으로 뛰어난 성능을 경험하세요.
                                    </p>
                                </div>
                            </div>
                        </div>

                        {/* Software Feature */}
                        <div className="flex flex-col lg:flex-row items-center gap-16">
                            <div
                                className={`flex-1 order-2 lg:order-1 animate-slide-left animate-delay-100 ${visibleElements.has("software-text") ? "visible" : ""}`}
                                data-animate="software-text"
                            >
                                <div className="space-y-6">
                                    <div className="space-y-2">
                                        <p className="text-sm font-medium text-blue-400 tracking-wider uppercase">2024</p>
                                        <h3 className="feature-title text-2xl md:text-3xl font-light text-white">소프트웨어</h3>
                                    </div>
                                    <p className="feature-text text-base md:text-lg text-gray-300 leading-relaxed font-light">
                                        iOS 18로 최신 기능과 보안을 경험하세요.
                                    </p>
                                </div>
                            </div>
                            <div
                                className={`flex-1 order-1 lg:order-2 animate-slide-right animate-delay-200 ${visibleElements.has("software-image") ? "visible" : ""}`}
                                data-animate="software-image"
                            >
                                <div className="relative">
                                    <img
                                        className="image-hover w-64 h-64 object-cover rounded-3xl shadow-2xl"
                                        src={require("../assets/img/ios18.jpg") || "/placeholder.svg"}
                                        alt="소프트웨어"
                                    />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    )
}

export default Homepage
