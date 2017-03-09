package br.sgpc.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.sgpc.dlo.CronogramaAuditDLO;
import br.sgpc.dominio.Cronograma;
import br.sgpc.dominio.Dadosconsolidados;

/**
 * Classe de objetos de acesso a dados relacionados ao contrato.
 *
 */
@Stateless
@Local
public class CronogramaDao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private CronogramaAuditDLO cronogramaAuditDLO;	
	
	@PersistenceContext(unitName = "pu")
	private EntityManager entityManager;

	public void salvarCronograma(Cronograma cronograma) throws Exception {
		try {
			this.entityManager.persist(cronograma);
			this.cronogramaAuditDLO.inserir(obterUltimoRegistro(), "I");
		} catch (Exception e) {
	
			throw e;
		}
	}

	public void atualizarCronograma(Cronograma cronograma) throws Exception {
		try {
			this.entityManager.merge(cronograma);
			this.cronogramaAuditDLO.inserir(cronograma, "A");
			this.entityManager.flush();
		} catch (Exception e) {
			throw e;
		}
	}

	public Cronograma obter(Integer id) {
		return this.entityManager.find(Cronograma.class, id);
	}

	public void excluirCronograma(Cronograma cronograma) throws Exception {
		try {
			this.entityManager.remove(cronograma);
			this.cronogramaAuditDLO.inserir(cronograma, "E");
		} catch (Exception e) {
			throw e;
		}
	}

	public List<Cronograma> consultarCronograma(Integer numProcesso) {
		// CriteriaQuery<Cronograma> cq =
		// this.entityManager.getCriteriaBuilder().createQuery(Cronograma.class);
		// cq.select(cq.from(Cronograma.class));
		List<Cronograma> cronogramas = new ArrayList<Cronograma>();

		cronogramas = this.entityManager
				.createQuery("SELECT c FROM Cronograma c where c.dadosconsolidados.numProcesso = :numProc ", Cronograma.class)
				.setParameter("numProc", numProcesso).getResultList();
		return cronogramas;

		// return this.entityManager.createQuery(cq).getResultList();
	}
	
	public Cronograma obterUltimoRegistro() {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from sgpc.cronograma ");
		sql.append("where sgpc.cronograma.idCronograma in (select max(idCronograma) FROM sgpc.cronograma)");
		Query q =this.entityManager.createNativeQuery(sql.toString(), Cronograma.class);
		Cronograma c = (Cronograma) q.getSingleResult();
		return c;
	}
}
