package br.sgpc.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.sgpc.dominio.enumerador.TipoUsuarioEnum;

@SuppressWarnings("serial")
@Entity
@Table(name = "usuario")
public class Usuario implements java.io.Serializable {

/*	@Transient
	public static final String TIPO_ADMINISTRADOR = "ADMINISTRADOR";
	@Transient
	public static final String TIPO_FUNCIONARIO = "FUNCIONARIO";
	@Transient
	public static final String TIPO_CONVIDADO = "CONVIDADO";
	@Transient
	public static final byte ADMINISTRADOR = 0;
	@Transient
	public static final byte FUNCIONARIO = 1;
	@Transient
	public static final byte CONVIDADO = 2;
	@Transient
	public static final byte INATIVO = 0;
	@Transient
	public static final byte ATIVO = 1;*/

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idUsuario", unique = true, nullable = false)
	private Integer idUsuario;

	@Column(name = "username", length = 20)
	private String userName;

	@Column(name = "senha", length = 20)
	private String senha;

	@Column(name = "tipo", length = 15)
	private String tipoUsuario;

	@Column(name = "email", length = 30)
	private String email;

	@Column(name = "status", nullable = false)
	private byte status;


	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}
	
	public String getTipo_Adm() {
		return TipoUsuarioEnum.TIPO_ADMINISTRADOR.getValue();
	}
	
	public String getTipo_Func() {
		return TipoUsuarioEnum.TIPO_FUNCIONARIO.getValue();
	}
	
	public String getTipo_Con() {
		return TipoUsuarioEnum.TIPO_CONVIDADO.getValue();
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idUsuario == null) ? 0 : idUsuario.hashCode());
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
		Usuario other = (Usuario) obj;
		if (idUsuario == null) {
			if (other.idUsuario != null)
				return false;
		} else if (!idUsuario.equals(other.idUsuario))
			return false;
		return true;
	}

}
