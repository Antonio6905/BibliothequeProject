package com.example.bibliotheque.Repositories;


import com.example.bibliotheque.Models.SuiviStatutPret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuiviStatutPretRepository extends JpaRepository<SuiviStatutPret, Integer> {
    List<SuiviStatutPret> findByPretId(Integer pretId);

    List<SuiviStatutPret> findByStatutId(Integer statutId);
}
