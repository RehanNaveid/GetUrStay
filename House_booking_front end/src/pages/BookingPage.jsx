import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import "react-datepicker/dist/react-datepicker.css";
import RoomDetails from './components/RoomDetails';
import ImageCarousel from './components/ImageCarousel';
import BookingForm from './components/BookingForm';
// We'll uncomment the ReviewSection once it's fixed
import ReviewSection from "./components/ReviewSecton";
import './BookingPage.css'

const BookingPage = () => {
  const { roomId } = useParams();
  const [roomData, setRoomData] = useState(null);
  const [bookedDates, setBookedDates] = useState([]);
  const [disabledDates, setDisabledDates] = useState([]);
  const [imgUrl, setImgUrl] = useState([]);
  const [currentIndex, setCurrentIndex] = useState(0);
  const [checkInDate, setCheckInDate] = useState(null);
  const [checkOutDate, setCheckOutDate] = useState(null);

  const currentuserid=window.localStorage.getItem('userid');

  useEffect(() => {
    const fetchRoomImages = async () => {
      try {
        const imgResponse = await fetch(`http://localhost:8080/images/room/${roomId}`);
        const data = await imgResponse.json();
        const imgUrl = data.map(imgJSON => imgJSON.imgUrl);
        setImgUrl(imgUrl);
      } catch (error) {
        console.error("Error fetching images:", error);
      }
    };
    fetchRoomImages();
  }, [roomId]);

  useEffect(() => {
    const fetchBookedDates = async () => {
      try {
        const res = await fetch(`http://localhost:8080/api/bookings/getAll`);
        const allbooking = await res.json();
        const filtered = allbooking.filter(b => b.roomId === parseInt(roomId) && b.status === "CONFIRMED");
        setBookedDates(filtered);
      } catch (error) {
        console.error("Error fetching previous booking", error);
      }
    };
    fetchBookedDates();
  }, [roomId]);

  useEffect(() => {
    if (!bookedDates.length) return;
    const dateIntervals = bookedDates.map(b => ({
      start: new Date(b.startDate),
      end: new Date(b.endDate)
    }));
    setDisabledDates(dateIntervals);
  }, [bookedDates]);

  useEffect(() => {
    const fetchRoomDetails = async () => {
      try {
        const res = await fetch(`http://localhost:8080/rooms/${roomId}`);
        if (!res.ok) throw new Error("Room not found");
        const data = await res.json();
        setRoomData(data);
      } catch (error) {
        console.error("Error fetching the room", error);
      }
    };
    fetchRoomDetails();
  }, [roomId]);

  const handleCheckInChange = (date) => setCheckInDate(date);
  const handleCheckOutChange = (date) => {
    setCheckOutDate(date);
    if (!checkOutDate || checkOutDate < date) setCheckOutDate(date);
  };

  const handleNext = () => setCurrentIndex((prev) => (prev + 1) % imgUrl.length);
  const handlePrevious = () => setCurrentIndex((prev) => (prev - 1 + imgUrl.length) % imgUrl.length);

  return (
    <div style={styles.pageContainer}>
      <div style={styles.mainContent}>
        <h2 style={styles.pageTitle}>
          {roomData ? roomData.name || `Room ${roomId}` : `Room ${roomId}`}
        </h2>
        <div style={styles.location}>
          {roomData && roomData.location ? roomData.location : 'Location details not available'}
        </div>
        
        <ImageCarousel
          imgUrl={imgUrl}
          currentIndex={currentIndex}
          handleNext={handleNext}
          handlePrevious={handlePrevious}
        />

        <RoomDetails roomId={roomId} />
        
        {/* Uncomment when ReviewSection is fixed */}
        <ReviewSection
          roomId={parseInt(roomId)}
          currentUserId={currentuserid} ///TODO GET USERID FROM LOCAL STORAGE
        /> 
      </div>
      
      <div style={styles.sideContent}>
        <BookingForm
          userId={currentuserid}
          roomId={roomId}
          checkInDate={checkInDate}
          checkOutDate={checkOutDate}
          setCheckInDate={handleCheckInChange}
          setCheckOutDate={handleCheckOutChange}
          disabledDates={disabledDates}
        />
      </div>
    </div>
  );
};

const styles = {
  pageContainer: {
    display: 'flex',
    padding: '24px',
    maxWidth: '1200px',
    margin: '0 auto',
    gap: '32px',
  },
  mainContent: {
    flex: '1',
    marginRight: '24px',
  },
  sideContent: {
    width: '350px',
  },
  pageTitle: {
    fontSize: '24px',
    fontWeight: 'bold',
    marginBottom: '8px',
  },
  location: {
    color: '#666',
    marginBottom: '16px',
  }
};

export default BookingPage;