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
 * Bean respons�vel por cadastrar um novo usu�rio, alterar, excluir e visualizar
 * usu�rios cadastrados.
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
	 * Utilizado para cadastrar e alterar os dados do usu�rio.
	 */
	public void cadastrar() {
		if (validarEmail(usuario.getEmail())) {
			if (modoEdicao) {
				try {
					mantemUsuarioDLO.alterar(usuario);
					carregaUsuario();

					msgInfo("Alterado com sucesso.");
				} catch (Exception e) {
					msgErro("Erro ao alterar dados do usu�rio, com a seguinte mensagem: " + e.getMessage());
				}
				modoEdicao = false;
				limpar();
			} else {
				if (validarCampos()) {
					try {
						mantemUsuarioDLO.cadastrar(usuario);
						carregaUsuario();

						msgInfo("Usu�rio cadastrado com sucesso!");
					} catch (Exception e) {
						msgErro("Erro ao cadastrar usu�rio, com a seguinte mensagem: " + e.getMessage());
					}
					limpar();
				} else {
					msgErro("Todos os campos devem ser preenchidos e a senha deve ser confirmada exatamente.");
				}
			}
		} else {
			msgErro("Email inv�lido.");
		}
	}

	/**
	 * Limpa todos os dados da tela de cadastro do usu�rio.
	 */
	public void limpar() {
		usuario = new Usuario();

		modoEdicao = false;
	}
	
	/**
	 * Utilizado para colocar o cadastro em modo de edi��o ou de cadastro.
	 */
	public void editar() {
		modoEdicao = true;
	}
	
	/**
	 * Utilizado para executar a exclus�o de um usu�rio.
	 */
	public void excluir(Usuario usu) {
		try {
			mantemUsuarioDLO.excluir(usu);
			carregaUsuario();
		} catch (Exception e) {
			msgErro("Erro ao deletar usu�rio.");
		}
	}
	
	/**
	 * Utilizado para carregar a lista de usu�rios.
	 */
	public void carregaUsuario() {
		usuarios = carregarUsuarioDLO.carregarDados();
	}
	
	/**
	 * Utilizado para verificar se o campo email, nome foi preenchido. Verifica tambem a senha com o campo de confirma��o de senha.
	 * 
	 * @return <code>true</code> se os campos est�o conforme o padr�o.
	 *         <code>false</code> caso contr�rio.
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
