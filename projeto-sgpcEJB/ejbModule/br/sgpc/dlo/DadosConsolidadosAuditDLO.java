package br.sgpc.dlo;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import br.sgpc.dao.DadosConsolidadosAuditDao;
import br.sgpc.dlo.funcoesUteis.Funcoes;
import br.sgpc.dominio.DadosConsolidadosAudit;
import br.sgpc.dominio.Dadosconsolidados;

@Stateless
@Remote
public class DadosConsolidadosAuditDLO extends Funcoes implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EJB
	private DadosConsolidadosAuditDao dao;
	
	
	public void incluir(Dadosconsolidados dadosconsolidados, String tipoOperacao) throws Exception {
		this.dao.incluir(this.popularObjeto(dadosconsolidados, tipoOperacao));
	}
	
	public List<DadosConsolidadosAudit> listarOperacoes(){
		return dao.listarOperacoes();
	}
	
	public DadosConsolidadosAudit popularObjeto(Dadosconsolidados dadosconsolidados, String tipoOperacao) throws Exception {
		java.util.Date dataAtual = new java.util.Date(System.currentTimeMillis());  
		DadosConsolidadosAudit dadosAudit=new DadosConsolidadosAudit();
		dadosAudit.setNumProcesso(dadosconsolidados.getNumProcesso());
		dadosAudit.setTipoDado(dadosconsolidados.getTipoDado());
		dadosAudit.setNumContrato(dadosconsolidados.getNumContrato());
		dadosAudit.setTac(dadosconsolidados.getTac());
		dadosAudit.setDescServico(dadosconsolidados.getDescServico());
		dadosAudit.setVlPropostaIni(dadosconsolidados.getVlPropostaIni());
		dadosAudit.setVlPropostaFim(dadosconsolidados.getVlPropostaFim());
		if (dadosconsolidados.getUsuario() !=null) {
		    dadosAudit.setUsuario(dadosconsolidados.getUsuario().getIdUsuario());
		}
		if (dadosconsolidados.getFornecedor() !=null) {
   		   dadosAudit.setFornecedor(dadosconsolidados.getFornecedor().getIdFornecedor());
		}	
		if (dadosconsolidados.getTipocontrato() !=null) {
			dadosAudit.setTipocontrato(dadosconsolidados.getTipocontrato().getIdTipoContrato());	
		}
		if (dadosconsolidados.getArea() !=null) {
  		   dadosAudit.setIdArea(dadosconsolidados.getArea().getIdArea());
		}
		if (dadosconsolidados.getStatus() !=null) {
		    dadosAudit.setStatus(dadosconsolidados.getStatus().getIdStatus());
		}
		dadosAudit.setFlgUrgente(dadosconsolidados.getFlgUrgente());
		dadosAudit.setTipoOperacao(tipoOperacao);
		dadosAudit.setDtOperacao(dataAtual);
		dadosAudit.setIdUsuarioAudit(getIdUsuario());
		return dadosAudit;
	}

}
