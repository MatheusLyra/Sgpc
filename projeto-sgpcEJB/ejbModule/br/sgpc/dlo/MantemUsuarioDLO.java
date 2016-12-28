package br.sgpc.dlo;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import br.sgpc.dao.UsuarioDao;
import br.sgpc.dominio.Usuario;

/**
 * Classe de objetos responsável por manter os dados cadastrais do usuário.
 *
 */

@Stateless
@Remote
public class MantemUsuarioDLO implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private UsuarioDao dao;

	public void cadastrar(Usuario usuario) throws Exception{
	    this.dao.salvarUsuario(usuario);
	}

	public void alterar(Usuario usuario) throws Exception{
		this.dao.atualizarUsuario(usuario);
	}

	public void excluir(Usuario usuario) throws Exception {
		Usuario usu = this.dao.obter(usuario.getIdUsuario());
		if (usu != null) {
			this.dao.excluirUsuario(usu);
		}
	}
	
	public List<Usuario> carregarDados(){
		return this.dao.consultarUsuarios();
	}
	
	public Usuario obter(Integer id){
		return this.dao.obter(id);
	}
}
