package br.sgpc.dominio;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "configuracao")
public class Configuracao implements java.io.Serializable{

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idConfiguracao", unique = true, nullable = false)
	private Integer idConfiguracao;
	
	@Column(name="dtFinalizacaoAut_Cronograma", nullable = false, columnDefinition = "TINYINT(1)")
	private boolean dtFinalizacaoAut_Cronograma;

	public Integer getIdConfiguracao() {
		return idConfiguracao;
	}

	public void setIdConfiguracao(Integer idConfiguracao) {
		this.idConfiguracao = idConfiguracao;
	}

	public boolean isDtFinalizacaoAut_Cronograma() {
		return dtFinalizacaoAut_Cronograma;
	}

	public void setDtFinalizacaoAut_Cronograma(boolean dtFinalizacaoAut_Cronograma) {
		this.dtFinalizacaoAut_Cronograma = dtFinalizacaoAut_Cronograma;
	}
	
	
}
