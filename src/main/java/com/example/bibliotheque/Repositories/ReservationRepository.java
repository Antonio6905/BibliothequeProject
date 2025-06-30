package com.example.bibliotheque.Repositories;


import com.example.bibliotheque.Models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findByUtilisateurId(Integer utilisateurId);

    List<Reservation> findByLivreId(Integer livreId);

    @Query("SELECT r FROM Reservation r WHERE r.dateExpiration >= CURRENT_DATE AND r.id IN " +
            "(SELECT ssr.reservation.id FROM SuiviStatutReservation ssr WHERE ssr.statut.libelle IN ('Validé', 'Actif'))")
    List<Reservation> findReservationsActives();

    @Query("SELECT r FROM Reservation r WHERE r.dateExpiration < CURRENT_DATE AND r.id IN " +
            "(SELECT ssr.reservation.id FROM SuiviStatutReservation ssr WHERE ssr.statut.libelle = 'Actif')")
    List<Reservation> findReservationsExpirees();
}
