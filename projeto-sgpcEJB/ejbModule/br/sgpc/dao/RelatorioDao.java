package br.sgpc.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
@Local
public class RelatorioDao  implements Serializable{
	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "pu")
	private EntityManager entityManager;
	
	public List<Object> consultarRelDadosConsolidados(int numProcesso, String tipoDado, String numContrato, int idArea, int idFornecedor, int idUsuario, int idStatus, int flgUrgente){

			String sql = "select d.numProcesso, d.tipoDado, d.numContrato, d.tac, f.descricao, d.descServico, "
					   + "a.descricao, d.vlpropostaini, d.vlpropostafim, (1-(d.vlpropostafim / d.vlpropostaini))*100 as saving, "
					   + "u.userName, s.descricao, d.flgUrgente, c.QtdediasEstimados, c.QtdediasDecorridos "
				       + "from dadosconsolidados d "  
				       + "INNER JOIN area a on d.idArea = a.idArea " 
				       + "INNER JOIN usuario u on d.idUsuario = u.idUsuario "
				       + "INNER JOIN status s on d.idStatus = s.idStatus "
				       + "LEFT OUTER JOIN fornecedor f on d.idFornecedor = f.idFornecedor "
				       + "LEFT OUTER JOIN (select c.numProcesso, sum(c.QtdDiasFim)QtdediasDecorridos, "
				       + "		sum(DATEDIFF(c.DtFim,c.DtIni))QtdediasEstimados  "
				       + "    from cronograma c "
				       + " where not QtdDiasFim is null"
				       + " group by numProcesso)c on c.numProcesso = d.numProcesso "				       
				       + "where 1=1 ";
			
			if (numProcesso > 0) {
				sql+= " and d.numProcesso = ?1";
			}
			if (tipoDado.length() > 0) {
				sql+= " and d.tipoDado = ?2";
			}
			if (numContrato.length() > 0) {
				sql+= " and d.numContrato like ?3";
			}	
			if (idArea > 0) {
				sql+= " and d.idArea = ?4";
			}
			if (idFornecedor > 0) {
				sql+= " and d.idFornecedor = ?5";
			}	
			if (idUsuario > 0) {
				sql+= " and d.idUsuario = ?6";
			}
			if (idStatus > 0) {
				sql+= " and d.idStatus = ?7";
			}
			if (flgUrgente > -1) {
				sql+= " and d.flgUrgente = ?8";
			}
			
			Query query = this.entityManager.createNativeQuery(sql);
			
			if (numProcesso > 0) {
				query.setParameter(1, numProcesso);
			}
			if (tipoDado.length() > 0) {
				query.setParameter(2, tipoDado);
			}
			if (numContrato.length() > 0) {
				query.setParameter(3, "%"+numContrato+"%");
			}
			if (idArea > 0) {
				query.setParameter(4, idArea);
			}	
			if (idFornecedor > 0) {
				query.setParameter(5, idFornecedor);
			}	
			if (idUsuario > 0) {
				query.setParameter(6, idUsuario);
			}	
			if (idStatus > 0) {
				query.setParameter(7, idStatus);
			}
			if (flgUrgente > -1) {
				query.setParameter(8, flgUrgente);
			}			
			
			return query.getResultList();		
	}
	
}
