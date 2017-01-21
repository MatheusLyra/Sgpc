package br.sgpc.dlo;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import br.sgpc.dao.DadosConsolidadosDao;
import br.sgpc.dominio.Dadosconsolidados;

/**
 * Classe de objetos responsável por manter os dados cadastrais dos Dados consolidados.
 *
 */
@Stateless
@Remote
public class MantemDadosConsolidadosDLO implements Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private DadosConsolidadosDao dao;

	public void cadastrar(Dadosconsolidados dadosConsolidados) throws Exception{
	    this.dao.salvarDadosConsolidados(dadosConsolidados);
	}

	public void alterar(Dadosconsolidados dadosConsolidados) throws Exception{
		this.dao.atualizarDadosConsolidados(dadosConsolidados);
	}
	
	public void excluir(Dadosconsolidados dadosConsolidados) throws Exception {
		Dadosconsolidados d = this.dao.obter(dadosConsolidados.getNumProcesso());
		if (d != null) {
			this.dao.excluirDadosConsolidados(d);
		}
	}
	
	public List<Dadosconsolidados> carregarDados(){
		return this.dao.consultarDadosConsolidados();
	}
	
}
