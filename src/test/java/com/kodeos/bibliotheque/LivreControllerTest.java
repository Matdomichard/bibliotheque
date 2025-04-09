package com.kodeos.bibliotheque;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodeos.bibliotheque.livre.LivreController;
import com.kodeos.bibliotheque.livre.LivreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LivreController.class)
class LivreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LivreService livreService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testEmprunterLivreQuandDisponible() throws Exception {
        when(livreService.emprunterLivre(1L)).thenReturn(true);

        mockMvc.perform(post("/livres/1/emprunter"))
                .andExpect(status().isOk())
                .andExpect(content().string("Livre emprunté"));
    }

    @Test
    void testEmprunterLivreQuandIndisponible() throws Exception {
        when(livreService.emprunterLivre(1L)).thenReturn(false);
        when(livreService.livreExiste(1L)).thenReturn(true);

        mockMvc.perform(post("/livres/1/emprunter"))
                .andExpect(status().isConflict())
                .andExpect(content().string("Livre déjà emprunté"));
    }

    @Test
    void testEmprunterLivreQuandInexistant() throws Exception {
        when(livreService.emprunterLivre(99L)).thenReturn(false);
        when(livreService.livreExiste(99L)).thenReturn(false);

        mockMvc.perform(post("/livres/99/emprunter"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Livre introuvable"));
    }
}

