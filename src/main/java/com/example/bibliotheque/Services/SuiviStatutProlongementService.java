package com.example.bibliotheque.Services;

import com.example.bibliotheque.Models.Prolongement;
import com.example.bibliotheque.Models.SuiviStatutProlongement;
import com.example.bibliotheque.Models.Prolongement;
import com.example.bibliotheque.Repositories.ProlongementRepository;
import com.example.bibliotheque.Repositories.SuiviStatutProlongementRepository;
import com.example.bibliotheque.Repositories.ProlongementRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuiviStatutProlongementService {

    @Autowired
    private SuiviStatutProlongementRepository statutProlongementRepository;

    /**
     * Récupérer tous les Prolongements
     * 
     * @return Liste de tous les Prolongements
     */
    public List<SuiviStatutProlongement> findAll() {
        return statutProlongementRepository.findAll();
    }

    /**
     * Récupérer un Prolongement par son ID
     * 
     * @param id Identifiant du Prolongement
     * @return Prolongement ou null si non trouvé
     */
    public SuiviStatutProlongement findById(Integer id) {
        return statutProlongementRepository.findById(id).orElse(null);
    }

    /**
     * Créer ou mettre à jour un Prolongement
     * 
     * @param Prolongement Prolongement à sauvegarder
     * @return Prolongement sauvegardé
     */
    public SuiviStatutProlongement save(SuiviStatutProlongement Prolongement){

        return statutProlongementRepository.save(Prolongement);
    }

    public void deleteById(Integer id) {
        statutProlongementRepository.deleteById(id);
    }

}