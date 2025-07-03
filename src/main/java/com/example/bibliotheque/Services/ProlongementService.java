package com.example.bibliotheque.Services;

import com.example.bibliotheque.Models.Prolongement;
import com.example.bibliotheque.Models.Prolongement;
import com.example.bibliotheque.Repositories.ProlongementRepository;
import com.example.bibliotheque.Repositories.ProlongementRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProlongementService {

    @Autowired
    private ProlongementRepository prolongementRepository;

    /**
     * Récupérer tous les Prolongements
     * 
     * @return Liste de tous les Prolongements
     */
    public List<Prolongement> findAll() {
        return prolongementRepository.findAll();
    }

    public Prolongement findByPretId(Integer id) {
        return prolongementRepository.findByPretId(id);
    }

    /**
     * Récupérer un Prolongement par son ID
     * 
     * @param id Identifiant du Prolongement
     * @return Prolongement ou null si non trouvé
     */
    public Prolongement findById(Integer id) {
        return prolongementRepository.findById(id).orElse(null);
    }

    /**
     * Créer ou mettre à jour un Prolongement
     * 
     * @param Prolongement Prolongement à sauvegarder
     * @return Prolongement sauvegardé
     */
    public Prolongement save(Prolongement Prolongement) throws Exception {

        return prolongementRepository.save(Prolongement);
    }

    public void deleteById(Integer id) {
        prolongementRepository.deleteById(id);
    }

}