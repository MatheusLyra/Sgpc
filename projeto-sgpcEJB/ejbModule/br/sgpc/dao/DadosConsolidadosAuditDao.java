package br.sgpc.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import br.sgpc.dominio.DadosConsolidadosAudit;
import br.sgpc.dominio.Tipocontrato;

@Stateless
@Local
public class DadosConsolidadosAuditDao implements Serializable {
	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "pu")
	private EntityManager entityManager;

	public void incluir(DadosConsolidadosAudit dadosAudit) throws Exception {
		try {
			this.entityManager.persist(dadosAudit);
		} catch (Exception e) {
			throw e;
		}
	}

	public List<DadosConsolidadosAudit> listarOperacoes() {
		CriteriaQuery<Tipocontrato> cq = this.entityManager.getCriteriaBuilder().createQuery(Tipocontrato.class);
		cq.select(cq.from(Tipocontrato.class));
		return null;
		// return this.entityManager.createQuery(cq).getResultList();
	}
}
