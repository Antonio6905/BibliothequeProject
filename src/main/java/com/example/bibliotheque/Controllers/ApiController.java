package com.example.bibliotheque.Controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.bibliotheque.Models.Exemplaire;
import com.example.bibliotheque.Models.Livre;
import com.example.bibliotheque.Services.ExemplaireService;
import com.example.bibliotheque.Services.LivreService;
import com.example.bibliotheque.dto.ExemplaireDto;
import com.example.bibliotheque.dto.LivreDto;

@RestController
public class ApiController {

    @Autowired
    private LivreService livreService;

    @Autowired 
    private ExemplaireService exemplaireService;

    @GetMapping("/api/livre/{livreId}")
    public ResponseEntity<LivreDto> testApi(@PathVariable Integer livreId) {
        Livre livre = livreService.findById(livreId);
        if (livre == null) {
            return ResponseEntity.notFound().build();
        }

        LivreDto dto = new LivreDto();
        dto.setNom(livre.getNom());
        dto.setDescription(livre.getDescription());
        dto.setDateEdition(livre.getDateEdition());
        
        for (Exemplaire exemplaire : livre.getExemplaires()) {
            ExemplaireDto ex = new ExemplaireDto();
            ex.setNumero(exemplaire.getId());
            ex.setDisponible(exemplaireService.checkDispo(exemplaire.getId(), LocalDate.now()));
            dto.getExemplaire().add(ex);
        }
        
        return ResponseEntity.ok(dto);
    }

}