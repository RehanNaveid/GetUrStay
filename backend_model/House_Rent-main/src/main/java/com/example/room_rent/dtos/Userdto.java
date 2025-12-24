package com.example.room_rent.dtos;

import java.util.List;

import com.example.room_rent.enitity.Roomentity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

public class Userdto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(
        name = "user_seq",
        sequenceName = "user_sequence",
        initialValue = 5,   // 👈 Start value
        allocationSize = 1
    )
    private Integer user_id;
    private String name;
    private String phone;
    @Column(unique = true)
    private String email;
    private String password;
    private List<Roomdto> rooms;
    public Userdto() {}
    public Userdto(Integer userid, String name, String phone, String email,String pass) {
        this.user_id = userid;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password=pass;
    }
    public Userdto(Integer userid, String name, String phone, String email,String pass,List<Roomdto> rooms) {
        this.user_id = userid;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password=pass;
        this.rooms=rooms;
    }
    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public List<Roomdto> getRooms() {
        return rooms;
    }

    public void setRooms(List<Roomdto> rooms) {
        this.rooms = rooms;
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

}
