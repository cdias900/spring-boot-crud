package com.surittec.spring.boot.crud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.surittec.spring.boot.crud.model.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
	public List<Session> findByUserId(Long user_id);
	public Session findByToken(String token);
}
