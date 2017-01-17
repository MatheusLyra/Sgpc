package br.sgpc.mbeans.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("validator.CamposValidator")
public class CamposValidator implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object valorTela) throws ValidatorException {
		if (campoVazio(String.valueOf(valorTela))) {
			FacesMessage message = new FacesMessage();
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			message.setSummary("Campo Obrigatório.");
			throw new ValidatorException(message);			
		}
		/*Pode colocar outras validações de tela*/
	}
	
	public boolean campoVazio(String campo){
		if (campo.length() == 0) {
			return true;
		}else {
			return false;
		}
	}
}
