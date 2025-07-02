package com.example.bibliotheque.Controllers;

import com.example.bibliotheque.Models.TypeAdherent;
import com.example.bibliotheque.Models.Utilisateur;
import com.example.bibliotheque.Services.TypeAdherentService;
import com.example.bibliotheque.Services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
public class IndexController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private TypeAdherentService typeAdherentService;

    @GetMapping({ "/login", "/" })
    public String showLoginPage(HttpSession session) {
        if (session.getAttribute("utilisateur") != null) {
            return "redirect:/home";
        }
        return "login";
    }

    @PostMapping("/login")
    public String verifyUser(@RequestParam String username, @RequestParam String password, HttpSession session,
            RedirectAttributes redirectAttributes) {
        Utilisateur utilisateur = utilisateurService.authentifier(username, password);
        if (utilisateur != null) {
            session.setAttribute("utilisateur", utilisateur);
            return "redirect:/home";
        } else {
            redirectAttributes.addAttribute("error", "true");
            return "redirect:/login";
        }
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/inscription")
    public String showInscriptionPage(Model model, HttpSession session) {
        // Si un utilisateur est déjà connecté, rediriger vers /home
        if (session.getAttribute("utilisateur") != null) {
            return "redirect:/home";
        }
        // Passer la liste des types d'adhérents au formulaire
        model.addAttribute("typeAdherents", typeAdherentService.findAll());
        return "form/inscription";
    }

    @PostMapping("/inscription")
    public String processInscription(
            @RequestParam String nom,
            @RequestParam String prenom,
            @RequestParam String password,
            @RequestParam Integer typeAdherentId,
            RedirectAttributes redirectAttributes) {
        // Vérifier si le nom existe déjà
        if (utilisateurService.existsByNom(nom)) {
            redirectAttributes.addAttribute("error", "Nom d'utilisateur déjà utilisé.");
            return "redirect:/inscription";
        }

        // Créer un nouvel utilisateur
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(nom);
        utilisateur.setPrenom(prenom);
        utilisateur.setPassword(password); // Le mot de passe sera haché dans le service
        TypeAdherent typeAdherent = typeAdherentService.findById(typeAdherentId);
        if (typeAdherent == null) {
            redirectAttributes.addAttribute("error", "Type d'adhérent invalide.");
            return "redirect:/inscription";
        }
        utilisateur.setTypeAdherent(typeAdherent);

        // Sauvegarder l'utilisateur
        utilisateurService.saveUtilisateur(utilisateur);
        return "redirect:/login";
    }
}