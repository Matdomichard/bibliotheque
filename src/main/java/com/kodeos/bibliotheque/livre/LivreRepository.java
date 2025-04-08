package com.kodeos.bibliotheque.livre;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivreRepository extends JpaRepository<Livre, Long> {

    List<Livre> findByDisponibleTrue(); // livres disponibles
    List<Livre> findByAuteur(String auteur); //livres d'un auteur
    Livre findByTitre(String titre); //un livre par titre

}
