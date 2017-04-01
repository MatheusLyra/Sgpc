package br.sgpc.mbeans;

import br.sgpc.controller.ControladorAcesso;
import br.sgpc.dlo.AtivarUsuarioDLO;
import br.sgpc.dlo.CarregarUsuarioDLO;
import br.sgpc.dlo.ControleSessaoDLO;
import br.sgpc.dlo.DesativarUsuarioDLO;
import br.sgpc.dlo.LoginDLO;
import br.sgpc.dlo.funcoesUteis.Funcoes;
import br.sgpc.dominio.Usuario;

import java.io.Serializable;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;

import com.sun.istack.internal.logging.Logger;

import br.sgpc.dominio.enumerador.StatusEnum;

/**
 * Bean respons�vel por controlar opera��es de login, logout e controle de
 * sess�es de usu�rios.
 *
 */
@ManagedBean(name = "mbLogin")
@SessionScoped
public class MbLogin extends Funcoes implements Serializable {

	private static final long serialVersionUID = -8462448134504214299L;

	private static final String LOGIN_SUCESSO = "login_sucesso";
	public static final String LOGIN_FALHA = "login_falha";
	public static final String SESSAO_INEXISTENTE = "sessao_invalida";
	private static final String OUTCOME_LOGOUT = "logout";
	public static final String USUARIO_SESSAO = "usuario";
	private static final String RECUPERAR_SENHA = "recuperar_senha";

	private Usuario usuario;
	private ControladorAcesso controladorAcesso;
	private Usuario usuarioSessaoTipo;
	
	private static boolean usuRecuperaSenha;

	@EJB
	private LoginDLO loginDLO;
	@EJB
	private ControleSessaoDLO controleSessaoDLO;
	@EJB
	private CarregarUsuarioDLO carregarUsuarioDLO;
	@EJB
	private AtivarUsuarioDLO ativarUsuarioDLO;
	@EJB
	private DesativarUsuarioDLO desativarUsuarioDLO;

	@PostConstruct
	public void inicializar() {
		usuario = new Usuario();
		controladorAcesso = new ControladorAcesso();
		Logger.getLogger(MbLogin.class).log(Level.INFO, ">>>>>>>>>>>>> Inicializando um bean de login.");
		usuRecuperaSenha = false;
	}

	/**
	 * Utilizado para tentativas de login no sistema, confrontando dados
	 * fornecidos pelo usu�rio com registros de usu�rios cadastrados.
	 * 
	 * @return Outcome associado a fracasso ou sucesso na tentativa de login.
	 */
	public String doLogin() {

		if (camposPreenchidos()) {
			if (!isUsuarioLogado()) {
				if (loginDLO.executar(usuario)) {
					// Descobrindo o tipo de usu�rio que est� tentando acessar o
					// sistema.
					Usuario usuarioLogado = carregarUsuarioDLO.carregarDados(usuario.getUserName(), usuario.getSenha())
							.get(0);
					//usuarioLogado.setStatus((byte) StatusEnum.ATIVO.getValue());

					HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
							.getSession(true);
					sessao.setAttribute(USUARIO_SESSAO, usuarioLogado);
					controladorAcesso.configurarAcesso();
					// pegar o tipo de usu�rio para apresentar na tela
					usuarioSessaoTipo = (Usuario) sessao.getAttribute(USUARIO_SESSAO);
					// Atualizando sistema de informa��es para informar que o
					// usu�rio est� logado.
					try {
						ativarUsuarioDLO.alterar(usuarioLogado);
					} catch (Exception e) {
						msgErro("Erro com a seguinte mensagem: " + e.getMessage());
					}
					return LOGIN_SUCESSO;
				} else {
					msgInfo("Usu�rio n�o localizado, por favor verifique a senha e o usu�rio.");
				}
			} else {
				msgInfo("Usu�rio j� logado.");
			}
		} else {
			msgInfo("Todos os campos devem ser preenchidos.");
		}
		// return LOGIN_FALHA;
		return "";
	}

	/**
	 * Utilizado para finalizar uma sess�o de um usu�rio no sistema.
	 * 
	 * @return Outcome associado a fracasso ou sucesso na tentativa de logout.
	 */
	public String doLogout() {
		FacesContext context = FacesContext.getCurrentInstance();

		HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuarioSessao = (Usuario) sessao.getAttribute(USUARIO_SESSAO);

		if (usuarioSessao != null) {
			usuarioSessao.setStatus((byte) StatusEnum.INATIVO.getValue());
			try {
				desativarUsuarioDLO.alterar(usuarioSessao);
			} catch (Exception e) {
				msgErro("Erro ao desativar o usu�rio com a seguinte mensagem: " + e.getMessage());
			}
		}
		context.getExternalContext().invalidateSession();
		return OUTCOME_LOGOUT;
	}

