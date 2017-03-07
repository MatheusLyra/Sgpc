package br.sgpc.mbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.sgpc.dlo.MantemCronogramaDLO;
import br.sgpc.dlo.MantemEtapaDLO;
import br.sgpc.dlo.MantemTmpDLO;
import br.sgpc.dlo.funcoesUteis.Funcoes;
import br.sgpc.dominio.Cronograma;
import br.sgpc.dominio.Dadosconsolidados;
import br.sgpc.dominio.Etapa;
import br.sgpc.dominio.Tmp;

/**
 * Bean responsável por cadastrar cronogramas
 */
@ManagedBean(name = "mbMantemCronograma")
@ViewScoped
public class MbMantemCronograma  extends Funcoes implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EJB
	private MantemCronogramaDLO mantemCronogramaDLO;

	@EJB
	private MantemEtapaDLO mantemEtapaDLO;

	@EJB
	private MantemTmpDLO mantemTmpDLO;

	private Cronograma cronograma;

	private Dadosconsolidados dadosConsolidados;

	private Etapa etapa;

	private Tmp tmp;

	private List<Cronograma> listaCronograma;

	private List<Etapa> listaEtapa;

	private List<Tmp> listaTmp;

	private Integer numProcesso;

	private boolean habilita;

	private long totalDias;

	private boolean editar;

	@PostConstruct
	public void init() {
		limpar();
	}

	private void limpar() {
		cronograma = new Cronograma();
		dadosConsolidados = new Dadosconsolidados();
		etapa = new Etapa();
		listaEtapa = new ArrayList<Etapa>();
		listaTmp = new ArrayList<Tmp>();
		habilita = true;
		totalDias = 0;
	}

	public void pesquisar() {
		carregarCronogramas();
		carregarEtapa();
		carregarTmp();
	}

	public void salvar() {
		if (!dataFinalMenorQueIncial()){
			if (editar) {
				try {
					mantemCronogramaDLO.alterar(cronograma);
	
				} catch (Exception e) {
					msgErro("Erro ao alterar dados com a seguinte mensagem: " + e.getMessage());
				}
				editar = false;
				
			} else {
				try {
					totalDias = cronograma.getDtFim().getTime() - cronograma.getDtIni().getTime();
					cronograma.setQtdDiasFim((int) TimeUnit.DAYS.convert(totalDias, TimeUnit.MILLISECONDS));
	
					mantemCronogramaDLO.cadastrar(cronograma);
					msgInfo("Registro cadastrado com sucesso!");
				} catch (Exception e) {
					msgErro("Erro ao cadastrar dados com a seguinte mensagem: " + e.getMessage());
				}
			}
			limpar();
			pesquisar();
		}else{
			msgErro("Data final menor que data inicial.");
		}
	}
	
	public boolean dataFinalMenorQueIncial(){
		return cronograma.getDtFim().compareTo(cronograma.getDtIni()) == 1 ? false : true;
	}

	public void excluir(Cronograma cronograma) {
		try {
			mantemCronogramaDLO.excluir(cronograma);
			limpar();
			pesquisar();
		} catch (Exception e) {
			msgErro("Erro ao deletar registro de cronograma com a seguinte mensagem: " + e.getMessage());
		}
	}

	public void editar(Cronograma cronograma) {
		this.cronograma = cronograma;
		habilita = false;
		editar = true;
		listaEtapa.add(0, cronograma.getEtapa());
	}

	public void finalizarEtapa(Cronograma cronograma) {
		try {
			cronograma.setStatus(true);
			cronograma.setDtFinalizado(new Date());
			mantemCronogramaDLO.alterar(cronograma);
			msgInfo("Cronograma finalizado com sucesso.");

		} catch (Exception e) {
			msgErro("Erro ao finalizar cronograma com a seguinte mensagem: " + e.getMessage());
		}

		pesquisar();
	}

	private void carregarCronogramas() {
		dadosConsolidados.setNumProcesso(numProcesso);
		cronograma.setDadosconsolidados(dadosConsolidados);

		listaCronograma = mantemCronogramaDLO.carregarDados(cronograma);

		for (int i = 0; i < listaCronograma.size(); i++) {
			if (listaCronograma.get(i).getStatus())
				listaCronograma.get(i).setQtdDiasFinalizados(
						(int) TimeUnit.DAYS.convert(listaCronograma.get(i).getDtFinalizado().getTime()
								- listaCronograma.get(i).getDtIni().getTime(), TimeUnit.MILLISECONDS));
		}
	}

	private void carregarEtapa() {
		listaEtapa = mantemEtapaDLO.carregarDados();
		for (Cronograma cronograma : listaCronograma) {
			listaEtapa.remove(cronograma.getEtapa());
		}
	}

	private void carregarTmp() {
		listaTmp = mantemTmpDLO.carregarDados();
	}

	public void habilitaCampos() {
		habilita = false;
	}

	public Date hoje(){
		return new Date();
	}
	public Cronograma getCronograma() {
		return cronograma;
	}

	public void setCronograma(Cronograma cronograma) {
		this.cronograma = cronograma;
	}

	public Dadosconsolidados getDadosConsolidados() {
		return dadosConsolidados;
	}

	public void setDadosConsolidados(Dadosconsolidados dadosConsolidados) {
		this.dadosConsolidados = dadosConsolidados;
	}

	public Etapa getEtapa() {
		return etapa;
	}

	public void setEtapa(Etapa etapa) {
		this.etapa = etapa;
	}

	public Tmp getTmp() {
		return tmp;
	}

	public void setTmp(Tmp tmp) {
		this.tmp = tmp;
	}

	public List<Cronograma> getListaCronograma() {
		return listaCronograma;
	}

	public void setListaCronograma(List<Cronograma> listaCronograma) {
		this.listaCronograma = listaCronograma;
	}

	public List<Etapa> getListaEtapa() {
		return listaEtapa;
	}

	public void setListaEtapa(List<Etapa> listaEtapa) {
		this.listaEtapa = listaEtapa;
	}

	public List<Tmp> getListaTmp() {
		return listaTmp;
	}

	public void setListaTmp(List<Tmp> listaTmp) {
		this.listaTmp = listaTmp;
	}

	public Integer getNumProcesso() {
		return numProcesso;
	}

	public void setNumProcesso(Integer numProcesso) {
		this.numProcesso = numProcesso;
	}

	public boolean isHabilita() {
		return habilita;
	}

	public void setHabilita(boolean habilita) {
		this.habilita = habilita;
	}

	public boolean isEditar() {
		return editar;
	}

	public void setEditar(boolean editar) {
		this.editar = editar;
	}
}
