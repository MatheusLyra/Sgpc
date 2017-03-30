package br.sgpc.mbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.sgpc.dlo.MantemConfiguracaoDLO;
import br.sgpc.dlo.funcoesUteis.Funcoes;
import br.sgpc.dominio.Configuracao;

/**
 * Bean responsável por manter todas os parametros de configuração do sistema.
 */
@ManagedBean(name = "mbMantemConfiguracao")
@SessionScoped
public class MbMantemConfiguracao extends Funcoes implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EJB
	private MantemConfiguracaoDLO mantemConfiguracaoDLO;
	
	private Configuracao configuracao;
	
	private List<Configuracao> listaConfiguracao;
	
	@PostConstruct
	public void inicializar(){
		configuracao      = new Configuracao();
		listaConfiguracao = new ArrayList<Configuracao>();
		listaConfiguracao = mantemConfiguracaoDLO.carregarDados();
		configuracao      = listaConfiguracao.get(0);
	}
	
	public void salvar(){
		try {
			mantemConfiguracaoDLO.alterar(configuracao);
			msgInfo("Registro gravado com sucesso.");
		} catch (Exception e) {
			msgErro("Erro ao gravar dados com a seguinte mensagem: " + e.getMessage());
		}
	}
	
    public void addMessage() {
        String summary = configuracao.isDtFinalizacaoAut_Cronograma() ? "Habilitado (Salve para gravar a opção)" : "Desabilitado (Salve para gravar a opção)";
        /*FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));*/
        msgInfo(summary);
    }	

	public Configuracao getConfiguracao() {
		return configuracao;
	}

	public void setConfiguracao(Configuracao configuracao) {
		this.configuracao = configuracao;
	}
	

}
