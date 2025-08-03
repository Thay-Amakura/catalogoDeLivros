package br.com.thaynna.catalogodelivros.service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.thaynna.catalogodelivros.dto.LivroData;
import br.com.thaynna.catalogodelivros.modelos.Autor;
import br.com.thaynna.catalogodelivros.modelos.Idioma;
import br.com.thaynna.catalogodelivros.modelos.Livro;
import br.com.thaynna.catalogodelivros.repositorio.AutorRepository;
import br.com.thaynna.catalogodelivros.repositorio.IdiomaRepository;
import br.com.thaynna.catalogodelivros.repositorio.LivroRepository;

@Service
public class LivroService {
    private static final Logger logger = LoggerFactory.getLogger(LivroService.class);
    
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final IdiomaRepository idiomaRepository;

    public LivroService(LivroRepository livroRepository,
                        AutorRepository autorRepository,
                        IdiomaRepository idiomaRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.idiomaRepository = idiomaRepository;
    }

    public void saveBooks(List<LivroData> livros) {
        if (livros == null || livros.isEmpty()) {
            logger.warn("Lista de livros vazia ou nula");
            return;
        }

        livros.stream()
            .filter(Objects::nonNull)
            .filter(l -> l.getId() != null && l.getTitle() != null)
            .forEach(this::processarLivro);
    }

    private void processarLivro(LivroData livroData) {
        try {
            List<Autor> autores = processarAutores(livroData);
            List<Idioma> idiomas = processarIdiomas(livroData);

            Livro novoLivro = new Livro();
            novoLivro.setPublicId(livroData.getId());
            novoLivro.setTitulo(livroData.getTitle());
            novoLivro.setAutores(autores);
            novoLivro.setIdiomas(idiomas);

            livroRepository.save(novoLivro);
            logger.info("Livro salvo: {}", novoLivro.getTitulo());
        } catch (Exception e) {
            logger.error("Erro ao processar livro: {}", livroData.getTitle(), e);
        }
    }

    private List<Autor> processarAutores(LivroData livroData) {
        return Optional.ofNullable(livroData.getAuthors())
            .orElse(Collections.emptyList())
            .stream()
            .filter(Objects::nonNull)
            .map(autorData -> autorRepository.findByNome(autorData.getName())
                .orElseGet(() -> {
                    Autor novoAutor = new Autor();
                    novoAutor.setNome(autorData.getName());
                    return autorRepository.save(novoAutor);
                }))
            .toList();
    }

    private List<Idioma> processarIdiomas(LivroData livroData) {
        return Optional.ofNullable(livroData.getLanguages())
            .orElse(Collections.emptyList())
            .stream()
            .filter(Objects::nonNull)
            .map(codigo -> idiomaRepository.findByCodigo(codigo)
                .orElseGet(() -> {
                    Idioma novoIdioma = new Idioma(codigo);
                    return idiomaRepository.save(novoIdioma);
                }))
            .toList();
    }
}