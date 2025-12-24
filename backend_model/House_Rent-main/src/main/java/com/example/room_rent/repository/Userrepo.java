package com.example.room_rent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.room_rent.enitity.Userentity;

@Repository
public interface Userrepo extends JpaRepository<Userentity,Integer>{
    boolean existsByEmail(String email);
}
