package com.example.schoolapplication.services;

import com.example.schoolapplication.entities.cours;
import com.example.schoolapplication.entities.dossierAdministratif;
import com.example.schoolapplication.entities.eleve;
import com.example.schoolapplication.entities.filiere;
import com.example.schoolapplication.repositories.coursRepository;
import com.example.schoolapplication.repositories.dossierAdministratifRepository;
import com.example.schoolapplication.repositories.eleveRepository;
import com.example.schoolapplication.repositories.filiereRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class eleveService {
    private eleveRepository EleveRepository;
    private filiereRepository FiliereRepository;
    private coursRepository CoursRepository;
    private dossierAdministratifRepository DossierAdminRepository;

    eleveService(eleveRepository ER, filiereRepository filiereRepository, coursRepository coursRepository, dossierAdministratifRepository dossierAdminRepository){
        EleveRepository = ER;
        FiliereRepository = filiereRepository;
        CoursRepository = coursRepository;
        DossierAdminRepository = dossierAdminRepository;
    }

    public eleve getEleveById(Long id){
        return EleveRepository.findById(id).orElseThrow(() -> new RuntimeException("Eleve introuvable"));
    }

    public List<eleve> getAllEleves(){
        return EleveRepository.findAll();
    }

    @Transactional
    public void CreateEleve(eleve eleve) {

        if (eleve.getFiliere() == null) {
            throw new RuntimeException("Un élève doit obligatoirement appartenir à une filière");
        }

        EleveRepository.save(eleve);

        List<cours> coursFiliere = eleve.getFiliere().getCours();

        if (coursFiliere != null) {
            eleve.setCours(new ArrayList<>());

            for (cours c : coursFiliere) {
                eleve.getCours().add(c);
                c.getEleves().add(eleve);
            }
        }

        dossierAdministratif da = new dossierAdministratif();
        da.setEleve(eleve);
        DossierAdminRepository.save(da);

        da.setNumeroInscription(
                eleve.getFiliere().getCode() + "-"
                        + da.getDateCreation().getYear() + "-"
                        + da.getId()
        );
    }

    public void UpdateEleve(Long id, eleve eleve){
        eleve eleveOriginal = EleveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Eleve introuvable"));
        eleveOriginal.setNom(eleve.getNom());
        eleveOriginal.setPrenom(eleve.getPrenom());
        eleveOriginal.setFiliere(eleve.getFiliere());
        EleveRepository.save(eleveOriginal);
    }

    @Transactional
    public void DeleteEleve(Long id){
        eleve Eleve = EleveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Eleve introuvable"));

        if (Eleve.getCours() != null) {
            for (cours c : Eleve.getCours()) {
                c.getEleves().remove(Eleve);
            }
        }

        EleveRepository.delete(Eleve);
    }

    public void AffectFiliere(Long eleveId, Long filiereId){
        filiere Filiere = FiliereRepository.findById(filiereId)
                .orElseThrow(()-> new RuntimeException("Filiere Introuvable"));
        eleve Eleve = EleveRepository.findById(eleveId)
                .orElseThrow(() -> new RuntimeException("Eleve introuvable"));
        Eleve.setFiliere(Filiere);
        Eleve.setCours(Filiere.getCours());
        EleveRepository.save(Eleve);
    }

    @Transactional
    public void inscritCours(Long coursId, Long eleveId){
        eleve Eleve = EleveRepository.findById(eleveId)
                .orElseThrow(()-> new RuntimeException("Eleve Introuvable"));
        cours Cours = CoursRepository.findById(coursId)
                .orElseThrow(() -> new RuntimeException("Cours introuvable"));
        if (Cours.getEleves() == null) {
            Cours.setEleves(new ArrayList<>());
        }

        if (Eleve.getCours() == null)
            Eleve.setCours(new ArrayList<>());

        if (!Cours.getEleves().contains(Eleve)) {
            Cours.getEleves().add(Eleve);
            Eleve.getCours().add(Cours);
        }

        CoursRepository.save(Cours);
    }

}
