package br.com.thaynna.catalogodelivros.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AutorData {
    private String name;
    private Integer birthYear;  // Novo campo

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getbirthYear() {
        return birthYear;
    }

    public void setbirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }
}

