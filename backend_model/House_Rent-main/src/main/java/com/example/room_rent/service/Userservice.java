package com.example.room_rent.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.room_rent.dtos.Roomdto;
import com.example.room_rent.dtos.Userdto;
import com.example.room_rent.enitity.Roomentity;
import com.example.room_rent.enitity.Userentity;
import com.example.room_rent.repository.Userrepo;

@Service
public class Userservice {
    @Autowired
    private Userrepo urep;
    public String adduser(Userdto dt)
    {
        Userentity e=new Userentity();
        // if(urep.existsByEmail(dt.getEmail()))
        e.setName(dt.getName());
        e.setPhone(dt.getPhone());
        e.setEmail(dt.getEmail());
        e.setPassword(dt.getPassword());
        // return urep.save(e);
        if (urep.existsByEmail(e.getEmail())) {
            // return ResponseEntity.status(HttpStatus.CONFLICT)
            //         .body("Email already exists");
            return "Email exists";
        }
        // return ResponseEntity.status(HttpStatus.CREATED).body(urep.save(e));
        urep.save(e);
        return "added";
    
    }    
    public List<Userdto> getall()
    {
        List<Userentity> entity=urep.findAll();
        return entity.stream().map(user->{
            List<Roomentity> urooms=user.getRooms();
            List<Roomdto> sample=urooms.stream().map(room->{
                 return new Roomdto(room.getRoomid(), room.getRoomtype(), room.getLocation(), room.getPrice(), room.getIsac(), room.getDescription(), room.getAvailability(), room.getMaxoccupancy());
            }).collect(Collectors.toList());
            return new Userdto(user.getUserid(), user.getName(), user.getPhone(), user.getEmail(), user.getPassword(),sample);
        }).collect(Collectors.toList());
    }
    public Userdto gettingbyid(Integer id)
    {
        Optional<Userentity> uen=urep.findById(id);
        if(uen.isPresent())
        {   
            Userentity data=uen.get();
            List<Roomentity> urooms=data.getRooms();
            List<Roomdto> sample=urooms.stream().map(room->{
                return new Roomdto(room.getRoomid(),room.getRoomtype(), room.getLocation(), room.getPrice(), room.getIsac(), room.getDescription(), room.getAvailability(), room.getMaxoccupancy());
            }).collect(Collectors.toList());
            return new Userdto(id, data.getName(), data.getPhone(), data.getEmail(), data.getPassword(),sample);
        }
        return new Userdto(id, null, null, null, null);
    }
    public String update(Integer id,Userdto value)
    {
        Optional<Userentity> derivedvalue=urep.findById(id);
        if(derivedvalue.isPresent())
        {
            Userentity user=derivedvalue.get();
            if(value.getEmail()!=null)
                user.setEmail(value.getEmail());
            if(value.getName()!=null)
                user.setName(value.getName());
            if(value.getPassword()!=null)
                user.setPassword(value.getPassword());
            if(value.getPhone()!=null)
                user.setPhone(value.getPhone());
            urep.save(user);
            return "updated";
        }
        return "not updated";
    }
    public String delete(Integer id)
    {
        try{
            urep.deleteById(id);
            return "deleted";
        }
        catch(Error e)
        {
            return "error";
        }
    }
}

