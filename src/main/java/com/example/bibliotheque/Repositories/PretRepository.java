package com.example.bibliotheque.Repositories;


import com.example.bibliotheque.Models.Pret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PretRepository extends JpaRepository<Pret, Integer> {
    @Query(value = "SELECT p.* FROM pret p WHERE p.id IN (SELECT pret_id FROM vue_emprunts_en_cours) AND p.id_utilisateur=:id",nativeQuery = true)

    List<Pret> findByUtilisateurIdEnCours(@Param("id")Integer utilisateurId);

    List<Pret> findByExemplaireId(Integer exemplaireId);

    @Query("SELECT p FROM Pret p WHERE p.id NOT IN (SELECT r.pret.id FROM Retour r)")
    List<Pret> findPretsEnCours();

    @Query("SELECT p FROM Pret p WHERE p.dateRetourPrevue < CURRENT_DATE AND p.id NOT IN (SELECT r.pret.id FROM Retour r)")
    List<Pret> findPretsEnRetard();

    List<Pret> findByDateRetourPrevueBetween(LocalDate start, LocalDate end);
}