package com.example.bibliotheque.Models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "prolongement")
public class Prolongement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_pret_livre", nullable = false)
    private Pret pret;

    @Column(name = "date_debut_prolongement", nullable = false)
    private LocalDate dateDebutProlongement;

    @Column(name = "date_fin_prolongement", nullable = false)
    private LocalDate dateFinProlongement;

    @OneToMany(mappedBy = "prolongement")
    private List<SuiviStatutProlongement> suivisStatut;
}
