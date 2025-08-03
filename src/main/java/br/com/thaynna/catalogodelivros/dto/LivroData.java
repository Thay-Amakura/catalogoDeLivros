package br.com.thaynna.catalogodelivros.dto;

import java.util.List;

public class LivroData {
    private Integer id;
    private String title;
    private List<AutorData> authors;  // ✅ CORRETO
    private List<String> languages;

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<AutorData> getAuthors() {
        return authors; // ✅ Aqui deve retornar List<AutorData>
    }

    public List<String> getLanguages() {
        return languages;
    }
}