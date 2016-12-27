package br.sgpc.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import br.sgpc.dominio.Area;

/**
 * Classe de objetos de acesso a dados relacionados a Area.
 *
 */
@Stateless
@Local
public class AreaDao implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName = "pu")
	private EntityManager entityManager;

	public void salvarArea(Area area) throws Exception {
		try {
			this.entityManager.persist(area);
		} catch (Exception e) {
			throw e;
		}
	}

	public void atualizarArea(Area area) throws Exception {
		try {
			this.entityManager.merge(area);
			this.entityManager.flush();
		} catch (Exception e) {
			throw e;
		}
	}

	public Area obter(Integer id) {
		return this.entityManager.find(Area.class, id);
	}

	public void excluirArea(Area area) throws Exception {
		try {
			this.entityManager.remove(area);
		} catch (Exception e) {
			throw e;
		}
	}

	public List<Area> consultarArea() {
		CriteriaQuery<Area> cq = this.entityManager.getCriteriaBuilder().createQuery(Area.class);
		cq.select(cq.from(Area.class));
		return this.entityManager.createQuery(cq).getResultList();
	}
}
