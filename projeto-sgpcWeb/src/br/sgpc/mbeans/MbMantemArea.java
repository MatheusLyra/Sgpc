package br.sgpc.mbeans;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.sgpc.dlo.MantemAreaDLO;
import br.sgpc.dlo.funcoesUteis.Funcoes;
import br.sgpc.dominio.Area;

/**
 * Bean responsável por cadastrar uma nova area, alterar, excluir e visualizar
 * as areas cadastradas.
 */
@ManagedBean(name = "mbMantemArea")
@SessionScoped
public class MbMantemArea extends Funcoes implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EJB
    private MantemAreaDLO mantemAreaDLO;
	
	private Area area;
	
	private List<Area> listaArea;
	
	private List<Area> listaAreaFiltrada;
	
	private Boolean modoEdicao;
    
	@PostConstruct
	public void inicializar(){
        area = new Area();		
		listaArea = new ArrayList<Area>();
		carregarArea();
		
		modoEdicao = false;
	}
	
	private void carregarArea(){
		listaArea = mantemAreaDLO.carregarDados();
	}
	
	public void cadastrar() {
		if (modoEdicao) {
			try {
				mantemAreaDLO.alterar(area);
				carregarArea();
				
				msgInfo("Registro alterado com sucesso.");
				
			} catch (Exception e) {
				msgErro("Erro ao alterar dados com a seguinte mensagem: " + e.getMessage());				
			}
			modoEdicao = false;
			limpar();
		} else {
			try {
				mantemAreaDLO.cadastrar(area);
				carregarArea();
				msgInfo("Registro cadastrado com sucesso!");
			} catch (Exception e) {
				msgErro("Erro ao cadastrar dados com a seguinte mensagem: " + e.getMessage());
			}
			limpar();
		}
	}
	
	public void limpar(){
		area = new Area();
		
		modoEdicao = false;
	}
	
	public void editar(){
		modoEdicao = true;
	}
	
	public void excluir(Area area){
		try {
			mantemAreaDLO.excluir(area);
			carregarArea();
		} catch (Exception e) {
			msgErro("Erro ao deletar registro com a seguinte mensagem: "+e.getMessage());
		}		
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public List<Area> getListaArea() {
		return listaArea;
	}

	public void setListaArea(List<Area> listaArea) {
		this.listaArea = listaArea;
	}

	public Boolean getModoEdicao() {
		return modoEdicao;
	}

	public void setModoEdicao(Boolean modoEdicao) {
		this.modoEdicao = modoEdicao;
	}

    public String getNomeArquivo() {
        return "Area";
    }

	public List<Area> getListaAreaFiltrada() {
		return listaAreaFiltrada;
	}

	public void setListaAreaFiltrada(List<Area> listaAreaFiltrada) {
		this.listaAreaFiltrada = listaAreaFiltrada;
	}
	
    
    
}
