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
@Table(name = "fornecedor", catalog = "sgpc")
public class Fornecedor implements java.io.Serializable {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idFornecedor", unique = true, nullable = false)
	private Integer idFornecedor;
	
	@Column(name = "Descricao", length = 100)
	private String descricao;
	
	@Column(name = "Cnpj")
	private Long cnpj;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fornecedor")
	private List<Dadosconsolidados> dadosconsolidadoses;


	public Integer getIdFornecedor() {
		return this.idFornecedor;
	}

	public void setIdFornecedor(Integer idFornecedor) {
		this.idFornecedor = idFornecedor;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getCnpj() {
		return this.cnpj;
	}

	public void setCnpj(Long cnpj) {
		this.cnpj = cnpj;
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
		result = prime * result + ((idFornecedor == null) ? 0 : idFornecedor.hashCode());
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
		Fornecedor other = (Fornecedor) obj;
		if (idFornecedor == null) {
			if (other.idFornecedor != null)
				return false;
		} else if (!idFornecedor.equals(other.idFornecedor))
			return false;
		return true;
	}
	
	

}
