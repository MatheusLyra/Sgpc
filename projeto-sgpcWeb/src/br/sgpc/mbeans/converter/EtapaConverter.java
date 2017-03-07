package br.sgpc.mbeans.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.sgpc.dominio.Etapa;

@FacesConverter(value = "etapaConverter", forClass = Etapa.class)
public class EtapaConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && !value.isEmpty()) {
            return (Etapa) component.getAttributes().get(value);
        }
        return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		 if (value instanceof Etapa) {
			 Etapa entity= (Etapa) value;
	            if (entity != null && entity instanceof Etapa && entity.getIdEtapa() != null) {
	                component.getAttributes().put( entity.getIdEtapa().toString(), entity);
	                return entity.getIdEtapa().toString();
	            }
	        }
	        return "";
	}

}
