package com.example.bibliotheque.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "configuration_pret")
public class ConfigurationPret {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_type_adherent", nullable = false)
    private TypeAdherent typeAdherent;

    @Column(name = "nombre_livre_quota", nullable = false)
    private Integer nombreLivreQuota;

    @Column(name = "duree_pret", nullable = false)
    private Integer dureePret;

    @Column(name = "duree_prolongement", nullable = false)
    private Integer dureeProlongement;

    @Column(name = "duree_reservation", nullable = false)
    private Integer dureeReservation;
}