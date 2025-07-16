package com.example.bibliotheque.Repositories;


import com.example.bibliotheque.Models.Sanction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SanctionRepository extends JpaRepository<Sanction, Integer> {
    List<Sanction> findByUtilisateurId(Integer utilisateurId);

    @Query("SELECT s FROM Sanction s WHERE :date BETWEEN s.dateDebutSanction AND s.dateFinSanction")
    List<Sanction> findSanctionsActives(LocalDate date);

    @Query("SELECT s FROM Sanction s WHERE s.utilisateur.id=:id AND CURRENT_DATE BETWEEN s.dateDebutSanction AND s.dateFinSanction ORDER BY s.dateFinSanction DESC")
    List<Sanction> findSanctionsActivesByUserNow(Integer id);

    @Query("SELECT s FROM Sanction s WHERE s.dateFinSanction > CURRENT_DATE")
    List<Sanction> findSanctionsNonExpirees();
}
