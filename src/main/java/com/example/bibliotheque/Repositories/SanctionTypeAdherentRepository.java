package com.example.bibliotheque.Repositories;


import com.example.bibliotheque.Models.SanctionTypeAdherent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanctionTypeAdherentRepository extends JpaRepository<SanctionTypeAdherent, Integer> {
    List<SanctionTypeAdherent> findByTypeAdherentId(Integer typeAdherentId);
}
