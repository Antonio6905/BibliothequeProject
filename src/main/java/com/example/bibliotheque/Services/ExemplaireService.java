package com.example.bibliotheque.Services;

import com.example.bibliotheque.Models.Exemplaire;
import com.example.bibliotheque.Models.Exemplaire;
import com.example.bibliotheque.Repositories.ExemplaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExemplaireService {

    @Autowired
    private ExemplaireRepository exemplaireRepository;

    /**
     * Récupérer tous les Exemplaires
     * 
     * @return Liste de tous les Exemplaires
     */
    public List<Exemplaire> findAll() {
        return exemplaireRepository.findAll();
    }

    /**
     * Récupérer un Exemplaire par son ID
     * 
     * @param id Identifiant du Exemplaire
     * @return Exemplaire ou null si non trouvé
     */
    public Exemplaire findById(Integer id) {
        return exemplaireRepository.findById(id).orElse(null);
    }

    public List<Exemplaire> findDispoByLivre(Integer id) {
        return exemplaireRepository.findDisponiblesByLivreId(id);
    }

    /**
     * Créer ou mettre à jour un Exemplaire
     * 
     * @param Exemplaire Exemplaire à sauvegarder
     * @return Exemplaire sauvegardé
     */
    public Exemplaire save(Exemplaire Exemplaire) {
        return exemplaireRepository.save(Exemplaire);
    }

    public void deleteById(Integer id) {
        exemplaireRepository.deleteById(id);
    }

}