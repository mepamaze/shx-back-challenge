package shx.cotacaodolar.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import shx.cotacaodolar.model.Moeda;
import shx.cotacaodolar.model.Periodo;
import shx.cotacaodolar.utils.DateTimeUtil;

@Service
public class MoedaService {
    public Moeda getCotacao(LocalDate data_param) throws IOException, MalformedURLException, ParseException {  
        LocalDate dia_cotacao = (data_param != null) ? data_param : LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String dia_cotacao_formatado = dia_cotacao.format(formatter);
        String periodo_verificacao_formatado = dia_cotacao.minusDays(8).format(formatter);
        
        // A cotação do dia corrente só é oficialmente liberada após ás 17:00 em dias úteis pelo banco central.
        String urlString = "https://olinda.bcb.gov.br/olinda/servico/PTAX/versao/v1/odata/CotacaoDolarPeriodo(dataInicial=@dataInicial,dataFinalCotacao=@dataFinalCotacao)?@dataInicial='" + periodo_verificacao_formatado + "'&@dataFinalCotacao='" + dia_cotacao_formatado + "'&$top=1&$orderby=dataHoraCotacao%20desc&$format=json";

        URL url = new URL(urlString);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        JsonElement response = JsonParser.parseReader(new InputStreamReader((InputStream) request.getContent()));

        JsonObject rootObj = response.getAsJsonObject();
        JsonArray cotacaoJson = rootObj.getAsJsonArray("value");

        Moeda cotacao = new Moeda();

        if (cotacaoJson == null || cotacaoJson.size() <= 0) {
            return cotacao;
        }

        JsonObject cotacaoObj = cotacaoJson.get(0).getAsJsonObject();

        cotacao.preco_compra = cotacaoObj.get("cotacaoCompra").getAsDouble();
        cotacao.preco_venda = cotacaoObj.get("cotacaoVenda").getAsDouble();

        String dataHoraCotacaoString = cotacaoObj.get("dataHoraCotacao").getAsString();

        cotacao.data = DateTimeUtil.extrairData(dataHoraCotacaoString);
        cotacao.hora = DateTimeUtil.extrairHora(dataHoraCotacaoString);

        return cotacao;
    }

    // o formato da data que o método recebe é "MM-dd-yyyy"
    public List<Moeda>  getCotacoesPeriodo(String startDate, String endDate, Boolean filter)
            throws IOException, MalformedURLException, ParseException {
        Periodo periodo = new Periodo(startDate, endDate);

        Moeda cotacao_corrente = filter ? getCotacao(null) : null;
        
        String urlString = "https://olinda.bcb.gov.br/olinda/servico/PTAX/versao/v1/odata/CotacaoDolarPeriodo(dataInicial=@dataInicial,dataFinalCotacao=@dataFinalCotacao)?@dataInicial='" + periodo.getDataInicial() + "'&@dataFinalCotacao='" + periodo.getDataFinal() + "'&$top=10000&$format=json" + (filter ? ("&$filter=cotacaoCompra%20lt%20" + cotacao_corrente.preco_compra) : "");

        URL url = new URL(urlString);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        JsonElement response = JsonParser.parseReader(new InputStreamReader((InputStream) request.getContent()));
        JsonObject rootObj = response.getAsJsonObject();
        JsonArray cotacoesArray = rootObj.getAsJsonArray("value");

        List<Moeda> moedasLista = new ArrayList<Moeda>();

        if (cotacoesArray == null || cotacoesArray.size() <= 0) {
            return moedasLista;
        }

        for (JsonElement obj : cotacoesArray) {
            Moeda moedaRef = new Moeda();
            String dataHoraCotacaoString = obj.getAsJsonObject().get("dataHoraCotacao").getAsString();

            moedaRef.preco_compra = obj.getAsJsonObject().get("cotacaoCompra").getAsDouble();
            moedaRef.preco_venda = obj.getAsJsonObject().get("cotacaoVenda").getAsDouble();
            moedaRef.data = DateTimeUtil.extrairData(dataHoraCotacaoString);
            moedaRef.hora = DateTimeUtil.extrairHora(dataHoraCotacaoString);
            moedasLista.add(moedaRef);
        }
        return moedasLista;
    }

}
