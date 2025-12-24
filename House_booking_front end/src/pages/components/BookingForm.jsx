import React, { useState, useEffect } from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

const BookingForm = ({ 
  userId,
  roomId,
  checkInDate, 
  checkOutDate, 
  setCheckInDate, 
  setCheckOutDate, 
  disabledDates 
}) => {
  // const userId = 3; // TODO: Replace with actual logged-in user ID
  const [roomData, setRoomData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [selectedSharingType, setSelectedSharingType] = useState("Private Room");
  
  // Custom styles for exact color matches that don't exist in Tailwind
  const customStyles = {
    blueAccent: {
      color: "#00adef" // Exact blue color from original CSS
    },
    blueButton: {
      backgroundColor: "#00adef" // Exact blue color for button
    },
    blueSelectedOption: {
      backgroundColor: "#e0f7ff",
      color: "#0073e6"
    }
  };
  
  // Fetch room details when component mounts or roomId changes
  useEffect(() => {
    const fetchRoomDetails = async () => {
      if (!roomId) return;
      
      setLoading(true);
      try {
        const response = await fetch(`http://localhost:8080/rooms/${roomId}`);
        if (!response.ok) throw new Error("Room not found");
        const data = await response.json();
        setRoomData(data);
        
        // Set default sharing type based on max occupancy
        if (data.maxoccupancy === 1) {
          setSelectedSharingType("Private Room");
        }
      } catch (error) {
        console.error("Error fetching room details:", error);
      } finally {
        setLoading(false);
      }
    };
    
    fetchRoomDetails();
  }, [roomId]);
  
  const handleBooking = async () => {
    console.log("handleBooking function called");
    
    if (!checkInDate || !checkOutDate) {
      alert("Please select both check-in and check-out dates");
      return;
    }

    console.log("Check-in date:", checkInDate);
    console.log("Check-out date:", checkOutDate);
    
    // Validate roomId is available
    if (!roomId) {
      console.error("roomId is missing:", roomId);
      alert("Room ID is missing. Please try again.");
      return;
    }

    const bookingData = {
      userId: userId,
      roomId: parseInt(roomId),
      startDate: checkInDate.toISOString().split("T")[0],
      endDate: checkOutDate.toISOString().split("T")[0],
      roomType: roomData?.roomtype || "Standard Room",
      sharingType: selectedSharingType,
      status: "Pending"
    };
    
    console.log("Booking data to send:", bookingData);

    try {
      console.log("Sending fetch request to:", "http://localhost:8080/api/bookings/create");
      
      const res = await fetch("http://localhost:8080/api/bookings/create", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(bookingData)
      });
      
      console.log("Fetch response status:", res.status);
      
      const responseText = await res.text();
      console.log("Response text:", responseText);

      if (res.ok) {
        alert("Booking request submitted! Awaiting confirmation.");
        setCheckInDate(null);
        setCheckOutDate(null);
      } else {
        alert(`Booking failed: ${res.status} ${res.statusText}`);
      }
    } catch (error) {
      console.error("Error submitting booking:", error);
      alert(`Error submitting booking: ${error.message}`);
    }
  };

  const logAndHandleBooking = () => {
    console.log("Confirm button clicked");
    handleBooking();
  };

  // Calculate price based on room data
  const getPrice = () => {
    if (!roomData || !roomData.price) return "₹27650/month"; // Default price
    
    let price = roomData.price;
    
    // Apply multiplier based on sharing type
    if (selectedSharingType === "Two Sharing") {
      price = price * 0.6; // 60% of private room price
    } else if (selectedSharingType === "Three Sharing") {
      price = price * 0.45; // 45% of private room price
    }
    
    return `₹${price.toFixed(0)}/month`;
  };

  // Determine which sharing options to show based on max occupancy
  const getSharingOptions = () => {
    if (!roomData || !roomData.maxoccupancy) {
      return ["Private Room"]; // Default
    }
    
    const maxOccupancy = roomData.maxoccupancy;
    const options = [];
    
    if (maxOccupancy >= 1) options.push("Private Room");
    if (maxOccupancy >= 2) options.push("Two Sharing");
    if (maxOccupancy >= 3) options.push("Three Sharing");
    
    return options;
  };

  return (
    <div className="bg-white rounded-xl shadow-lg p-6 sticky top-6 max-w-sm">
      <h2 className="text-xl font-semibold mb-6 text-center">Book This Room</h2>
      
      {loading ? (
        <p className="text-center text-gray-600">Loading room details...</p>
      ) : (
        <>
          {/* Room Type Display */}
          <div className="mb-5">
            <p className="text-base font-medium mb-2">Room Type</p>
            <div className="flex justify-between items-center p-3 bg-gray-50 rounded-lg font-medium">
              <span>{roomData?.roomtype || "Standard Room"}</span>
              <span className="font-semibold" style={customStyles.blueAccent}>{getPrice()}</span>
            </div>
          </div>
          
          {/* Sharing Type Selection */}
          <div className="mb-5">
            <p className="text-base font-medium mb-2">Select Sharing Type</p>
            <div className="flex gap-2 mb-4">
              {getSharingOptions().map((option) => (
                <button 
                  key={option}
                  className="flex-1 py-2.5 px-2 rounded-lg text-sm cursor-pointer"
                  style={selectedSharingType === option ? customStyles.blueSelectedOption : {backgroundColor: "#f5f5f5"}}
                  onClick={() => setSelectedSharingType(option)}
                >
                  {option}
                </button>
              ))}
            </div>
          </div>
          
          <div className="mb-6">
            <div className="mb-4">
              <label className="block mb-1.5 text-sm font-medium">Check-in Date:</label>
              <DatePicker
                selected={checkInDate}
                onChange={date => {
                  console.log("Check-in date selected:", date);
                  setCheckInDate(date);
                }}
                excludeDateIntervals={disabledDates}
                minDate={new Date()}
                placeholderText="Select check-in date"
                className="w-full p-2.5 border border-gray-300 rounded-lg"
              />
            </div>

            <div className="mb-4">
              <label className="block mb-1.5 text-sm font-medium">Check-out Date:</label>
              <DatePicker
                selected={checkOutDate}
                onChange={date => {
                  console.log("Check-out date selected:", date);
                  setCheckOutDate(date);
                }}
                excludeDateIntervals={disabledDates}
                minDate={checkInDate || new Date()}
                placeholderText="Select check-out date"
                className="w-full p-2.5 border border-gray-300 rounded-lg"
              />
            </div>
          </div>

          <div className="flex gap-3">
            <button className="flex-1 py-3 bg-white border font-semibold text-xs rounded-md cursor-pointer" style={{borderColor: "#00adef", color: "#00adef"}}>
              CONTACT  OWNER
            </button>
            <button 
              className="flex-1 py-3 border-none text-white rounded-md cursor-pointer font-semibold text-xs"
              style={customStyles.blueButton}
              onClick={logAndHandleBooking}
            >
              CONFIRM DETAILS
            </button>
          </div>
          
          
        </>
      )}
    </div>
  );
};

export default BookingForm;