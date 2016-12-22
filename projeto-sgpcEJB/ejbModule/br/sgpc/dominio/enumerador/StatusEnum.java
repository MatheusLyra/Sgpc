package br.sgpc.dominio.enumerador;

public enum StatusEnum {
	
	INATIVO(0),
	ATIVO(1);
	
    private int t;
    
    private StatusEnum(int t){
        this.t = t;
    }

    public int getValue(){
        return this.t;
    }
}
