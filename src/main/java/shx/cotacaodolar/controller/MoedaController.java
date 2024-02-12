package shx.cotacaodolar.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import shx.cotacaodolar.model.Moeda;
import shx.cotacaodolar.service.MoedaService;


@RestController
@RequestMapping(value = "/")
public class MoedaController {

    @Autowired
    private MoedaService moedaService;

    @GetMapping("/moeda/cotacao/{data}")
    public Moeda getCotacoesData(@PathVariable("data") String data) throws IOException, MalformedURLException, ParseException{
        return moedaService.getCotacao(LocalDate.parse(data, DateTimeFormatter.ofPattern("MM-dd-yyyy")));
    }

    @GetMapping("/moeda/atual")
    public Moeda getCotacaoAtual() throws IOException, MalformedURLException, ParseException{
        return moedaService.getCotacao(null);
    }

    @GetMapping("/moeda/{data1}&{data2}")
    public List<Moeda> getCotacoesPeriodo(@PathVariable("data1") String startDate, @PathVariable("data2") String endDate) throws IOException, MalformedURLException, ParseException{
        return moedaService.getCotacoesPeriodo(startDate, endDate, false);
    }

    @GetMapping("/moeda/cotacao-menor/{data1}&{data2}")
    public List<Moeda> getCotacoesMenoresAtual(@PathVariable("data1") String startDate, @PathVariable("data2") String endDate) throws IOException, MalformedURLException, ParseException{
        return moedaService.getCotacoesPeriodo(startDate, endDate, true);
    }
}
