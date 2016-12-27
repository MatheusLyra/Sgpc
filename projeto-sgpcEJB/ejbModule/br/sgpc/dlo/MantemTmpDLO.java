package br.sgpc.dlo;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import br.sgpc.dao.TmpDao;
import br.sgpc.dominio.Tmp;

/**
 * Classe de objetos responsável por manter os dados cadastrais da tmp.
 *
 */

@Stateless
@Remote
public class MantemTmpDLO implements Serializable{
	private static final long serialVersionUID = 1L;

	@EJB
	private TmpDao dao;

	public void cadastrar(Tmp tmp) throws Exception{
	    this.dao.salvarTmp(tmp);
	}

	public void alterar(Tmp tmp) throws Exception{
		this.dao.atualizarTmp(tmp);
	}

	public void excluir(Tmp tmp) throws Exception {
		Tmp t = this.dao.obter(tmp.getIdTmp());
		if (t != null) {
			this.dao.excluirTmp(t);
		}
	}
	
	public List<Tmp> carregarDados(){
		return this.dao.consultarTmps();
	}
}
