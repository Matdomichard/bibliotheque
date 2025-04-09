package com.kodeos.bibliotheque;

import com.kodeos.bibliotheque.livre.Livre;
import com.kodeos.bibliotheque.livre.LivreRepository;
import com.kodeos.bibliotheque.livre.LivreService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LivreServiceTest {

    @Mock
    private LivreRepository livreRepository;

    @InjectMocks
    private LivreService livreService;

    @Test
    void testAjouterLivre() {
        Livre livre = new Livre("Niourk","Stephan Wul");
        livreService.ajouterLivre(livre);
        verify(livreRepository).save(livre); // on vérifie que le livre ait bien été ajouté au mock du repository
    }

    @Test
    void testEmprunterLivreQuandDisponible() {
        // Given
        Livre livre = new Livre("Fondation", "Isaac Asimov");
        livre.setId(1L);
        livre.setDisponible(true);

        when(livreRepository.findById(1L)).thenReturn(Optional.of(livre));

        // When
        boolean result = livreService.emprunterLivre(1L);

        // Then
        assertTrue(result); // l'emprunt a réussi
        assertFalse(livre.isDisponible()); // le livre est marqué comme emprunté
        verify(livreRepository).save(livre); // le livre est bien sauvegardé
    }


    @Test
    void testEmprunterLivreQuandIndisponible() {
        // Given
        Livre livre = new Livre("Fondation", "Isaac Asimov");
        livre.setId(1L);
        livre.setDisponible(false);

        when(livreRepository.findById(1L)).thenReturn(Optional.of(livre));

        // When
        boolean result = livreService.emprunterLivre(1L);

        // Then
        assertFalse(result); // l'emprunt échoue
        verify(livreRepository, never()).save(any()); // rien n'est sauvegardé
    }


    @Test
    void testRetournerLivreQuandExistant() {
        // Given
        Livre livre = new Livre("Dune", "Frank Herbert");
        livre.setId(2L);
        livre.setDisponible(false); // emprunté

        when(livreRepository.findById(2L)).thenReturn(Optional.of(livre));

        // When
        boolean result = livreService.retournerLivre(2L);

        // Then
        assertTrue(result);
        assertTrue(livre.isDisponible()); // le livre est dispo à nouveau
        verify(livreRepository).save(livre);
    }

    @Test
    void testRetournerLivreQuandInexistant() {
        // Given
        when(livreRepository.findById(99L)).thenReturn(Optional.empty());

        // When
        boolean result = livreService.retournerLivre(99L);

        // Then
        assertFalse(result);
        verify(livreRepository, never()).save(any());
    }


    @Test
    void testListerLivresDisponibles() {
        // Given
        Livre livre1 = new Livre("1984", "George Orwell");
        Livre livre2 = new Livre("L'Écume des jours", "Boris Vian");
        livre1.setDisponible(true);
        livre2.setDisponible(true);

        List<Livre> livresDisponibles = List.of(livre1, livre2);

        when(livreRepository.findByDisponibleTrue()).thenReturn(livresDisponibles);

        // When
        List<Livre> result = livreService.listerLivresDisponibles();

        // Then
        assertEquals(2, result.size());
        assertTrue(result.contains(livre1));
        assertTrue(result.contains(livre2));
        verify(livreRepository).findByDisponibleTrue();
    }

    @Test
    void testLivreExisteQuandPresent() {
        // Given
        Long livreId = 1L;
        when(livreRepository.existsById(livreId)).thenReturn(true);

        // When
        boolean result = livreService.livreExiste(livreId);

        // Then
        assertTrue(result);
        verify(livreRepository).existsById(livreId);
    }

    @Test
    void testLivreExisteQuandAbsent() {
        // Given
        Long livreId = 42L;
        when(livreRepository.existsById(livreId)).thenReturn(false);

        // When
        boolean result = livreService.livreExiste(livreId);

        // Then
        assertFalse(result);
        verify(livreRepository).existsById(livreId);
    }

}
