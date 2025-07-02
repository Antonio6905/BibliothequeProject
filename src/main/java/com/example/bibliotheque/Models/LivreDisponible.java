package com.example.bibliotheque.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Immutable
@Table(name = "vue_livres_disponibles", schema = "public")
public class LivreDisponible {

    @Id
    @Column(name = "livre_id")
    private Integer livreId;

    @Column(name = "titre", nullable = false)
    private String titre;

    @Column(name = "type_livre", nullable = false)
    private String typeLivre;

    @Column(name = "exemplaire_id")
    private Integer exemplaireId;

    @Column(name = "date_ajout")
    private LocalDate dateAjout;
}