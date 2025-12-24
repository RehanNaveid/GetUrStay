package com.example.room_rent.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.relation.RelationNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.room_rent.dtos.Roomdto;
import com.example.room_rent.dtos.Userdto;
import com.example.room_rent.enitity.Roomentity;
import com.example.room_rent.enitity.Userentity;
import com.example.room_rent.repository.Roomrepo;
import com.example.room_rent.repository.Userrepo;

@Service
public class Roomservice {
    @Autowired
    private Roomrepo rrepo;
    @Autowired
    private Userrepo urepo;
    public Roomdto get(Integer id)
    {
        Optional<Roomentity> roome=rrepo.findById(id);
        if(roome.isPresent())
        {
            Roomentity room = roome.get();
            Userentity user=room.getOwner();
            Userdto udto=new Userdto(id, user.getName(), user.getPhone(), user.getEmail(), user.getPassword());
            return new Roomdto(room.getRoomid(), room.getRoomtype(), room.getLocation(), room.getPrice(), room.getIsac(), room.getDescription(), room.getAvailability(), room.getMaxoccupancy(),udto);
        }
        return new Roomdto(id, null, null, null, null, null, null, id, null);
    }
    // public List<Roomentity> getall()
    // {
    //     return rrepo.findAll();
    // }
    public List<Roomdto> getAllRooms() {
    List<Roomentity> roomEntities = rrepo.findAll();

    return roomEntities.stream().map(room -> {
        Userentity user = room.getOwner();
        Userdto userDTO = new Userdto(
            user.getUserid(),
            user.getName(),
            user.getPhone(),
            user.getEmail(),
            user.getPassword()
        );

        return new Roomdto(
            room.getRoomid(),
            room.getRoomtype(),
            room.getLocation(),
            room.getPrice(),
            room.getIsac(),
            room.getDescription(),
            room.getAvailability(),
            room.getMaxoccupancy(),
            userDTO
        );
    }).collect(Collectors.toList());
}

    public List<Roomdto> getuser(Integer id)
    {
        // return rrepo.findByowner_userid(id);
        List<Roomentity> roomEntities = rrepo.findByowner_userid(id);

    return roomEntities.stream().map(room -> {
        Userentity user = room.getOwner();
        Userdto userDTO = new Userdto(
            user.getUserid(),
            user.getName(),
            user.getPhone(),
            user.getEmail(),
            user.getPassword()
        );

        return new Roomdto(
            room.getRoomid(),
            room.getRoomtype(),
            room.getLocation(),
            room.getPrice(),
            room.getIsac(),
            room.getDescription(),
            room.getAvailability(),
            room.getMaxoccupancy(),
            userDTO
        );
    }).collect(Collectors.toList());
    }
    public String add(Roomdto dt)
    {
        Roomentity e=new Roomentity();
        e.setAvailability(dt.getAvailability());
        e.setDescription(dt.getDescription());
        e.setIsac(dt.getIsac());
        e.setLocation(dt.getLocation());
        e.setMaxoccupancy(dt.getMaxoccupancy());
        Userentity user=urepo.findById(dt.getOwner().getUser_id()).orElseThrow(() -> new RuntimeException("User not found with id " + dt.getOwner())); 
        e.setOwner(user);
        e.setPrice(dt.getPrice());
        e.setRoomid(dt.getRoomid());
        e.setRoomtype(dt.getRoomtype());
        rrepo.save(e);
        return "added";
    }
    public String delete(Integer id)
    {
        try{
            rrepo.deleteById(id);
            return "deleted";
        }
        catch(Error e){
            return "error";
        }
    }
    public String update(Integer id,Roomdto value)
    {
        Optional<Roomentity> get=rrepo.findById(id);
        if(get.isPresent())
        {
            Roomentity derivedvalue=get.get();
            if(value.getAvailability()!=null)
                derivedvalue.setAvailability(value.getAvailability());
            if(value.getIsac()!=null)
                derivedvalue.setIsac(value.getIsac());
            if(value.getDescription()!=null)
                derivedvalue.setAvailability(value.getAvailability());
            if(value.getLocation()!=null)
                derivedvalue.setLocation(value.getLocation());
            if(value.getMaxoccupancy()!=null)
                derivedvalue.setMaxoccupancy(value.getMaxoccupancy());
            if(value.getPrice()!=null)
                derivedvalue.setPrice(value.getPrice());
            return "updated";
        }
        return "not updated";
    }
}
