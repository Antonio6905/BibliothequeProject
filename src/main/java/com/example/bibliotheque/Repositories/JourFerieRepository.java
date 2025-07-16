package com.example.bibliotheque.Repositories;

import com.example.bibliotheque.Models.JourFerie;
import com.example.bibliotheque.Models.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface JourFerieRepository extends JpaRepository<JourFerie, Integer> {
    Optional<JourFerie> findFirstByDateFerie(LocalDate date);
}
