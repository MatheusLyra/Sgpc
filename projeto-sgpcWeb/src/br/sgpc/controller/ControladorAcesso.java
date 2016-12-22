package br.sgpc.controller;

import br.sgpc.dominio.Usuario;
import br.sgpc.mbeans.MbLogin;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.sgpc.dominio.enumerador.TipoUsuarioEnum;
/**
 * Controlador utilizado especificamente para verifica��o de permiss�es de 
 * acesso a funcionalidades oferecidas pela aplica��o.
 *
 */
@Stateless
@Remote
public class ControladorAcesso {
  
  private boolean permissaoAdministrador;
  private boolean permissaoFuncionario;
  private boolean permissaoComum;

  public boolean isPermissaoAdministrador() {    
    HttpSession sessao = (HttpSession) 
            FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    Usuario usuarioSessao = (Usuario) sessao.getAttribute(MbLogin.USUARIO_SESSAO);
    
    if (usuarioSessao != null) {
    	permissaoAdministrador  = (usuarioSessao.getTipoUsuario().equals(TipoUsuarioEnum.TIPO_ADMINISTRADOR.getValue()));
    } else {
      permissaoAdministrador = false;
    }
    return permissaoAdministrador;
  }

  public boolean isPermissaoFuncionario() {
    HttpSession sessao = (HttpSession) 
            FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    Usuario usuarioSessao = (Usuario) sessao.getAttribute(MbLogin.USUARIO_SESSAO);
    
    if (usuarioSessao != null) {
    	permissaoAdministrador  = (usuarioSessao.getTipoUsuario().equals(TipoUsuarioEnum.TIPO_ADMINISTRADOR.getValue()));
    	      
      if (permissaoAdministrador) {
        permissaoFuncionario = true;
      } else {
    	  permissaoFuncionario  = (usuarioSessao.getTipoUsuario().equals(TipoUsuarioEnum.TIPO_FUNCIONARIO.getValue()));
      }
    } else {
      permissaoFuncionario = false;
    }
    return permissaoFuncionario;
  }

  public boolean isPermissaoComum() {
    HttpSession sessao = (HttpSession) 
            FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    Usuario usuarioSessao = (Usuario) sessao.getAttribute(MbLogin.USUARIO_SESSAO);
    
    if (usuarioSessao != null) {
        permissaoAdministrador  = (usuarioSessao.getTipoUsuario().equals(TipoUsuarioEnum.TIPO_ADMINISTRADOR.getValue()));
        permissaoFuncionario    = (usuarioSessao.getTipoUsuario().equals(TipoUsuarioEnum.TIPO_FUNCIONARIO.getValue()));
      
      if (permissaoAdministrador || permissaoFuncionario) {
        permissaoComum = true;
      } else {
    	  permissaoComum  = (usuarioSessao.getTipoUsuario().equals(TipoUsuarioEnum.TIPO_CONVIDADO.getValue()));
      }
    } else {
      permissaoComum = false;
    }
    return permissaoComum;
  }
  
  /**
   * M�todo utilizado para configurar o perfil de acesso do usu�rio logado �s
   * funcionalidades da aplica��o.
   */
  public void configurarAcesso() {
    HttpSession sessao = (HttpSession) 
            FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    Usuario usuarioSessao = (Usuario) sessao.getAttribute(MbLogin.USUARIO_SESSAO);
    
    if (usuarioSessao != null) {
    	
      Logger.getLogger("ControladorAcesso").log(Level.INFO, 
                ">>>>>>>>>>>>>> Usu�rio da sess�o tem tipo {0}", usuarioSessao.getTipoUsuario());      
      permissaoAdministrador  = (usuarioSessao.getTipoUsuario().equals(TipoUsuarioEnum.TIPO_ADMINISTRADOR.getValue()));
      
      if (permissaoAdministrador) {
        permissaoFuncionario = true;
      } else {
          permissaoFuncionario    = (usuarioSessao.getTipoUsuario().equals(TipoUsuarioEnum.TIPO_FUNCIONARIO.getValue()));  
          permissaoComum  = (usuarioSessao.getTipoUsuario().equals(TipoUsuarioEnum.TIPO_CONVIDADO.getValue()));
          
      }
    }
  }
}

