package com.example.bibliotheque.Models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "statut")
public class Statut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String libelle;


    @OneToMany(mappedBy = "statut")
    private List<SuiviStatutReservation> suivisStatutReservation;

    @OneToMany(mappedBy = "statut")
    private List<SuiviStatutProlongement> suivisStatutProlongement;
}
