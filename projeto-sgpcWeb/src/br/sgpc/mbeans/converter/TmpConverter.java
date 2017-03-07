package br.sgpc.mbeans.converter;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.sgpc.dlo.MantemTmpDLO;
import br.sgpc.dominio.Tmp;

@FacesConverter(value = "tmpConverter", forClass = Tmp.class)
public class TmpConverter  implements Converter{
	@EJB
	private MantemTmpDLO mantemTMPDLO;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && !value.isEmpty()) {
            return (Tmp) component.getAttributes().get(value);
        }
        return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		 if (value instanceof Tmp) {
			 Tmp entity= (Tmp) value;
	            if (entity != null && entity instanceof Tmp && entity.getIdTmp() != null) {
	                component.getAttributes().put( entity.getIdTmp().toString(), entity);
	                return entity.getIdTmp().toString();
	            }
	        }
	        return "";
	}
	
	
//	@Override
//	public Object getAsObject(FacesContext contexto, UIComponent componente, String valor) {
//		if (valor == null || valor.isEmpty()) {
//			return null;
//		} else {
//			Tmp tmp = null;
//			try {
//				int id = Integer.parseInt(valor);
//				tmp   = mantemTMPDLO.obterDados(id);
//			} catch (NumberFormatException e) {
//				Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, e);
//			}
//			return tmp;
//		}
//	}
//
//	@Override
//	public String getAsString(FacesContext contexto, UIComponent componente, Object valor) {
//		if (valor == null) {
//			return null;
//		}
//		
//		if (valor instanceof Tmp) {
//			if (((Tmp) valor).getIdTmp() != 0) {
//				return String.valueOf(((Tmp) valor).getIdTmp());
//			} else {
//				return "";
//			}
//		} else if (valor instanceof Integer && valor != null) {
//			return ((Integer) valor).toString();
//		} else {
//			return valor.toString();
//		}
//	}	


}
