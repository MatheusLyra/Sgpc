package br.sgpc.mbeans.converter;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.sgpc.dlo.MantemStatusDLO;
import br.sgpc.dominio.Status;

@FacesConverter(value = "statusConverter", forClass = Status.class)
public class StatusConverter implements Converter {
	
	@EJB
	private MantemStatusDLO mantemStatusDLO;
	
	@Override
	public Object getAsObject(FacesContext contexto, UIComponent componente, String valor) {
		if (valor == null || valor.isEmpty()) {
			return null;
		} else {
			Status status = null;
			try {
				int id = Integer.parseInt(valor);
				status = mantemStatusDLO.obterDados(id);
			} catch (NumberFormatException e) {
			}
			return status;
		}
	}

	@Override
	public String getAsString(FacesContext contexto, UIComponent componente, Object valor) {
		if (valor == null) {
			return null;
		}
		
		if (valor instanceof Status) {
			if (((Status) valor).getIdStatus() != 0) {
				return String.valueOf(((Status) valor).getIdStatus());
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
