package com.example.bibliotheque.Repositories;


import com.example.bibliotheque.Models.TypeAdherent;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeAdherentRepository extends JpaRepository<TypeAdherent, Integer> {
    // Méthodes personnalisées si nécessaire
    TypeAdherent findByNomType(String nomType);

    @Query(value = "SELECT t.* FROM type_adherent t JOIN type_livre_autorise tla ON t.id=tla.id_type_adherent WHERE tla.id_type_adherent=:adherent_id AND tla.id_type_livre=:livre ",nativeQuery = true)
    public Optional<TypeAdherent> findIfCanBorrow(@Param("adherent_id") Integer typeAdherent,
            @Param("livre") Integer id_type_livre);
}
