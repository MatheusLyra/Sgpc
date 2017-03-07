package br.sgpc.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.sgpc.dominio.Cronograma;

/**
 * Classe de objetos de acesso a dados relacionados ao contrato.
 *
 */
@Stateless
@Local
public class CronogramaDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "pu")
	private EntityManager entityManager;

	public void salvarCronograma(Cronograma cronograma) throws Exception {
		try {
			this.entityManager.persist(cronograma);
		} catch (Exception e) {
			throw e;
		}
	}

	public void atualizarCronograma(Cronograma cronograma) throws Exception {
		try {
			this.entityManager.merge(cronograma);
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
}
