package br.sgpc.dlo;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import br.sgpc.dao.StatusDao;
import br.sgpc.dlo.funcoesUteis.Funcoes;
import br.sgpc.dominio.Status;

/**
 * Classe de objetos responsável por manter os dados cadastrais do status.
 *
 */
@Stateless
@Remote
public class MantemStatusDLO extends Funcoes implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EJB
	private StatusDao dao;

	public void cadastrar(Status status) throws Exception {
		if (!campoVazio(status.getDescricao())) {
			this.dao.salvarStatus(status);
		} else {
			String msg = "Campo Obrigatório.";
			throw new Exception(msg);
		}
	}

	public void alterar(Status status) throws Exception {
		if (!campoVazio(status.getDescricao())) {
			this.dao.atualizarStatus(status);
		} else {
			String msg = "Campo Obrigatório.";
			throw new Exception(msg);
		}
	}

	public void excluir(Status status) throws Exception {
		Status t = this.dao.obter(status.getIdStatus());
		if (t != null) {
			this.dao.excluirStatus(t);
		}
	}
	
	public Status obterDados(Integer id){
		return this.dao.obter(id);
	}
	
	public List<Status> carregarDados(){
		return this.dao.consultarStatus();
	}
}
