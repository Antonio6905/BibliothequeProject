package com.example.bibliotheque.Repositories;


import com.example.bibliotheque.Models.Exemplaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExemplaireRepository extends JpaRepository<Exemplaire, Integer> {
    List<Exemplaire> findByLivreId(Integer livreId);

    @Query("SELECT e FROM Exemplaire e WHERE e.livre.id = :livreId AND e.id NOT IN " +
            "(SELECT p.exemplaire.id FROM Pret p WHERE p.id NOT IN " +
            "(SELECT r.pret.id FROM Retour r))")
    List<Exemplaire> findDisponiblesByLivreId(Integer livreId);
}