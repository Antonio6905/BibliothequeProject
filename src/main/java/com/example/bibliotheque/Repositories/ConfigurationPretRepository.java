package com.example.bibliotheque.Repositories;


import com.example.bibliotheque.Models.ConfigurationPret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationPretRepository extends JpaRepository<ConfigurationPret, Integer> {
    ConfigurationPret findByTypeAdherentId(Integer typeAdherentId);
}