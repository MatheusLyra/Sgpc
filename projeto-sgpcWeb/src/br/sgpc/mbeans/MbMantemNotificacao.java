package br.sgpc.mbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.sgpc.dlo.MantemNotificacaoDLO;
import br.sgpc.dlo.MantemUsuarioDLO;
import br.sgpc.dominio.Notificacao;
import br.sgpc.dominio.Usuario;

/**
 * Bean responsável por cadastrar uma nova notificação, alterar, excluir e visualizar
 * as notificações cadastradas.
 */
@ManagedBean(name = "mbMantemNotificacao")
@SessionScoped
public class MbMantemNotificacao implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EJB
    private MantemNotificacaoDLO mantemNotificacaoDLO;
	
	@EJB
    private MantemUsuarioDLO mantemUsuarioDLO;	
	
	private Notificacao notificacao;
	
	private List<Notificacao> listaNotificacao;
	
	private List<Usuario> listaUsuario;
	
	private Boolean modoEdicao;
    
	@PostConstruct
	public void inicializar(){
		notificacao      = new Notificacao();
		listaNotificacao = new ArrayList<Notificacao>();
		listaUsuario     = new ArrayList<Usuario>();
		carregarNotificacao();
		carregarUsuarios();
		modoEdicao = false;
	}
	
	private void carregarNotificacao(){
		listaNotificacao = mantemNotificacaoDLO.carregarDados();
	}
	
	
	private void carregarUsuarios(){
		listaUsuario = mantemUsuarioDLO.carregarDados();
	}
	
	private void preencherDados(){
		Date data = new Date(System.currentTimeMillis());
	    notificacao.setData_aviso(data);
	    
	    HttpSession sessao = (HttpSession) 
	            FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	    Usuario usuarioSessao = (Usuario) sessao.getAttribute(MbLogin.USUARIO_SESSAO);
	    notificacao.setDe_quem(usuarioSessao.getUserName());
	    
	}
	
	public void cadastrar() {
		if (modoEdicao) {
			try {
				mantemNotificacaoDLO.alterar(notificacao);
				carregarNotificacao();

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro alterado com sucesso."));

			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
						"Erro ao alterar dados com a seguinte mensagem: " + e.getMessage()));
				
			}
			modoEdicao = false;
			limpar();
		} else {
			try {
				preencherDados();
				mantemNotificacaoDLO.cadastrar(notificacao);
				carregarNotificacao();
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro cadastrado com sucesso!"));
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
						"Erro ao cadastrar dados com a seguinte mensagem: " + e.getMessage()));
			}
			limpar();
		}
	}
	
	public void limpar(){
		notificacao = new Notificacao();
		
		modoEdicao = false;
	}
	
	public void editar(){
		modoEdicao = true;
	}
	
	public void excluir(Notificacao notificacao){
		try {
			mantemNotificacaoDLO.excluir(notificacao);
			carregarNotificacao();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao deletar registro com a seguinte mensagem: "+e.getMessage()));
		}		
	}

	public Notificacao getNotificacao() {
		return notificacao;
	}

	public void setNotificacao(Notificacao notificacao) {
		this.notificacao = notificacao;
	}

	public List<Notificacao> getListaNotificacao() {
		return listaNotificacao;
	}

	public void setListaNotificacao(List<Notificacao> listaNotificacao) {
		this.listaNotificacao = listaNotificacao;
	}

	public Boolean getModoEdicao() {
		return modoEdicao;
	}

	public void setModoEdicao(Boolean modoEdicao) {
		this.modoEdicao = modoEdicao;
	}

	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}
	
    public String getNomeArquivo() {
        return "Notificação";
    }		
}
