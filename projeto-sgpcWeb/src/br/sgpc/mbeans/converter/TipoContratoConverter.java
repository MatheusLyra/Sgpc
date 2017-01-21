package br.sgpc.mbeans.converter;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.sgpc.dlo.MantemTipoContratoDLO;
import br.sgpc.dominio.Tipocontrato;

@FacesConverter(value = "tipoContratoConverter", forClass = Tipocontrato.class)
public class TipoContratoConverter implements Converter {

	@EJB
	private MantemTipoContratoDLO mantemTipoContratoDLO;
	
	@Override
	public Object getAsObject(FacesContext contexto, UIComponent componente, String valor) {
		if (valor == null || valor.isEmpty()) {
			return null;
		} else {
			Tipocontrato tipoContrato = null;
			try {
				int id = Integer.parseInt(valor);
				tipoContrato = mantemTipoContratoDLO.obterDados(id);
			} catch (NumberFormatException e) {
			}
			return tipoContrato;
		}
	}

	@Override
	public String getAsString(FacesContext contexto, UIComponent componente, Object valor) {
		if (valor == null) {
			return null;
		}
		
		if (valor instanceof Tipocontrato) {
			if (((Tipocontrato) valor).getIdTipoContrato() != 0) {
				return String.valueOf(((Tipocontrato) valor).getIdTipoContrato());
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
