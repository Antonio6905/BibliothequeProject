package com.example.bibliotheque.Repositories;


import com.example.bibliotheque.Models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
        List<Reservation> findByUtilisateurId(Integer utilisateurId);

        @Query(value = "SELECT r.* FROM reservation r WHERE r.id IN (SELECT reservation_id FROM vue_reservations_non_traites) ",nativeQuery = true)
        List<Reservation> findNonTraite();

        @Query(value = "SELECT COALESCE(COUNT(*),0) AS nombre_reservations\r\n" + //
                        "    FROM reservation r\r\n" + //
                        "    JOIN vue_reservations_non_traites v ON r.id=v.reservation_id\r\n" + //
                        "    WHERE \r\n" + //
                        "        r.Id_Utilisateur = :userId", nativeQuery = true)
        Integer countByUserNonTraite(Integer userId);
}

