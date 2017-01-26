package br.sgpc.dlo;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import br.sgpc.dao.EtapaDao;
import br.sgpc.dlo.funcoesUteis.Funcoes;
import br.sgpc.dominio.Etapa;

/**
 * Classe de objetos responsável por manter os dados cadastrais da etapa.
 *
 */
@Stateless
@Remote
public class MantemEtapaDLO extends Funcoes implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EJB
	private EtapaDao dao;

	public void cadastrar(Etapa etapa) throws Exception {
		if (!campoVazio(etapa.getDescricao())) {
			this.dao.salvarEtapa(etapa);
		} else {
			String msg = "Campo Obrigatório.";
			throw new Exception(msg);
		}
	}

	public void alterar(Etapa etapa) throws Exception {
		if (!campoVazio(etapa.getDescricao())) {
			this.dao.atualizarEtapa(etapa);
		} else {
			String msg = "Campo Obrigatório.";
			throw new Exception(msg);
		}
	}

	public void excluir(Etapa etapa) throws Exception {
		Etapa e = this.dao.obter(etapa.getIdEtapa());
		if (e != null) {
			this.dao.excluirEtapa(e);
		}
	}
	
	public List<Etapa> carregarDados(){
		return this.dao.consultarEtapa();
	}
}
