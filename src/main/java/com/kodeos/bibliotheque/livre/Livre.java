package com.kodeos.bibliotheque.livre;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity //  indique que c’est une entité persistée dans la base
@Data //ajoute getters/setters/toString automatiquement
public class Livre {

    // Id et GeneratedValue identifiant auto-généré
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String auteur;
    private boolean disponible;

}
