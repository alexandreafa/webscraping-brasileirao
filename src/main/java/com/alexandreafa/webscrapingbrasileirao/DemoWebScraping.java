package com.alexandreafa.webscrapingbrasileirao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
class Passing {
    private String time;
    private String pontos;
    private String jogos;
    private String vitoria;
    private String empate;
    private String derrota;
    private String golsPro;
    private String golsContra;
    private String saldoDeGols;
}

public class DemoWebScraping {
    public static void main(String[] args) throws IOException {
        String url = "https://www.brasileirao.com.br/";

        Document doc = Jsoup.connect(url).get();

        Element table = doc.getElementsByTag("table").first();

        assert table != null;
        Element tbody = table.getElementsByTag("tbody").first();

        assert tbody != null;
        List<Element> times = tbody.getElementsByTag("tr");

        List<Passing> objetosTimes = new ArrayList<>();

        for (Element time: times) {
            List<Element> atributos = time.getElementsByTag("td");

            Passing passing = new Passing(
                atributos.get(0).getElementsByClass("time__nome").text(),
                atributos.get(1).text(),
                atributos.get(2).text(),
                atributos.get(3).text(),
                atributos.get(4).text(),
                atributos.get(5).text(),
                atributos.get(6).text(),
                atributos.get(7).text(),
                atributos.get(8).text()
            );
            objetosTimes.add(passing);
        }
        for (Passing passing: objetosTimes) {
            converterToJson(passing);
        }
    }

    private static void converterToJson(Passing passing) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(passing);
            System.out.println("Objeto em JSON: " + json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}


