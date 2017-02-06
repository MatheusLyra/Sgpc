package br.sgpc.mbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import br.sgpc.dlo.MantemAreaDLO;
import br.sgpc.dlo.MantemFornecedorDLO;
import br.sgpc.dlo.MantemStatusDLO;
import br.sgpc.dlo.MantemUsuarioDLO;
import br.sgpc.dlo.RelatorioDLO;
import br.sgpc.dlo.funcoesUteis.Funcoes;
import br.sgpc.dominio.Area;
import br.sgpc.dominio.Fornecedor;
import br.sgpc.dominio.Status;
import br.sgpc.dominio.Usuario;

@ManagedBean(name = "mbRelatorioDadosConsolidados")
@ViewScoped
public class MbRelatorioDadosConsolidados extends Funcoes implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private RelatorioDLO relatorioDLO;
	
	@EJB
	private MantemAreaDLO mantemAreaDLO;
	
	@EJB
	private MantemFornecedorDLO mantemFornecedorDLO;
	
	@EJB
	private MantemUsuarioDLO mantemUsuarioDLO;	
	
	@EJB
	private MantemStatusDLO mantemStatusDLO;		
	
	private List<Object> listaRelatorio;
	private List<Area> listaArea;
	private List<Fornecedor> listaFornecedor;
	private List<Usuario> listaUsuario;
	private List<Status> listaStatus;

	private int numProcesso;
	private int idArea;
	private int idFornecedor;
	private int idUsuario;
	private int idStatus;
	private int flgUrgente;
	private String tipoDado;
	private String numContrato;
	
	private Area area;
	private Fornecedor fornecedor;
	private Usuario usuario;
	private Status status;
	
	@PostConstruct
	public void inicializar(){ 
		listaArea           = new ArrayList<Area>();
		listaFornecedor     = new ArrayList<Fornecedor>();
		listaUsuario        = new ArrayList<Usuario>();
		listaStatus         = new ArrayList<Status>();
		
		carregarDados();
		
		flgUrgente = -1;
	}
	
	private void carregarDados(){
		listaArea             = mantemAreaDLO.carregarDados();
		listaFornecedor       = mantemFornecedorDLO.carregarDados();
		listaUsuario          = mantemUsuarioDLO.carregarDados();
		listaStatus           = mantemStatusDLO.carregarDados();
	}
	
	public void pesquisar(){
		try {
			idArea = area.getIdArea();
		} catch (Exception e) {
			idArea = 0;
		}
			
		try {
			idFornecedor = fornecedor.getIdFornecedor();
		} catch (Exception e) {
			idFornecedor = 0;
		}
		
		try {
			idUsuario = usuario.getIdUsuario();
		} catch (Exception e) {
			idUsuario = 0;
		}	
		
		try {
			idStatus = status.getIdStatus();
		} catch (Exception e) {
			idStatus = 0;
		}		

		listaRelatorio = relatorioDLO.consultarRelDadosConsolidados(numProcesso, tipoDado, numContrato, idArea, idFornecedor, idUsuario, idStatus, flgUrgente);
	}
	
	public void limpar(){
		numProcesso = 0;
		tipoDado    = null;
		numContrato = null;
		
		idArea = 0;
		idFornecedor = 0;
		idStatus = 0;
		idUsuario = 0;
		flgUrgente = -1;
		listaRelatorio = null;
	}
	
	public void postProcessXLS(Object document) {
		int celulas=0, linhas=2;
 		
		HSSFWorkbook wb = (HSSFWorkbook) document;
        
     // obtendo a primeira planilha
        HSSFSheet sheet = wb.getSheetAt(0);
        celulas=sheet.getRow(0).getLastCellNum();
        sheet.shiftRows(sheet.getFirstRowNum(), sheet.getLastRowNum(), linhas);
        
        // criar as linhas
        for (int i = 0; i < linhas + 1; i++) {
               sheet.createRow(i);
               // cria as colunas
               for (int j = 0; j < celulas + 1; j++) {
                      sheet.getRow(i).createCell(j);
               }
        }
        
        HSSFCellStyle cellStyle = wb.createCellStyle();

		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        
        HSSFRow  cabecalho = sheet.getRow(0);
       	HSSFCell cellcabec = cabecalho.getCell(0);
       	
       	cellcabec.setCellValue("Relatório de Dados Consolidados");

        cabecalho = sheet.getRow(1);
        
        cellcabec = cabecalho.getCell(0);
       	cellcabec.setCellValue("Núm. do Processo");
       	sheet.addMergedRegion(new CellRangeAddress(1, 2, 0, 0));
    	cellcabec.setCellStyle(cellStyle); 
    	
        cellcabec = cabecalho.getCell(1);
       	cellcabec.setCellValue("");
       	sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 6));
    	cellcabec.setCellStyle(cellStyle);
    	
    	cellcabec = cabecalho.getCell(7);
       	cellcabec.setCellValue("Proposta");
       	sheet.addMergedRegion(new CellRangeAddress(1, 1, 7, 8));
    	cellcabec.setCellStyle(cellStyle);  
    	
    	cellcabec = cabecalho.getCell(9);
       	cellcabec.setCellValue("");
       	sheet.addMergedRegion(new CellRangeAddress(1, 1, 9, 11));
    	cellcabec.setCellStyle(cellStyle);    
    	
    	cellcabec = cabecalho.getCell(12);
       	cellcabec.setCellValue("Total de Dias");
       	sheet.addMergedRegion(new CellRangeAddress(1, 1, 12, 13));
    	cellcabec.setCellStyle(cellStyle);     	
       	
        
        cabecalho = sheet.getRow(2);      	
       	cellcabec = cabecalho.getCell(1);
       	cellcabec.setCellValue("Tipo de Dados");
    	cellcabec.setCellStyle(cellStyle);       	
     	cellcabec = cabecalho.getCell(2);
       	cellcabec.setCellValue("Núm. do Contrato");
    	cellcabec.setCellStyle(cellStyle);      
        cellcabec = cabecalho.getCell(3);
       	cellcabec.setCellValue("TAC");
    	cellcabec.setCellStyle(cellStyle);       	
        cellcabec = cabecalho.getCell(4);
       	cellcabec.setCellValue("Fornecedor");
    	cellcabec.setCellStyle(cellStyle);       	
        cellcabec = cabecalho.getCell(5);
       	cellcabec.setCellValue("Desc. Serviço");
    	cellcabec.setCellStyle(cellStyle);       	
       	cellcabec = cabecalho.getCell(6);  
       	cellcabec.setCellValue("Área Requisitante");
    	cellcabec.setCellStyle(cellStyle);       	
       	cellcabec = cabecalho.getCell(7);  
       	cellcabec.setCellValue("Inicial(R$)");
    	cellcabec.setCellStyle(cellStyle);       	
       	cellcabec = cabecalho.getCell(8);  
       	cellcabec.setCellValue("Final(R$)");
    	cellcabec.setCellStyle(cellStyle);       	
       	cellcabec = cabecalho.getCell(9);  
       	cellcabec.setCellValue("Saving(%)");
    	cellcabec.setCellStyle(cellStyle);       	
       	cellcabec = cabecalho.getCell(10);  
       	cellcabec.setCellValue("Analista Responsável");
    	cellcabec.setCellStyle(cellStyle);       	
       	cellcabec = cabecalho.getCell(11);  
       	cellcabec.setCellValue("Status");
    	cellcabec.setCellStyle(cellStyle);       	
    	cellcabec = cabecalho.getCell(12);  
       	cellcabec.setCellValue("Estimados");
    	cellcabec.setCellStyle(cellStyle);       	
    	cellcabec = cabecalho.getCell(13);  
       	cellcabec.setCellValue("Decorridos");
    	cellcabec.setCellStyle(cellStyle);       	     	

       	int i = sheet.getPhysicalNumberOfRows()+2;
    	cabecalho = sheet.createRow(i);
    	cellcabec =   cabecalho.createCell(0);
