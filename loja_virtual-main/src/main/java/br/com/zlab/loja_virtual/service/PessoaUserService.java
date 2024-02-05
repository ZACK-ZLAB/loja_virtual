package br.com.zlab.loja_virtual.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import br.com.zlab.loja_virtual.config.JwtService;
import br.com.zlab.loja_virtual.exception.dto.CepDTO;
import br.com.zlab.loja_virtual.model.PessoaFisica;
import br.com.zlab.loja_virtual.model.PessoaJuridica;
import br.com.zlab.loja_virtual.model.Usuario;
import br.com.zlab.loja_virtual.repository.PesssoaFisicaRepository;
import br.com.zlab.loja_virtual.repository.PesssoaRepository;
import br.com.zlab.loja_virtual.repository.UsuarioRepository;
import br.com.zlab.loja_virtual.token.Token;
import br.com.zlab.loja_virtual.token.TokenRepository;
import br.com.zlab.loja_virtual.token.TokenType;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PessoaUserService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PesssoaRepository pesssoaRepository;
	
	@Autowired
	private PesssoaFisicaRepository pesssoaFisicaRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private final JwtService jwtService;

	@Autowired
	private final TokenRepository tokenRepository;

	@Autowired
	private final EmailService emailService;

	@Transactional // Ensure email and database operations are within a transaction
	public PessoaJuridica salvarPessoaJuridica(PessoaJuridica juridica) throws MessagingException {

		for (int i = 0; i < juridica.getEnderecos().size(); i++) {
			juridica.getEnderecos().get(i).setPessoa(juridica);
			juridica.getEnderecos().get(i).setEmpresa(juridica);
		}

		juridica = pesssoaRepository.save(juridica);

		Usuario usuarioPj = usuarioRepository.findUserByPessoa(juridica.getId(), juridica.getEmail());

		if (usuarioPj == null) {

			String constraint = usuarioRepository.consultaConstraintAcesso();
			if (constraint != null) {
				jdbcTemplate.execute("begin; alter table usuarios_acesso drop constraint " + constraint + "; commit;");
			}

			usuarioPj = new Usuario();
			usuarioPj.setDataAtualSenha(Calendar.getInstance().getTime());
			usuarioPj.setEmpresa(juridica);
			usuarioPj.setPessoa(juridica);
			usuarioPj.setLogin(juridica.getEmail());

			String senha = "" + Calendar.getInstance().getTimeInMillis();
			String senhaCript = new BCryptPasswordEncoder().encode(senha);

			usuarioPj.setSenha(senhaCript);

			StringBuilder menssagemHtml = new StringBuilder();

			menssagemHtml.append("<b>Segue abaixo seus dados de acesso para a loja virtual</b><br/>");
			menssagemHtml.append("<b>Login: </b>" + juridica.getEmail() + "<br/>");
			menssagemHtml.append("<b>Senha: </b>").append(senha).append("<br/><br/>");
			menssagemHtml.append("Obrigado!");

			/* create a email sended here */
			try {
			emailService.sendEmail(juridica.getEmail(), "Dados de Acesso - Loja Virtual", menssagemHtml.toString());
			}catch (Exception e) {
				e.printStackTrace();
			}

			// 1. Gerar o token JWT
			var jwtToken = jwtService.generateToken(usuarioPj);

			// 2. Gerar o token de atualização (refresh token) - opcional, dependendo do seu
			// fluxo de autenticação
			jwtService.generateRefreshToken(usuarioPj);

			// 3. Salvar o usuário no banco de dados (se ainda não foi salvo)
			usuarioPj = usuarioRepository.save(usuarioPj);

			// 4. Inserir informações de acesso para o usuário PJ no banco de dados
			usuarioRepository.insereAcessoUser(usuarioPj.getId());
			usuarioRepository.insereAcessoUserPj(usuarioPj.getId(), "ROLE_ADMIN");
			saveUserToken(usuarioPj, jwtToken);

		}

		return juridica;

	}
	
	@Transactional // Ensure email and database operations are within a transaction
	public PessoaFisica salvarPessoaFisica(PessoaFisica pessoaFisica) throws MessagingException {

		for (int i = 0; i < pessoaFisica.getEnderecos().size(); i++) {
			pessoaFisica.getEnderecos().get(i).setPessoa(pessoaFisica);
			//pessoaFisica.getEnderecos().get(i).setEmpresa(pessoaFisica);
		}

		pessoaFisica = pesssoaFisicaRepository.save(pessoaFisica);

		Usuario usuarioPj = usuarioRepository.findUserByPessoa(pessoaFisica.getId(), pessoaFisica.getEmail());

		if (usuarioPj == null) {

			String constraint = usuarioRepository.consultaConstraintAcesso();
			if (constraint != null) {
				jdbcTemplate.execute("begin; alter table usuarios_acesso drop constraint " + constraint + "; commit;");
			}

			usuarioPj = new Usuario();
			usuarioPj.setDataAtualSenha(Calendar.getInstance().getTime());
			usuarioPj.setEmpresa(pessoaFisica.getEmpresa());
			usuarioPj.setPessoa(pessoaFisica);
			usuarioPj.setLogin(pessoaFisica.getEmail());

			String senha = "" + Calendar.getInstance().getTimeInMillis();
			String senhaCript = new BCryptPasswordEncoder().encode(senha);

			usuarioPj.setSenha(senhaCript);

			StringBuilder menssagemHtml = new StringBuilder();

			menssagemHtml.append("<b>Segue abaixo seus dados de acesso para a loja virtual</b><br/>");
			menssagemHtml.append("<b>Login: </b>" + pessoaFisica.getEmail() + "<br/>");
			menssagemHtml.append("<b>Senha: </b>").append(senha).append("<br/><br/>");
			menssagemHtml.append("Obrigado!");

			/* create a email sended here */
			try {
			emailService.sendEmail(pessoaFisica.getEmail(), "Dados de Acesso - Loja Virtual", menssagemHtml.toString());
			}catch (Exception e) {
				e.printStackTrace();
			}

			// 1. Gerar o token JWT
			var jwtToken = jwtService.generateToken(usuarioPj);

			// 2. Gerar o token de atualização (refresh token) - opcional, dependendo do seu
			// fluxo de autenticação
			jwtService.generateRefreshToken(usuarioPj);

			// 3. Salvar o usuário no banco de dados (se ainda não foi salvo)
			usuarioPj = usuarioRepository.save(usuarioPj);

			// 4. Inserir informações de acesso para o usuário PJ no banco de dados
			usuarioRepository.insereAcessoUser(usuarioPj.getId());
			saveUserToken(usuarioPj, jwtToken);

		}

		return pessoaFisica;

	}
	
	public CepDTO consultaCep(String cep) {
		return new RestTemplate().getForEntity("https://viacep.com.br/ws/"+ cep +"/json/", CepDTO.class).getBody();
	}

	private void saveUserToken(Usuario user, String jwtToken) {
		var token = Token.builder().user(user).token(jwtToken).tokenType(TokenType.BEARER).expired(false).revoked(false)
				.build();
		tokenRepository.save(token);
	}
	
}
