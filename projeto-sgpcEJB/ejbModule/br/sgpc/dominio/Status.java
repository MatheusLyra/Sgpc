package br.sgpc.dominio;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "status")
public class Status implements java.io.Serializable {
	
	@Id
	@Column(name = "idStatus", unique = true, nullable = false)
	private int idStatus;
	
	@Column(name = "Descricao", length = 60)
	private String descricao;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "status")
	private List<Cronograma> cronogramas;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "status")
	private List<Dadosconsolidados> dadosconsolidadoses;


	public int getIdStatus() {
		return this.idStatus;
	}

	public void setIdStatus(int idStatus) {
		this.idStatus = idStatus;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Cronograma> getCronogramas() {
		return this.cronogramas;
	}

	public void setCronogramas(List<Cronograma> cronogramas) {
		this.cronogramas = cronogramas;
	}

	public List<Dadosconsolidados> getDadosconsolidadoses() {
		return this.dadosconsolidadoses;
	}

	public void setDadosconsolidadoses(List<Dadosconsolidados> dadosconsolidadoses) {
		this.dadosconsolidadoses = dadosconsolidadoses;
	}

}
