package com.example.bibliotheque.Services;

import com.example.bibliotheque.Models.SanctionTypeAdherent;
import com.example.bibliotheque.Repositories.SanctionTypeAdherentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SanctionTypeAdherentService {

    @Autowired
    private SanctionTypeAdherentRepository sanctionTypeAdherentRepository;

    /**
     * Récupérer tous les Sanctions
     * 
     * @return Liste de tous les Sanctions
     */
    public List<SanctionTypeAdherent> findAll() {
        return sanctionTypeAdherentRepository.findAll();
    }

    /**
     * Récupérer un Sanction par son ID
     * 
     * @param id Identifiant du Sanction
     * @return Sanction ou null si non trouvé
     */
    public SanctionTypeAdherent findByTypeAdherentId(Integer id) {
        return sanctionTypeAdherentRepository.findByTypeAdherentId(id);
    }

    /**
     * Créer ou mettre à jour un Sanction
     * 
     * @param Sanction Sanction à sauvegarder
     * @return Sanction sauvegardé
     */
    public SanctionTypeAdherent save(SanctionTypeAdherent Sanction) {
        return sanctionTypeAdherentRepository.save(Sanction);
    }

    public void deleteById(Integer id) {
        sanctionTypeAdherentRepository.deleteById(id);
    }

}