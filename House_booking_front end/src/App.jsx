import React from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import BookingPage from "./pages/BookingPage";

const App = () => {
  return(
    <div>
      <Router>
        <Routes>
          <Route path="/" element={<Navigate to="/book/1" />} />
          <Route path="/book/:roomId" element={<BookingPage/>} />
        </Routes>
      </Router>
    </div>
  )
};

export default App;