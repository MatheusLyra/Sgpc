package br.sgpc.mbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import br.sgpc.dlo.MantemCronogramaDLO;
import br.sgpc.dlo.MantemDadosConsolidadosDLO;
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
public class MbMantemCronograma extends Funcoes implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private MantemCronogramaDLO mantemCronogramaDLO;

	@EJB
	private MantemDadosConsolidadosDLO mantemDadosConsolidadosDLO;

	@EJB
	private MantemEtapaDLO mantemEtapaDLO;

	@EJB
	private MantemTmpDLO mantemTmpDLO;

	private Cronograma cronograma;

	private Cronograma cronogramaFimEtapa;

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

	private String mensagem;

	@PostConstruct
	public void init() {
		limpar();
	}

	public void limpar() {
		cronograma = new Cronograma();
		cronogramaFimEtapa = new Cronograma();
		dadosConsolidados = new Dadosconsolidados();
		etapa = new Etapa();
		listaEtapa = new ArrayList<Etapa>();
		listaTmp = new ArrayList<Tmp>();
		habilita = true;
		totalDias = 0;
		listaCronograma = new ArrayList<Cronograma>();
	}

	public void pesquisar() {
		dadosConsolidados = mantemDadosConsolidadosDLO.obter(numProcesso);
		if (dadosConsolidados == null) {
			limpar();
			msgErro("Número de processo inexistente");
		} else {
			carregarCronogramas();
			carregarEtapa();
			carregarTmp();
		}
	}

	public void salvar() {
		mensagem = "Selecione uma etapa";
		if (validaDatas() && cronograma.getEtapa() != null) {
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
					cronograma.setQtdDiasFim((int) TimeUnit.DAYS.convert(totalDias, TimeUnit.MILLISECONDS) );//Retirado +1 que estava acrescentando a data

					mantemCronogramaDLO.cadastrar(cronograma);
					msgInfo("Registro cadastrado com sucesso!");
				} catch (Exception e) {
					msgErro("Erro ao cadastrar dados com a seguinte mensagem: " + e.getMessage());
				}
			}
			limpar();
			pesquisar();
		} else {
			msgErro(mensagem);
		}
	}

	public boolean validaDatas() {
		boolean valida = true;
		Calendar c1 = Calendar.getInstance();

		valida = !cronograma.getDtFim().before(cronograma.getDtIni());
		mensagem = "Data final menor que data inicial.";

		if (cronograma.getDtIni().before(hoje()) || cronograma.getDtFim().before(hoje())) {
			valida = false;
			mensagem = "Data anterior ao dia atual";
		}

		c1.setTime(cronograma.getDtIni());
		if ((c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			valida = false;
			mensagem = "Não é permitido período inicial no fim de semana";
		}

		c1.setTime(cronograma.getDtFim());

		if ((c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			valida = false;
			mensagem = "Não é permitido período final no fim de semana";
		}

		return valida;
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
		if (cronograma.getDtFim().compareTo(hoje()) < 0) {
			cronogramaFimEtapa = cronograma;
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dlgObservacoes').show();");

		} else {
			gravarFinalizarEtapa(cronograma);
		}
	}

	public void finalizarObsEtapa(Cronograma cronograma) {
		if (cronograma.getObservacoes().isEmpty()) {
			msgErro("Campo observação é obrigatório para finalizar etapa após data de fim.");
		} else {
			gravarFinalizarEtapa(cronograma);
		}
	}

	private void gravarFinalizarEtapa(Cronograma cronograma) {
		try {
			cronograma.setStatus(true);
			cronograma.setDtFinalizado(hoje());
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
		try {
			listaCronograma = mantemCronogramaDLO.carregarDados(cronograma);

			for (int i = 0; i < listaCronograma.size(); i++) {
				if (listaCronograma.get(i).getStatus())
					listaCronograma.get(i)
							.setQtdDiasFinalizados(
									(int) TimeUnit.DAYS.convert(
											listaCronograma.get(i).getDtFinalizado().getTime()
													- listaCronograma.get(i).getDtIni().getTime(),
											TimeUnit.MILLISECONDS));
			}
		} catch (Exception e) {
			msgErro("Erro ao buscar Cronograma: " + e.getMessage());
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

	public Date hoje() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public Cronograma getCronograma() {
		return cronograma;
	}

	public void setCronograma(Cronograma cronograma) {
		this.cronograma = cronograma;
	}

	public Cronograma getCronogramaFimEtapa() {
		return cronogramaFimEtapa;
	}

	public void setCronogramaFimEtapa(Cronograma cronogramaFimEtapa) {
		this.cronogramaFimEtapa = cronogramaFimEtapa;
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
