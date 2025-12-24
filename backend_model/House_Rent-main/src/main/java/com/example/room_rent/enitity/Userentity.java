package com.example.room_rent.enitity;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

//@JsonIdentityInfo(
//  generator = ObjectIdGenerators.PropertyGenerator.class,
//  property = "userid")
@Entity
@Table(name="USER")
public class Userentity {
        @Id
        @Column(name = "user_id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer userid;

        private String name;
        private String phone;
        @Column(unique = true)
        private String email;
        private String password;
        @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
        @JsonManagedReference   //to avoid infinite recursion(this matches to room and in room it again matches to user and goes on)
        private List<Roomentity> rooms;
        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
        @JsonManagedReference   //to avoid infinite recursion(this matches to room and in room it again matches to user and goes on)
        private List<Bookingentity> booking;

    public List<Bookingentity> getBooking() {
        return booking;
    }

    public void setBooking(List<Bookingentity> booking) {
        this.booking = booking;
    }



    public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getPhone() {
            return phone;
        }
        public void setPhone(String phone) {
            this.phone = phone;
        }
        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }
        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }
        public Integer getUserid() {
            return userid;
        }
        public void setUserid(Integer userid) {
            this.userid = userid;
        }
        public List<Roomentity> getRooms() {
            return rooms;
        }
        public void setRooms(List<Roomentity> rooms) {
            this.rooms = rooms;
        }

}