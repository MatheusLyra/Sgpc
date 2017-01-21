package br.sgpc.mbeans.converter;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.sgpc.dlo.MantemFornecedorDLO;
import br.sgpc.dominio.Fornecedor;

@FacesConverter(value = "fornecedorConverter", forClass = Fornecedor.class)
public class FornecedorConverter implements Converter{
	
	@EJB
	private MantemFornecedorDLO mantemFornecedorDLO;
	
	@Override
	public Object getAsObject(FacesContext contexto, UIComponent componente, String valor) {
		if (valor == null || valor.isEmpty()) {
			return null;
		} else {
			Fornecedor fornecedor = null;
			try {
				int id = Integer.parseInt(valor);
				fornecedor = mantemFornecedorDLO.obterDados(id);
			} catch (NumberFormatException e) {
			}
			return fornecedor;
		}
	}

	@Override
	public String getAsString(FacesContext contexto, UIComponent componente, Object valor) {
		if (valor == null) {
			return null;
		}
		
		if (valor instanceof Fornecedor) {
			if (((Fornecedor) valor).getIdFornecedor() != 0) {
				return String.valueOf(((Fornecedor) valor).getIdFornecedor());
			} else {
				return "";
			}
		} else if (valor instanceof Integer && valor != null) {
			return ((Integer) valor).toString();
		} else {
			return valor.toString();
		}
	}	

}
