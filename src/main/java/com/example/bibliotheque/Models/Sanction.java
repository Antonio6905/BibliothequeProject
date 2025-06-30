package com.example.bibliotheque.Models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "sanction")
public class Sanction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur", nullable = false)
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "id_pret_livre")
    private Pret pret;

    @Column(name = "date_debut_sanction", nullable = false)
    private LocalDate dateDebutSanction;

    @Column(name = "date_fin_sanction", nullable = false)
    private LocalDate dateFinSanction;

    @Column(length = 100)
    private String motif;
}
