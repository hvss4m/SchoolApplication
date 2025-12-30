package com.example.schoolapplication.repositories;

import com.example.schoolapplication.entities.eleve;
import org.springframework.data.jpa.repository.JpaRepository;

public interface eleveRepository extends JpaRepository<eleve, Long> {
}
