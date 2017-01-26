package br.sgpc.dlo;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import br.sgpc.dao.TipoContratoDao;
import br.sgpc.dlo.funcoesUteis.Funcoes;
import br.sgpc.dominio.Tipocontrato;

/**
 * Classe de objetos responsável por manter os dados cadastrais do tipo de contrato.
 *
 */
@Stateless
@Remote
public class MantemTipoContratoDLO extends Funcoes implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EJB
	private TipoContratoDao dao;

	public void cadastrar(Tipocontrato tipoContrato) throws Exception {
		if (!campoVazio(tipoContrato.getDescricao())) {
			this.dao.salvarTipoContrato(tipoContrato);
		} else {
			String msg = "Campo Obrigatório.";
			throw new Exception(msg);
		}
	}

	public void alterar(Tipocontrato tipoContrato) throws Exception{
		if (!campoVazio(tipoContrato.getDescricao())) {
			this.dao.atualizarTipoContrato(tipoContrato);
		} else {
			String msg = "Campo Obrigatório.";
			throw new Exception(msg);
		}
	}

	public void excluir(Tipocontrato tipoContrato) throws Exception {
		Tipocontrato t = this.dao.obter(tipoContrato.getIdTipoContrato());
		if (t != null) {
			this.dao.excluirTipoContrato(t);
		}
	}
	
	public Tipocontrato obterDados(Integer id){
		return this.dao.obter(id);
	}
	
	public List<Tipocontrato> carregarDados(){
		return this.dao.consultarTipoContratos();
	}
}
