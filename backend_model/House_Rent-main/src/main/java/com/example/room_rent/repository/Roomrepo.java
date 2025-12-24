package com.example.room_rent.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.room_rent.enitity.Roomentity;
import java.util.List;
import java.util.Optional;


@Repository
public interface Roomrepo extends JpaRepository<Roomentity,Integer>{

    List<Roomentity> findByowner_userid(Integer id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM Roomentity r WHERE r.id = :roomId")
    Optional<Roomentity> findRoomForUpdate(@Param("roomId") int roomId);
}
