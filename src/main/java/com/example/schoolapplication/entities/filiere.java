package com.example.schoolapplication.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.apache.tomcat.util.net.openssl.ciphers.EncryptionLevel;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
public class filiere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code;

    private String nom;

    @OneToMany(mappedBy = "filiere")
    @JsonIgnore
    private List<eleve> eleves;

    @ManyToMany(mappedBy = "filieres", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<cours> cours;
}
