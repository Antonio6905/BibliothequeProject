package com.example.bibliotheque.Repositories;


import com.example.bibliotheque.Models.SuiviStatutReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuiviStatutReservationRepository extends JpaRepository<SuiviStatutReservation, Integer> {
    List<SuiviStatutReservation> findByReservationId(Integer reservationId);

}
