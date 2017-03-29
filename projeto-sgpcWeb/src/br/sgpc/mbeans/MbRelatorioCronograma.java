package br.sgpc.mbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

import br.sgpc.dlo.MantemDadosConsolidadosDLO;
import br.sgpc.dlo.MantemEtapaDLO;
import br.sgpc.dlo.MantemTmpDLO;
import br.sgpc.dlo.RelatorioDLO;
import br.sgpc.dlo.funcoesUteis.Funcoes;
import br.sgpc.dominio.Cronograma;
import br.sgpc.dominio.Dadosconsolidados;
import br.sgpc.dominio.Etapa;
import br.sgpc.dominio.Tmp;

@ManagedBean(name = "MbRelatorioCronograma")
@ViewScoped
public class MbRelatorioCronograma extends Funcoes implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private RelatorioDLO relatorioDLO;
	
	@EJB
	private MantemDadosConsolidadosDLO mantemDadosConsolidadosDLO;

	@EJB
	private MantemEtapaDLO mantemEtapaDLO;

	@EJB
	private MantemTmpDLO mantemTmpDLO;

	private Cronograma cronograma;

	private Dadosconsolidados dadosConsolidados;

	private List<Cronograma> listaCronograma;

	private List<Etapa> listaEtapa;

	private List<Tmp> listaTmp;

	private int numProcesso;

	private boolean habilita;
	
	private int status;
	
	private int finalizado;

	@PostConstruct
	public void init() {
		limpar();
		popularListas();
	}

	public void limpar() {
		cronograma = new Cronograma();
		dadosConsolidados = new Dadosconsolidados();
		listaEtapa = new ArrayList<Etapa>();
		listaTmp = new ArrayList<Tmp>();
		habilita = true;
		listaCronograma = new ArrayList<Cronograma>();
		status = -1;
		finalizado = -1;
	}
	
	public void popularListas(){
		carregarEtapa();
		carregarTmp();
	}
	
	public void cancelar(){
		limpar();
		popularListas();
	}
	public void pesquisar() {
		dadosConsolidados = mantemDadosConsolidadosDLO.obter(numProcesso);
		if (dadosConsolidados == null && numProcesso != 0) {
			limpar();
			popularListas();
			msgErro("Número de processo inexistente");
		} else {
			if (validaDatas()) {
				carregarCronogramas();
			} else {
				msgErro("Data final menor que data inicial.");
			}
		}
	}

	public boolean validaDatas() {

		boolean valida = true;
		
		if (cronograma.getDtFim() != null && cronograma.getDtIni() != null)
			valida = !cronograma.getDtFim().before(cronograma.getDtIni());

		return valida;
	}

	private void carregarCronogramas() {
		dadosConsolidados = dadosConsolidados == null ? new Dadosconsolidados() :  dadosConsolidados;
		dadosConsolidados.setNumProcesso(numProcesso);
		cronograma.setDadosconsolidados(dadosConsolidados);
		try {
			listaCronograma = relatorioDLO.consultarRelAuditoriaCronograma(cronograma, status);

			for (int i = 0; i < listaCronograma.size(); i++) {
				if (listaCronograma.get(i).getStatus())
					listaCronograma.get(i)
							.setQtdDiasFinalizados(
									(int) TimeUnit.DAYS.convert(
											listaCronograma.get(i).getDtFinalizado().getTime()
													- listaCronograma.get(i).getDtIni().getTime(),
											TimeUnit.MILLISECONDS));
			}
		} catch (Exception e) {
			msgErro("Erro ao buscar Cronograma: " + e.getMessage());
		}

	}

	private void carregarEtapa() {
		listaEtapa = mantemEtapaDLO.carregarDados();
	}

	private void carregarTmp() {
		listaTmp = mantemTmpDLO.carregarDados();
	}

	public void habilitaCampos() {
		habilita = false;
	}

	public Date hoje() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
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
       	
       	cellcabec.setCellValue("Relatório de Cronogramas");
       	sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));
       	cellcabec.setCellStyle(cellStyle); 
       	
        cabecalho = sheet.getRow(1);
        
        cellcabec = cabecalho.getCell(0);
       	cellcabec.setCellValue("Processo");
       	sheet.addMergedRegion(new CellRangeAddress(1, 2, 0, 0));
    	cellcabec.setCellStyle(cellStyle); 
    	
        cellcabec = cabecalho.getCell(1);
       	cellcabec.setCellValue("");
       	sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 3));
    	cellcabec.setCellStyle(cellStyle);
    	
    	cellcabec = cabecalho.getCell(4);
       	cellcabec.setCellValue("Datas");
       	sheet.addMergedRegion(new CellRangeAddress(1, 1, 4, 6));
    	cellcabec.setCellStyle(cellStyle);  
    	
    	cellcabec = cabecalho.getCell(7);
       	cellcabec.setCellValue("Dias");
       	sheet.addMergedRegion(new CellRangeAddress(1, 1, 7, 8));
    	cellcabec.setCellStyle(cellStyle);    
    	
    	cellcabec = cabecalho.getCell(9);
       	cellcabec.setCellValue("");
       	sheet.addMergedRegion(new CellRangeAddress(1, 1, 9, 9));
    	cellcabec.setCellStyle(cellStyle);     	
       	
        
        cabecalho = sheet.getRow(2);      	
       	cellcabec = cabecalho.getCell(1);
       	cellcabec.setCellValue("Etapa");
    	cellcabec.setCellStyle(cellStyle);       	
     	cellcabec = cabecalho.getCell(2);
       	cellcabec.setCellValue("Status");
    	cellcabec.setCellStyle(cellStyle);      
        cellcabec = cabecalho.getCell(3);
       	cellcabec.setCellValue("Tempo");
    	cellcabec.setCellStyle(cellStyle);       	
        cellcabec = cabecalho.getCell(4);
       	cellcabec.setCellValue("Início");
    	cellcabec.setCellStyle(cellStyle);       	
        cellcabec = cabecalho.getCell(5);
       	cellcabec.setCellValue("Fim");
    	cellcabec.setCellStyle(cellStyle);       	
       	cellcabec = cabecalho.getCell(6);  
       	cellcabec.setCellValue("Finalizado");
    	cellcabec.setCellStyle(cellStyle);       	
       	cellcabec = cabecalho.getCell(7);  
       	cellcabec.setCellValue("Estimado");
    	cellcabec.setCellStyle(cellStyle);       	
       	cellcabec = cabecalho.getCell(8);  
       	cellcabec.setCellValue("Decorrido");
    	cellcabec.setCellStyle(cellStyle);       	
       	cellcabec = cabecalho.getCell(9);  
       	cellcabec.setCellValue("Observações");
    	cellcabec.setCellStyle(cellStyle);   	

       	int i = sheet.getPhysicalNumberOfRows()+2;
    	cabecalho = sheet.createRow(i);
    	cellcabec =   cabecalho.createCell(0);
