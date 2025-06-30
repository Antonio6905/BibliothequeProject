package com.example.bibliotheque.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "type_adherent")
public class TypeAdherent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nom_type", nullable = false, length = 50)
    private String nomType;

    @OneToMany(mappedBy = "typeAdherent")
    private List<Utilisateur> utilisateurs;

    @OneToMany(mappedBy = "typeAdherent")
    private List<ConfigurationPret> configurationsPret;

    @OneToMany(mappedBy = "typeAdherent")
    private List<SanctionTypeAdherent> sanctionsTypeAdherent;

    @OneToMany(mappedBy = "typeAdherent")
    private List<TypeLivreAutorise> typesLivreAutorises;
}