package br.sgpc.dlo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import br.sgpc.dao.CronogramaDao;
import br.sgpc.dominio.Area;
import br.sgpc.dominio.Cronograma;

/**
 * Classe de objetos responsável por manter do cronograma.
 *
 */
@Stateless
@Remote
public class MantemCronogramaDLO  implements Serializable{
 
	private static final long serialVersionUID = 1L;

	@EJB
	private CronogramaDao dao;

	public void cadastrar(Cronograma cronograma) throws Exception{
	    this.dao.salvarCronograma(cronograma);
	}

	public void alterar(Cronograma cronograma) throws Exception{
		this.dao.atualizarCronograma(cronograma);
	}

	public void excluir(Cronograma cronograma) throws Exception {
		Cronograma a = this.dao.obter(cronograma.getIdCronograma());
		if (a != null) {
			this.dao.excluirCronograma(a);
		}
	}
	
	public List<Cronograma> carregarDados(Cronograma cronograma){
		return this.dao.consultarCronograma(cronograma.getDadosconsolidados().getNumProcesso());
	}
	
	public List<Cronograma> carregarDados(String... criterios) {

		List<Cronograma> cronogramas = new ArrayList<Cronograma>();

		if (criterios != null && criterios.length == 1) {

			//cronogramas = this.dao.consultarCronograma(criterios[0]);
		}
		return cronogramas;
	}
}
