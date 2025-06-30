package com.example.bibliotheque.Models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "sanction_type_adherent")
public class SanctionTypeAdherent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_type_adherent", nullable = false)
    private TypeAdherent typeAdherent;

    @Column(name = "duree_sanction", nullable = false)
    private Integer dureeSanction;

    @Column(name = "description_sanction", columnDefinition = "TEXT")
    private String descriptionSanction;
}