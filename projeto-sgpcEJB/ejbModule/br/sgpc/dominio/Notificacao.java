package br.sgpc.dominio;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "notificacao")
public class Notificacao implements Serializable{
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idNotificacao", unique = true, nullable = false)
	private int idNotificacao;
	
	@Column(name = "de_quem", length = 90)
	private String de_quem;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idUsuario", nullable = false)
	private Usuario usuario;
	
	@Column(name = "aviso", length = 900)
	private String aviso;
	
	@Temporal(value=TemporalType.DATE)
	private Date data_aviso;
	
	@Column(name = "assunto", length = 100)
	private String assunto;

	
	public int getIdNotificacao() {
		return idNotificacao;
	}

	public void setIdNotificacao(int idNotificacao) {
		this.idNotificacao = idNotificacao;
	}

	public String getDe_quem() {
		return de_quem;
	}

	public void setDe_quem(String de_quem) {
		this.de_quem = de_quem;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getAviso() {
		return aviso;
	}

	public void setAviso(String aviso) {
		this.aviso = aviso;
	}

	public Date getData_aviso() {
		return data_aviso;
	}

	public void setData_aviso(Date data_aviso) {
		this.data_aviso = data_aviso;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	
	
}
