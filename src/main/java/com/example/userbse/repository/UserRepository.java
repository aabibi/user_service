package com.example.userbse.repository;


import com.example.userbse.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {


    Users findUsersByFirstNameAndLastName(String firstName, String lastName);

    Users findUsersById(long id);
}
