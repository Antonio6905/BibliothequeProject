package com.example.bibliotheque.Repositories;


import com.example.bibliotheque.Models.Exemplaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExemplaireRepository extends JpaRepository<Exemplaire, Integer> {
    List<Exemplaire> findByLivreId(Integer livreId);

    @Query("SELECT e FROM Exemplaire e WHERE e.livre.id = :livreId AND e.id NOT IN " +
            "(SELECT p.exemplaire.id FROM Pret p WHERE p.id NOT IN " +
            "(SELECT r.pret.id FROM Retour r))")
    List<Exemplaire> findDisponiblesByLivreId(Integer livreId);

    @Query("SELECT e FROM Exemplaire e WHERE e.livre.id = :livreId  AND e.id NOT IN " +
            "(SELECT p.exemplaire.id FROM Pret p WHERE p.id NOT IN " +
            "(SELECT r.pret.id FROM Retour r))  AND e.id=:id")
    Exemplaire findDisponiblesByLivreIdAndId(@Param("livreId")Integer livreId, @Param("id")Integer id);

    @Query(value = "SELECT CASE WHEN COUNT(vra) = 0 THEN TRUE ELSE FALSE END " +
            "FROM vue_reservations_actives vra " +
            "WHERE vra.exemplaire_id = :id AND :date BETWEEN vra.date_demande AND vra.date_expiration AND vra.exemplaire_id NOT IN (SELECT vec.exemplaire_id FROM vue_emprunts_en_cours vec)", nativeQuery = true)
    Boolean isExemplaireAvailable(@Param("id") Integer id, @Param("date") LocalDate date);
}