package com.example.bibliotheque.Services;

import com.example.bibliotheque.Models.Sanction;
import com.example.bibliotheque.Repositories.SanctionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SanctionService {

    @Autowired
    private SanctionRepository sanctionService;

    /**
     * Récupérer tous les Sanctions
     * 
     * @return Liste de tous les Sanctions
     */
    public List<Sanction> findAll() {
        return sanctionService.findAll();
    }

    /**
     * Récupérer un Sanction par son ID
     * 
     * @param id Identifiant du Sanction
     * @return Sanction ou null si non trouvé
     */
    public Sanction findById(Integer id) {
        return sanctionService.findById(id).orElse(null);
    }

    /**
     * Créer ou mettre à jour un Sanction
     * 
     * @param Sanction Sanction à sauvegarder
     * @return Sanction sauvegardé
     */
    public Sanction save(Sanction Sanction) {
        return sanctionService.save(Sanction);
    }

    public void deleteById(Integer id) {
        sanctionService.deleteById(id);
    }

}