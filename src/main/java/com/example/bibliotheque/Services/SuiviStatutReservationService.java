package com.example.bibliotheque.Services;

import com.example.bibliotheque.Models.SuiviStatutReservation;
import com.example.bibliotheque.Repositories.SuiviStatutReservationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuiviStatutReservationService {

    @Autowired
    private SuiviStatutReservationRepository suiviStatutReservationRepository;

    /**
     * Récupérer tous les Reservations
     * 
     * @return Liste de tous les Reservations
     */
    public List<SuiviStatutReservation> findAll() {
        return suiviStatutReservationRepository.findAll();
    }

    /**
     * Récupérer un Reservation par son ID
     * 
     * @param id Identifiant du Reservation
     * @return Reservation ou null si non trouvé
     */
    public SuiviStatutReservation findById(Integer id) {
        return suiviStatutReservationRepository.findById(id).orElse(null);
    }

    /**
     * Créer ou mettre à jour un Reservation
     * 
     * @param Reservation Reservation à sauvegarder
     * @return Reservation sauvegardé
     */
    public SuiviStatutReservation save(SuiviStatutReservation Reservation) throws Exception {

        return suiviStatutReservationRepository.save(Reservation);
    }

    public void deleteById(Integer id) {
        suiviStatutReservationRepository.deleteById(id);
    }

}