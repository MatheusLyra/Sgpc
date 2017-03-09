package br.sgpc.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import br.sgpc.dlo.DadosConsolidadosAuditDLO;
import br.sgpc.dominio.Dadosconsolidados;

/**
 * Classe de objetos de acesso a dados relacionados aos dados consolidados.
 *
 */
@Stateless
@Local
public class DadosConsolidadosDao implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EJB
	private DadosConsolidadosAuditDLO dadosAuditDLO;	


	@PersistenceContext(unitName = "pu")
	private EntityManager entityManager;

	public void salvarDadosConsolidados(Dadosconsolidados dadosConsolidados) throws Exception {
		try {
			this.entityManager.persist(dadosConsolidados);
			this.dadosAuditDLO.incluir(obterUltimoRegistro(), "I");
		} catch (Exception e) {
			throw e;
		}
	}

	public void atualizarDadosConsolidados(Dadosconsolidados dadosConsolidados) throws Exception {
		try {
			this.entityManager.merge(dadosConsolidados);
			dadosAuditDLO.incluir(dadosConsolidados, "A");
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
			this.dadosAuditDLO.incluir(dadosConsolidados, "E");
		} catch (Exception e) {
			throw e;
		}
	}
	
	public List<Dadosconsolidados> consultarDadosConsolidados() {
		CriteriaQuery<Dadosconsolidados> cq = this.entityManager.getCriteriaBuilder().createQuery(Dadosconsolidados.class);
		cq.select(cq.from(Dadosconsolidados.class));
		return this.entityManager.createQuery(cq).getResultList();
	}

	public Dadosconsolidados obterUltimoRegistro() {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from sgpc.dadosconsolidados ");
		sql.append("where sgpc.dadosconsolidados.NumProcesso in (select max(NumProcesso) FROM sgpc.dadosconsolidados)");
		Query q =this.entityManager.createNativeQuery(sql.toString(), Dadosconsolidados.class);
		Dadosconsolidados dc = (Dadosconsolidados) q.getSingleResult();
		return dc;
	}
	
	
}
