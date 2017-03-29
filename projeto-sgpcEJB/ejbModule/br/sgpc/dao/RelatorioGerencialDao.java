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
public class RelatorioGerencialDao implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName = "pu")
	private EntityManager entityManager;
	
	public List<Object> consultarRelGerencialTmpProcesso(int numProcesso, Date dtOperacaoIni, Date dtOperacaoFim, int numStatus)
			throws Exception {
		try {
			String sql = "select d.idArea, a.descricao,sum(c.QtdediasEstimados) estimado, sum(c.QtdediasDecorridos) finalizado "
					+ "  from dadosconsolidados d " + " INNER JOIN area a on d.idArea = a.idArea "
					+ " LEFT OUTER JOIN (  "
					+ "      select c.numProcesso,sum(DATEDIFF(c.DtFinalizado,c.DtIni))QtdediasDecorridos, "
					+ " 		       sum(DATEDIFF(c.DtFim,c.DtIni))QtdediasEstimados " + "        from cronograma c "
					+ "       where not QtdDiasFim is null ";
			if (dtOperacaoIni != null)
				sql += " and c.DtIni >= ?1 ";
			if (dtOperacaoFim != null)
				sql += " and c.DtFim <= ?2 ";
			sql += "  group by numProcesso) " + " c on c.numProcesso = d.numProcesso ";
			if (numProcesso > 0)
				sql += " where d.numProcesso = ?3 ";
			if (numStatus > 0)
				sql += " and d.idStatus = ?4";			
			sql += " group by 1,2  ";

			Query query = this.entityManager.createNativeQuery(sql);

			if (dtOperacaoIni != null)
				query.setParameter(1, dtOperacaoIni);
			if (dtOperacaoFim != null)
				query.setParameter(2, dtOperacaoFim);
			if (numProcesso > 0)
				query.setParameter(3, numProcesso);
			if (numStatus > 0)
				query.setParameter(4, numStatus);			

			return query.getResultList();
		} catch (Exception e) {
			throw e;
		}
	}

	
	public List<Object> consultarRelGerencialTmpArea(int numArea, Date dtOperacaoIni, Date dtOperacaoFim, int numStatus)
			throws Exception {
		try {
			String sql = "select d.idArea, d.numProcesso,sum(c.QtdediasEstimados), sum(c.QtdediasDecorridos) "
					+ " from dadosconsolidados d " + "  INNER JOIN area a on d.idArea = a.idArea "
					+ "  LEFT OUTER JOIN (select c.numProcesso, sum(DATEDIFF(c.DtFinalizado,c.DtIni))QtdediasDecorridos, "
					+ "  						 sum(DATEDIFF(c.DtFim,c.DtIni))QtdediasEstimados "
					+ "      				from cronograma c " + "   				   where not QtdDiasFim is null ";
			if (dtOperacaoIni != null)
				sql += " and c.DtIni >= ?1 ";
			if (dtOperacaoFim != null)
				sql += " and c.DtFim <= ?2 ";
			sql += "   			   group by numProcesso)c " + " on c.numProcesso = d.numProcesso ";
			if (numArea > 0)
				sql += " where d.idarea = ?3 ";
			if (numStatus > 0)
				sql += " and d.idStatus = ?4";
			sql += " group by 1,2   ";

			Query query = this.entityManager.createNativeQuery(sql);

			if (dtOperacaoIni != null)
				query.setParameter(1, dtOperacaoIni);
			if (dtOperacaoFim != null)
				query.setParameter(2, dtOperacaoFim);
			if (numArea > 0)
				query.setParameter(3, numArea);
			if (numStatus > 0)
				query.setParameter(4, numStatus);			

			return query.getResultList();
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	public List<Object> consultarRelGerencialTmpProcTmp(int numProcesso, int numTmp, Date dtOperacaoIni,
			Date dtOperacaoFim, int numStatus) throws Exception {
		try {
/*			String sql = "select  t.Descricao," + "        sum(DATEDIFF(c.DtFim,c.DtIni))QtdediasEstimados, "
					+ "        sum(DATEDIFF(c.DtFinalizado,c.DtIni))QtdediasDecorridos  "
					+ "      from cronograma c , tmp t " + "   where not QtdDiasFim is null "
					+ "     and c.idtmp = t.idtmp ";*/
			
			String sql = "select  t.Descricao," + "        sum(DATEDIFF(c.DtFim,c.DtIni))QtdediasEstimados, "
					+ "        sum(DATEDIFF(c.DtFinalizado,c.DtIni))QtdediasDecorridos  "
					+ "      from cronograma c , tmp t, dadosconsolidados d " + "   where not QtdDiasFim is null "
					+ "     and c.idtmp = t.idtmp "
					+ "     and c.numprocesso = d.numprocesso ";			
			if (dtOperacaoIni != null)
				sql += " and c.DtIni >= ?1 ";
			if (dtOperacaoFim != null)
				sql += " and c.DtFim <= ?2 ";
			if (numTmp > 0)
				sql += " and c.idtmp = ?3";
			if (numProcesso > 0)
				sql += " and c.numProcesso = ?4";
			if (numStatus > 0)
				sql += " and d.idStatus = ?5";
			sql += "   group by 1 ";

			Query query = this.entityManager.createNativeQuery(sql);

			if (dtOperacaoIni != null)
				query.setParameter(1, dtOperacaoIni);
			if (dtOperacaoFim != null)
				query.setParameter(2, dtOperacaoFim);
			if (numTmp > 0)
				query.setParameter(3, numTmp);
			if (numProcesso > 0)
				query.setParameter(4, numProcesso);
			if (numStatus > 0)
				query.setParameter(5, numStatus);
			
			return query.getResultList();
		} catch (Exception e) {
			throw e;
		}
	}
	

}
