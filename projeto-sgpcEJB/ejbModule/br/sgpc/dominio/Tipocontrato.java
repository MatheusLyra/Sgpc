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
@Table(name = "tipocontrato")
public class Tipocontrato implements java.io.Serializable {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idTipoContrato", unique = true, nullable = false)
	private Integer idTipoContrato;
	
	@Column(name = "Descricao", length = 100)
	private String descricao;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipocontrato")
	private List<Dadosconsolidados> dadosconsolidadoses;


	public Integer getIdTipoContrato() {
		return this.idTipoContrato;
	}

	public void setIdTipoContrato(Integer idTipoContrato) {
		this.idTipoContrato = idTipoContrato;
	}
	
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Dadosconsolidados> getDadosconsolidadoses() {
		return this.dadosconsolidadoses;
	}

	public void setDadosconsolidadoses(List<Dadosconsolidados> dadosconsolidadoses) {
		this.dadosconsolidadoses = dadosconsolidadoses;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idTipoContrato == null) ? 0 : idTipoContrato.hashCode());
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
		Tipocontrato other = (Tipocontrato) obj;
		if (idTipoContrato == null) {
			if (other.idTipoContrato != null)
				return false;
		} else if (!idTipoContrato.equals(other.idTipoContrato))
			return false;
		return true;
	}

	
}
