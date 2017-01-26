package br.sgpc.dlo;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import br.sgpc.dao.DadosConsolidadosDao;
import br.sgpc.dlo.funcoesUteis.Funcoes;
import br.sgpc.dominio.Dadosconsolidados;

/**
 * Classe de objetos responsável por manter os dados cadastrais dos Dados consolidados.
 *
 */
@Stateless
@Remote
public class MantemDadosConsolidadosDLO extends Funcoes implements Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private DadosConsolidadosDao dao;

	public void cadastrar(Dadosconsolidados dadosConsolidados) throws Exception {
		if (!campoVazio(dadosConsolidados.getDescServico()) 
				|| !campoVazio(dadosConsolidados.getArea().getDescricao())
				|| !campoVazio(dadosConsolidados.getStatus().getDescricao())
				|| !campoVazio(dadosConsolidados.getTipocontrato().getDescricao())
				|| !campoVazio(dadosConsolidados.getUsuario().getUserName())) {
			this.dao.salvarDadosConsolidados(dadosConsolidados);
		} else {
			String msg = "Campo Obrigatório.";
			throw new Exception(msg);
		}
	}

	public void alterar(Dadosconsolidados dadosConsolidados) throws Exception {
		if (!campoVazio(dadosConsolidados.getDescServico()) 
				|| !campoVazio(dadosConsolidados.getArea().getDescricao())
				|| !campoVazio(dadosConsolidados.getStatus().getDescricao())
				|| !campoVazio(dadosConsolidados.getTipocontrato().getDescricao())
				|| !campoVazio(dadosConsolidados.getUsuario().getUserName())) {
			this.dao.atualizarDadosConsolidados(dadosConsolidados);
		} else {
			String msg = "Campo Obrigatório.";
			throw new Exception(msg);
		}
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
