package com.example.bibliotheque.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Immutable
@Table(name = "vue_sanctions_en_cours", schema = "public")
public class SanctionEnCours {

    @Id
    @Column(name = "sanction_id")
    private Integer sanctionId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "nom_sanctionne")
    private String nomSanctionne;

    @Column(name = "prenom_sanctionne")
    private String prenomSanctionne;

    @Column(name = "type_adherent")
    private String typeAdherent;

    @Column(name = "titre_livre")
    private String titreLivre;

    @Column(name = "date_pret")
    private LocalDateTime datePret;

    @Column(name = "date_debut_sanction", nullable = false)
    private LocalDate dateDebutSanction;

    @Column(name = "date_fin_sanction", nullable = false)
    private LocalDate dateFinSanction;

    @Column(name = "motif")
    private String motif;

    @Column(name = "etat_sanction", nullable = false)
    private String etatSanction;
}