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

    @Query(value = "SELECT COALESCE(COUNT(*),0) AS nombre_prolongements\r\n" + //
                "    FROM Prolongement p\r\n" + //
                "    JOIN Pret pr ON p.Id_pret_livre = pr.Id\r\n" + //
                "    JOIN vue_prolongements_non_traites v ON p.id=v.prolongement_id\r\n" + //
                "    WHERE \r\n" + //
                "        pr.Id_Utilisateur = :userId", nativeQuery = true)
    Integer countByUserNonTraite(Integer userId);
}
