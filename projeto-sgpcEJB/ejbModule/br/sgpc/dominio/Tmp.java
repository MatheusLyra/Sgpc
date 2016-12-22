package br.sgpc.dominio;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "tmp")
public class Tmp implements java.io.Serializable {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idTMP", unique = true, nullable = false)
	private Integer idTmp;
	
	@Column(name = "Descricao", length = 60)
	private String descricao;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tmp")
	private List<Cronograma> cronogramas;


	public Integer getIdTmp() {
		return this.idTmp;
	}

	public void setIdTmp(Integer idTmp) {
		this.idTmp = idTmp;
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

}
