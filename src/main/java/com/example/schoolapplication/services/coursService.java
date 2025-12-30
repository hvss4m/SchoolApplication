package com.example.schoolapplication.services;

import com.example.schoolapplication.entities.cours;
import com.example.schoolapplication.entities.filiere;
import com.example.schoolapplication.repositories.coursRepository;
import com.example.schoolapplication.repositories.filiereRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class coursService {
    private coursRepository CoursRepository;
    private filiereRepository FiliereRepository;

    coursService(coursRepository CR, filiereRepository FR){
        CoursRepository = CR;
        FiliereRepository = FR;
    }

    public cours getCoursById(Long id){
        return CoursRepository.findById(id).orElseThrow(() -> new RuntimeException("Cours introuvable"));
    }

    public List<cours> getAllCours(){
        return CoursRepository.findAll();
    }

    public void CreateCours(cours Cours){
        if (CoursRepository.existsByCode(Cours.getCode())) {
            throw new IllegalArgumentException("Ce code de cours existe déjà");
        }
        CoursRepository.save(Cours);
    }

    public void UpdateCours(Long id, cours cours){
        cours coursOriginal = CoursRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cours introuvable"));
        coursOriginal.setCode(cours.getCode());
        coursOriginal.setIntitule(cours.getIntitule());
        CoursRepository.save(coursOriginal);
    }

    public void DeleteCours(Long id){
        cours cours= CoursRepository.findById(id).orElseThrow(() -> new RuntimeException("Cours introuvable"));
        CoursRepository.delete(cours);
    }

    @Transactional
    public void AttachFiliere(Long coursId, Long filiereId){
        filiere Filiere = FiliereRepository.findById(filiereId)
                .orElseThrow(()-> new RuntimeException("Filiere Introuvable"));
        cours Cours = CoursRepository.findById(coursId)
                .orElseThrow(() -> new RuntimeException("Cours introuvable"));
        if (Cours.getFilieres() == null) {
            Cours.setFilieres(new ArrayList<>());
        }

        if (!Cours.getFilieres().contains(Filiere)) {
            Cours.getFilieres().add(Filiere);
        }

        CoursRepository.save(Cours);
    }
}
