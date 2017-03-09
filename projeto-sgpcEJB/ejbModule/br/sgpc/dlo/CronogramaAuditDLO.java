package br.sgpc.dlo;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.sgpc.dao.CronogramaAuditDao;
import br.sgpc.dominio.Cronograma;
import br.sgpc.dominio.CronogramaAudit;
import br.sgpc.dominio.Usuario;



/**
 * Classe de objetos responsável por manter do cronograma.
 *
 */
@Stateless
@Remote
public class CronogramaAuditDLO  implements Serializable{
 
	private static final long serialVersionUID = 1L;

	@EJB
	private CronogramaAuditDao dao;

	
	public void inserir(Cronograma cronograma, String tipoOperacao) throws Exception{
		this.dao.incluir(this.popularObjeto(cronograma, tipoOperacao));
	}
	
	public CronogramaAudit popularObjeto(Cronograma cronograma, String tipoOperacao) throws Exception {
		java.util.Date dataAtual = new java.util.Date(System.currentTimeMillis());  
		CronogramaAudit cronogramaAudit=new CronogramaAudit();
		cronogramaAudit.setIdCronograma(cronograma.getIdCronograma());
		cronogramaAudit.setDtIni(cronograma.getDtIni());
		cronogramaAudit.setDtFim(cronograma.getDtFim());
		cronogramaAudit.setQtdDiasFim(cronograma.getQtdDiasFim());
		cronogramaAudit.setDtFinalizado(cronograma.getDtFinalizado());
		if (cronograma.getDadosconsolidados() != null) {
			cronogramaAudit.setNumProcesso(cronograma.getDadosconsolidados().getNumProcesso());	
		}
		cronogramaAudit.setStatus(cronograma.getStatus());
		if (cronograma.getEtapa() != null) {
			cronogramaAudit.setIdEtapa(cronograma.getEtapa().getIdEtapa());
		}
		if (cronograma.getTmp() != null) {
			cronogramaAudit.setIdTMP(cronograma.getTmp().getIdTmp());	
		}
		cronogramaAudit.setObservacoes(cronograma.getObservacoes());
//		cronogramaAudit.setIdUsuario(getIdUsuario());
		cronogramaAudit.setTipoOperacao(tipoOperacao);
		cronogramaAudit.setDtOperacao(dataAtual);
		return cronogramaAudit;
	}

	private Integer getIdUsuario(){
		HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuarioSessao = (Usuario) sessao.getAttribute("usuario");
	    
	    return usuarioSessao.getIdUsuario();
	}
	
}
