package com.example.bibliotheque.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "type_livre")
public class TypeLivre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nom_type", nullable = false, length = 50)
    private String nomType;

    @OneToMany(mappedBy = "typeLivre")
    private List<Livre> livres;

    @OneToMany(mappedBy = "typeLivre")
    private List<TypeLivreAutorise> typesLivreAutorises;
}