package br.sgpc.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import br.sgpc.dominio.Etapa;

/**
 * Classe de objetos de acesso a dados relacionados a Etapa.
 *
 */
@Stateless
@Local
public class EtapaDao implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName = "pu")
	private EntityManager entityManager;

	public void salvarEtapa(Etapa etapa) throws Exception {
		try {
			this.entityManager.persist(etapa);
		} catch (Exception e) {
			throw e;
		}
	}

	public void atualizarEtapa(Etapa etapa) throws Exception {
		try {
			this.entityManager.merge(etapa);
			this.entityManager.flush();
		} catch (Exception e) {
			throw e;
		}
	}

	public Etapa obter(Integer id) {
		return this.entityManager.find(Etapa.class, id);
	}

	public void excluirEtapa(Etapa etapa) throws Exception {
		try {
			this.entityManager.remove(etapa);
		} catch (Exception e) {
			throw e;
		}
	}

	public List<Etapa> consultarEtapa() {
		CriteriaQuery<Etapa> cq = this.entityManager.getCriteriaBuilder().createQuery(Etapa.class);
		cq.select(cq.from(Etapa.class));
		return this.entityManager.createQuery(cq).getResultList();
	}
}
