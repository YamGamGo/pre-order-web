"use client";
import { useState, useEffect, useRef } from "react";
import { readyPayment } from "../api/paymentService"; 

function PurchasePage() {
    const [email, setEmail] = useState("");
    const [address, setAddress] = useState("");
    const [number, setNumber] = useState("");
    const [name, setName] = useState("");
    const [isLoading, setIsLoading] = useState(false);

    const [isVisible, setIsVisible] = useState(false);
    const sectionRef = useRef(null);

    useEffect(() => {
        const observer = new IntersectionObserver(
            ([entry]) => {
                if (entry.isIntersecting) {
                    setIsVisible(true);
                }
            },
            { threshold: 0.1 }
        );

        if (sectionRef.current) {
            observer.observe(sectionRef.current);
        }

        return () => observer.disconnect();
    }, []);

    // ✅ 결제 요청 함수 (service에서 가져옴)
    const handleBuy = async () => {
        setIsLoading(true);
        try {
            const response = await readyPayment({
                email,
                address,
                number,
                name,
                fail_url: "http://localhost:3000/fail",
            });

            if (response && response.next_redirect_pc_url) {
                window.location.href = response.next_redirect_pc_url;
            } else {
                throw new Error("Redirect URL not found in response.");
            }
        } catch (error) {
            console.error("❌ 결제 준비 실패:", error);
            alert("결제 준비 실패: " + (error.response?.data?.message || "서버 오류"));
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <div className="min-h-screen bg-black text-white">
            <section
                ref={sectionRef}
                className={`py-20 px-4 transition-all duration-1000 ease-[cubic-bezier(0.25,0.46,0.45,0.94)] ${isVisible ? "opacity-100 translate-y-0" : "opacity-0 translate-y-16"
                    }`}
            >
                <div className="max-w-4xl mx-auto">
                    {/* Header */}
                    <div
                        className={`text-center mb-16 transition-all duration-1000 ease-[cubic-bezier(0.25,0.46,0.45,0.94)] delay-200 ${isVisible ? "opacity-100 translate-y-0" : "opacity-0 translate-y-16"
                            }`}
                    >
                        <h1 className="text-4xl md:text-5xl lg:text-6xl font-light text-white mb-4 tracking-tight">
                            iPhone 16 Pro
                        </h1>
                        <p className="text-xl md:text-2xl text-gray-300 font-light">사전구매 예약</p>
                        <div className="w-12 h-0.5 bg-white mx-auto mt-8"></div>
                    </div>

                    {/* Form Card */}
                    <div
                        className={`bg-gray-900/80 backdrop-blur-xl rounded-3xl shadow-2xl border border-gray-700 p-8 md:p-12 transition-all duration-1000 ease-[cubic-bezier(0.25,0.46,0.45,0.94)] delay-400 hover:shadow-3xl hover:scale-[1.02] ${isVisible ? "opacity-100 translate-y-0" : "opacity-0 translate-y-16"
                            }`}
                    >
                        <form
                            onSubmit={(e) => {
                                e.preventDefault();
                                handleBuy();
                            }}
                            className="space-y-8"
                        >
                            <div className="grid md:grid-cols-2 gap-8">
                                {/* Left Column */}
                                <div className="space-y-6">
                                    <div
                                        className={`transition-all duration-1000 delay-600 ${isVisible ? "opacity-100 translate-y-0" : "opacity-0 translate-y-16"
                                            }`}
                                    >
                                        <label
                                            htmlFor="name"
                                            className="block text-sm font-medium text-gray-300 mb-2"
                                        >
                                            이름
                                        </label>
                                        <input
                                            className="w-full px-4 py-4 bg-gray-800/50 border border-gray-600 rounded-2xl focus:outline-none focus:ring-2 focus:ring-blue-500/20 focus:border-blue-500 transition-all duration-300 text-white placeholder-gray-400 hover:bg-gray-800"
                                            id="name"
                                            type="text"
                                            placeholder="성함을 입력해주세요"
                                            value={name}
                                            onChange={(e) => setName(e.target.value)}
                                            required
                                        />
                                    </div>
                                    <div
                                        className={`transition-all duration-1000 delay-700 ${isVisible ? "opacity-100 translate-y-0" : "opacity-0 translate-y-16"
                                            }`}
                                    >
                                        <label
                                            htmlFor="email"
                                            className="block text-sm font-medium text-gray-300 mb-2"
                                        >
                                            이메일
                                        </label>
                                        <input
                                            className="w-full px-4 py-4 bg-gray-800/50 border border-gray-600 rounded-2xl focus:outline-none focus:ring-2 focus:ring-blue-500/20 focus:border-blue-500 transition-all duration-300 text-white placeholder-gray-400 hover:bg-gray-800"
                                            id="email"
                                            type="email"
                                            placeholder="이메일을 입력해주세요"
                                            value={email}
                                            onChange={(e) => setEmail(e.target.value)}
                                            required
                                        />
                                    </div>
                                    <div
                                        className={`transition-all duration-1000 delay-800 ${isVisible ? "opacity-100 translate-y-0" : "opacity-0 translate-y-16"
                                            }`}
                                    >
                                        <label
                                            htmlFor="phone"
                                            className="block text-sm font-medium text-gray-300 mb-2"
                                        >
                                            전화번호
                                        </label>
                                        <input
                                            className="w-full px-4 py-4 bg-gray-800/50 border border-gray-600 rounded-2xl focus:outline-none focus:ring-2 focus:ring-blue-500/20 focus:border-blue-500 transition-all duration-300 text-white placeholder-gray-400 hover:bg-gray-800"
                                            id="phone"
                                            type="tel"
                                            placeholder="전화번호를 입력해주세요"
                                            value={number}
                                            onChange={(e) => setNumber(e.target.value)}
                                            required
                                        />
                                    </div>
                                </div>

                                {/* Right Column */}
                                <div
                                    className={`transition-all duration-1000 delay-900 ${isVisible ? "opacity-100 translate-y-0" : "opacity-0 translate-y-16"
                                        }`}
                                >
                                    <label
                                        htmlFor="address"
                                        className="block text-sm font-medium text-gray-300 mb-2"
                                    >
                                        배송 주소
                                    </label>
                                    <textarea
                                        className="w-full h-48 px-4 py-4 bg-gray-800/50 border border-gray-600 rounded-2xl focus:outline-none focus:ring-2 focus:ring-blue-500/20 focus:border-blue-500 transition-all duration-300 text-white placeholder-gray-400 hover:bg-gray-800 resize-none"
                                        id="address"
                                        placeholder="배송받을 주소를 상세히 입력해주세요"
                                        value={address}
                                        onChange={(e) => setAddress(e.target.value)}
                                        required
                                    />
                                </div>
                            </div>

                            {/* Submit Button */}
                            <div
                                className={`text-center pt-8 transition-all duration-1000 delay-1000 ${isVisible ? "opacity-100 translate-y-0" : "opacity-0 translate-y-16"
                                    }`}
                            >
                                <button
                                    className="group relative px-12 py-4 bg-gradient-to-r from-blue-600 to-blue-700 text-white font-medium rounded-full transition-all duration-300 hover:from-blue-700 hover:to-blue-800 hover:scale-105 hover:shadow-2xl hover:shadow-blue-500/25 focus:outline-none focus:ring-4 focus:ring-blue-500/20 disabled:opacity-50 disabled:cursor-not-allowed disabled:hover:scale-100"
                                    type="submit"
                                    disabled={isLoading}
                                >
                                    <span className="relative z-10 text-lg">
                                        {isLoading ? "처리 중..." : "iPhone 16 Pro 구매하기"}
                                    </span>
                                    <div className="absolute inset-0 rounded-full bg-white/20 opacity-0 group-hover:opacity-100 transition-opacity duration-300"></div>
                                </button>
                                <p className="text-sm text-gray-400 mt-4 font-light">
                                    안전한 결제 시스템으로 보호됩니다
                                </p>
                            </div>
                        </form>
                    </div>
                </div>
            </section>
        </div>
    );
}

export default PurchasePage;
