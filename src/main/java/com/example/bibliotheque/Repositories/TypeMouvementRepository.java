package com.example.bibliotheque.Repositories;

import com.example.bibliotheque.Models.TypeMouvement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeMouvementRepository extends JpaRepository<TypeMouvement, Integer> {
    TypeMouvement findByLibelle(String libelle);
}
