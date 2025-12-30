package com.example.schoolapplication.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class cours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String intitule;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "cours_filiere",
            joinColumns = @JoinColumn(name = "cours_id"),
            inverseJoinColumns = @JoinColumn(name = "filiere_id")
    )
    @JsonIgnore
    private List<filiere> filieres;

    @ManyToMany
    @JoinTable(
            name = "cours_eleve",
            joinColumns = @JoinColumn(name = "cours_id"),
            inverseJoinColumns = @JoinColumn(name = "eleve_id")
    )
    @JsonIgnore
    private List<eleve> eleves;
}
