package com.example.bibliotheque.Repositories;


import com.example.bibliotheque.Models.TypeLivre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeLivreRepository extends JpaRepository<TypeLivre, Integer> {
    TypeLivre findByNomType(String nomType);
}
