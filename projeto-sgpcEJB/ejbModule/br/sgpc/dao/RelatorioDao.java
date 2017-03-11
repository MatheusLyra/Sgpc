package br.sgpc.dao;

import java.io.Serializable;
import java.util.Date;
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
				       + "LEFT OUTER JOIN (select c.numProcesso, sum(DATEDIFF(c.DtFinalizado,c.DtIni))QtdediasDecorridos, "
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
	

	public List<Object> consultarRelAuditoriaDadosConsolidados(int numProcesso, String tipoOperacao, Date dtOperacaoIni, Date dtOperacaoFim, int idUsuarioAuditoria ){

		String sql = "Select d.numProcesso, d.tipoDado, d.numContrato, d.tac, f.descricao, d.descServico, " 
				   + "       a.descricao, d.vlpropostaini, d.vlpropostafim, " 
				   + "       u.userName, s.descricao, d.flgUrgente, d.tipoOperacao, d.dtOperacao, u2.userName, d.idVersao "
				   + "  from dadosconsolidados_audit d "
				   + "INNER JOIN usuario u on            d.idUsuario      = u.idUsuario "
				   + "INNER JOIN usuario u2 on           d.idUsuarioAudit = u2.idUsuario "
				   + "LEFT OUTER JOIN fornecedor f on    d.idFornecedor   = f.idFornecedor "
				   + "LEFT OUTER JOIN tipoContrato tc on d.idTipoContrato = tc.idTipoContrato "
				   + "INNER JOIN area a on               d.idArea         = a.idArea "
				   + "INNER JOIN status s on             d.idStatus       = s.idStatus "
                   + "  where 1=1 ";
		
		if (numProcesso > 0) {
			sql+= " and d.numProcesso = ?1";
		}
		if (tipoOperacao.length() > 0) {
			sql+= " and d.tipoOperacao = ?2";
		}
		if (dtOperacaoIni != null) {
			sql+= " and d.dtOperacao >= ?3";
		}
		if (dtOperacaoFim != null) {
			sql+= " and d.dtOperacao <= ?4";
		}	
		if (idUsuarioAuditoria > 0) {
			sql+= " and d.idUsuarioAudit = ?5";
		}
		
		Query query = this.entityManager.createNativeQuery(sql);
		
		if (numProcesso > 0) {
			query.setParameter(1, numProcesso);
		}
		if (tipoOperacao.length() > 0) {
			query.setParameter(2, tipoOperacao);
		}
		if (dtOperacaoIni != null) {
			query.setParameter(3, dtOperacaoIni);
		}
		if (dtOperacaoFim != null) {
			query.setParameter(4, dtOperacaoFim);
		}
		if (idUsuarioAuditoria > 0) {
			query.setParameter(5, idUsuarioAuditoria);
		}
		
		return query.getResultList();		
}
	
	
	public List<Object> consultarRelAuditoriaCronograma(int numProcesso, String tipoOperacao, Date dtOperacaoIni, Date dtOperacaoFim, int idUsuarioAuditoria ){

		String sql = "select c.numProcesso, c.idVersao, c.DtIni, c.DtFim, c.QtdDiasFim, c.DtFinalizado, "
				   + "		 c.status, e.Descricao, t.Descricao, c.observacoes, c.tipoOperacao, "
				   + "       c.dtOperacao, u.username "
				   + "  from cronograma_audit c "
				   + "INNER JOIN usuario u    on c.idUsuario = u.idUsuario "
				   + "LEFT OUTER JOIN etapa e on c.idEtapa   = e.idEtapa "
				   + "LEFT OUTER JOIN tmp t   on c.idTmp     = t.idTmp "
                   + "  where 1=1 ";
		
		if (numProcesso > 0) {
			sql+= " and c.numProcesso = ?1";
		}
		if (tipoOperacao.length() > 0) {
			sql+= " and c.tipoOperacao = ?2";
		}
		if (dtOperacaoIni != null) {
			sql+= " and c.dtOperacao >= ?3";
		}
		if (dtOperacaoFim != null) {
			sql+= " and c.dtOperacao <= ?4";
		}	
		if (idUsuarioAuditoria > 0) {
			sql+= " and c.idUsuario = ?5";
		}
		
		Query query = this.entityManager.createNativeQuery(sql);
		
		if (numProcesso > 0) {
			query.setParameter(1, numProcesso);
		}
		if (tipoOperacao.length() > 0) {
			query.setParameter(2, tipoOperacao);
		}
		if (dtOperacaoIni != null) {
			query.setParameter(3, dtOperacaoIni);
		}
		if (dtOperacaoFim != null) {
			query.setParameter(4, dtOperacaoFim);
		}
		if (idUsuarioAuditoria > 0) {
			query.setParameter(5, idUsuarioAuditoria);
		}
		
		return query.getResultList();		
}	

	
}
