package com.example.bibliotheque.Repositories;

import com.example.bibliotheque.Models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {

    // Trouver un utilisateur par son nom (pour l'authentification)
    Optional<Utilisateur> findByNom(String nom);

    // Vérifier si un utilisateur existe par son nom
    boolean existsByNom(String nom);

    // Trouver des utilisateurs par type d'adhérent
    List<Utilisateur> findByTypeAdherentId(Integer typeAdherentId);

    // Trouver des utilisateurs avec des sanctions actives
    @Query("SELECT u FROM Utilisateur u JOIN u.sanctions s WHERE CURRENT_DATE BETWEEN s.dateDebutSanction AND s.dateFinSanction")
    List<Utilisateur> findUtilisateursAvecSanctionsActives();

    // Trouver des utilisateurs avec des prêts en retard
    @Query("SELECT DISTINCT u FROM Utilisateur u JOIN u.prets p WHERE p.dateRetourPrevue < CURRENT_DATE AND p.id NOT IN (SELECT r.pret.id FROM Retour r)")
    List<Utilisateur> findUtilisateursAvecPretsEnRetard();

    // Trouver des utilisateurs par nom ou prénom (recherche)
    List<Utilisateur> findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(String nom, String prenom);
}