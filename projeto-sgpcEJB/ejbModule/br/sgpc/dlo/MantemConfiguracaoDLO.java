package br.sgpc.dlo;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import br.sgpc.dao.ConfiguracaoDao;
import br.sgpc.dominio.Configuracao;

/**
 * Classe de objetos responsável por manter os dados cadastrais de Configuração.
 *
 */
@Stateless
@Remote
public class MantemConfiguracaoDLO implements Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private ConfiguracaoDao dao;
	
	public void alterar(Configuracao configuracao) throws Exception{
		this.dao.atualizarConfiguracao(configuracao);
	}
	
	public Configuracao obterDados(int id) throws Exception{
		return this.dao.obter(id);
	}
	
	public List<Configuracao> carregarDados(){
		return this.dao.consultarConfiguracao();
	}
}
