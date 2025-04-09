package com.kodeos.bibliotheque.livre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivreRepository extends JpaRepository<Livre, Long> {

    List<Livre> findByDisponibleTrue(); // livres disponibles
    List<Livre> findByAuteur(String auteur); //livres d'un auteur
    Optional<Livre> findByTitre(String titre); //un livre par titre

}
