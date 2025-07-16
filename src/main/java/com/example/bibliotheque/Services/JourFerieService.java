package com.example.bibliotheque.Services;

import com.example.bibliotheque.Models.Abonnement;
import com.example.bibliotheque.Models.JourFerie;
import com.example.bibliotheque.Repositories.AbonnementRepository;
import com.example.bibliotheque.Repositories.JourFerieRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class JourFerieService {

    @Autowired
    private JourFerieRepository jourFerieRepository;

    public JourFerie saveAbonnement(JourFerie jourFerie) {
        return jourFerieRepository.save(jourFerie);
    }


    public Boolean isFerie(LocalDate date) {
        return jourFerieRepository.findFirstByDateFerie(date).isPresent();
    }

}