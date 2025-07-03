package com.example.bibliotheque.Services;

import com.example.bibliotheque.Models.TypeAdherent;
import com.example.bibliotheque.Repositories.TypeAdherentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeAdherentService {

    @Autowired
    private TypeAdherentRepository typeAdherentRepository;

    /**
     * Récupérer tous les types d'adhérents
     * 
     * @return Liste de tous les types d'adhérents
     */
    public List<TypeAdherent> findAll() {
        return typeAdherentRepository.findAll();
    }

    /**
     * Récupérer un type d'adhérent par son ID
     * 
     * @param id Identifiant du type d'adhérent
     * @return TypeAdherent ou null si non trouvé
     */
    public TypeAdherent findById(Integer id) {
        return typeAdherentRepository.findById(id).orElse(null);
    }

    /**
     * Récupérer un type d'adhérent par son nom
     * 
     * @param nomType Nom du type d'adhérent
     * @return TypeAdherent ou null si non trouvé
     */
    public TypeAdherent findByNomType(String nomType) {
        return typeAdherentRepository.findByNomType(nomType);
    }

    public TypeAdherent findIfCanBorrow(Integer id,Integer typeLivre) {
        return typeAdherentRepository.findIfCanBorrow(id,typeLivre).orElse(null);
    }

    /**
     * Créer ou mettre à jour un type d'adhérent
     * 
     * @param typeAdherent TypeAdherent à sauvegarder
     * @return TypeAdherent sauvegardé
     */
    public TypeAdherent save(TypeAdherent typeAdherent) {
        return typeAdherentRepository.save(typeAdherent);
    }

    /**
     * Supprimer un type d'adhérent par son ID
     * 
     * @param id Identifiant du type d'adhérent
     */
    public void deleteById(Integer id) {
        typeAdherentRepository.deleteById(id);
    }

    /**
     * Vérifier si un type d'adhérent existe par son nom
     * 
     * @param nomType Nom du type d'adhérent
     * @return true si existe, false sinon
     */
    public boolean existsByNomType(String nomType) {
        return typeAdherentRepository.findByNomType(nomType) != null;
    }
}