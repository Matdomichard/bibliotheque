package com.kodeos.bibliotheque.livre;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Livre> ajouterLivre(@RequestBody Livre livre){
        Livre saved = livreService.ajouterLivre(livre);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }


    @PostMapping("/{id}/emprunter")
    public ResponseEntity<Map<String, String>> emprunterLivre(@PathVariable Long id) {
        if (livreService.emprunterLivre(id)) {
            // Renvoie {"message": "Livre emprunté"} en JSON
            return ResponseEntity.ok(Map.of("message", "Livre emprunté"));
        } else if (livreService.livreExiste(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "Livre déjà emprunté"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Livre introuvable"));
        }
    }


    @PostMapping("/{id}/retourner")
    public ResponseEntity<Map<String, String>> retournerLivre(@PathVariable Long id) {
        boolean retourReussi = livreService.retournerLivre(id);
        if (retourReussi) {
            return ResponseEntity.ok(Map.of("message", "Livre retourné"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Livre introuvable"));
        }
    }



}
