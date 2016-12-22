package br.sgpc.dlo;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import br.sgpc.dao.UsuarioDao;
import br.sgpc.dominio.Usuario;

/**
 * Classe de objetos responsável por atualizar a informação de
 * (in)atividade de um usuário.
 *
 */
@Stateless
@Remote
public class AtivarUsuarioDLO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EJB
	private UsuarioDao dao;

	public void alterar(Usuario usuario) throws Exception {
		this.dao.atualizarUsuario(usuario);
	}


}
