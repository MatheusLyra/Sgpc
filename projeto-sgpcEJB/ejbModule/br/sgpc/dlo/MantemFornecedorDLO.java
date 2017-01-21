package br.sgpc.dlo;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import br.sgpc.dao.FornecedorDao;
import br.sgpc.dominio.Fornecedor;

/**
 * Classe de objetos responsável por manter os dados cadastrais do fornecedor.
 *
 */
@Stateless
@Remote
public class MantemFornecedorDLO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EJB
	private FornecedorDao dao;

	public void cadastrar(Fornecedor fornecedor) throws Exception{
	    this.dao.salvarFornecedor(fornecedor);
	}

	public void alterar(Fornecedor fornecedor) throws Exception{
		this.dao.atualizarFornecedor(fornecedor);
	}
	
	public Fornecedor obterDados(Integer id){
		return this.dao.obter(id);
	}

	public void excluir(Fornecedor fornecedor) throws Exception {
		Fornecedor f = this.dao.obter(fornecedor.getIdFornecedor());
		if (f != null) {
			this.dao.excluirFornecedor(f);
		}
	}
	
	public List<Fornecedor> carregarDados(){
		return this.dao.consultarFornecedor();
	}	

}
