package br.sgpc.mbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.event.DragDropEvent;

import br.sgpc.dlo.MantemNotificacaoDLO;
import br.sgpc.dominio.Notificacao;
import br.sgpc.dominio.Usuario;

@ManagedBean(name = "mbNotificacaoHome")
@ViewScoped
public class MbNotificacaoHome implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private MantemNotificacaoDLO mantemNotificacaoDLO;

	private List<Notificacao> notificacoes;

	private List<Notificacao> droppedNotificacoes;

	private Notificacao selectedNotificacao;

	@PostConstruct
	public void init() {
		notificacoes = mantemNotificacaoDLO.carregarDadoNotUsu(getIdUsuario());
		droppedNotificacoes = new ArrayList<Notificacao>();
	}
	
	private Integer getIdUsuario(){
	    HttpSession sessao = (HttpSession) 
	            FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	    Usuario usuarioSessao = (Usuario) sessao.getAttribute(MbLogin.USUARIO_SESSAO);
	    
	    return usuarioSessao.getIdUsuario();
	}

	public void onNotificacaoDrop(DragDropEvent ddEvent) {
		Notificacao notificacao = ((Notificacao) ddEvent.getData());

		droppedNotificacoes.add(notificacao);
		notificacoes.remove(notificacao);
	}

	public List<Notificacao> getNotificacoes() {
		return notificacoes;
	}

	public List<Notificacao> getDroppedNotificacoes() {
		return droppedNotificacoes;	
	}

	public Notificacao getSelectedNotificacao() {
		return selectedNotificacao;
	}

	public void setSelectedNotificacao(Notificacao selectedNotificacao) {
		this.selectedNotificacao = selectedNotificacao;
	}

}
