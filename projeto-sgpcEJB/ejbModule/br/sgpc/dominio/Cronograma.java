package br.sgpc.dominio;
// Generated 23/11/2016 03:44:08 by Hibernate Tools 4.3.1.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@SuppressWarnings("serial")
@Entity
@Table(name = "cronograma")
public class Cronograma implements java.io.Serializable {
	
	@Id
	@Column(name = "idCronograma", unique = true, nullable = false)
	private int idCronograma;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "numProcesso", nullable = false)
	private Dadosconsolidados dadosconsolidados;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idEtapa", nullable = false)
	private Etapa etapa;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idStatus", nullable = false)
	private Status status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idTMP", nullable = false)
	private Tmp tmp;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DtIni", length = 10)	
	private Date dtIni;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DtFim", length = 10)
	private Date dtFim;
	
	@Column(name = "QtdDiasFim")
	private Integer qtdDiasFim;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DtFinalizado", length = 10)
	private Date dtFinalizado;


	public int getIdCronograma() {
		return this.idCronograma;
	}

	public void setIdCronograma(int idCronograma) {
		this.idCronograma = idCronograma;
	}

	public Dadosconsolidados getDadosconsolidados() {
		return this.dadosconsolidados;
	}

	public void setDadosconsolidados(Dadosconsolidados dadosconsolidados) {
		this.dadosconsolidados = dadosconsolidados;
	}

	public Etapa getEtapa() {
		return this.etapa;
	}

	public void setEtapa(Etapa etapa) {
		this.etapa = etapa;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Tmp getTmp() {
		return this.tmp;
	}

	public void setTmp(Tmp tmp) {
		this.tmp = tmp;
	}

	public Date getDtIni() {
		return this.dtIni;
	}

	public void setDtIni(Date dtIni) {
		this.dtIni = dtIni;
	}

	public Date getDtFim() {
		return this.dtFim;
	}

	public void setDtFim(Date dtFim) {
		this.dtFim = dtFim;
	}

	public Integer getQtdDiasFim() {
		return this.qtdDiasFim;
	}

	public void setQtdDiasFim(Integer qtdDiasFim) {
		this.qtdDiasFim = qtdDiasFim;
	}

	public Date getDtFinalizado() {
		return this.dtFinalizado;
	}

	public void setDtFinalizado(Date dtFinalizado) {
		this.dtFinalizado = dtFinalizado;
	}

}
