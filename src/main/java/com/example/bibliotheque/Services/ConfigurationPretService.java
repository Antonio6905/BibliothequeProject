package com.example.bibliotheque.Services;

import com.example.bibliotheque.Models.ConfigurationPret;
import com.example.bibliotheque.Repositories.ConfigurationPretRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigurationPretService {

    @Autowired
    private ConfigurationPretRepository configurationPretRepository;

    /**
     * Récupérer tous les Livres
     * 
     * @return Liste de tous les livres
     */
    public List<ConfigurationPret> findAll() {
        return configurationPretRepository.findAll();
    }

    public ConfigurationPret findByTypeAdherent(Integer id) {
        return configurationPretRepository.findByTypeAdherentId(id);
    }

    /**
     * Récupérer un livre par son ID
     * 
     * @param id Identifiant du livre
     * @return configurationPret ou null si non trouvé
     */
    public ConfigurationPret findById(Integer id) {
        return configurationPretRepository.findById(id).orElse(null);
    }

    /**
     * Créer ou mettre à jour un livre
     * 
     * @param configurationPret configurationPret à sauvegarder
     * @return configurationPret sauvegardé
     */
    public ConfigurationPret save(ConfigurationPret configurationPret) {
        return configurationPretRepository.save(configurationPret);
    }

    public void deleteById(Integer id) {
        configurationPretRepository.deleteById(id);
    }

}