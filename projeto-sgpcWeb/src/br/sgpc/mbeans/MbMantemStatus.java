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

import br.sgpc.dlo.MantemStatusDLO;
import br.sgpc.dominio.Status;

/**
 * Bean responsável por cadastrar um novo status, alterar, excluir e visualizar
 * os status cadastrados.
 */
@ManagedBean(name = "mbMantemStatus")
@SessionScoped
public class MbMantemStatus  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
    private MantemStatusDLO mantemStatusDLO;
	
	private Status status;
	
	private List<Status> listaStatus;
	
	private Boolean modoEdicao;
    
	@PostConstruct
	public void inicializar(){
		status = new Status();
		listaStatus = new ArrayList<Status>();
		carregarStatus();
		
		modoEdicao = false;
	}
	
	private void carregarStatus(){
		listaStatus = mantemStatusDLO.carregarDados();
	}
	
	public void cadastrar() {
		if (modoEdicao) {
			try {
				mantemStatusDLO.alterar(status);
				carregarStatus();

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
				mantemStatusDLO.cadastrar(status);
				carregarStatus();
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
		status = new Status();
		
		modoEdicao = false;
	}
	
	public void editar(){
		modoEdicao = true;
	}
	
	public void excluir(Status status){
		try {
			mantemStatusDLO.excluir(status);
			carregarStatus();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao deletar registro com a seguinte mensagem: "+e.getMessage()));
		}		
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<Status> getListaStatus() {
		return listaStatus;
	}

	public void setListaStatus(List<Status> listaStatus) {
		this.listaStatus = listaStatus;
	}

	public Boolean getModoEdicao() {
		return modoEdicao;
	}

	public void setModoEdicao(Boolean modoEdicao) {
		this.modoEdicao = modoEdicao;
	}
	
    public String getNomeArquivo() {
        return "Status";
    }		
}
