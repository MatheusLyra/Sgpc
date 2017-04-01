package br.sgpc.dlo;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import br.sgpc.dao.UsuarioDao;
import br.sgpc.dominio.Usuario;

/**
 * Serviço para autenticação de usuário.
 * 
 */

@Stateless
@Remote
public class LoginDLO implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private UsuarioDao dao;

	public boolean executar(Usuario usuario) {
		return (this.dao.consultarUsuario(usuario.getUserName(), usuario.getSenha()).size() > 0);
	}
	
	public Usuario consultarUsuarioEmail(String email) throws Exception{
		return this.dao.consultarUsuarioEmail(email);
	}
}
