package com.example.schoolapplication.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
public class dossierAdministratif {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroInscription;

    private LocalDate dateCreation;

    @OneToOne
    @JoinColumn(name = "eleve_id", nullable = false, unique = true)
    private eleve Eleve;

    @PrePersist
    protected void onCreate() {
        this.dateCreation = LocalDate.now();
    }
}
