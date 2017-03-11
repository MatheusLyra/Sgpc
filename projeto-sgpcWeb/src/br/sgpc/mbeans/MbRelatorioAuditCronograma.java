package br.sgpc.mbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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

import br.sgpc.dlo.MantemUsuarioDLO;
import br.sgpc.dlo.RelatorioDLO;
import br.sgpc.dominio.Usuario;

@ManagedBean(name = "mbRelatorioAuditCronograma")
@ViewScoped
public class MbRelatorioAuditCronograma  implements Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private RelatorioDLO relatorioDLO;
	
	@EJB
	private MantemUsuarioDLO mantemUsuarioDLO;	
	
	private List<Object> listaRelatorio;
	private List<Usuario> listaUsuario;
	
	private int    numProcesso;
	private int    idUsuarioAudit;
	private String tipoOp;
	private Date   dtOperacaoIni;
	private Date   dtOperacaoFim;
	
	private Usuario usuario;
	
	@PostConstruct
	public void inicializar(){ 
		listaUsuario  = new ArrayList<Usuario>();
		carregarDados();
	}	
	
	private void carregarDados(){
		listaUsuario  = mantemUsuarioDLO.carregarDados();
	}
	
	public void pesquisar(){
		try {
			idUsuarioAudit = usuario.getIdUsuario();
		} catch (Exception e) {
			idUsuarioAudit = 0;
		}
		
		listaRelatorio = relatorioDLO.consultarRelAuditoriaCronograma(numProcesso, tipoOp, dtOperacaoIni, dtOperacaoFim, idUsuarioAudit);
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
        HSSFCellStyle cellStyle2 = wb.createCellStyle();//----------------
        
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		//----------------
		cellStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont font2 = wb.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font2.setColor(HSSFFont.COLOR_RED);
		cellStyle2.setFont(font2);
		//----------------
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
       	sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 1));
    	cellcabec.setCellStyle(cellStyle);
    	
    	cellcabec = cabecalho.getCell(2);
       	cellcabec.setCellValue("Data");
       	sheet.addMergedRegion(new CellRangeAddress(1, 1, 2, 3));
    	cellcabec.setCellStyle(cellStyle);  
    	
    	cellcabec = cabecalho.getCell(4);
       	cellcabec.setCellValue("");
       	sheet.addMergedRegion(new CellRangeAddress(1, 1, 4, 9));
    	cellcabec.setCellStyle(cellStyle); 
    	
    	cellcabec = cabecalho.getCell(10);
       	cellcabec.setCellValue("Dados de Auditoria");
       	sheet.addMergedRegion(new CellRangeAddress(1, 1, 10, 12));
    	cellcabec.setCellStyle(cellStyle);    	

        cabecalho = sheet.getRow(2);      	
       	cellcabec = cabecalho.getCell(1);
       	cellcabec.setCellValue("Cód. Versão");
    	cellcabec.setCellStyle(cellStyle);       	
     	cellcabec = cabecalho.getCell(2);
       	cellcabec.setCellValue("Início");
    	cellcabec.setCellStyle(cellStyle);      
        cellcabec = cabecalho.getCell(3);
       	cellcabec.setCellValue("Fim");
    	cellcabec.setCellStyle(cellStyle);       	
        cellcabec = cabecalho.getCell(4);
       	cellcabec.setCellValue("Total de dias");
    	cellcabec.setCellStyle(cellStyle);       	
        cellcabec = cabecalho.getCell(5);
       	cellcabec.setCellValue("Data de Finalização");
    	cellcabec.setCellStyle(cellStyle);       	
       	cellcabec = cabecalho.getCell(6);  
       	cellcabec.setCellValue("Status");
    	cellcabec.setCellStyle(cellStyle);       	
       	cellcabec = cabecalho.getCell(7);  
       	cellcabec.setCellValue("Etapa");
    	cellcabec.setCellStyle(cellStyle);       	
       	cellcabec = cabecalho.getCell(8);  
       	cellcabec.setCellValue("Tmp (Tempo)");
    	cellcabec.setCellStyle(cellStyle);       	   	
       	cellcabec = cabecalho.getCell(9);  
       	cellcabec.setCellValue("Observações");
    	cellcabec.setCellStyle(cellStyle);       	 
    	
    	//----------------
    	cellcabec = cabecalho.getCell(10);  
       	cellcabec.setCellValue("Tipo de Operação");
    	cellcabec.setCellStyle(cellStyle2);       	
    	cellcabec = cabecalho.getCell(11);  
       	cellcabec.setCellValue("Data da Operação");
    	cellcabec.setCellStyle(cellStyle2); 
    	cellcabec = cabecalho.getCell(12);  
       	cellcabec.setCellValue("Usuário Auditado");
    	cellcabec.setCellStyle(cellStyle2);    	      	     	
    	//----------------
    	
       	int i = sheet.getPhysicalNumberOfRows()+2;
    	cabecalho = sheet.createRow(i);
    	cellcabec =   cabecalho.createCell(0);
    }		
	
	public void limpar(){
		numProcesso = 0;
		listaRelatorio = null;
	}

	public List<Object> getListaRelatorio() {
		return listaRelatorio;
	}

	public void setListaRelatorio(List<Object> listaRelatorio) {
		this.listaRelatorio = listaRelatorio;
	}

	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	public int getNumProcesso() {
		return numProcesso;
	}

	public void setNumProcesso(int numProcesso) {
		this.numProcesso = numProcesso;
	}

	public int getIdUsuarioAudit() {
		return idUsuarioAudit;
	}

	public void setIdUsuarioAudit(int idUsuarioAudit) {
		this.idUsuarioAudit = idUsuarioAudit;
	}

	public String getTipoOp() {
		return tipoOp;
	}

	public void setTipoOp(String tipoOp) {
		this.tipoOp = tipoOp;
	}

	public Date getDtOperacaoIni() {
		return dtOperacaoIni;
	}

	public void setDtOperacaoIni(Date dtOperacaoIni) {
		this.dtOperacaoIni = dtOperacaoIni;
	}

	public Date getDtOperacaoFim() {
		return dtOperacaoFim;
	}

	public void setDtOperacaoFim(Date dtOperacaoFim) {
		this.dtOperacaoFim = dtOperacaoFim;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
}
