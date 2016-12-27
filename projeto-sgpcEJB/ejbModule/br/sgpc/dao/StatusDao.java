package br.sgpc.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import br.sgpc.dominio.Status;

/**
 * Classe de objetos de acesso a dados relacionados ao Status.
 *
 */
@Stateless
@Local
public class StatusDao implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "pu")
	private EntityManager entityManager;

	public void salvarStatus(Status status) throws Exception {
		try {
			this.entityManager.persist(status);
		} catch (Exception e) {
			throw e;
		}
	}

	public void atualizarStatus(Status status) throws Exception {
		try {
			this.entityManager.merge(status);
			this.entityManager.flush();
		} catch (Exception e) {
			throw e;
		}
	}

	public Status obter(Integer id) {
		return this.entityManager.find(Status.class, id);
	}

	public void excluirStatus(Status status) throws Exception {
		try {
			this.entityManager.remove(status);
		} catch (Exception e) {
			throw e;
		}
	}

	public List<Status> consultarStatus() {
		CriteriaQuery<Status> cq = this.entityManager.getCriteriaBuilder().createQuery(Status.class);
		cq.select(cq.from(Status.class));
		return this.entityManager.createQuery(cq).getResultList();
	}
}
