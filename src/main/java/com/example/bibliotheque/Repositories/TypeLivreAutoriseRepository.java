package com.example.bibliotheque.Repositories;

import com.example.bibliotheque.Models.TypeLivreAutorise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeLivreAutoriseRepository extends JpaRepository<TypeLivreAutorise, Integer> {
    List<TypeLivreAutorise> findByTypeAdherentId(Integer typeAdherentId);

    List<TypeLivreAutorise> findByTypeLivreId(Integer typeLivreId);

    @Query("SELECT tla FROM TypeLivreAutorise tla WHERE tla.typeAdherent.id = :typeAdherentId AND tla.typeLivre.id = :typeLivreId")
    TypeLivreAutorise findByTypeAdherentIdAndTypeLivreId(Integer typeAdherentId, Integer typeLivreId);
}