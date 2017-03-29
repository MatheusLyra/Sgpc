package br.sgpc.dlo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import br.sgpc.dao.RelatorioGerencialDao;

@Stateless
@Remote
public class RelatorioGerencialDLO implements Serializable{

	private static final long serialVersionUID = 1L;
	@EJB
	private RelatorioGerencialDao dao;
	
	public List<Object> consultarRelGerencialTmpProcesso(int numProcesso, Date dtOperacaoIni, Date dtOperacaoFim, int numStatus) throws Exception {
		return this.dao.consultarRelGerencialTmpProcesso(numProcesso, dtOperacaoIni, dtOperacaoFim, numStatus);
	}
	
	public List<Object> consultarRelGerencialTmpArea(int numArea, Date dtOperacaoIni, Date dtOperacaoFim, int numStatus) throws Exception{
		return this.dao.consultarRelGerencialTmpArea(numArea, dtOperacaoIni, dtOperacaoFim, numStatus);
	}
	
	public List<Object> consultarRelGerencialTmpProcTmp(int numProcesso, int numTmp, Date dtOperacaoIni, Date dtOperacaoFim, int numStatus)  throws Exception{
		return this.dao.consultarRelGerencialTmpProcTmp(numProcesso, numTmp, dtOperacaoIni, dtOperacaoFim, numStatus);
	}

}
