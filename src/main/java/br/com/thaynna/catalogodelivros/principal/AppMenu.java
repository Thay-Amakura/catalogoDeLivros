package br.com.thaynna.catalogodelivros.principal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.thaynna.catalogodelivros.modelos.Autor;
import br.com.thaynna.catalogodelivros.modelos.Idioma;
import br.com.thaynna.catalogodelivros.modelos.Livro;
import br.com.thaynna.catalogodelivros.repositorio.AutorRepository;
import br.com.thaynna.catalogodelivros.repositorio.LivroRepository;
import br.com.thaynna.catalogodelivros.service.ConsumoAPI;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class AppMenu implements CommandLineRunner {

    private final ConsumoAPI consumoAPI;
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    private static final Logger logger = LoggerFactory.getLogger(AppMenu.class);


    public AppMenu(ConsumoAPI consumoAPI, LivroRepository livroRepository, AutorRepository autorRepository) {
        this.consumoAPI = consumoAPI;
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {
            exibirMenu();
            logger.info("Escolha o número de sua opção: ");
            if(scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine(); // limpar buffer após nextInt
            } else {
                logger.info("Opção inválida, digite um número.");
                scanner.nextLine(); // limpar entrada inválida
                continue;
            }

            switch (opcao) {
                case 1 -> buscarLivro(scanner);
                case 2 -> listarLivros();
                case 3 -> listarAutores();
                case 4 -> listarAutoresPorAno(scanner);
                case 5 -> listarLivrosPorIdioma(scanner);
                case 0 -> logger.info("Saindo...");
                default -> logger.info("Opção inválida, tente novamente.");
            }

            logger.info("");
        }

        scanner.close();
    }

    private void buscarLivro(Scanner scanner) {
        logger.info("Digite o título do livro: ");
        String titulo = scanner.nextLine();
        Livro livro = consumoAPI.buscarLivroPorTitulo(titulo);
        if (livro != null) {
            logger.info("Livro encontrado e salvo:");
            exibirLivro(livro);
        } else {
            logger.info("Livro não encontrado na API.");
        }
    }

    private void listarLivros() {
        List<Livro> livros = livroRepository.findAll();
        logger.info("Livros registrados:");
        livros.forEach(this::exibirLivro);
    }

    private void listarAutores() {
        List<Autor> autores = autorRepository.findAll();
        logger.info("Autores registrados:");
        autores.forEach(this::exibirAutor);
    }

    private void listarAutoresPorAno(Scanner scanner) {
        logger.info("Digite o ano: ");
        if(scanner.hasNextInt()) {
            int ano = scanner.nextInt();
            scanner.nextLine();
            List<Autor> autoresPorAno = autorRepository.findByAnoNascimentoLessThanEqualAndAnoFalecimentoGreaterThanEqual(ano, ano);
            logger.info("Autores vivos em {}:", ano);
            if (autoresPorAno.isEmpty()) {
                logger.info("Nenhum autor encontrado para esse ano.");
            } else {
                autoresPorAno.forEach(this::exibirAutor);
            }
        } else {
            logger.info("Ano inválido.");
            scanner.nextLine();
        }
    }

    private void listarLivrosPorIdioma(Scanner scanner) {
        logger.info("Digite o idioma (pt, en, es, fr): ");
        String idioma = scanner.nextLine().toLowerCase();
        List<Livro> livrosPorIdioma = livroRepository.findByIdiomas(idioma);
        logger.info("Livros no idioma {}:", idioma);
        if (livrosPorIdioma.isEmpty()) {
            logger.info("Nenhum livro encontrado para esse idioma.");
        } else {
            livrosPorIdioma.forEach(this::exibirLivro);
        }
    }

    private void exibirLivro(Livro livro) {
        logger.info("Título: {}", livro.getTitulo());

        String autores = (livro.getAutores() != null && !livro.getAutores().isEmpty())
                ? livro.getAutores().stream()
                    .map(Autor::getNome)
                    .collect(Collectors.joining(", "))
                : "Desconhecido";

        String idiomas = (livro.getIdiomas() != null && !livro.getIdiomas().isEmpty())
                ? livro.getIdiomas().stream()
                    .map(Idioma::getCodigo)
                    .collect(Collectors.joining(", "))
                : "Desconhecido";

        logger.info("Autor(es): {}", autores);
        logger.info("Idioma(s): {}", idiomas);
        logger.info("----------------------");
    }

    private void exibirAutor(Autor autor) {
        logger.info("Nome: {}", autor.getNome());
        logger.info("Ano Nascimento: {}", autor.getAnoNascimento());
        logger.info("Ano Falecimento: {}", autor.getAnoFalecimento());
        logger.info("----------------------");
    }

    private void exibirMenu() {
        logger.info("-------------");
        logger.info("1 - Buscar livro pelo título");
        logger.info("2 - Listar livros registrados");
        logger.info("3 - Listar autores registrados");
        logger.info("4 - Listar autores vivos em um determinado ano");
        logger.info("5 - Listar livros em um determinado idioma");
        logger.info("0 - Sair");
    }
}
