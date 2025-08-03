package br.com.thaynna.catalogodelivros.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.thaynna.catalogodelivros.modelos.Autor;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    List<Autor> findByAnoNascimentoLessThanEqualAndAnoFalecimentoGreaterThanEqual(Integer anoInicio, Integer anoFim);
    List<Autor> findByNomeContainingIgnoreCase(String nome);
    Optional<Autor> findByNome(String nome);
}
