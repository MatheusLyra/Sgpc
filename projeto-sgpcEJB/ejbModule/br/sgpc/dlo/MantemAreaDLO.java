package br.sgpc.dlo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import br.sgpc.dao.AreaDao;
import br.sgpc.dominio.Area;

/**
 * Classe de objetos responsável por manter os dados cadastrais da Area.
 *
 */
@Stateless
@Remote
public class MantemAreaDLO implements Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private AreaDao dao;

	public void cadastrar(Area area) throws Exception{
	    this.dao.salvarArea(area);
	}

	public void alterar(Area area) throws Exception{
		this.dao.atualizarArea(area);
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
}
