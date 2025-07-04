package com.example.bibliotheque.Services;

import com.example.bibliotheque.Models.Retour;
import com.example.bibliotheque.Repositories.RetourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RetourService {

    @Autowired
    private RetourRepository retourRepository;

    /**
     * Récupérer tous les Retours
     * 
     * @return Liste de tous les Retours
     */
    public List<Retour> findAll() {
        return retourRepository.findAll();
    }

    /**
     * Récupérer un Retour par son ID
     * 
     * @param id Identifiant du Retour
     * @return Retour ou null si non trouvé
     */
    public Retour findById(Integer id) {
        return retourRepository.findById(id).orElse(null);
    }

    /**
     * Créer ou mettre à jour un Retour
     * 
     * @param Retour Retour à sauvegarder
     * @return Retour sauvegardé
     */
    public Retour save(Retour Retour) throws Exception {

        return retourRepository.save(Retour);
    }

    public void deleteById(Integer id) {
        retourRepository.deleteById(id);
    }

}