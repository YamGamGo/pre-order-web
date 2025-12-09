// src/components/ScrollIndicator.js
export default function ScrollIndicator() {
    return (
        <div
            className="absolute bottom-8 left-1/2 -translate-x-1/2 transform"
            data-animate="scroll-indicator"
        >
            <div className="scroll-indicator text-center text-white/60">
                <div className="flex h-10 w-6 justify-center rounded-full border-2 border-white/30">
                    <div className="mt-2 h-3 w-1 animate-pulse rounded-full bg-white/60"></div>
                </div>
                <p className="mt-2 text-xs font-light">스크롤</p>
            </div>
        </div>
    );
}
