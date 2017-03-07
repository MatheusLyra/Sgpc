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
@Table(name = "etapa")
public class Etapa implements java.io.Serializable {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idEtapa", unique = true, nullable = false)
	private Integer idEtapa;
	
	@Column(name = "Descricao", length = 60)
	private String descricao;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "etapa")
	private List<Cronograma> cronogramas;


	public Integer getIdEtapa() {
		return this.idEtapa;
	}

	public void setIdEtapa(Integer idEtapa) {
		this.idEtapa = idEtapa;
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
		result = prime * result + ((idEtapa == null) ? 0 : idEtapa.hashCode());
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
		Etapa other = (Etapa) obj;
		if (idEtapa == null) {
			if (other.idEtapa != null)
				return false;
		} else if (!idEtapa.equals(other.idEtapa))
			return false;
		return true;
	}

}
