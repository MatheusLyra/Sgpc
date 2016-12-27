package br.sgpc.dao;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import br.sgpc.dominio.Tmp;

/**
 * Classe de objetos de acesso a dados relacionados a Tmp.
 *
 */
@Stateless
@Local
public class TmpDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "pu")
	private EntityManager entityManager;

	public void salvarTmp(Tmp tmp) throws Exception {
		try {
			this.entityManager.persist(tmp);
		} catch (Exception e) {
			throw e;
		}
	}

	public void atualizarTmp(Tmp tmp) throws Exception {
		try {
			this.entityManager.merge(tmp);
			this.entityManager.flush();
		} catch (Exception e) {
			throw e;
		}
	}

	public Tmp obter(Integer id) {
		return this.entityManager.find(Tmp.class, id);
	}

	public void excluirTmp(Tmp tmp) throws Exception {
		try {
			this.entityManager.remove(tmp);
		} catch (Exception e) {
			throw e;
		}
	}

	public List<Tmp> consultarTmps() {

		CriteriaQuery<Tmp> cq = this.entityManager.getCriteriaBuilder().createQuery(Tmp.class);
		cq.select(cq.from(Tmp.class));
		return this.entityManager.createQuery(cq).getResultList();
	}
}
