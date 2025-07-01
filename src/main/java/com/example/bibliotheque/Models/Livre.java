package com.example.bibliotheque.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "livre")
public class Livre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_type_livre", nullable = false)
    private TypeLivre typeLivre;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "date_edition")
    private LocalDate dateEdition;

    @ManyToMany
    @JoinTable(name = "categorie_livre", joinColumns = @JoinColumn(name = "id_livre"), inverseJoinColumns = @JoinColumn(name = "id_categorie"))
    private List<Categorie> categories;

    @OneToMany(mappedBy = "livre")
    private List<Exemplaire> exemplaires;

    @OneToMany(mappedBy = "livre")
    private List<Reservation> reservations;
}