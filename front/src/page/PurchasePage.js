"use client"

import { useState, useEffect, useRef } from "react"
import axios from "axios"

function PurchasePage() {
    const [email, setEmail] = useState("")
    const [address, setAddress] = useState("")
    const [number, setNumber] = useState("")
    const [name, setName] = useState("")
    const [isLoading, setIsLoading] = useState(false)

    const [isVisible, setIsVisible] = useState(false)
    const sectionRef = useRef(null)

    useEffect(() => {
        const observer = new IntersectionObserver(
            ([entry]) => {
                if (entry.isIntersecting) {
                    setIsVisible(true)
                }
            },
            { threshold: 0.1 },
        )

        if (sectionRef.current) {
            observer.observe(sectionRef.current)
        }

        return () => observer.disconnect()
    }, [])

    const handleBuy = async () => {
        setIsLoading(true)
        try {
            const response = await axios.post("http://localhost:8080/api/payment/ready", {
                email,
                address,
                number,
                name,
                fail_url: "http://localhost:3000/fail",
            })

            if (response.data && response.data.next_redirect_pc_url) {
                window.location.href = response.data.next_redirect_pc_url
            } else {
                throw new Error("Redirect URL not found in response.")
            }
        } catch (error) {
            console.error("결제 준비 실패:", error)
            alert("결제 준비 실패: " + (error.response?.data?.message || "서버 오류"))
        } finally {
            setIsLoading(false)
        }
    }

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
                                e.preventDefault()
                                handleBuy()
                            }}
                            className="space-y-8"
                        >
                            <div className="grid md:grid-cols-2 gap-8">
                                {/* Left Column */}
                                <div className="space-y-6">
                                    <div
                                        className={`transition-all duration-1000 ease-[cubic-bezier(0.25,0.46,0.45,0.94)] delay-600 ${isVisible ? "opacity-100 translate-y-0" : "opacity-0 translate-y-16"
                                            }`}
                                    >
                                        <label htmlFor="name" className="block text-sm font-medium text-gray-300 mb-2">
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
                                        className={`transition-all duration-1000 ease-[cubic-bezier(0.25,0.46,0.45,0.94)] delay-700 ${isVisible ? "opacity-100 translate-y-0" : "opacity-0 translate-y-16"
                                            }`}
                                    >
                                        <label htmlFor="email" className="block text-sm font-medium text-gray-300 mb-2">
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
                                        className={`transition-all duration-1000 ease-[cubic-bezier(0.25,0.46,0.45,0.94)] delay-800 ${isVisible ? "opacity-100 translate-y-0" : "opacity-0 translate-y-16"
                                            }`}
                                    >
                                        <label htmlFor="phone" className="block text-sm font-medium text-gray-300 mb-2">
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
                                    className={`transition-all duration-1000 ease-[cubic-bezier(0.25,0.46,0.45,0.94)] delay-900 ${isVisible ? "opacity-100 translate-y-0" : "opacity-0 translate-y-16"
                                        }`}
                                >
                                    <label htmlFor="address" className="block text-sm font-medium text-gray-300 mb-2">
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
                                className={`text-center pt-8 transition-all duration-1000 ease-[cubic-bezier(0.25,0.46,0.45,0.94)] delay-1000 ${isVisible ? "opacity-100 translate-y-0" : "opacity-0 translate-y-16"
                                    }`}
                            >
                                <button
                                    className="group relative px-12 py-4 bg-gradient-to-r from-blue-600 to-blue-700 text-white font-medium rounded-full transition-all duration-300 hover:from-blue-700 hover:to-blue-800 hover:scale-105 hover:shadow-2xl hover:shadow-blue-500/25 focus:outline-none focus:ring-4 focus:ring-blue-500/20 disabled:opacity-50 disabled:cursor-not-allowed disabled:hover:scale-100"
                                    type="submit"
                                    disabled={isLoading}
                                >
                                    <span className="relative z-10 text-lg">{isLoading ? "처리 중..." : "iPhone 16 Pro 구매하기"}</span>
                                    <div className="absolute inset-0 rounded-full bg-white/20 opacity-0 group-hover:opacity-100 transition-opacity duration-300"></div>
                                </button>

                                <p className="text-sm text-gray-400 mt-4 font-light">안전한 결제 시스템으로 보호됩니다</p>
                            </div>
                        </form>
                    </div>

                    {/* Product Info */}
                    <div
                        className={`mt-16 text-center transition-all duration-1000 ease-[cubic-bezier(0.25,0.46,0.45,0.94)] delay-1200 ${isVisible ? "opacity-100 translate-y-0" : "opacity-0 translate-y-16"
                            }`}
                    >
                        <div className="grid md:grid-cols-3 gap-8 max-w-2xl mx-auto">
                            <div className="text-center">
                                <div className="w-12 h-12 bg-blue-900/50 rounded-full flex items-center justify-center mx-auto mb-3">
                                    <svg className="w-6 h-6 text-blue-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path
                                            strokeLinecap="round"
                                            strokeLinejoin="round"
                                            strokeWidth={2}
                                            d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z"
                                        />
                                    </svg>
                                </div>
                                <h3 className="font-medium text-white mb-1">안전한 결제</h3>
                                <p className="text-sm text-gray-400">암호화된 보안 결제</p>
                            </div>

                            <div className="text-center">
                                <div className="w-12 h-12 bg-green-900/50 rounded-full flex items-center justify-center mx-auto mb-3">
                                    <svg className="w-6 h-6 text-green-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path
                                            strokeLinecap="round"
                                            strokeLinejoin="round"
                                            strokeWidth={2}
                                            d="M5 8h14M5 8a2 2 0 110-4h1.586a1 1 0 01.707.293l1.414 1.414a1 1 0 00.707.293H19a2 2 0 110 4M5 8v10a2 2 0 002 2h10a2 2 0 002-2V8m-9 4h4"
                                        />
                                    </svg>
                                </div>
                                <h3 className="font-medium text-white mb-1">빠른 배송</h3>
                                <p className="text-sm text-gray-400">출시일 당일 배송</p>
                            </div>

                            <div className="text-center">
                                <div className="w-12 h-12 bg-purple-900/50 rounded-full flex items-center justify-center mx-auto mb-3">
                                    <svg className="w-6 h-6 text-purple-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path
                                            strokeLinecap="round"
                                            strokeLinejoin="round"
                                            strokeWidth={2}
                                            d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"
                                        />
                                    </svg>
                                </div>
                                <h3 className="font-medium text-white mb-1">품질 보증</h3>
                                <p className="text-sm text-gray-400">1년 무상 A/S</p>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    )
}

export default PurchasePage
