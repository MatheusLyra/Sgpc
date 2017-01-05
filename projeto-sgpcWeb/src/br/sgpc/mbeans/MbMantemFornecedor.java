package br.sgpc.mbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.sgpc.dlo.MantemFornecedorDLO;
import br.sgpc.dominio.Fornecedor;

/**
 * Bean responsável por cadastrar uma nova area, alterar, excluir e visualizar
 * as areas cadastradas.
 */
@ManagedBean(name = "mbMantemFornecedor")
@SessionScoped
public class MbMantemFornecedor implements Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
    private MantemFornecedorDLO mantemFornecedorDLO;
	
	private Fornecedor fornecedor;
	
    private String cnpj;
	
	private List<Fornecedor> listaFornecedor;
	
	private Boolean modoEdicao;
    
	@PostConstruct
	public void inicializar(){
		fornecedor = new Fornecedor();
		listaFornecedor = new ArrayList<Fornecedor>();
		carregarFornecedor();
		
		modoEdicao = false;
	}
	
	private void carregarFornecedor(){
		listaFornecedor = mantemFornecedorDLO.carregarDados();
	}
	
	public void cadastrar() {
		try {
			fornecedor.setCnpj(Long.parseLong(cnpj));
		} catch (Exception e) {
			e.getMessage();
		}
		
		if (modoEdicao) {
			try {
				mantemFornecedorDLO.alterar(fornecedor);
				carregarFornecedor();

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro alterado com sucesso."));

			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
						"Erro ao alterar dados com a seguinte mensagem: " + e.getMessage()));
				
			}
			modoEdicao = false;
			limpar();
		} else {
			try {
				mantemFornecedorDLO.cadastrar(fornecedor);
				carregarFornecedor();
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro cadastrado com sucesso!"));
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
						"Erro ao cadastrar dados com a seguinte mensagem: " + e.getMessage()));
			}
			limpar();
		}
	}
	
	public void limpar(){
		fornecedor = new Fornecedor();
		
		cnpj = null;
		
		modoEdicao = false;
	}
	
	public void editar(){
		modoEdicao = true;
		cnpj = String.valueOf(fornecedor.getCnpj());
	}
	
	public void excluir(Fornecedor fornecedor){
		try {
			mantemFornecedorDLO.excluir(fornecedor);
			carregarFornecedor();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao deletar registro com a seguinte mensagem: "+e.getMessage()));
		}		
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public List<Fornecedor> getListaFornecedor() {
		return listaFornecedor;
	}

	public void setListaFornecedor(List<Fornecedor> listaFornecedor) {
		this.listaFornecedor = listaFornecedor;
	}

	public Boolean getModoEdicao() {
		return modoEdicao;
	}

	public void setModoEdicao(Boolean modoEdicao) {
		this.modoEdicao = modoEdicao;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
    public String getNomeArquivo() {
        return "Fornecedor";
    }	
	
}
