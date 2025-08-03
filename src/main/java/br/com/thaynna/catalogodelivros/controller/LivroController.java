package br.com.thaynna.catalogodelivros.controller;

import org.springframework.web.bind.annotation.*;

import br.com.thaynna.catalogodelivros.modelos.Autor;
import br.com.thaynna.catalogodelivros.modelos.Livro;
import br.com.thaynna.catalogodelivros.repositorio.AutorRepository;
import br.com.thaynna.catalogodelivros.repositorio.LivroRepository;
import br.com.thaynna.catalogodelivros.service.ConsumoAPI;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LivroController {

    private final ConsumoAPI consumoAPI;
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    public LivroController(ConsumoAPI consumoAPI, LivroRepository livroRepository, AutorRepository autorRepository) {
        this.consumoAPI = consumoAPI;
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    // 1. Buscar livro por título na API e salvar
    @PostMapping("/livros/buscar")
    public Livro buscarELancarLivro(@RequestParam String titulo) {
        return consumoAPI.buscarLivroPorTitulo(titulo);
    }

    // 2. Listar todos livros
    @GetMapping("/livros")
    public List<Livro> listarLivros() {
        return livroRepository.findAll();
    }

    // 3. Listar autores
    @GetMapping("/autores")
    public List<Autor> listarAutores() {
        return autorRepository.findAll();
    }

    // 4. Listar autores por ano (vivos em determinado ano)
    @GetMapping("/autores/ano")
    public List<Autor> listarAutoresPorAno(@RequestParam Integer ano) {
        return autorRepository.findByAnoNascimentoLessThanEqualAndAnoFalecimentoGreaterThanEqual(ano, ano);
    }

    // 5. Listar livros por idioma
    @GetMapping("/livros/idioma")
    public List<Livro> listarLivrosPorIdioma(@RequestParam String codigoIdioma) {
        return livroRepository.findByIdiomas(codigoIdioma.toLowerCase());
    }
}
