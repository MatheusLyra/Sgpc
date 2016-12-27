package br.sgpc.mbeans.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("converter.CnpjConverter")
public class CnpjConverter implements Converter{
	
	 @Override
	 public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
         /*
          * Ir� converter CNPJ formatado para um sem pontos, tra�o e barra.
          * Ex.: 07.374.998/0001-33 torna-se 07374998000133.
          */
          String cnpj = value;
          if (value!= null && !value.equals(""))
               cnpj = value.replaceAll("\\.", "").replaceAll("\\-", "").replaceAll("/", "");
 
          return cnpj;
     }
	 
	 @Override
     public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
         /*
          * Ir� converter CNPJ n�o formatado para um com pontos, tra�o e barra.
          * Ex.: 07374998000133 torna-se 07.374.998/0001-33.
          */
          String cnpj = (String) value;
          if (cnpj != null && cnpj.length() == 14)
              cnpj = cnpj.substring(0, 2) + "." + cnpj.substring(2, 5) + "." + cnpj.substring(5, 8 ) + "/" + cnpj.substring(8, 12) + "-" + cnpj.substring(12, 14);
 
          return cnpj;
     }
}
