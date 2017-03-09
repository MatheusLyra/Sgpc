package br.sgpc.dominio;
//Generated 23/11/2016 03:44:08 by Hibernate Tools 4.3.1.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "dadosconsolidados_audit")
public class DadosConsolidadosAudit {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idVersao", unique = true, nullable = false)
	private int idVersao;
	
	@Column(name = "numProcesso")
	private Integer numProcesso;
	
	@Column(name = "idArea")
	private Integer idArea;
	
	@Column(name = "idUsuario")
	private Integer Usuario;	
	
	@Column(name = "idFornecedor")
	private Integer Fornecedor;
	
	@Column(name = "idStatus")
	private Integer status;
	
	@Column(name = "idTipoContrato")
	private Integer tipocontrato;
	
	@Column(name = "TipoDado", length = 2)
	private String tipoDado;
	
	@Column(name = "NumContrato", length = 45)
	private String numContrato;
	
	@Column(name = "Tac", length = 10)
	private String tac;
	
	@Column(name = "DescServico", length = 100)
	private String descServico;
	
	@Column(name = "VlPropostaIni", precision = 22, scale = 0)
	private Double vlPropostaIni;
	
	@Column(name = "VlPropostaFim", precision = 22, scale = 0)
	private Double vlPropostaFim;

	@Column(name = "flgUrgente")
    private Integer flgUrgente;
	
	@Column(name = "tipoOperacao")
	private String tipoOperacao;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dtOperacao", length = 10)
	private Date dtOperacao;

	public int getIdVersao() {
		return idVersao;
	}

	public void setIdVersao(int idVersao) {
		this.idVersao = idVersao;
	}

	public Integer getNumProcesso() {
		return numProcesso;
	}

	public void setNumProcesso(Integer numProcesso) {
		this.numProcesso = numProcesso;
	}

	public Integer getIdArea() {
		return idArea;
	}

	public void setIdArea(Integer idArea) {
		this.idArea = idArea;
	}

	public Integer getUsuario() {
		return Usuario;
	}

	public void setUsuario(Integer usuario) {
		Usuario = usuario;
	}

	public Integer getFornecedor() {
		return Fornecedor;
	}

	public void setFornecedor(Integer fornecedor) {
		Fornecedor = fornecedor;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTipocontrato() {
		return tipocontrato;
	}

	public void setTipocontrato(Integer tipocontrato) {
		this.tipocontrato = tipocontrato;
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

	public String getTac() {
		return tac;
	}

	public void setTac(String tac) {
		this.tac = tac;
	}

	public String getDescServico() {
		return descServico;
	}

	public void setDescServico(String descServico) {
		this.descServico = descServico;
	}

	public Double getVlPropostaIni() {
		return vlPropostaIni;
	}

	public void setVlPropostaIni(Double vlPropostaIni) {
		this.vlPropostaIni = vlPropostaIni;
	}

	public Double getVlPropostaFim() {
		return vlPropostaFim;
	}

	public void setVlPropostaFim(Double vlPropostaFim) {
		this.vlPropostaFim = vlPropostaFim;
	}

	public Integer getFlgUrgente() {
		return flgUrgente;
	}

	public void setFlgUrgente(Integer flgUrgente) {
		this.flgUrgente = flgUrgente;
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

}
