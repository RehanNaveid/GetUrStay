import React from "react";

const ImageCarousel = ({ imgUrl, currentIndex, handleNext, handlePrevious }) => {
  if (!imgUrl || imgUrl.length === 0) return null;

  return (
    <div className="w-full max-w-3xl mb-6 relative">
      <div className="relative w-full rounded-lg overflow-hidden">
        <img
          src={imgUrl[currentIndex]}
          alt={`Room Image ${currentIndex + 1}`}
          className="w-full h-auto block"
        />

        <button
          onClick={handlePrevious}
          disabled={imgUrl.length <= 1}
          className="absolute top-1/2 left-2.5 -translate-y-1/2 bg-black/40 border-none text-white py-2.5 px-3.5 text-base rounded-full cursor-pointer z-10 transition-colors duration-200 hover:bg-black/60 disabled:opacity-50 disabled:cursor-not-allowed"
        >
          ❮
        </button>

        <button
          onClick={handleNext}
          disabled={imgUrl.length <= 1}
          className="absolute top-1/2 right-2.5 -translate-y-1/2 bg-black/40 border-none text-white py-2.5 px-3.5 text-base rounded-full cursor-pointer z-10 transition-colors duration-200 hover:bg-black/60 disabled:opacity-50 disabled:cursor-not-allowed"
        >
          ❯
        </button>
        
        <div className="absolute bottom-4 w-full flex justify-center gap-2">
          {imgUrl.map((_, index) => (
            <span 
              key={index} 
              className={`w-2 h-2 rounded-full inline-block ${
                index === currentIndex ? "bg-white" : "bg-white/50"
              }`}
            />
          ))}
        </div>
      </div>
    </div>
  );
};

export default ImageCarousel;