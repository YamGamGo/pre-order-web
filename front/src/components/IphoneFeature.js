// src/components/IphoneFeature.js
export default function IphoneFeature({
  title,
  year,
  text,
  img,
  reverse = false,
  animateKeys
}) {
  return (
    <div className={`flex flex-col items-center gap-16 lg:flex-row ${reverse ? "lg:flex-row-reverse" : ""}`}>
      {/* 텍스트 영역 */}
      <div
        className={`flex-1 animate-slide-left animate-delay-100 ${animateKeys.text}`}
        data-animate={animateKeys.text}
      >
        <div className="space-y-6">
          <div className="space-y-2">
            <p className="text-sm font-medium uppercase tracking-wider text-blue-400">{year}</p>
            <h3 className="feature-title text-2xl font-light text-white md:text-3xl">{title}</h3>
          </div>
          <p className="feature-text text-base leading-relaxed font-light text-gray-300 md:text-lg">
            {text}
          </p>
        </div>
      </div>

      {/* 이미지 영역 */}
      <div
        className={`flex-1 animate-slide-right animate-delay-200 ${animateKeys.image}`}
        data-animate={animateKeys.image}
      >
        <img
          className="image-hover h-64 w-64 rounded-3xl object-cover shadow-2xl transition-transform duration-500 hover:scale-110"
          src={img}
          alt={title}
        />
      </div>
    </div>
  )
}
