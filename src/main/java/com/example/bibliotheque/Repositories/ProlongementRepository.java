package com.example.bibliotheque.Repositories;


import com.example.bibliotheque.Models.Prolongement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProlongementRepository extends JpaRepository<Prolongement, Integer> {
    Prolongement findByPretId(Integer pretId);

    @Query(value="SELECT p.* FROM prolongement p WHERE p.id IN (SELECT prolongement_id FROM vue_prolongements_non_traites)",nativeQuery=true)
    List<Prolongement> findNonTraite();
}
