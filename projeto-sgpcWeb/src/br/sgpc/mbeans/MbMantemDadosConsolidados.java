package br.sgpc.mbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.sgpc.dlo.MantemAreaDLO;
import br.sgpc.dlo.MantemDadosConsolidadosDLO;
import br.sgpc.dlo.MantemFornecedorDLO;
import br.sgpc.dlo.MantemStatusDLO;
import br.sgpc.dlo.MantemTipoContratoDLO;
import br.sgpc.dlo.MantemUsuarioDLO;
import br.sgpc.dlo.funcoesUteis.Funcoes;
import br.sgpc.dominio.Area;
import br.sgpc.dominio.Dadosconsolidados;
import br.sgpc.dominio.Fornecedor;
import br.sgpc.dominio.Status;
import br.sgpc.dominio.Tipocontrato;
import br.sgpc.dominio.Usuario;

/**
 * Bean responsável por cadastrar os dados consolidados, alterar, excluir e visualizar.
 */
@ManagedBean(name = "mbMantemDadosConsolidados")
@SessionScoped
public class MbMantemDadosConsolidados extends Funcoes implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private MantemAreaDLO mantemAreaDLO;
	
	@EJB
	private MantemFornecedorDLO mantemFornecedorDLO;
	
	@EJB
	private MantemStatusDLO mantemStatusDLO;
	
	@EJB
	private MantemTipoContratoDLO mantemTipoContratoDLO;
	
	@EJB
	private MantemDadosConsolidadosDLO mantemDadosConsolidadosDLO;	
	
	@EJB
	private MantemUsuarioDLO mantemUsuarioDLO;
	
	private Dadosconsolidados dadosConsolidados;
	
	private List<Dadosconsolidados> listaDadosConsolidados;
	private List<Area> listaArea;
	private List<Fornecedor> listaFornecedor;
	private List<Status> listaStatus;
	private List<Tipocontrato> listaTipoContrato;
	private List<Usuario> listaUsuario;
	
	private Boolean modoEdicao;
	private Boolean flgUrgente;
		
	@PostConstruct
	public void inicializar(){
        dadosConsolidados      = new Dadosconsolidados();
        
		listaDadosConsolidados = new ArrayList<Dadosconsolidados>();
		listaArea              = new ArrayList<Area>();
		listaFornecedor        = new ArrayList<Fornecedor>();
		listaStatus            = new ArrayList<Status>();
		listaTipoContrato      = new ArrayList<Tipocontrato>();
		listaUsuario           = new ArrayList<Usuario>();
		
		carregarDados();
		
		modoEdicao = false;
	}
	
	private void carregarDados(){
		listaDadosConsolidados = mantemDadosConsolidadosDLO.carregarDados();
		listaArea              = mantemAreaDLO.carregarDados();
		listaFornecedor        = mantemFornecedorDLO.carregarDados();
		listaStatus            = mantemStatusDLO.carregarDados();
		listaTipoContrato      = mantemTipoContratoDLO.carregarDados();
		listaUsuario           = mantemUsuarioDLO.carregarDados();
	}
	
	public void cadastrar() {
		if (flgUrgente) {
			dadosConsolidados.setFlgUrgente(1);
		}else{
			dadosConsolidados.setFlgUrgente(0);
		}
		
		if (modoEdicao) {
			try {
				mantemDadosConsolidadosDLO.alterar(dadosConsolidados);
				carregarDados();
				msgInfo("Registro alterado com sucesso.");	
			} catch (Exception e) {
				msgErro("Erro ao alterar dados com a seguinte mensagem: " + e.getMessage());				
			}
			modoEdicao = false;
			limpar();
		} else {
			try {
				mantemDadosConsolidadosDLO.cadastrar(dadosConsolidados);
				carregarDados();
				msgInfo("Registro cadastrado com sucesso!");
			} catch (Exception e) {
				msgErro("Erro ao cadastrar dados com a seguinte mensagem: " + e.getMessage());
			}
			limpar();
		}
	}
	
	public void limpar(){
		dadosConsolidados = new Dadosconsolidados();
		modoEdicao = false;
		flgUrgente = false;
	}
	
	public void editar(){
		modoEdicao = true;
		if (dadosConsolidados.getFlgUrgente().equals(1)) {
			flgUrgente = true;
		}else {
			flgUrgente = false;
		}
	}
	
	public void excluir(Dadosconsolidados dadosConsolidados){
		try {
			mantemDadosConsolidadosDLO.excluir(dadosConsolidados);
			carregarDados();
		} catch (Exception e) {
			msgErro("Erro ao deletar registro com a seguinte mensagem: "+e.getMessage());
		}		
	}

	public Dadosconsolidados getDadosConsolidados() {
		return dadosConsolidados;
	}

	public void setDadosConsolidados(Dadosconsolidados dadosConsolidados) {
		this.dadosConsolidados = dadosConsolidados;
	}

	public List<Dadosconsolidados> getListaDadosConsolidados() {
		return listaDadosConsolidados;
	}

	public void setListaDadosConsolidados(List<Dadosconsolidados> listaDadosConsolidados) {
		this.listaDadosConsolidados = listaDadosConsolidados;
	}

	public List<Area> getListaArea() {
		return listaArea;
	}

	public void setListaArea(List<Area> listaArea) {
		this.listaArea = listaArea;
	}

	public List<Fornecedor> getListaFornecedor() {
		return listaFornecedor;
	}

	public void setListaFornecedor(List<Fornecedor> listaFornecedor) {
		this.listaFornecedor = listaFornecedor;
	}

	public List<Status> getListaStatus() {
		return listaStatus;
	}

	public void setListaStatus(List<Status> listaStatus) {
		this.listaStatus = listaStatus;
	}

	public List<Tipocontrato> getListaTipoContrato() {
		return listaTipoContrato;
	}

	public void setListaTipoContrato(List<Tipocontrato> listaTipoContrato) {
		this.listaTipoContrato = listaTipoContrato;
	}

	public Boolean getModoEdicao() {
		return modoEdicao;
	}

	public void setModoEdicao(Boolean modoEdicao) {
		this.modoEdicao = modoEdicao;
	}

	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}
	
    public String getNomeArquivo() {
        return "Dados Consolidados";
    }

	public Boolean getFlgUrgente() {
		return flgUrgente;
	}

	public void setFlgUrgente(Boolean flgUrgente) {
		this.flgUrgente = flgUrgente;
	}	
	
}
