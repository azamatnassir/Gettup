package com.example.gettup.repository;

import com.example.gettup.entity.Creator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreatorRepository extends JpaRepository<Creator, Long> {
    Optional<Creator> findByUserId(Long id);
}
