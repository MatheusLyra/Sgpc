package br.sgpc.dominio;
// Generated 23/11/2016 03:44:08 by Hibernate Tools 4.3.1.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "cronograma_audit")
public class CronogramaAudit implements java.io.Serializable {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idVersao", unique = true, nullable = false)
	private int idVersao;
	
	@Column(name = "idCronograma")
	private int idCronograma;

	@Column(name = "numProcesso")
	private Integer numProcesso;
		
	@Column(name = "idEtapa")
	private Integer idEtapa;
	
	@Column(name="status", nullable = false, columnDefinition = "TINYINT(1)")
	private boolean status; 	
	
	@Column(name = "idTMP", nullable = false)
	private Integer idTMP;
	
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
		
	@Lob 
	@Column(name="observacoes", length=512)
	private String observacoes;
	
	@Column(name = "tipoOperacao")
	private String tipoOperacao;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dtOperacao", length = 10)
	private Date dtOperacao;
	
	@Column(name = "idUsuario")
	private Integer idUsuario;

	public int getIdVersao() {
		return idVersao;
	}

	public void setIdVersao(int idVersao) {
		this.idVersao = idVersao;
	}

	public int getIdCronograma() {
		return idCronograma;
	}

	public void setIdCronograma(int idCronograma) {
		this.idCronograma = idCronograma;
	}

	public Integer getNumProcesso() {
		return numProcesso;
	}

	public void setNumProcesso(Integer numProcesso) {
		this.numProcesso = numProcesso;
	}

	public Integer getIdEtapa() {
		return idEtapa;
	}

	public void setIdEtapa(Integer idEtapa) {
		this.idEtapa = idEtapa;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Integer getIdTMP() {
		return idTMP;
	}

	public void setIdTMP(Integer idTMP) {
		this.idTMP = idTMP;
	}

	public Date getDtIni() {
		return dtIni;
	}

	public void setDtIni(Date dtIni) {
		this.dtIni = dtIni;
	}

	public Date getDtFim() {
		return dtFim;
	}

	public void setDtFim(Date dtFim) {
		this.dtFim = dtFim;
	}

	public Integer getQtdDiasFim() {
		return qtdDiasFim;
	}

	public void setQtdDiasFim(Integer qtdDiasFim) {
		this.qtdDiasFim = qtdDiasFim;
	}

	public Date getDtFinalizado() {
		return dtFinalizado;
	}

	public void setDtFinalizado(Date dtFinalizado) {
		this.dtFinalizado = dtFinalizado;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public String getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(String tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	public Date getDtOperacao() {
		return dtOperacao;
	}

	public void setDtOperacao(Date dtOperacao) {
		this.dtOperacao = dtOperacao;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
}
