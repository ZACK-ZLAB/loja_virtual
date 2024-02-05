package br.com.zlab.loja_virtual.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.zlab.loja_virtual.model.Usuario;
import jakarta.transaction.Transactional;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    
    @Query(value = "select u from Usuario u where u.login = ?1")
    Optional<Usuario> findUserByLogin(String login);

    @Query(value = "select u from Usuario u where u.pessoa.id = ?1 or u.login =?2")
    Usuario findUserByPessoa(Long id, String email);

    @Query(value = "select constraint_name from information_schema.constraint_column_usage where table_name = 'usuarios_acesso' and column_name = 'acesso_id' and constraint_name <> 'unique_acesso_user';", nativeQuery = true)
    String consultaConstraintAcesso();
    
    @Query("select u from Usuario u where u.dataAtualSenha <= :expiryDate")
    List<Usuario> usuarioSenhaVencida(@Param("expiryDate") LocalDate expiryDate);
    
    @Transactional
	@Modifying
	@Query(nativeQuery = true, value = "insert into usuarios_acesso(usuario_id, acesso_id) values (?1, (select id from acesso where descricao = 'ROLE_USER'))")
	void insereAcessoUser(Long iduser);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "insert into usuarios_acesso(usuario_id, acesso_id) values (?1, (select id from acesso where descricao = ?2 limit 1))")
	void insereAcessoUserPj(Long iduser, String acesso);
}

