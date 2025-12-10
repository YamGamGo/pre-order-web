/** @type {import('tailwindcss').Config} */
module.exports = {
    content: [
        "./src/**/*.{js,jsx,ts,tsx}",
    ],
    theme: {
        extend: {
            keyframes: {
                fadeIn: {
                    "0%": { opacity: "0", transform: "translateY(60px)" },
                    "100%": { opacity: "1", transform: "translateY(0)" },
                },
                slideLeft: {
                    "0%": { opacity: "0", transform: "translateY(60px) translateX(-30px)" },
                    "100%": { opacity: "1", transform: "translateY(0) translateX(0)" },
                },
                slideRight: {
                    "0%": { opacity: "0", transform: "translateY(60px) translateX(30px)" },
                    "100%": { opacity: "1", transform: "translateY(0) translateX(0)" },
                },
                scaleUp: {
                    "0%": { opacity: "0", transform: "translateY(40px) scale(0.95)" },
                    "100%": { opacity: "1", transform: "translateY(0) scale(1)" },
                },
            },
            animation: {
                "fade-in": "fadeIn 1s cubic-bezier(0.25,0.46,0.45,0.94) forwards",
                "slide-left": "slideLeft 1s cubic-bezier(0.25,0.46,0.45,0.94) forwards",
                "slide-right": "slideRight 1s cubic-bezier(0.25,0.46,0.45,0.94) forwards",
                "scale-up": "scaleUp 1s cubic-bezier(0.25,0.46,0.45,0.94) forwards",
            },
            transitionDelay: {
                0: "0ms",
                100: "100ms",
                200: "200ms",
                300: "300ms",
                400: "400ms",
                500: "500ms",
                600: "600ms",
                700: "700ms",
                800: "800ms",
                900: "900ms",
                1000: "1000ms",
            },
        },
    },
    plugins: [],
}
