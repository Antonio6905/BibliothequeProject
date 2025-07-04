package com.example.bibliotheque.Services;

import com.example.bibliotheque.Models.Livre;
import com.example.bibliotheque.Models.TypeLivre;
import com.example.bibliotheque.Repositories.LivreRepository;
import com.example.bibliotheque.Repositories.TypeLivreRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeLivreService {

    @Autowired
    private TypeLivreRepository typeLivreRepository;

    /**
     * Récupérer tous les Livres
     * 
     * @return Liste de tous les livres
     */
    public List<TypeLivre> findAll() {
        return typeLivreRepository.findAll();
    }

    /**
     * Récupérer un livre par son ID
     * 
     * @param id Identifiant du livre
     * @return Livre ou null si non trouvé
     */
    public TypeLivre findById(Integer id) {
        return typeLivreRepository.findById(id).orElse(null);
    }

    /**
     * Créer ou mettre à jour un livre
     * 
     * @param Livre Livre à sauvegarder
     * @return Livre sauvegardé
     */
    public TypeLivre save(TypeLivre Livre) {
        return typeLivreRepository.save(Livre);
    }

    public void deleteById(Integer id) {
        typeLivreRepository.deleteById(id);
    }

}