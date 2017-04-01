package br.sgpc.dlo.funcoesUteis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.sgpc.dominio.Usuario;

/**
Classe que � utilizada para inserir qualquer rotina util, 
que ser� ou poder� ser utilizada em v�rias partes do sistema.
*/ 
@Stateless
@Remote
public class Funcoes {

/*	
 *  Rotina: campoVazio(String campo). 
 *  Considera��es:
 *  Tentei fazer esta valida��o com validator porem 
	houveram problemas na edi��o e exclus�o pois tive que utilizar o immediate="True".
	Tive problemas tbm para validar com o required.
*/	
	public boolean campoVazio(String campo){
		if (campo.length() == 0) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Utilizado para verificar se o email est� de acordo com o padr�o.
	 * 
	 * @return <code>true</code> se o email cadastrado est� conforme padr�o.
	 *         <code>false</code> caso contr�rio.
	 */
	public static boolean validarEmail(String email) {
		boolean isEmailIdValid = false;
		if (email != null && email.length() > 0) {
			String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
			Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(email);
			if (matcher.matches()) {
				isEmailIdValid = true;
			}
		}
		return isEmailIdValid;
	}
	
	public Integer getIdUsuario(){
		HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuarioSessao = (Usuario) sessao.getAttribute("usuario");
	    
	    return usuarioSessao.getIdUsuario();
	}
	
	/////////////////MENSAGENS///////////////
	/**
	 * Utilizada para mostrar mensagem de aviso ou informa��o para usu�rio.
	 * @param parametro de entrada com conte�do da mensagem.
	*/	
	public void msgInfo(String mensagem){
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", mensagem));
	}
	
	/**
	 * Utilizada para mostrar mensagem de erro para usu�rio.
	 * @param parametro de entrada com conte�do da mensagem.
	*/	
	public void msgErro(String mensagem){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
				mensagem));
	}
	
	
}
