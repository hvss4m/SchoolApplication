package com.example.schoolapplication.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
public class eleve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private String prenom;

    @OneToOne(mappedBy = "Eleve", cascade = CascadeType.ALL)
    private dossierAdministratif DossierAdminstratif;

    @ManyToOne
    @JoinColumn(name = "filiere_id")
    private filiere filiere;

    @ManyToMany(mappedBy = "eleves")
    @JsonIgnore
    private List<cours> cours;
}
