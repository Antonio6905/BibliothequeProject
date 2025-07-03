package com.example.bibliotheque.Models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur", nullable = false)
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "id_exemplaire", nullable = false)
    private Exemplaire exemplaire;

    @Column(name = "date_demande", columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private LocalDate dateDemande;

    @Column(name = "date_expiration")
    private LocalDate dateExpiration;

    @OneToMany(mappedBy = "reservation")
    private List<SuiviStatutReservation> suivisStatut;
}
