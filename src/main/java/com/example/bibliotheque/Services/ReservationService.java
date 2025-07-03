package com.example.bibliotheque.Services;

import com.example.bibliotheque.Models.Reservation;
import com.example.bibliotheque.Repositories.ReservationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    /**
     * Récupérer tous les Reservations
     * 
     * @return Liste de tous les Reservations
     */
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    /**
     * Récupérer un Reservation par son ID
     * 
     * @param id Identifiant du Reservation
     * @return Reservation ou null si non trouvé
     */
    public Reservation findById(Integer id) {
        return reservationRepository.findById(id).orElse(null);
    }

    /**
     * Créer ou mettre à jour un Reservation
     * 
     * @param Reservation Reservation à sauvegarder
     * @return Reservation sauvegardé
     */
    public Reservation save(Reservation Reservation)throws Exception {

        return reservationRepository.save(Reservation);
    }

    public void deleteById(Integer id) {
        reservationRepository.deleteById(id);
    }

}