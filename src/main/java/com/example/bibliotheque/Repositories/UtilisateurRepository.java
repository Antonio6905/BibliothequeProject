package com.example.bibliotheque.Repositories;


import com.example.bibliotheque.Models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    List<Utilisateur> findByNomContainingIgnoreCase(String nom);

    List<Utilisateur> findByPrenomContainingIgnoreCase(String prenom);

    List<Utilisateur> findByTypeAdherentId(Integer typeAdherentId);
}