//        cellcabec.setCellValue(this.gerarFooterCSV().toString());
    }
	
	public Cronograma getCronograma() {
		return cronograma;
	}

	public void setCronograma(Cronograma cronograma) {
		this.cronograma = cronograma;
	}

	public Dadosconsolidados getDadosConsolidados() {
		return dadosConsolidados;
	}

	public void setDadosConsolidados(Dadosconsolidados dadosConsolidados) {
		this.dadosConsolidados = dadosConsolidados;
	}

	public List<Cronograma> getListaCronograma() {
		return listaCronograma;
	}

	public void setListaCronograma(List<Cronograma> listaCronograma) {
		this.listaCronograma = listaCronograma;
	}

	public List<Etapa> getListaEtapa() {
		return listaEtapa;
	}

	public void setListaEtapa(List<Etapa> listaEtapa) {
		this.listaEtapa = listaEtapa;
	}

	public List<Tmp> getListaTmp() {
		return listaTmp;
	}

	public void setListaTmp(List<Tmp> listaTmp) {
		this.listaTmp = listaTmp;
	}

	public Integer getNumProcesso() {
		return numProcesso;
	}

	public void setNumProcesso(Integer numProcesso) {
		numProcesso = numProcesso == null ? 0 : numProcesso;
		this.numProcesso = numProcesso;
	}

	public boolean isHabilita() {
		return habilita;
	}

	public void setHabilita(boolean habilita) {
		this.habilita = habilita;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getFinalizado() {
		return finalizado;
	}

	public void setFinalizado(Integer finalizado) {
		this.finalizado = finalizado;
	}

}
