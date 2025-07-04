package com.example.bibliotheque.Services;

import com.example.bibliotheque.Models.Utilisateur;
import com.example.bibliotheque.Repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Utilisateur authentifier(String nom, String password) {
        return utilisateurRepository.findByNom(nom)
                .filter(u -> passwordEncoder.matches(password, u.getPassword()))
                .orElse(null);
    }

    public boolean existsByNom(String nom) {
        return utilisateurRepository.existsByNom(nom);
    }

    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
        // Hacher le mot de passe avant de sauvegarder
        utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
        return utilisateurRepository.save(utilisateur);
    }

    public Utilisateur findById(Integer id) {
        return utilisateurRepository.findById(id).orElse(null);
    }

    public Utilisateur findIfSanctionne(Integer id) {
        return utilisateurRepository.findUserSanctionne(id).orElse(null);
    }

    
    public void deleteById(Integer id) {
        utilisateurRepository.deleteById(id);
    }

    public Integer getQuota(Integer id) {
        return utilisateurRepository.countQuota(id);
    }
}