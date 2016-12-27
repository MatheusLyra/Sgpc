package br.sgpc.dlo;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import br.sgpc.dao.EtapaDao;
import br.sgpc.dominio.Etapa;

/**
 * Classe de objetos responsável por manter os dados cadastrais da etapa.
 *
 */
@Stateless
@Remote
public class MantemEtapaDLO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EJB
	private EtapaDao dao;

	public void cadastrar(Etapa etapa) throws Exception{
	    this.dao.salvarEtapa(etapa);
	}

	public void alterar(Etapa etapa) throws Exception{
		this.dao.atualizarEtapa(etapa);
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
