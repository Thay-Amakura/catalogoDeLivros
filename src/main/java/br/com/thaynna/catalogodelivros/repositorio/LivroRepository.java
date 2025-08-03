package br.com.thaynna.catalogodelivros.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.thaynna.catalogodelivros.modelos.Livro;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    @Query("SELECT l FROM Livro l JOIN l.idiomas i WHERE i.codigo = :codigo")
    List<Livro> findByIdiomas(@Param("codigo") String codigo);
    
    Optional<Livro> findByPublicId(Integer publicId);

    Optional<Livro> findByTitulo(String titulo);

}