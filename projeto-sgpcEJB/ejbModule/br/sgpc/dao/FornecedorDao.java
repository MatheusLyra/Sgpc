package br.sgpc.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import br.sgpc.dominio.Fornecedor;

/**
 * Classe de objetos de acesso a dados relacionados ao Fornecedor.
 *
 */
@Stateless
@Local
public class FornecedorDao implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName = "pu")
	private EntityManager entityManager;

	public void salvarFornecedor(Fornecedor fornecedor) throws Exception {
		try {
			this.entityManager.persist(fornecedor);
		} catch (Exception e) {
			throw e;
		}
	}

	public void atualizarFornecedor(Fornecedor fornecedor) throws Exception {
		try {
			this.entityManager.merge(fornecedor);
			this.entityManager.flush();
		} catch (Exception e) {
			throw e;
		}
	}

	public Fornecedor obter(Integer id) {
		return this.entityManager.find(Fornecedor.class, id);
	}

	public void excluirFornecedor(Fornecedor fornecedor) throws Exception {
		try {
			this.entityManager.remove(fornecedor);
		} catch (Exception e) {
			throw e;
		}
	}

	public List<Fornecedor> consultarFornecedor() {
		CriteriaQuery<Fornecedor> cq = this.entityManager.getCriteriaBuilder().createQuery(Fornecedor.class);
		cq.select(cq.from(Fornecedor.class));
		return this.entityManager.createQuery(cq).getResultList();
	}
}
