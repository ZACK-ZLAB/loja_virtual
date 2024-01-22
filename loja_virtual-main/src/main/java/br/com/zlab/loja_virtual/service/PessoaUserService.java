package br.com.zlab.loja_virtual.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.zlab.loja_virtual.config.JwtService;
import br.com.zlab.loja_virtual.model.PessoaJuridica;
import br.com.zlab.loja_virtual.model.Usuario;
import br.com.zlab.loja_virtual.repository.PesssoaRepository;
import br.com.zlab.loja_virtual.repository.UsuarioRepository;
import br.com.zlab.loja_virtual.token.Token;
import br.com.zlab.loja_virtual.token.TokenRepository;
import br.com.zlab.loja_virtual.token.TokenType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PessoaUserService {
	
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PesssoaRepository pesssoaRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private final JwtService jwtService;
	
	@Autowired
	private final TokenRepository tokenRepository;
	
	public PessoaJuridica salvarPessoaJuridica(PessoaJuridica juridica) {
		
		
		
		for (int i = 0; i< juridica.getEnderecos().size(); i++) {
			juridica.getEnderecos().get(i).setPessoa(juridica);
			juridica.getEnderecos().get(i).setEmpresa(juridica);
		}
		
		juridica = pesssoaRepository.save(juridica);
		
		Usuario usuarioPj = usuarioRepository.findUserByPessoa(juridica.getId(), juridica.getEmail());
		
		if (usuarioPj == null) {
			
			String constraint = usuarioRepository.consultaConstraintAcesso();
			if (constraint != null) {
				jdbcTemplate.execute("begin; alter table usuarios_acesso drop constraint " + constraint +"; commit;");
			}
			
			usuarioPj = new Usuario();
			usuarioPj.setDataAtualSenha(Calendar.getInstance().getTime());
			usuarioPj.setEmpresa(juridica);
			usuarioPj.setPessoa(juridica);
			usuarioPj.setLogin(juridica.getEmail());
			
			
			String senha = "" + Calendar.getInstance().getTimeInMillis();
			String senhaCript = new BCryptPasswordEncoder().encode(senha);
			
			usuarioPj.setSenha(senhaCript);
			// 1. Gerar o token JWT
			var jwtToken = jwtService.generateToken(usuarioPj);

			// 2. Gerar o token de atualização (refresh token) - opcional, dependendo do seu fluxo de autenticação
			jwtService.generateRefreshToken(usuarioPj);

			// 3. Salvar o usuário no banco de dados (se ainda não foi salvo)
			usuarioPj = usuarioRepository.save(usuarioPj);

			// 4. Inserir informações de acesso para o usuário PJ no banco de dados
			usuarioRepository.insereAcessoUserPj(usuarioPj.getId());

			// 5. Salvar o token associado ao usuário no banco de dados
			saveUserToken(usuarioPj, jwtToken);
	
		}
		
		return juridica;
		
	}

	private void saveUserToken(Usuario user, String jwtToken) {
	    var token = Token.builder()
	        .user(user)
	        .token(jwtToken)
	        .tokenType(TokenType.BEARER)
	        .expired(false)
	        .revoked(false)
	        .build();
	    tokenRepository.save(token);
	  }
	
	

}
