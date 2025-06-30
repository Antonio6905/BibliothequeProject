package com.example.bibliotheque.Repositories;


import com.example.bibliotheque.Models.Retour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetourRepository extends JpaRepository<Retour, Integer> {
    Retour findByPretId(Integer pretId);
}