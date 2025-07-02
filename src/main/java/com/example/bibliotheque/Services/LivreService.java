package com.example.bibliotheque.Services;

import com.example.bibliotheque.Models.Livre;
import com.example.bibliotheque.Repositories.LivreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivreService {

    @Autowired
    private LivreRepository livreRepository;

    /**
     * Récupérer tous les Livres
     * 
     * @return Liste de tous les livres
     */
    public List<Livre> findAll() {
        return livreRepository.findAll();
    }

    /**
     * Récupérer un livre par son ID
     * 
     * @param id Identifiant du livre
     * @return Livre ou null si non trouvé
     */
    public Livre findById(Integer id) {
        return livreRepository.findById(id).orElse(null);
    }


    /**
     * Créer ou mettre à jour un livre
     * 
     * @param Livre Livre à sauvegarder
     * @return Livre sauvegardé
     */
    public Livre save(Livre Livre) {
        return livreRepository.save(Livre);
    }

    
    public void deleteById(Integer id) {
        livreRepository.deleteById(id);
    }

}