package com.example.room_rent.service;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;

import com.example.room_rent.dtos.BookingRequestDto;
import com.example.room_rent.dtos.Roomdto;
import com.example.room_rent.enitity.Bookingentity;
import com.example.room_rent.enitity.Roomentity;
import com.example.room_rent.enitity.Userentity;
import com.example.room_rent.repository.BookingRepository;
import com.example.room_rent.repository.Roomrepo;
import com.example.room_rent.repository.Userrepo;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookingService {
    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private Userrepo userRepo;

    @Autowired
    private Roomrepo roomRepo;

    private static final long EXPIRATION_TIME_MINUTES = 1;

    public ResponseEntity<String> createBooking(BookingRequestDto dt){
        Optional<Userentity> optUser= userRepo.findById(dt.getUserId());
        if(optUser.isEmpty() ){
            return new ResponseEntity<>("User not found",HttpStatus.NOT_FOUND);
        }
        Roomentity room = roomRepo.findRoomForUpdate(dt.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        if (room.getAvailability() != null && !room.getAvailability()) {
            return new ResponseEntity<>("Room not available", HttpStatus.BAD_REQUEST);
        }

        room.setAvailability(false);  // Mark the room as unavailable
        roomRepo.save(room);

        Userentity user=optUser.get();

        Bookingentity booking=new Bookingentity();
        booking.setUser(user);
        booking.setRoom(room);
        booking.setStatus("PENDING");
        booking.setBooking_date(LocalDate.now());
        booking.setStartdate(dt.getStartDate());
        booking.setEnddate(dt.getEndDate());
        booking.setCreatedAt(LocalDateTime.now());
        bookingRepo.save(booking);
        return ResponseEntity.ok("Booking initiated with ID: \" + booking.getBookingid() + \". Awaiting payment.\"");
    }

    public ResponseEntity<String> CancelBooking(int bookingId){
        Optional<Bookingentity> optBooking=bookingRepo.findById(bookingId);
        if(optBooking.isEmpty()){
            return new ResponseEntity<>("Booking not found",HttpStatus.NOT_FOUND);
        }

        Bookingentity booking=optBooking.get();
        booking.setStatus("CANCELLED");

        Roomentity room = booking.getRoom();
        room.setAvailability(true);  // Room is now available
        roomRepo.save(room);  // Save the updated room availability

        bookingRepo.save(booking);
        return ResponseEntity.ok("CANCELLED SUCCESSFULLY");

    }

   public ResponseEntity<String> updateDate(int bookingid,BookingRequestDto dt){
       Optional<Bookingentity> optBooking=bookingRepo.findById(bookingid);
       if(optBooking.isEmpty()){
           return new ResponseEntity<>("Booking not found",HttpStatus.NOT_FOUND);
       }
       Bookingentity booking=optBooking.get();
       booking.setStartdate(dt.getStartDate());
       booking.setEnddate(dt.getEndDate());
       bookingRepo.save(booking);
       return ResponseEntity.ok("Updated Successfully");
   }

    public Bookingentity getBookingByID(int bookingId) {
        return bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking Not found"));
    }

    public List<Bookingentity> getAllBooking() {
        return bookingRepo.findAll();
    }



    public ResponseEntity<String> confirmPayment(int bookingId, boolean payment){
        Optional<Bookingentity> optBooking=bookingRepo.findById(bookingId);
        if(optBooking.isEmpty()){
            return new ResponseEntity<>("Booking not found", HttpStatus.NOT_FOUND);
        }


        Bookingentity booking= optBooking.get();
        Roomentity room = booking.getRoom();

        LocalDateTime expirationThreshold = booking.getCreatedAt().plusMinutes(EXPIRATION_TIME_MINUTES);
        if (LocalDateTime.now().isAfter(expirationThreshold) && "PENDING".equals(booking.getStatus())) {
            // Mark the booking as expired if it's past the expiration time
            booking.setStatus("FAILED");
            room.setAvailability(true);  // Mark the room as available again
            roomRepo.save(room);
            bookingRepo.save(booking);
            return new ResponseEntity<>("Booking expired. Payment failed.", HttpStatus.BAD_REQUEST);
        }


        if(payment && booking.getStatus().equals("PENDING")){
            booking.setStatus("CONFIRMED");

            room.setAvailability(false);  // Room is now available
            roomRepo.save(room);

            bookingRepo.save(booking);
            return ResponseEntity.ok("BOOKED SUCCESSFULLY");
        }

        room.setAvailability(true);  // Room is now available
        roomRepo.save(room);

        booking.setStatus("FAILED");
        bookingRepo.save(booking);
        return ResponseEntity.badRequest().body("Booking failed");

    }
//    @Scheduled(fixedRate = 30000) // every 1 minute
//    public void checkExpiredBookings() {
//        LocalDateTime expirationThreshold = LocalDateTime.now().minusMinutes(EXPIRATION_TIME_MINUTES);
//        List<Bookingentity> expiredBookings = bookingRepo.findExpiredPendingBookings(expirationThreshold);
//
//        for (Bookingentity booking : expiredBookings) {
//            booking.setStatus("EXPIRED");
//
//            Roomentity room = booking.getRoom();
//            room.setAvailability(true);  // Room is now available
//            roomRepo.save(room);  // Save the updated room availability
//
//            bookingRepo.save(booking);
//        }
//    }



}
