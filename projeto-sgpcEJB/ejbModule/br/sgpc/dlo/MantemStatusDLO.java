package br.sgpc.dlo;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import br.sgpc.dao.StatusDao;
import br.sgpc.dominio.Status;

/**
 * Classe de objetos responsável por manter os dados cadastrais do status.
 *
 */
@Stateless
@Remote
public class MantemStatusDLO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EJB
	private StatusDao dao;

	public void cadastrar(Status status) throws Exception{
	    this.dao.salvarStatus(status);
	}

	public void alterar(Status status) throws Exception{
		this.dao.atualizarStatus(status);
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
