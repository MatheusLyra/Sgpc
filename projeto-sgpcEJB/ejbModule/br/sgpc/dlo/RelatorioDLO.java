package br.sgpc.dlo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import br.sgpc.dao.RelatorioDao;

@Stateless
@Remote
public class RelatorioDLO implements Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private RelatorioDao dao;
	
	public List<Object> consultarRelDadosConsolidados(int numProcesso, String tipoDado, String numContrato, int idArea, int idFornecedor, int idUsuario, int idStatus, int flgUrgente){
		return this.dao.consultarRelDadosConsolidados(numProcesso, tipoDado, numContrato, idArea, idFornecedor, idUsuario, idStatus, flgUrgente);
	}
	
	public List<Object> consultarRelAuditoriaDadosConsolidados(int numProcesso, String tipoOperacao, Date dtOperacaoIni, Date dtOperacaoFim, int idUsuarioAuditoria ){
		return this.dao.consultarRelAuditoriaDadosConsolidados(numProcesso, tipoOperacao, dtOperacaoIni, dtOperacaoFim, idUsuarioAuditoria);
	}
	
	public List<Object> consultarRelAuditoriaCronograma(int numProcesso, String tipoOperacao, Date dtOperacaoIni, Date dtOperacaoFim, int idUsuarioAuditoria ){
		return this.dao.consultarRelAuditoriaCronograma(numProcesso, tipoOperacao, dtOperacaoIni, dtOperacaoFim, idUsuarioAuditoria);
	}	
}
