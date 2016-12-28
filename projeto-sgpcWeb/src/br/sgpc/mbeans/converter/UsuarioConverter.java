package br.sgpc.mbeans.converter;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.sgpc.dlo.MantemUsuarioDLO;
import br.sgpc.dominio.Usuario;

@FacesConverter(value = "usuarioConverter", forClass = Usuario.class)
public class UsuarioConverter implements Converter{
	@EJB
    private MantemUsuarioDLO mantemUsuarioDLO;	
	
	@Override
	public Object getAsObject(FacesContext contexto, UIComponent componente, String valor) {
		if (valor == null || valor.isEmpty()) {
			return null;
		} else {
			Usuario usuario = null;
			try {
				int id = Integer.parseInt(valor);
				usuario = mantemUsuarioDLO.obter(id);
			} catch (NumberFormatException e) {
			}
			return usuario;
		}
	}

	@Override
	public String getAsString(FacesContext contexto, UIComponent componente, Object valor) {
		if (valor == null) {
			return null;
		}
		
		if (valor instanceof Usuario) {
			if (((Usuario) valor).getIdUsuario() != 0) {
				return String.valueOf(((Usuario) valor).getIdUsuario());
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
