package br.com.thaynna.catalogodelivros.modelos;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "idiomas")
public class Idioma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", unique = true, nullable = false, length = 5)
    private String codigo; // ex: "en", "pt", "fr"

    public Idioma() {
    }

    public Idioma(String codigo) {
        this.codigo = codigo;
    }

    public Long getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Idioma)) return false;
        Idioma idioma = (Idioma) o;
        return Objects.equals(codigo, idioma.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "Idioma{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                '}';
    }
}
