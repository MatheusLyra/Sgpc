package br.sgpc.mbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.sgpc.dlo.AlterarSenhaDLO;
import br.sgpc.dlo.funcoesUteis.Funcoes;
import br.sgpc.dominio.Usuario;

/**
 * Bean respons�vel por alterar a senha de um usu�rio previamente cadastrado.
 *
 */
@ManagedBean(name = "mbAlterarSenha")
@SessionScoped
public class MbAlterarSenha extends Funcoes implements Serializable {

	private static final long serialVersionUID = -1527245200931483533L;

	@EJB
	private AlterarSenhaDLO alterarSenhaDLO;

	private Usuario usuario;
	private Usuario usuarioSessao;
	private String senhaAntiga;
	private String confirmaSenha;
	List<Usuario> senhaAnt;

	public MbAlterarSenha() {
	}

	@PostConstruct
	public void inicializar() {
		HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		usuarioSessao = (Usuario) sessao.getAttribute(MbLogin.USUARIO_SESSAO);
		senhaAnt  = new ArrayList<Usuario>();

		usuario   = new Usuario();
	}
	
	/**
	 * Utilizado para alterar a senha do usu�rio.
	 */
	public void AlterarSenha() {
		if (VerificarSenha()) {
			usuario.setUserName(usuarioSessao.getUserName());
			usuario.setStatus(usuarioSessao.getStatus());
			usuario.setEmail(usuarioSessao.getEmail());
			usuario.setTipoUsuario(usuarioSessao.getTipoUsuario());
			usuario.setIdUsuario(usuarioSessao.getIdUsuario());
			try {
				alterarSenhaDLO.alterar(usuario);
				usuarioSessao.setSenha(usuario.getSenha());

				msgInfo("Senha alterada com sucesso.");
				limparTela();
			} catch (Exception e) {
				msgErro("Erro ao alterar senha com a seguinte mensagem: " + e.getMessage());
			}
		} else {
			msgInfo("Por favor, verifique os dados digitados.");
		}
	}
	
	/**
	 * Utilizado para verificar se a senha atual est� correta e
	 * se a senha nova est� igual ao campo de confirma��o.
	 * 
	 * @return <code>true</code> se a senha atual est� correta e a senha nova est� conforme a confirma��o.
	 *         <code>false</code> caso contr�rio.
	 */
	public boolean VerificarSenha() {
		try {
			senhaAnt = alterarSenhaDLO.consultarUsuarioSenha(usuarioSessao.getUserName(), senhaAntiga);
			if (senhaAnt.get(0).getSenha().equals(senhaAntiga) && usuario.getSenha().equals(confirmaSenha)
					&& confirmaSenha.length() > 0 && usuario.getSenha().length() > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Limpa todos os dados da tela.
	 */
	public void limparTela() {
		usuario = new Usuario();
		senhaAntiga = "";
		confirmaSenha = "";
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}

	public String getSenhaAntiga() {
		return senhaAntiga;
	}

	public void setSenhaAntiga(String senhaAntiga) {
		this.senhaAntiga = senhaAntiga;
	}

}
