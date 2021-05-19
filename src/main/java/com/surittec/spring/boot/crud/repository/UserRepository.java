package com.surittec.spring.boot.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.surittec.spring.boot.crud.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
