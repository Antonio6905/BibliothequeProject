package com.example.bibliotheque.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdherentDto {

    private Integer numero;
    private String nom;
    private String prenom;
    private LocalDate dtn;
    private Integer nbPret;
    private Integer quota;
    private Boolean isSanctionne;
    private Boolean isAbonne;

}