	/**
	 * Utilizado para verificar se as credenciais necess�rias para realiza��o do
	 * login foram preenchidas.
	 * 
	 * @return <code>true</code> em caso de dados preenchidos.
	 *         <code>false</code> caso contr�rio.
	 */
	private boolean camposPreenchidos() {
		return (usuario != null && usuario.getUserName() != null && !"".equals(usuario.getUserName())
				&& usuario.getSenha() != null && !"".equals(usuario.getSenha()));
	}

	/**
	 * M�todo utilizado para verificar se um usu�rio tentando logar na aplica��o
	 * j� n�o possui alguma sess�o aberta em outro navegador ou outra aba. A
	 * aplica��o est� barrando m�ltiplos acessos simult�neos de um usu�rio.
	 * 
	 * @return <code>true</code> se j� existir uma sess�o ativa para o usu�rio.
	 *         <code>false</code> caso contr�rio.
	 */
	private boolean isUsuarioLogado() {
		return controleSessaoDLO.executar(usuario);
	}

	/**
	 * Limpa todos os dados da tela de login.
	 */
	public void limparTela() {
		usuario = new Usuario();
		controladorAcesso = new ControladorAcesso();
	}
	
	public String doRecuperarSenha(){
		usuRecuperaSenha = true;
		return RECUPERAR_SENHA;
	}
	
	public void recuperarSenha() {
		try {
			if (validarEmail(usuario.getEmail())) {
				Usuario usu = new Usuario();
				usu = loginDLO.consultarUsuarioEmail(usuario.getEmail());

				if (!usu.equals(null)) {
					String msg = "Prezado(a) " + usu.getUserName()+"," 
					        	+ "\r\n"+ "\r\n"
					        	+ "Esta � uma notifica��o autom�tica de recupera��o de senha do SGPC." 
					        	+ "\r\n"+ "\r\n"
					        	+ "Segue abaixo seus dados de acesso:" 
					        	+ "\r\n" + "\r\n" 
					        	+ "  USU�RIO: " + usu.getUserName()
					        	+ "\r\n"  
					        	+ "  SENHA: " + usu.getSenha() 
					        	+ "\r\n";
					if (envioEmail(usu.getEmail(), usu.getUserName(), "Recupera��o de senha - SGPC", msg)) {
						msgInfo("Seus dados de acesso foram enviados com sucesso para o email.");
					} else {
						msgErro("Erro ao encaminhar os dados.");
					}
				} 
			}else{
				msgInfo("Formato do email inv�lido");
			}		
		} catch (Exception e) {
			msgErro("Email n�o localizado no cadastro de usu�rios." + e.getMessage());
		}
	}
	
	public String doLogoutRecuperarSenha() {
		usuRecuperaSenha = false;
		return OUTCOME_LOGOUT;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public ControladorAcesso getControladorAcesso() {
		return controladorAcesso;
	}

	public String tipoSessao() {
		return usuarioSessaoTipo.getTipoUsuario();
	}

	public static boolean isUsuRecuperaSenha() {
		return usuRecuperaSenha;
	}

	public static void setUsuRecuperaSenha(boolean usuRecuperaSenha) {
		MbLogin.usuRecuperaSenha = usuRecuperaSenha;
	}

	   /**
		 * Utilizada para envio de email autom�tico para o usu�rio.
		 * 
		 * @return <code>true</code> se o email foi enviado com sucesso.
		 *         <code>false</code> caso contr�rio.
		*/
		public static boolean envioEmail(String destinatarioEmail, String destinatarioNome, 
								  String assunto, String msgEmail) {
			String servSmtp       = "smtp.mail.yahoo.com.br";
			String remetenteEmail = "sistemasgpc@yahoo.com";
			String remetenteNome  = "SGPC - Sistema Gerenciador de Processos para Contrata��o";
			try {
				SimpleEmail email = new SimpleEmail();
				email.setHostName(servSmtp); // o servidor SMTP para envio do e-mail
				email.setSmtpPort(587);
				email.setAuthenticator(new DefaultAuthenticator(remetenteEmail, "matandsanmac"));
				email.setStartTLSEnabled(true);
				email.addTo(destinatarioEmail, destinatarioNome); // destinat�rio
				email.setFrom(remetenteEmail, remetenteNome); // remetente
				email.setSubject(assunto); // assunto do e-mail
				email.setMsg(msgEmail); // conteudo do e-mail
				
				email.send(); // envia o e-mail
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		
}
