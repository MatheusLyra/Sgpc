package br.sgpc.dlo;

import br.sgpc.dao.UsuarioDao;
import br.sgpc.dominio.Usuario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 * Classe de objetos responsáveis por carregar informações de usuários
 * cadastrados no sistema de informações da aplicação.
 *
 */
@Stateless
@Remote
public class CarregarUsuarioDLO implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private UsuarioDao dao;

	public List<Usuario> carregarDados(String... criterios) {

		List<Usuario> usuarios = new ArrayList<Usuario>();

		if (criterios != null && criterios.length == 2) {

			usuarios = this.dao.consultarUsuario(criterios[0], criterios[1]);
		}
		return usuarios;
	}

	public List<Usuario> carregarDados() {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		
		usuarios = this.dao.consultarUsuarios();
			
		return usuarios;
	}
}