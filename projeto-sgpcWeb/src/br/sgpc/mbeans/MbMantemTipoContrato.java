package br.sgpc.mbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.sgpc.dlo.MantemTipoContratoDLO;
import br.sgpc.dlo.funcoesUteis.Funcoes;
import br.sgpc.dominio.Tipocontrato;

/**
 * Bean responsável por cadastrar um novo tipo de contrato, alterar, excluir e visualizar
 * os tipos de contratos cadastrados.
 */
@ManagedBean(name = "mbMantemTipoContrato")
@SessionScoped
public class MbMantemTipoContrato extends Funcoes implements Serializable{

		private static final long serialVersionUID = 1L;
		
		@EJB
	    private MantemTipoContratoDLO mantemTipoContratoDLO;
		
		private Tipocontrato tipoContrato;
		
		private List<Tipocontrato> listaTipoContrato;
		
		private Boolean modoEdicao;
	    
		@PostConstruct
		public void inicializar(){
			tipoContrato = new Tipocontrato();
			listaTipoContrato = new ArrayList<Tipocontrato>();
			carregarTipoContrato();
			
			modoEdicao = false;
		}
		
		private void carregarTipoContrato(){
			listaTipoContrato = mantemTipoContratoDLO.carregarDados();
		}
		
		public void cadastrar() {
			if (modoEdicao) {
				try {
					mantemTipoContratoDLO.alterar(tipoContrato);
					carregarTipoContrato();

					msgInfo("Registro alterado com sucesso.");
					
				} catch (Exception e) {
					msgErro("Erro ao alterar dados com a seguinte mensagem: " + e.getMessage());
				}
				modoEdicao = false;
				limpar();
			} else {
				try {
					mantemTipoContratoDLO.cadastrar(tipoContrato);
					carregarTipoContrato();
					msgInfo("Registro cadastrado com sucesso!");
				} catch (Exception e) {
					msgErro("Erro ao cadastrar dados com a seguinte mensagem: " + e.getMessage());
				}
				limpar();
			}
		}
		
		public void limpar(){
			tipoContrato = new Tipocontrato();
			
			modoEdicao = false;
		}
		
		public void editar(){
			modoEdicao = true;
		}
		
		public void excluir(Tipocontrato tipoContrato){
			try {
				mantemTipoContratoDLO.excluir(tipoContrato);
				carregarTipoContrato();
			} catch (Exception e) {
				msgErro("Erro ao deletar registro com a seguinte mensagem: "+e.getMessage());
			}		
		}

		public Tipocontrato getTipoContrato() {
			return tipoContrato;
		}

		public void setTipoContrato(Tipocontrato tipoContrato) {
			this.tipoContrato = tipoContrato;
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
		
	    public String getNomeArquivo() {
	        return "TipoContrato";
	    }				
}
