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
Classe que é utilizada para inserir qualquer rotina util, 
que será ou poderá ser utilizada em várias partes do sistema.
*/ 
@Stateless
@Remote
public class Funcoes {

/*	
 *  Rotina: campoVazio(String campo). 
 *  Considerações:
 *  Tentei fazer esta validação com validator porem 
	houveram problemas na edição e exclusão pois tive que utilizar o immediate="True".
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
	 * Utilizado para verificar se o email está de acordo com o padrão.
	 * 
	 * @return <code>true</code> se o email cadastrado está conforme padrão.
	 *         <code>false</code> caso contrário.
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
	 * Utilizada para mostrar mensagem de aviso ou informação para usuário.
	 * @param parametro de entrada com conteúdo da mensagem.
	*/	
	public void msgInfo(String mensagem){
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", mensagem));
	}
	
	/**
	 * Utilizada para mostrar mensagem de erro para usuário.
	 * @param parametro de entrada com conteúdo da mensagem.
	*/	
	public void msgErro(String mensagem){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
				mensagem));
	}
	
	
}
