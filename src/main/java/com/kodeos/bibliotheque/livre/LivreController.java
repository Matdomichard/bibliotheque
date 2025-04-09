package com.kodeos.bibliotheque.livre;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/livres")
public class LivreController {

    public final LivreService livreService;


    public LivreController(LivreService livreService) {
        this.livreService = livreService;
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<Livre>> listerLivresDisponibles() {
        return ResponseEntity.ok(livreService.listerLivresDisponibles());
    }

    @PostMapping
    public ResponseEntity<Void> ajouterLivre(@RequestBody Livre livre){
        livreService.ajouterLivre(livre);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PostMapping("/{id}/emprunter")
    public ResponseEntity<String> emprunterLivre(@PathVariable Long id) {
        if (livreService.emprunterLivre(id)) {
            return ResponseEntity.ok("Livre emprunté");
        } else if (livreService.livreExiste(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Livre déjà emprunté");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livre introuvable");
        }
    }

    @PostMapping("/{id}/retourner")
    public ResponseEntity<String> retournerLivre(@PathVariable Long id) {
        boolean retourReussi = livreService.retournerLivre(id);
        return retourReussi
                ? ResponseEntity.ok("Livre retourné")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livre introuvable");
    }

}
