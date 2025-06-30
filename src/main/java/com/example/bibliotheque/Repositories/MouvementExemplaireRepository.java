package com.example.bibliotheque.Repositories;

import com.example.bibliotheque.Models.MouvementExemplaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MouvementExemplaireRepository extends JpaRepository<MouvementExemplaire, Integer> {
    List<MouvementExemplaire> findByExemplaireId(Integer exemplaireId);

    List<MouvementExemplaire> findByTypeMouvementId(Integer typeMouvementId);
}
