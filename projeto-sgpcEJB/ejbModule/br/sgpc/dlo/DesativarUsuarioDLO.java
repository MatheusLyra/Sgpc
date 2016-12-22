package br.sgpc.dlo;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import br.sgpc.dao.UsuarioDao;
import br.sgpc.dominio.Usuario;

/**
 * Classe de objetos que representa a desativação de um usuário,
 * indicando que não há mais sessão ativa para ele no sistema a partir daquele
 * momento, o que o libera para abrir nova sessão futuramente.
 *
 */
@Stateless
@Remote
public class DesativarUsuarioDLO implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private UsuarioDao dao;

	public void alterar(Usuario usuario) throws Exception {
		this.dao.atualizarUsuario(usuario);
	}

}