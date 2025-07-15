package com.example.bibliotheque.Services;

import com.example.bibliotheque.Models.Abonnement;
import com.example.bibliotheque.Repositories.AbonnementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AbonnementService {

    @Autowired
    private AbonnementRepository abonnementRepository;

    public Abonnement saveAbonnement(Abonnement abonnement) {
        return abonnementRepository.save(abonnement);
    }

    public List<Abonnement> getAbonnementsByUtilisateur(Integer utilisateurId) {
        return abonnementRepository.findByUtilisateurId(utilisateurId);
    }

    public boolean isAbonnementActif(Integer utilisateurId) {
        return abonnementRepository.hasActiveAbonnement(utilisateurId, LocalDate.now());
    }

    public List<Abonnement> getAbonnementsActifs() {
        return abonnementRepository.findAbonnementsActifs(LocalDate.now());
    }

    public List<Abonnement> getAbonnementsExpirantBientot() {
        return abonnementRepository.findAbonnementsExpirantBientot(
                LocalDate.now(),
                LocalDate.now().plusDays(30));
    }

    public void deleteAbonnement(Integer id) {
        abonnementRepository.deleteById(id);
    }
}