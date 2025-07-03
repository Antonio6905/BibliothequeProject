package com.example.bibliotheque.Services;

import com.example.bibliotheque.Models.Pret;
import com.example.bibliotheque.Repositories.PretRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PretService {

    @Autowired
    private PretRepository pretRepository;

    /**
     * Récupérer tous les Prets
     * 
     * @return Liste de tous les Prets
     */
    public List<Pret> findAll() {
        return pretRepository.findAll();
    }

    public List<Pret> findByUtilisateur(Integer id) {
        return pretRepository.findByUtilisateurIdEnCours(id);
    }

    /**
     * Récupérer un Pret par son ID
     * 
     * @param id Identifiant du Pret
     * @return Pret ou null si non trouvé
     */
    public Pret findById(Integer id) {
        return pretRepository.findById(id).orElse(null);
    }

    /**
     * Créer ou mettre à jour un Pret
     * 
     * @param Pret Pret à sauvegarder
     * @return Pret sauvegardé
     */
    public Pret save(Pret Pret)throws Exception {

        return pretRepository.save(Pret);
    }

    public void deleteById(Integer id) {
        pretRepository.deleteById(id);
    }

}