package br.sgpc.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import br.sgpc.dominio.Tipocontrato;

/**
 * Classe de objetos de acesso a dados relacionados ao Tipo de contrato.
 *
 */
@Stateless
@Local
public class TipoContratoDao implements Serializable{

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "pu")
	private EntityManager entityManager;

	public void salvarTipoContrato(Tipocontrato tipoContrato) throws Exception {
		try {
			this.entityManager.persist(tipoContrato);
		} catch (Exception e) {
			throw e;
		}
	}

	public void atualizarTipoContrato(Tipocontrato tipoContrato) throws Exception {
		try {
			this.entityManager.merge(tipoContrato);
			this.entityManager.flush();
		} catch (Exception e) {
			throw e;
		}
	}

	public Tipocontrato obter(Integer id) {
		return this.entityManager.find(Tipocontrato.class, id);
	}

	public void excluirTipoContrato(Tipocontrato tipoContrato) throws Exception {
		try {
			this.entityManager.remove(tipoContrato);
		} catch (Exception e) {
			throw e;
		}
	}

	public List<Tipocontrato> consultarTipoContratos() {
		CriteriaQuery<Tipocontrato> cq = this.entityManager.getCriteriaBuilder().createQuery(Tipocontrato.class);
		cq.select(cq.from(Tipocontrato.class));
		return this.entityManager.createQuery(cq).getResultList();
	}
}
