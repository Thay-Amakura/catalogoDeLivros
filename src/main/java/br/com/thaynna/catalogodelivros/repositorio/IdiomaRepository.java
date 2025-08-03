package br.com.thaynna.catalogodelivros.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.thaynna.catalogodelivros.modelos.Idioma;

@Repository
public interface IdiomaRepository extends JpaRepository<Idioma, Long> {
    Optional<Idioma> findByCodigo(String codigo);
}
