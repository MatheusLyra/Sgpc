package br.sgpc.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import br.sgpc.dominio.Usuario;

/**
 * Classe de objetos de acesso a dados relacionados a usuário.
 *
 */
@Stateless
@Local
public class UsuarioDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "pu")
	private EntityManager entityManager;

	public void salvarUsuario(Usuario usuario) throws Exception{
		try {
			this.entityManager.persist(usuario);
		} catch (Exception e) {
			throw e;
		}
	}

	public void atualizarUsuario(Usuario usuario) throws Exception{
		try {
			this.entityManager.merge(usuario);
			this.entityManager.flush();
		} catch (Exception e) {
			throw e;
		}	
	}
	
	public Usuario obter(Integer id){
		return this.entityManager.find(Usuario.class, id);
	}

	public void excluirUsuario(Usuario usuario) throws Exception {
		try {
			this.entityManager.remove(usuario);
		} catch (Exception e) {
			throw e;
		}
	}

	public List<Usuario> consultarUsuario(String... criterios) {

		List<Usuario> usuarios = new ArrayList<Usuario>();

		if (criterios.length == 2) {
			usuarios = this.entityManager.createQuery("select usu from Usuario usu where usu.userName = ?1 and usu.senha = ?2", Usuario.class)
					.setParameter(1, criterios[0]).setParameter(2, criterios[1]).getResultList();
		}
		return usuarios;
	}

	public List<Usuario> consultarUsuarios() {
		
		CriteriaQuery<Usuario> cq = this.entityManager.getCriteriaBuilder().createQuery(Usuario.class);
		cq.select(cq.from(Usuario.class));
		return this.entityManager.createQuery(cq).getResultList();
	}
	
	public Usuario consultarUsuarioEmail(String email) throws Exception {
		Usuario usuario = new Usuario();

		try {
			usuario = this.entityManager
					.createQuery("select usu from Usuario usu where Trim(UPPER(usu.email)) like ?1", Usuario.class)
					.setParameter(1, email.toUpperCase().trim()).getSingleResult();

		} catch (NoResultException e) {
			throw e;
		}
		return usuario;
	}

}
