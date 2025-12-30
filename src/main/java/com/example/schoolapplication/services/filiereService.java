package com.example.schoolapplication.services;

import com.example.schoolapplication.entities.cours;
import com.example.schoolapplication.entities.eleve;
import com.example.schoolapplication.entities.filiere;
import com.example.schoolapplication.repositories.filiereRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class filiereService {
    private filiereRepository FiliereRepository;
    
    filiereService(filiereRepository FR){
        FiliereRepository = FR;
    }

    public filiere getFiliereById(Long id){
        return FiliereRepository.findById(id).orElseThrow(() -> new RuntimeException("Filiere introuvable"));
    }

    public List<filiere> getAllFilieres(){
        return FiliereRepository.findAll();
    }

    public void CreateFiliere(filiere Dep){
        FiliereRepository.save(Dep);
    }

    public void UpdateFiliere(Long id, filiere Filiere){
        filiere FiliereOriginale = FiliereRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("filiere introuvable"));
        FiliereOriginale.setNom(Filiere.getNom());
        FiliereOriginale.setCode(Filiere.getCode());
        FiliereRepository.save(FiliereOriginale);
    }

    public void DeleteFiliere(Long id){
        filiere Filiere= FiliereRepository.findById(id).orElseThrow(() -> new RuntimeException("Filiere introuvable"));
        FiliereRepository.delete(Filiere);
    }

    public List<eleve> getElevesList(Long id){
        filiere f = FiliereRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Filiere introuvable"));
        return f.getEleves();
    }

    public List<cours> getCoursList(Long id){
        filiere f = FiliereRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Filiere introuvable"));
        return f.getCours();
    }


}
