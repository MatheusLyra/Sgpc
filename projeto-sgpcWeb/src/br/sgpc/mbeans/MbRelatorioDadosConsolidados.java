package br.sgpc.mbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.sgpc.dlo.MantemAreaDLO;
import br.sgpc.dlo.MantemFornecedorDLO;
import br.sgpc.dlo.MantemStatusDLO;
import br.sgpc.dlo.MantemUsuarioDLO;
import br.sgpc.dlo.RelatorioDLO;
import br.sgpc.dlo.funcoesUteis.Funcoes;
import br.sgpc.dominio.Area;
import br.sgpc.dominio.Fornecedor;
import br.sgpc.dominio.Status;
import br.sgpc.dominio.Usuario;

@ManagedBean(name = "mbRelatorioDadosConsolidados")
@ViewScoped
public class MbRelatorioDadosConsolidados extends Funcoes implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private RelatorioDLO relatorioDLO;
	
	@EJB
	private MantemAreaDLO mantemAreaDLO;
	
	@EJB
	private MantemFornecedorDLO mantemFornecedorDLO;
	
	@EJB
	private MantemUsuarioDLO mantemUsuarioDLO;	
	
	@EJB
	private MantemStatusDLO mantemStatusDLO;		
	
	private List<Object> listaRelatorio;
	private List<Area> listaArea;
	private List<Fornecedor> listaFornecedor;
	private List<Usuario> listaUsuario;
	private List<Status> listaStatus;

	private int numProcesso;
	private int idArea;
	private int idFornecedor;
	private int idUsuario;
	private int idStatus;
	private int flgUrgente;
	private String tipoDado;
	private String numContrato;
	private boolean paginatorActive = true;
	
	private Area area;
	private Fornecedor fornecedor;
	private Usuario usuario;
	private Status status;
	
	@PostConstruct
	public void inicializar(){ 
		listaArea           = new ArrayList<Area>();
		listaFornecedor     = new ArrayList<Fornecedor>();
		listaUsuario        = new ArrayList<Usuario>();
		listaStatus         = new ArrayList<Status>();
		
		carregarDados();
		
		flgUrgente = -1;
	}
	
	private void carregarDados(){
		listaArea             = mantemAreaDLO.carregarDados();
		listaFornecedor       = mantemFornecedorDLO.carregarDados();
		listaUsuario          = mantemUsuarioDLO.carregarDados();
		listaStatus           = mantemStatusDLO.carregarDados();
	}
	
	public void pesquisar(){
		try {
			idArea = area.getIdArea();
		} catch (Exception e) {
			idArea = 0;
		}
			
		try {
			idFornecedor = fornecedor.getIdFornecedor();
		} catch (Exception e) {
			idFornecedor = 0;
		}
		
		try {
			idUsuario = usuario.getIdUsuario();
		} catch (Exception e) {
			idUsuario = 0;
		}	
		
		try {
			idStatus = status.getIdStatus();
		} catch (Exception e) {
			idStatus = 0;
		}		

		listaRelatorio = relatorioDLO.consultarRelDadosConsolidados(numProcesso, tipoDado, numContrato, idArea, idFornecedor, idUsuario, idStatus, flgUrgente);
	}
	
	public void limpar(){
		numProcesso = 0;
		tipoDado    = null;
		numContrato = null;
		
		idArea = 0;
		idFornecedor = 0;
		idStatus = 0;
		idUsuario = 0;
		flgUrgente = -1;
		listaRelatorio = null;
	}

	public List<Object> getListaRelatorio() {
		return listaRelatorio;
	}

	public void setListaRelatorio(List<Object> listaRelatorio) {
		this.listaRelatorio = listaRelatorio;
	}

	public int getNumProcesso() {
		return numProcesso;
	}

	public void setNumProcesso(int numProcesso) {
		this.numProcesso = numProcesso;
	}

	public String getTipoDado() {
		return tipoDado;
	}

	public void setTipoDado(String tipoDado) {
		this.tipoDado = tipoDado;
	}

	public String getNumContrato() {
		return numContrato;
	}

	public void setNumContrato(String numContrato) {
		this.numContrato = numContrato;
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

	public List<Fornecedor> getListaFornecedor() {
		return listaFornecedor;
	}

	public void setListaFornecedor(List<Fornecedor> listaFornecedor) {
		this.listaFornecedor = listaFornecedor;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Status> getListaStatus() {
		return listaStatus;
	}

	public void setListaStatus(List<Status> listaStatus) {
		this.listaStatus = listaStatus;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getFlgUrgente() {
		return flgUrgente;
	}

	public void setFlgUrgente(int flgUrgente) {
		this.flgUrgente = flgUrgente;
	}
	
	public void activatePaginator() {
        paginatorActive = true;
    }

    public void deactivatePaginator() {
        paginatorActive = false;
    }

    public boolean isPaginatorActive() {
        return paginatorActive;
    }	
		
}
