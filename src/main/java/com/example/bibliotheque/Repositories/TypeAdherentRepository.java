package com.example.bibliotheque.Repositories;


import com.example.bibliotheque.Models.TypeAdherent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeAdherentRepository extends JpaRepository<TypeAdherent, Integer> {
    // Méthodes personnalisées si nécessaire
    TypeAdherent findByNomType(String nomType);
}
