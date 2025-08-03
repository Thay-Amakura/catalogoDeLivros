package br.com.thaynna.catalogodelivros.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Service;

import br.com.thaynna.catalogodelivros.dto.ApiData;
import br.com.thaynna.catalogodelivros.dto.LivroData;
import br.com.thaynna.catalogodelivros.modelos.Livro;

@Service
public class ConsumoAPI {

    public String obterDados(String endereco) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String json = response.body();
        return json;
    }

    public Livro buscarLivroPorTitulo(String titulo) {
        String json = obterDados("http://gutendex.com/books/?search=" + titulo.replace(" ", "%20"));
        ApiData apiData = new ConverteDados().obterDados(json, ApiData.class);

        if (apiData.getCount() > 0 && !apiData.getResults().isEmpty()) {
            LivroData livroData = apiData.getResults().get(0);
            Livro livro = new Livro();
            livro.setPublicId(livroData.getId());
            livro.setTitulo(livroData.getTitle());
            return livro;
        } else {
            throw new RuntimeException("Livro não encontrado: " + titulo);
        }
    }
}