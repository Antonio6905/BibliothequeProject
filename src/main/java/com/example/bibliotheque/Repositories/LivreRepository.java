package com.example.bibliotheque.Repositories;


import com.example.bibliotheque.Models.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivreRepository extends JpaRepository<Livre, Integer> {
    List<Livre> findByNomContainingIgnoreCase(String nom);

    List<Livre> findByTypeLivreId(Integer typeLivreId);

    @Query("SELECT l FROM Livre l WHERE l.nom LIKE %:searchTerm% OR l.description LIKE %:searchTerm%")
    List<Livre> searchByNomOrDescription(String searchTerm);
 
    @Query("SELECT l FROM Livre l WHERE l.id IN (select livreId from LivreDisponible)")
    List<Livre> findifDispo(Integer livreId);
}
