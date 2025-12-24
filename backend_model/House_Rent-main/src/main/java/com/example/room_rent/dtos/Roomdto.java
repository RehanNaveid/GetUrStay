package com.example.room_rent.dtos;
import com.example.room_rent.enitity.Userentity;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class Roomdto{
    @Id
    @Column(name = "room_id")
    private Integer roomid;
    @Column(name = "room_type")
    private String roomtype;
    private String location;
    private Float  price;
    @Column(name="is_ac")
    private Boolean isac;
    private String description;
    private Boolean availability;
    @Column(name = "max_occupancy")
    private Integer maxoccupancy;
    private Userdto owner;
    public Roomdto(Integer roomid, String roomtype, String location, Float price, Boolean isac,
                   String description, Boolean availability, Integer maxoccupancy, Userdto owner) {
        this.roomid = roomid;
        this.roomtype = roomtype;
        this.location = location;
        this.price = price;
        this.isac = isac;
        this.description = description;
        this.availability = availability;
        this.maxoccupancy = maxoccupancy;
        this.owner = owner;
    }
    public Roomdto(Integer roomid, String roomtype, String location, Float price, Boolean isac,
                   String description, Boolean availability, Integer maxoccupancy) {
        this.roomid = roomid;
        this.roomtype = roomtype;
        this.location = location;
        this.price = price;
        this.isac = isac;
        this.description = description;
        this.availability = availability;
        this.maxoccupancy = maxoccupancy;
    }
    public Integer getRoomid() {
        return roomid;
    }
    public void setRoomid(Integer roomid) {
        this.roomid = roomid;
    }
    public String getRoomtype() {
        return roomtype;
    }
    public void setRoomtype(String roomtype) {
        this.roomtype = roomtype;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public Float getPrice() {
        return price;
    }
    public void setPrice(Float price) {
        this.price = price;
    }
    public Boolean getIsac() {
        return isac;
    }
    public void setIsac(Boolean isac) {
        this.isac = isac;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Boolean getAvailability() {
        return availability;
    }
    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }
    public Integer getMaxoccupancy() {
        return maxoccupancy;
    }
    public void setMaxoccupancy(Integer maxoccupancy) {
        this.maxoccupancy = maxoccupancy;
    }
    public Userdto getOwner() {
        return owner;
    }
    public void setOwner(Userdto owner) {
        this.owner = owner;
    }
}
