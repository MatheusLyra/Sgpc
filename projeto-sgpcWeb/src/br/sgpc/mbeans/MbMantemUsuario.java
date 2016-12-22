package br.sgpc.mbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.sgpc.dlo.CarregarUsuarioDLO;
import br.sgpc.dlo.MantemUsuarioDLO;
import br.sgpc.dominio.Usuario;

import javax.faces.application.FacesMessage;

/**
 * Bean responsável por cadastrar um novo usuário, alterar, excluir e visualizar
 * usuários cadastrados.
 */
@ManagedBean(name = "mbMantemUsuario")
@SessionScoped
public class MbMantemUsuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private MantemUsuarioDLO mantemUsuarioDLO;
	@EJB
	private CarregarUsuarioDLO carregarUsuarioDLO;

	private String confirmaSenha;
	private Boolean modoEdicao;
	private Usuario usuario;
	private List<Usuario> usuarios;

	@PostConstruct
	public void inicializar() {
		usuario     = new Usuario();

		modoEdicao = false;

		usuarios = new ArrayList<Usuario>();
		carregaUsuario();
	}
	
	/**
	 * Utilizado para cadastrar e alterar os dados do usuário.
	 */
	public void cadastrar() {
		if (validarEmail(usuario.getEmail())) {
			if (modoEdicao) {
				try {
					mantemUsuarioDLO.alterar(usuario);
					carregaUsuario();
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Alterado com sucesso."));
				} catch (Exception e) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Erro", "Erro ao alterar dados do usuário, com a seguinte mensagem: " + e.getMessage()));

				}
				modoEdicao = false;
				limpar();
			} else {
				if (validarCampos()) {
					try {
						mantemUsuarioDLO.cadastrar(usuario);
						carregaUsuario();
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Info", "Usuário cadastrado com sucesso!"));
					} catch (Exception e) {
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Erro", "Erro ao cadastrar usuário, com a seguinte mensagem: " + e.getMessage()));

					}
					limpar();
				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Erro", "Todos os campos devem ser preenchidos e a senha deve ser confirmada exatamente."));
				}
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Email inválido."));
		}
	}

	/**
	 * Limpa todos os dados da tela de cadastro do usuário.
	 */
	public void limpar() {
		usuario = new Usuario();

		modoEdicao = false;
	}
	
	/**
	 * Utilizado para colocar o cadastro em modo de edição ou de cadastro.
	 */
	public void editar() {
		modoEdicao = true;
	}
	
	/**
	 * Utilizado para executar a exclusão de um usuário.
	 */
	public void excluir(Usuario usu) {
		try {
			mantemUsuarioDLO.excluir(usu);
			carregaUsuario();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao deletar usuário."));
		}
	}
	
	/**
	 * Utilizado para carregar a lista de usuários.
	 */
	public void carregaUsuario() {
		usuarios = carregarUsuarioDLO.carregarDados();
	}
	
	/**
	 * Utilizado para verificar se o campo email, nome foi preenchido. Verifica tambem a senha com o campo de confirmação de senha.
	 * 
	 * @return <code>true</code> se os campos estão conforme o padrão.
	 *         <code>false</code> caso contrário.
	 */
	public boolean validarCampos() {
		if (usuario.getEmail().length() > 0 && usuario.getUserName().length() > 0
				&& usuario.getSenha().equals(confirmaSenha)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Utilizado para verificar se o email está de acordo com o padrão.
	 * 
	 * @return <code>true</code> se o email cadastrado está conforme padrão.
	 *         <code>false</code> caso contrário.
	 */
	public static boolean validarEmail(String email) {
		boolean isEmailIdValid = false;
		if (email != null && email.length() > 0) {
			String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
			Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(email);
			if (matcher.matches()) {
				isEmailIdValid = true;
			}
		}
		return isEmailIdValid;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}

	public Boolean getModoEdicao() {
		return modoEdicao;
	}

	public void setModoEdicao(Boolean modoEdicao) {
		this.modoEdicao = modoEdicao;
	}


}
