package com.example.bibliotheque.Repositories;

import com.example.bibliotheque.Models.SuiviStatutProlongement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuiviStatutProlongementRepository extends JpaRepository<SuiviStatutProlongement, Integer> {
    List<SuiviStatutProlongement> findByProlongementId(Integer prolongementId);
}
