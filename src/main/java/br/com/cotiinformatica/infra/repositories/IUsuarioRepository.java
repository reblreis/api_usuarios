package br.com.cotiinformatica.infra.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cotiinformatica.domain.models.Usuario;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {

	@Query("select u from Usuario u where u.email = :pEmail")
	Usuario findByEmail(@Param("pEmail") String email);

	@Query("select u from Usuario u where u.email = :pEmail and u.senha = :pSenha")
	Usuario findByEmailAndSenha(@Param("pEmail") String email, @Param("pSenha") String senha);
}