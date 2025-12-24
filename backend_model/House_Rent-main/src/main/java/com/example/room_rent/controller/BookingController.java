package com.example.room_rent.controller;

import com.example.room_rent.dtos.BookingRequestDto;
import com.example.room_rent.enitity.Bookingentity;
import com.example.room_rent.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // Create a new booking
    @PostMapping("/create")
    public ResponseEntity<String> createBooking(@RequestBody BookingRequestDto bookingRequest) {
//        try {
//            return bookingService.createBooking(bookingRequest);
////            return ResponseEntity.status(HttpStatus.CREATED).body("Booking created successfully!");
//        } catch (Exception e) {
////            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating booking: " + e.getMessage());
//            return "error";
//        }`

        return bookingService.createBooking(bookingRequest);
    }

    // Confirm payment for a booking
    @PostMapping("/confirm/{bookingId}")
    public ResponseEntity<String> confirmPayment(@PathVariable int bookingId, @RequestParam boolean paymentStatus) {
        try {
            bookingService.confirmPayment(bookingId, paymentStatus);
            return ResponseEntity.status(HttpStatus.OK).body("Booking confirmed successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error confirming booking: " + e.getMessage());
        }
    }

    // Cancel a booking
    @DeleteMapping("/cancel/{bookingId}")
    public ResponseEntity<String> cancelBooking(@PathVariable int bookingId) {
//        try {
//            bookingService.CancelBooking(bookingId);
//            return ResponseEntity.status(HttpStatus.OK).body("Booking canceled successfully!");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found: " + e.getMessage());
//        }
        return bookingService.CancelBooking(bookingId);
    }

    // Get a booking by ID
    @GetMapping("/get/{bookingId}")
    public ResponseEntity<Bookingentity> getBookingById(@PathVariable int bookingId) {
        try {
            Bookingentity booking = bookingService.getBookingByID(bookingId);
            return ResponseEntity.status(HttpStatus.OK).body(booking);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Get all bookings
    @GetMapping("/getAll")
    public ResponseEntity<Iterable<Bookingentity>> getAllBookings() {
        try {
            Iterable<Bookingentity> bookings = bookingService.getAllBooking();
            return ResponseEntity.status(HttpStatus.OK).body(bookings);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{bookingId}/dates")
    public ResponseEntity<String> updateDate( @PathVariable("bookingId") int bookingId, @RequestBody BookingRequestDto dt) {
        return bookingService.updateDate(bookingId, dt);
    }
}
