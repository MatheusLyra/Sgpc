package br.sgpc.mbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.sgpc.dlo.CarregarUsuarioDLO;
import br.sgpc.dlo.MantemUsuarioDLO;
import br.sgpc.dlo.funcoesUteis.Funcoes;
import br.sgpc.dominio.Usuario;


/**
 * Bean responsável por cadastrar um novo usuário, alterar, excluir e visualizar
 * usuários cadastrados.
 */
@ManagedBean(name = "mbMantemUsuario")
@SessionScoped
public class MbMantemUsuario extends Funcoes implements Serializable {

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

					msgInfo("Alterado com sucesso.");
				} catch (Exception e) {
					msgErro("Erro ao alterar dados do usuário, com a seguinte mensagem: " + e.getMessage());
				}
				modoEdicao = false;
				limpar();
			} else {
				if (validarCampos()) {
					try {
						mantemUsuarioDLO.cadastrar(usuario);
						carregaUsuario();

						msgInfo("Usuário cadastrado com sucesso!");
					} catch (Exception e) {
						msgErro("Erro ao cadastrar usuário, com a seguinte mensagem: " + e.getMessage());
					}
					limpar();
				} else {
					msgErro("Todos os campos devem ser preenchidos e a senha deve ser confirmada exatamente.");
				}
			}
		} else {
			msgErro("Email inválido.");
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
			msgErro("Erro ao deletar usuário.");
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
