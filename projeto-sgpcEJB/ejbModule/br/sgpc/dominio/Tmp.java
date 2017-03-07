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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idTmp == null) ? 0 : idTmp.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tmp other = (Tmp) obj;
		if (idTmp == null) {
			if (other.idTmp != null)
				return false;
		} else if (!idTmp.equals(other.idTmp))
			return false;
		return true;
	}

}
