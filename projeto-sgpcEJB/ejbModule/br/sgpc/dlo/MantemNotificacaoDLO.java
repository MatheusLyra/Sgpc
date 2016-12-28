package br.sgpc.dlo;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import br.sgpc.dao.NotificacaoDao;
import br.sgpc.dominio.Notificacao;

/**
 * Classe de objetos responsável por manter os dados cadastrais das notificaçãoes.
 *
 */
@Stateless
@Remote
public class MantemNotificacaoDLO  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private NotificacaoDao dao;

	public void cadastrar(Notificacao notificacao) throws Exception{
	    this.dao.salvarNotificacao(notificacao);
	}

	public void alterar(Notificacao notificacao) throws Exception{
		this.dao.atualizarNotificacao(notificacao);
	}

	public void excluir(Notificacao notificacao) throws Exception {
		Notificacao n = this.dao.obter(notificacao.getIdNotificacao());
		if (n != null) {
			this.dao.excluirNotificacao(n);
		}
	}
	
	public List<Notificacao> carregarDados(){
		return this.dao.consultarNotificacoes();
	}
	
	public List<Notificacao> carregarDadoNotUsu(Integer id){
		return this.dao.consultarNotificacao(id);
	}
}
