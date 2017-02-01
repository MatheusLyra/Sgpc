package br.sgpc.dominio;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "dadosconsolidados")
public class Dadosconsolidados implements Serializable {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "numProcesso", unique = true, nullable = false)
	private Integer numProcesso;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idArea")
	private Area area;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idUsuario")
	private Usuario usuario;	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idFornecedor")
	private Fornecedor fornecedor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idStatus")
	private Status status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idTipoContrato")
	private Tipocontrato tipocontrato;
	
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
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dadosconsolidados")
	private List<Cronograma> cronogramas;

	@Column(name = "flgUrgente")
    private Integer flgUrgente;
	
	public Integer getNumProcesso() {
		return this.numProcesso;
	}

	public void setNumProcesso(Integer numProcesso) {
		this.numProcesso = numProcesso;
	}

	public Area getArea() {
		return this.area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Fornecedor getFornecedor() {
		return this.fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Tipocontrato getTipocontrato() {
		return this.tipocontrato;
	}

	public void setTipocontrato(Tipocontrato tipocontrato) {
		this.tipocontrato = tipocontrato;
	}

	public String getTipoDado() {
		return this.tipoDado;
	}

	public void setTipoDado(String tipoDado) {
		this.tipoDado = tipoDado;
	}

	public String getNumContrato() {
		return this.numContrato;
	}

	public void setNumContrato(String numContrato) {
		this.numContrato = numContrato;
	}

	public String getDescServico() {
		return this.descServico;
	}

	public void setDescServico(String descServico) {
		this.descServico = descServico;
	}

	public Double getVlPropostaIni() {
		return this.vlPropostaIni;
	}

	public void setVlPropostaIni(Double vlPropostaIni) {
		this.vlPropostaIni = vlPropostaIni;
	}

	public Double getVlPropostaFim() {
		return this.vlPropostaFim;
	}

	public void setVlPropostaFim(Double vlPropostaFim) {
		this.vlPropostaFim = vlPropostaFim;
	}

	public List<Cronograma> getCronogramas() {
		return this.cronogramas;
	}

	public void setCronogramas(List<Cronograma> cronogramas) {
		this.cronogramas = cronogramas;
	}

	public String getTac() {
		return tac;
	}

	public void setTac(String tac) {
		this.tac = tac;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Integer getFlgUrgente() {
		return flgUrgente;
	}

	public void setFlgUrgente(Integer flgUrgente) {
		this.flgUrgente = flgUrgente;
	}
		
}
