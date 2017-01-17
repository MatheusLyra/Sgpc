package br.sgpc.dlo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import br.sgpc.dao.AreaDao;
import br.sgpc.dlo.funcoesUteis.Funcoes;
import br.sgpc.dominio.Area;

/**
 * Classe de objetos responsável por manter os dados cadastrais da Area.
 *
 */
@Stateless
@Remote
public class MantemAreaDLO extends Funcoes implements Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private AreaDao dao;

	public void cadastrar(Area area) throws Exception{
		if (!campoVazio(area.getDescricao())) {
			
		    this.dao.salvarArea(area);
		    
		} else {
			String msg = "Campo Obrigatório.";
			throw new Exception(msg);
		}
	}

	public void alterar(Area area) throws Exception{
		if (!campoVazio(area.getDescricao())) {
			
			this.dao.atualizarArea(area);
			
		} else {
			String msg = "Campo Obrigatório.";
			throw new Exception(msg);
		}
	}

	public void excluir(Area area) throws Exception {
		Area a = this.dao.obter(area.getIdArea());
		if (a != null) {
			this.dao.excluirArea(a);
		}
	}
	
	public List<Area> carregarDados(){
		return this.dao.consultarArea();
	}
	
	public List<Area> carregarDados(String... criterios) {

		List<Area> areas = new ArrayList<Area>();

		if (criterios != null && criterios.length == 1) {

			areas = this.dao.consultarArea(criterios[0]);
		}
		return areas;
	}
	
	public Area obterDados(Integer id){
		return this.dao.obter(id);
	}
}
