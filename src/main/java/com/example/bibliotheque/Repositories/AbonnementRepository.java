package com.example.bibliotheque.Repositories;

import com.example.bibliotheque.Models.Abonnement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AbonnementRepository extends JpaRepository<Abonnement, Integer> {

    // Trouver les abonnements d'un utilisateur
    List<Abonnement> findByUtilisateurId(Integer utilisateurId);

    // Trouver les abonnements actifs (date courante entre dateDebut et dateFin)
    @Query("SELECT a FROM Abonnement a WHERE :date BETWEEN a.dateDebut AND a.dateFin")
    List<Abonnement> findAbonnementsActifs(@Param("date") LocalDate date);

    // Trouver les abonnements expirant bientôt (dans les 30 jours)
    @Query("SELECT a FROM Abonnement a WHERE a.dateFin BETWEEN :today AND :futureDate")
    List<Abonnement> findAbonnementsExpirantBientot(
            @Param("today") LocalDate today,
            @Param("futureDate") LocalDate futureDate);

    // Vérifier si un utilisateur a un abonnement actif
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END " +
            "FROM Abonnement a WHERE a.utilisateur.id = :utilisateurId " +
            "AND :date BETWEEN a.dateDebut AND a.dateFin")
    boolean hasActiveAbonnement(
            @Param("utilisateurId") Integer utilisateurId,
            @Param("date") LocalDate date);
}