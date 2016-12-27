package br.sgpc.dlo;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import br.sgpc.dao.TipoContratoDao;
import br.sgpc.dominio.Tipocontrato;

/**
 * Classe de objetos responsável por manter os dados cadastrais do tipo de contrato.
 *
 */
@Stateless
@Remote
public class MantemTipoContratoDLO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EJB
	private TipoContratoDao dao;

	public void cadastrar(Tipocontrato tipoContrato) throws Exception{
	    this.dao.salvarTipoContrato(tipoContrato);
	}

	public void alterar(Tipocontrato tipoContrato) throws Exception{
		this.dao.atualizarTipoContrato(tipoContrato);
	}

	public void excluir(Tipocontrato tipoContrato) throws Exception {
		Tipocontrato t = this.dao.obter(tipoContrato.getIdTipoContrato());
		if (t != null) {
			this.dao.excluirTipoContrato(t);
		}
	}
	
	public List<Tipocontrato> carregarDados(){
		return this.dao.consultarTipoContratos();
	}
}
