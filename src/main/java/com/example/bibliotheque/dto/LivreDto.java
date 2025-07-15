package com.example.bibliotheque.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LivreDto {

    private String nom;
    private String description;
    private LocalDate dateEdition;
    private List<ExemplaireDto> exemplaire = new ArrayList<ExemplaireDto>();

}