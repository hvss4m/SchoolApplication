package com.example.schoolapplication.repositories;

import com.example.schoolapplication.entities.eleve;
import com.example.schoolapplication.entities.filiere;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface filiereRepository extends JpaRepository<filiere, Long> {
    //List<eleve> findAllElevesById(Long id);
}