//        cellcabec.setCellValue(this.gerarFooterCSV().toString());
    }

	public List<Object> getListaRelatorio() {
		return listaRelatorio;
	}

	public void setListaRelatorio(List<Object> listaRelatorio) {
		this.listaRelatorio = listaRelatorio;
	}

	public int getNumProcesso() {
		return numProcesso;
	}

	public void setNumProcesso(int numProcesso) {
		this.numProcesso = numProcesso;
	}

	public String getTipoDado() {
		return tipoDado;
	}

	public void setTipoDado(String tipoDado) {
		this.tipoDado = tipoDado;
	}

	public String getNumContrato() {
		return numContrato;
	}

	public void setNumContrato(String numContrato) {
		this.numContrato = numContrato;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public List<Area> getListaArea() {
		return listaArea;
	}

	public void setListaArea(List<Area> listaArea) {
		this.listaArea = listaArea;
	}

	public List<Fornecedor> getListaFornecedor() {
		return listaFornecedor;
	}

	public void setListaFornecedor(List<Fornecedor> listaFornecedor) {
		this.listaFornecedor = listaFornecedor;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Status> getListaStatus() {
		return listaStatus;
	}

	public void setListaStatus(List<Status> listaStatus) {
		this.listaStatus = listaStatus;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getFlgUrgente() {
		return flgUrgente;
	}

	public void setFlgUrgente(int flgUrgente) {
		this.flgUrgente = flgUrgente;
	}
		
}
