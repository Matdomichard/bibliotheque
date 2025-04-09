package com.kodeos.bibliotheque.livre;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity //  indique que c’est une entité persistée dans la base
@Data //ajoute getters/setters/toString automatiquement
@NoArgsConstructor
public class Livre {

    // Id et GeneratedValue identifiant auto-généré
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String auteur;
    private boolean disponible;


    public Livre(String titre, String auteur) {
        this.titre = titre;
        this.auteur = auteur;
        this.disponible = true; // par défaut disponible à l’ajout
    }


}
