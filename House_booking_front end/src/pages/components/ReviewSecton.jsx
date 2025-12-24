import React, { useEffect, useState } from "react";
import axios from "axios";

const ReviewSection = ({ roomId, currentUserId }) => {
  const [reviews, setReviews] = useState([]);
  const [usernames, setUsernames] = useState({});
  const [comment, setComment] = useState("");
  const [rating, setRating] = useState(5);
  const [showAllReviews, setShowAllReviews] = useState(false); // State to manage showing all reviews

  // 1. Fetch reviews
  useEffect(() => {
    const fetchReviews = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/review/getreview/${roomId}`);
        const reviewsData = response.data;
        console.log("Fetched reviews:", reviewsData);
        setReviews(reviewsData);
      } catch (error) {
        console.log("Error fetching reviews:", error);
      }
    };

    fetchReviews();
  }, [roomId]);

  // 2. Fetch usernames after reviews are loaded
  useEffect(() => {
    const fetchUsernames = async () => {
      const userIds = [...new Set(reviews.map((review) => review.userid))];
      for (const userid of userIds) {
        if (!usernames[userid]) {
          try {
            const userResponse = await axios.get(`http://localhost:8080/users/${userid}`);
            console.log("Fetched user:", userResponse.data);
            setUsernames((prev) => ({
              ...prev,
              [userid]: userResponse.data.name,
            }));
          } catch (err) {
            console.error(`Error fetching username for ${userid}:`, err);
          }
        }
      }
    };

    if (reviews.length > 0) {
      fetchUsernames();
    }
  }, [reviews]);

  const handlePostReview = async (e) => {
    e.preventDefault();
  
    try {
      // Fetch all bookings for the current user
      const bookingRes = await axios.get(`http://localhost:8080/api/bookings/getAll`);
      
      // Log the bookings to check the status
      console.log("Fetched bookings:", bookingRes.data);
      
      // Find the confirmed booking for the specific room
      const booking = bookingRes.data.find(
        (b) => b.userId === currentUserId && b.roomId === roomId && b.status === 'Confirmed'
      );
  
      console.log("Booking status:", booking ? booking.status : "No booking found");
      
      if (!booking) {
        // If no confirmed booking is found for the current user and room, inform the user
        alert('You must complete a confirmed booking before posting a review.');
        return;
      }
      
      // Proceed with review posting logic
      const newReview = {
        roomId,
        userid: currentUserId,
        comment,
        rating,
        bookingId: booking.bookingId  // Use the bookingId from the confirmed booking
      };
  
      console.log("Posting review:", newReview);
      await axios.post("http://localhost:8080/api/review/post", newReview);
  
      // Clear form
      setComment("");
      setRating(5);
  
      // Refresh reviews
      const res = await axios.get(`http://localhost:8080/api/review/getreview/${roomId}`);
      setReviews(res.data);
  
    } catch (err) {
      console.error("Error posting review:", err);
    }
  };

  // Toggle between showing all reviews or just top 3 reviews
  const handleToggleReviews = () => {
    setShowAllReviews((prevState) => !prevState);
  };

  return (
    <div className="review-section bg-white p-6 rounded-lg shadow-lg max-w-3xl mx-auto mt-10">
      <h2 className="text-2xl font-semibold text-gray-800 mb-4">Reviews</h2>

      {reviews.length === 0 ? (
        <p className="text-gray-500">No reviews yet.</p>
      ) : (
        <div className="space-y-4">
          {/* Display top 3 reviews initially, or all if showAllReviews is true */}
          {reviews.slice(0, showAllReviews ? reviews.length : 3).map((review) => (
            <div key={review.rId} className="review-card p-4 bg-gray-100 rounded-lg shadow-sm">
              <h3 className="text-xl font-semibold text-gray-800">{usernames[review.userid] || "Loading..."}</h3>
              <p className="text-gray-600 mt-2">{review.comment}</p>
              <p className="text-yellow-500 mt-2"><b>Rating:</b> {review.rating}/5</p>
            </div>
          ))}

          {/* Show 'Show More' or 'Show Less' button */}
          {reviews.length > 3 && (
            <button
              onClick={handleToggleReviews}
              className="mt-4 bg-[#0073E6] text-white py-2 px-6 rounded-lg shadow-md hover:bg-[#005bb5] focus:outline-none focus:ring-2 focus:ring-[#0073E6] focus:ring-opacity-50 transition-all transform hover:scale-105"
            >
              {showAllReviews ? "Show Less Reviews" : "Show More Reviews"}
            </button>
          )}
        </div>
      )}

      {/* Post Review Form */}
      <form onSubmit={handlePostReview} className="mt-6">
        <h3 className="text-xl font-semibold text-gray-800 mb-2">Post a Review</h3>
        <textarea
          value={comment}
          onChange={(e) => setComment(e.target.value)}
          placeholder="Write your review..."
          required
          className="w-full p-4 border-2 border-gray-300 rounded-lg focus:outline-none focus:border-[#0073E6] resize-none"
        />
        <br />
        <div className="flex items-center mt-4">
          <input
            type="number"
            min="1"
            max="5"
            value={rating}
            onChange={(e) => setRating(e.target.value)}
            required
            className="w-16 p-2 border-2 border-gray-300 rounded-lg focus:outline-none focus:border-[#0073E6] mr-4"
          />
          <button
            type="submit"
            className="bg-[#0073E6] text-white py-2 px-6 rounded-lg shadow-md hover:bg-[#005bb5] focus:outline-none focus:ring-2 focus:ring-[#0073E6] focus:ring-opacity-50 transition-all transform hover:scale-105"
          >
            Submit Review
          </button>
        </div>
      </form>
    </div>
  );
};

export default ReviewSection;
