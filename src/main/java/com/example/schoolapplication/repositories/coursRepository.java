package com.example.schoolapplication.repositories;

import com.example.schoolapplication.entities.cours;
import org.springframework.data.jpa.repository.JpaRepository;

public interface coursRepository extends JpaRepository<cours, Long> {
    boolean existsByCode(String code);
}
