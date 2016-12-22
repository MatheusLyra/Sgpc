package br.sgpc.dominio.enumerador;


public enum TipoUsuarioEnum {
	
	TIPO_ADMINISTRADOR("ADMINISTRADOR"),
	TIPO_FUNCIONARIO("FUNCIONARIO"),
	TIPO_CONVIDADO("CONVIDADO"),
	
	ADMINISTRADOR(0),
	FUNCIONARIO(1),
	CONVIDADO(2);
	
	
	private String value;
	
	private TipoUsuarioEnum(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return this.value;
	}
	
	public String getValue() {
		return value;
	}
	
	//////////////////////////////////////////////////////////////////////
		
	private Integer valuei;	

	private TipoUsuarioEnum(Integer valuei) {
		this.valuei = valuei;
	}
	
	public Integer toInteger() {
		return this.valuei;
	}
	
	public Integer getValuei() {
		return valuei;
	}

    
}
