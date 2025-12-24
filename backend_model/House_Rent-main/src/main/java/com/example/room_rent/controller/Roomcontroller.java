package com.example.room_rent.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.room_rent.dtos.Roomdto;
import com.example.room_rent.enitity.Roomentity;
import com.example.room_rent.service.Roomservice;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;





@RestController
@RequestMapping("/room")
public class Roomcontroller {
    @Autowired
    private Roomservice rserv;
    @GetMapping("/getroom/{id}")
    public Roomdto rooms(@PathVariable Integer id) {
        return rserv.get(id);
    }
    @GetMapping("/getall")
    public List<Roomdto> all() {
        return rserv.getAllRooms();
    }
     @GetMapping("/getuserroom/{id}")
     public List<Roomdto> getMethodName(@PathVariable Integer id) {
         return rserv.getuser(id);
     }
     @PostMapping("/addroom")
     public String postMethodName(@RequestBody Roomdto dt) {
        return this.rserv.add(dt);
     }
     @PutMapping("updateroom/{id}")
     public String putMethodName(@PathVariable Integer id, @RequestBody Roomdto dt) {
         return this.rserv.update(id, dt);
     }
     @DeleteMapping("/deleteroom/{id}")
     public String delete(@PathVariable Integer id)
     {
        return rserv.delete(id);
     }
    
}
