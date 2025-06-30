package com.example.bibliotheque.Repositories;


import com.example.bibliotheque.Models.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Integer> {
    Categorie findByNomCategorie(String nomCategorie);
}