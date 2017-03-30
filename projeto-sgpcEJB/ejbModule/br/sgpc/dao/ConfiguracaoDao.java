package br.sgpc.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import br.sgpc.dominio.Configuracao;

/**
 * Classe de objetos de acesso a dados relacionados a Configuração.
 *
 */
@Stateless
@Local
public class ConfiguracaoDao implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName = "pu")
	private EntityManager entityManager;
	
	public void atualizarConfiguracao(Configuracao configuracao) throws Exception {
		try {
			this.entityManager.merge(configuracao);
			this.entityManager.flush();
		} catch (Exception e) {
			throw e;
		}
	}

	public Configuracao obter(Integer id) {
		return this.entityManager.find(Configuracao.class, id);
	}	

	public List<Configuracao> consultarConfiguracao() {
		CriteriaQuery<Configuracao> cq = this.entityManager.getCriteriaBuilder().createQuery(Configuracao.class);
		cq.select(cq.from(Configuracao.class));
		return this.entityManager.createQuery(cq).getResultList();
	}
}
