package br.sgpc.controller;

import br.sgpc.mbeans.MbLogin;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

/**
 * Responsável por manipular requisições de usuário, permitindo acesso ao
 * conteúdo da aplicação somente no caso do usuário já ter se autenticado.
 *
 */
@Stateless
@Remote
public class ListenerAutenticacao implements PhaseListener {

	private static final long serialVersionUID = 1L;
	
	private static final String PAGINA_LOGIN = "index.xhtml";

	public void afterPhase(PhaseEvent event) {
		
		FacesContext contexto = event.getFacesContext();
		String pagina = contexto.getViewRoot().getViewId();

		System.out.println(">>>>>>>>>>>>>>>>> ListenerAutenticacao.afterPhase() " + "para página de ID "
				+ event.getFacesContext().getViewRoot().getViewId());

		if (!(pagina.lastIndexOf(PAGINA_LOGIN) > -1)) {
			HttpSession sessao = (HttpSession) contexto.getExternalContext().getSession(false);
			Object usuario = sessao.getAttribute(MbLogin.USUARIO_SESSAO);

			if (usuario == null) {
				if(!MbLogin.isUsuRecuperaSenha()){
					NavigationHandler navHandler = contexto.getApplication().getNavigationHandler();
					navHandler.handleNavigation(contexto, null, MbLogin.SESSAO_INEXISTENTE);
				}
			}
		}
	}

	public void beforePhase(PhaseEvent event) {
		if (event.getFacesContext().getViewRoot() != null) {
			System.out.println(">>>>>>>>>>>>>>>>> ListenerAutenticacao.beforePhase() " + "para página de ID "
					+ event.getFacesContext().getViewRoot().getViewId());
		} else {
			System.out.println(
					">>>>>>>>>>>>>>>>> ListenerAutenticacao.beforePhase() " + "indicando view root ainda nula");
		}
	}

	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}
}
