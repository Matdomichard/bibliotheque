package com.kodeos.bibliotheque.livre;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivreService {

    private final LivreRepository livreRepository;

    public LivreService(LivreRepository livreRepository) {
        this.livreRepository = livreRepository;
    }

    public void ajouterLivre(Livre livre) {
        livreRepository.save(livre);
    }

    public boolean emprunterLivre(Long livreId) {
        return livreRepository.findById(livreId)
                .filter(Livre::isDisponible)
                .map(livre -> {
                    livre.setDisponible(false);
                    livreRepository.save(livre);
                    return true;
                })
                .orElse(false);
    }

    public boolean retournerLivre(Long livreId) {
        return livreRepository.findById(livreId)
                .map(livre -> {
                    livre.setDisponible(true);
                    livreRepository.save(livre);
                    return true;
                })
                .orElse(false); // livre introuvable
    }

    public List<Livre> listerLivresDisponibles() {
        return livreRepository.findByDisponibleTrue();
    }

    public boolean livreExiste(Long livreId) {
        return livreRepository.existsById(livreId);
    }


}
