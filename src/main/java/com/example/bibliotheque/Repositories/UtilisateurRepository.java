package com.example.bibliotheque.Repositories;

import com.example.bibliotheque.Models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {

    // Trouver un utilisateur par son nom (pour l'authentification)
    Optional<Utilisateur> findByNom(String nom);

    // Trouver un utilisateur par nom et mot de passe
    Optional<Utilisateur> findByNomAndPassword(String nom, String password);

    // Vérifier si un utilisateur existe par son nom
    boolean existsByNom(String nom);

    // Trouver des utilisateurs par type d'adhérent
    List<Utilisateur> findByTypeAdherentId(Integer typeAdherentId);

    // Trouver des utilisateurs avec des sanctions actives
    @Query("SELECT u FROM Utilisateur u JOIN u.sanctions s WHERE CURRENT_DATE BETWEEN s.dateDebutSanction AND s.dateFinSanction")
    List<Utilisateur> findUtilisateursAvecSanctionsActives();

    @Query("SELECT u FROM Utilisateur u JOIN u.sanctions s WHERE CURRENT_DATE BETWEEN s.dateDebutSanction AND s.dateFinSanction WHERE u.id=:id")
    Optional<Utilisateur> findUserSanctionne(@Param("id") Integer id);

    // Trouver des utilisateurs avec des prêts en retard
    @Query("SELECT DISTINCT u FROM Utilisateur u JOIN u.prets p WHERE p.dateRetourPrevue < CURRENT_DATE AND p.id NOT IN (SELECT r.pret.id FROM Retour r)")
    List<Utilisateur> findUtilisateursAvecPretsEnRetard();

    // Trouver des utilisateurs par nom ou prénom (recherche)
    List<Utilisateur> findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(String nom, String prenom);

    @Query(value="SELECT count(*) FROM vue_emprunts_en_cours as v WHERE v.user_id=:id",nativeQuery = true)
    Integer countQuota(Integer userId);

    
}