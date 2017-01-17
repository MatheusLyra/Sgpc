package br.sgpc.mbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.sgpc.dlo.MantemTmpDLO;
import br.sgpc.dlo.funcoesUteis.Funcoes;
import br.sgpc.dominio.Tmp;

/**
 * Bean responsável por cadastrar um novo tmp, alterar, excluir e visualizar
 * tmps cadastradas.
 */
@ManagedBean(name = "mbMantemTmp")
@SessionScoped
public class MbMantemTmp extends Funcoes implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EJB
    private MantemTmpDLO mantemTmpDLO;
	
	private Tmp tmp;
	
	private List<Tmp> listaTmp;
	
	private Boolean modoEdicao;
    
	@PostConstruct
	public void inicializar(){
		tmp      = new Tmp();
		listaTmp = new ArrayList<Tmp>();
		carregarTmp();
		
		modoEdicao = false;
	}
	
	private void carregarTmp(){
		listaTmp = mantemTmpDLO.carregarDados();
	}
	
	public void cadastrar() {
		if (modoEdicao) {
			try {
				mantemTmpDLO.alterar(tmp);
				carregarTmp();

				msgInfo("Registro alterado com sucesso.");
			} catch (Exception e) {
				msgErro("Erro ao alterar dados com a seguinte mensagem: " + e.getMessage());
			}
			modoEdicao = false;
			limpar();
		} else {
			try {
				mantemTmpDLO.cadastrar(tmp);
				carregarTmp();	
				msgInfo("Registro cadastrado com sucesso!");
			} catch (Exception e) {
				msgErro("Erro ao cadastrar dados com a seguinte mensagem: " + e.getMessage());
			}
			limpar();
		}
	}
	
	public void limpar(){
		tmp = new Tmp();
		
		modoEdicao = false;
	}
	
	public void editar(){
		modoEdicao = true;
	}
	
	public void excluir(Tmp tmp){
		try {
			mantemTmpDLO.excluir(tmp);
			carregarTmp();
		} catch (Exception e) {
			msgErro("Erro ao deletar registro com a seguinte mensagem: "+e.getMessage());
		}		
	}

	public Tmp getTmp() {
		return tmp;
	}

	public void setTmp(Tmp tmp) {
		this.tmp = tmp;
	}

	public List<Tmp> getListaTmp() {
		return listaTmp;
	}

	public void setListaTmp(List<Tmp> listaTmp) {
		this.listaTmp = listaTmp;
	}

	public Boolean getModoEdicao() {
		return modoEdicao;
	}

	public void setModoEdicao(Boolean modoEdicao) {
		this.modoEdicao = modoEdicao;
	}
	
    public String getNomeArquivo() {
        return "TMP";
    }		
	
}
