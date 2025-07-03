package com.example.bibliotheque.Repositories;


import com.example.bibliotheque.Models.Prolongement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProlongementRepository extends JpaRepository<Prolongement, Integer> {
    Prolongement findByPretId(Integer pretId);
}
