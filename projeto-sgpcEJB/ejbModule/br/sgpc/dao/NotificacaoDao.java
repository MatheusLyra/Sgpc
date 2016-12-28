package br.sgpc.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import br.sgpc.dominio.Notificacao;

/**
 * Classe de objetos de acesso a dados relacionados a Notificação.
 *
 */
@Stateless
@Local
public class NotificacaoDao implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName = "pu")
	private EntityManager entityManager;

	public void salvarNotificacao(Notificacao notificacao) throws Exception {
		try {
			this.entityManager.persist(notificacao);
		} catch (Exception e) {
			throw e;
		}
	}

	public void atualizarNotificacao(Notificacao notificacao) throws Exception {
		try {
			this.entityManager.merge(notificacao);
			this.entityManager.flush();
		} catch (Exception e) {
			throw e;
		}
	}

	public Notificacao obter(Integer id) {
		return this.entityManager.find(Notificacao.class, id);
	}

	public void excluirNotificacao(Notificacao notificacao) throws Exception {
		try {
			this.entityManager.remove(notificacao);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public List<Notificacao> consultarNotificacao(Integer id) {

		List<Notificacao> notificacoes = new ArrayList<Notificacao>();

		if (id > 0) {
			notificacoes = this.entityManager.createQuery("select n from Notificacao n JOIN n.usuario u where u.idUsuario = ?1", Notificacao.class)
					.setParameter(1, id).getResultList();
		}
		return notificacoes;
	}

	public List<Notificacao> consultarNotificacoes() {
		CriteriaQuery<Notificacao> cq = this.entityManager.getCriteriaBuilder().createQuery(Notificacao.class);
		cq.select(cq.from(Notificacao.class));
		return this.entityManager.createQuery(cq).getResultList();
	}
}
