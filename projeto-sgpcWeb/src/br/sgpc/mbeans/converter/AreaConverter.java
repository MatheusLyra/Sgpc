package br.sgpc.mbeans.converter;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.sgpc.dlo.MantemAreaDLO;
import br.sgpc.dominio.Area;

@FacesConverter(value = "areaConverter", forClass = Area.class)
public class AreaConverter implements Converter{
	
	@EJB
	private MantemAreaDLO mantemAreaDLO;
	
	@Override
	public Object getAsObject(FacesContext contexto, UIComponent componente, String valor) {
		if (valor == null || valor.isEmpty()) {
			return null;
		} else {
			Area area = null;
			try {
				int id = Integer.parseInt(valor);
				area   = mantemAreaDLO.obterDados(id);
			} catch (NumberFormatException e) {
			}
			return area;
		}
	}

	@Override
	public String getAsString(FacesContext contexto, UIComponent componente, Object valor) {
		if (valor == null) {
			return null;
		}
		
		if (valor instanceof Area) {
			if (((Area) valor).getIdArea() != 0) {
				return String.valueOf(((Area) valor).getIdArea());
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
