package br.sgpc.mbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.sgpc.dlo.MantemEtapaDLO;
import br.sgpc.dlo.funcoesUteis.Funcoes;
import br.sgpc.dominio.Etapa;

/**
 * Bean responsável por cadastrar uma nova area, alterar, excluir e visualizar
 * as areas cadastradas.
 */
@ManagedBean(name = "mbMantemEtapa")
@SessionScoped
public class MbMantemEtapa extends Funcoes implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
    private MantemEtapaDLO mantemEtapaDLO;
	
	private Etapa etapa;
	
	private List<Etapa> listaEtapa;
	
	private Boolean modoEdicao;
    
	@PostConstruct
	public void inicializar(){
		etapa = new Etapa();
		listaEtapa = new ArrayList<Etapa>();
		carregarEtapa();
		
		modoEdicao = false;
	}
	
	private void carregarEtapa(){
		listaEtapa = mantemEtapaDLO.carregarDados();
	}
	
	public void cadastrar() {
		if (modoEdicao) {
			try {
				mantemEtapaDLO.alterar(etapa);
				carregarEtapa();

				msgInfo("Registro alterado com sucesso.");

			} catch (Exception e) {
				msgErro("Erro ao alterar dados com a seguinte mensagem: " + e.getMessage());		
			}
			modoEdicao = false;
			limpar();
		} else {
			try {
				mantemEtapaDLO.cadastrar(etapa);
				carregarEtapa();
				
				msgInfo("Registro cadastrado com sucesso!");
			} catch (Exception e) {
				msgErro("Erro ao cadastrar dados com a seguinte mensagem: " + e.getMessage());
			}
			limpar();
		}
	}
	
	public void limpar(){
		etapa = new Etapa();
		
		modoEdicao = false;
	}
	
	public void editar(){
		modoEdicao = true;
	}
	
	public void excluir(Etapa etapa){
		try {
			mantemEtapaDLO.excluir(etapa);
			carregarEtapa();
		} catch (Exception e) {
			msgErro("Erro ao deletar registro com a seguinte mensagem: "+e.getMessage());
		}		
	}

	public Etapa getEtapa() {
		return etapa;
	}

	public void setEtapa(Etapa etapa) {
		this.etapa = etapa;
	}

	public List<Etapa> getListaEtapa() {
		return listaEtapa;
	}

	public void setListaEtapa(List<Etapa> listaEtapa) {
		this.listaEtapa = listaEtapa;
	}

	public Boolean getModoEdicao() {
		return modoEdicao;
	}

	public void setModoEdicao(Boolean modoEdicao) {
		this.modoEdicao = modoEdicao;
	}
	
    public String getNomeArquivo() {
        return "Etapa";
    }	
	
}
