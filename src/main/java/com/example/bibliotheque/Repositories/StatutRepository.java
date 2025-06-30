package com.example.bibliotheque.Repositories;


import com.example.bibliotheque.Models.Statut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatutRepository extends JpaRepository<Statut, Integer> {
    Statut findByLibelle(String libelle);
}
