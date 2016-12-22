package br.sgpc.dlo;

import br.sgpc.dao.UsuarioDao;
import br.sgpc.dominio.Usuario;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import br.sgpc.dominio.enumerador.StatusEnum;

/**
 * Classe de objetos que representa a verificação da atividade de um
 * usuário no sistema, indicando se há sessão ativa para ele em um dado momento.
 * 
 */

@Stateless
@Remote
public class ControleSessaoDLO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private UsuarioDao dao;

	public boolean executar(Usuario usuario) {
		List<Usuario> usuarios = this.dao.consultarUsuario(usuario.getUserName(), usuario.getSenha());
		return (usuarios.size() > 0 && usuarios.get(0).getStatus() == StatusEnum.ATIVO.getValue());
	}
}
