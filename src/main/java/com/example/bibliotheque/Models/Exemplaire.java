package com.example.bibliotheque.Models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "exemplaire")
public class Exemplaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_livre", nullable = false)
    private Livre livre;

    @Column(name = "date_ajout", columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private LocalDate dateAjout;

    @OneToMany(mappedBy = "exemplaire")
    private List<MouvementExemplaire> mouvements;

    @OneToMany(mappedBy = "exemplaire")
    private List<Pret> prets;
}
