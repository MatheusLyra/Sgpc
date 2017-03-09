package br.sgpc.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.sgpc.dominio.Cronograma;
import br.sgpc.dominio.CronogramaAudit;

/**
 * Classe de objetos de acesso a dados relacionados ao contrato.
 *
 */
@Stateless
@Local
public class CronogramaAuditDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "pu")
	private EntityManager entityManager;

	public void incluir(CronogramaAudit cronogramaAudit) throws Exception {
		try {
			this.entityManager.persist(cronogramaAudit);
		} catch (Exception e) {
			throw e;
		}
	}
}
