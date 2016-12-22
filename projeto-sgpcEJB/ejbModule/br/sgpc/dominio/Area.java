package br.sgpc.dominio;
// Generated 23/11/2016 03:44:08 by Hibernate Tools 4.3.1.Final

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
@Table(name = "area")
public class Area implements java.io.Serializable {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idArea", unique = true, nullable = false)
	private Integer idArea;
	
	@Column(name = "Descricao", length = 60)
	private String descricao;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "area")
	private List<Dadosconsolidados> dadosconsolidadoses;


	public Integer getIdArea() {
		return this.idArea;
	}

	public void setIdArea(Integer idArea) {
		this.idArea = idArea;
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

}
