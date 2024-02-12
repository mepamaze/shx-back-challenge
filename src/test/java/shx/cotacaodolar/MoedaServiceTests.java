package shx.cotacaodolar;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import shx.cotacaodolar.model.Moeda;
import shx.cotacaodolar.service.MoedaService;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class MoedaServiceTests {
    @Autowired
    private MoedaService moedaService;

    @Test
    public void testGetCotacao() throws IOException, MalformedURLException, ParseException {
        Moeda cotacao = moedaService.getCotacao(LocalDate.of(2024, 1, 2));
        assertNotNull(cotacao);
        assertEquals("02/01/2024", cotacao.data);
        assertTrue(cotacao.preco_compra > 0);
        assertTrue(cotacao.preco_venda > 0);
    }

    @Test
    public void testGetCotacaoWithNullDate() throws IOException, MalformedURLException, ParseException {
        Moeda cotacao = moedaService.getCotacao(null);
        assertNotNull(cotacao);
        assertTrue(cotacao.preco_compra > 0);
        assertTrue(cotacao.preco_venda > 0);
    }

    @Test
    public void testGetCotacaoWithNoDataAvailable() throws IOException, MalformedURLException, ParseException {
        Moeda cotacao = moedaService.getCotacao(LocalDate.of(2025, 1, 1));
        assertNotNull(cotacao);
    }

    @Test
    public void testGetCotacoesPeriodo() throws IOException, MalformedURLException, ParseException {
        List<Moeda> cotacoes = moedaService.getCotacoesPeriodo("01-01-2024", "01-31-2024", false);
        assertNotNull(cotacoes);
        assertFalse(cotacoes.isEmpty());
        for (Moeda moeda : cotacoes) {
            assertNotNull(moeda.data);
            assertTrue(moeda.preco_compra > 0); 
            assertTrue(moeda.preco_venda > 0); 
        }
    }

    @Test
    public void testGetCotacoesPeriodoWithFilter() throws IOException, MalformedURLException, ParseException {
        List<Moeda> cotacoes = moedaService.getCotacoesPeriodo("01-01-2024", "01-31-2024", true);
        assertNotNull(cotacoes);
        assertFalse(cotacoes.isEmpty());
        for (Moeda moeda : cotacoes) {
            assertTrue(moeda.preco_compra < 6.0);
        }
    }

    @Test
    public void testGetCotacoesPeriodoWithNoDataAvailable() throws IOException, MalformedURLException, ParseException {
        List<Moeda> cotacoes = moedaService.getCotacoesPeriodo("2040-01-01", "2050-01-10", false);
        assertNotNull(cotacoes);
        assertTrue(cotacoes.isEmpty());
    }
}
