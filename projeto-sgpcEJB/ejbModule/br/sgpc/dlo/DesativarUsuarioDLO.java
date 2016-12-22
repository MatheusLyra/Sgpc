package br.sgpc.dlo;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import br.sgpc.dao.UsuarioDao;
import br.sgpc.dominio.Usuario;

/**
 * Classe de objetos que representa a desativa��o de um usu�rio,
 * indicando que n�o h� mais sess�o ativa para ele no sistema a partir daquele
 * momento, o que o libera para abrir nova sess�o futuramente.
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