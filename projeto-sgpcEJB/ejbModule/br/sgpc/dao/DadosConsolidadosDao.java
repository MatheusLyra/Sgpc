package br.sgpc.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import br.sgpc.dominio.Dadosconsolidados;

/**
 * Classe de objetos de acesso a dados relacionados aos dados consolidados.
 *
 */
@Stateless
@Local
public class DadosConsolidadosDao implements Serializable{

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "pu")
	private EntityManager entityManager;

	public void salvarDadosConsolidados(Dadosconsolidados dadosConsolidados) throws Exception {
		try {
			this.entityManager.persist(dadosConsolidados);
		} catch (Exception e) {
			throw e;
		}
	}

	public void atualizarDadosConsolidados(Dadosconsolidados dadosConsolidados) throws Exception {
		try {
			this.entityManager.merge(dadosConsolidados);
			this.entityManager.flush();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public Dadosconsolidados obter(Integer id) {
		return this.entityManager.find(Dadosconsolidados.class, id);
	}

	public void excluirDadosConsolidados(Dadosconsolidados dadosConsolidados) throws Exception {
		try {
			this.entityManager.remove(dadosConsolidados);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public List<Dadosconsolidados> consultarDadosConsolidados() {
		CriteriaQuery<Dadosconsolidados> cq = this.entityManager.getCriteriaBuilder().createQuery(Dadosconsolidados.class);
		cq.select(cq.from(Dadosconsolidados.class));
		return this.entityManager.createQuery(cq).getResultList();
	}

}
