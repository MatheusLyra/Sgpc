package br.sgpc.mbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.sgpc.dlo.MantemAreaDLO;
import br.sgpc.dlo.MantemStatusDLO;
import br.sgpc.dlo.MantemTmpDLO;
import br.sgpc.dlo.RelatorioGerencialDLO;
import br.sgpc.dlo.funcoesUteis.Funcoes;
import br.sgpc.dominio.Area;
import br.sgpc.dominio.Status;
import br.sgpc.dominio.Tmp;

@ManagedBean(name = "mbRelatorioGerencial")
@ViewScoped
public class MbRelatorioGerencial extends Funcoes implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EJB
	private RelatorioGerencialDLO relatorioGerencialDLO;
	
	@EJB
	private MantemAreaDLO mantemAreaDLO;
	
	@EJB
	private MantemTmpDLO mantemTmpDLO;
	
	@EJB
	private MantemStatusDLO mantemStatusDLO;	
	
	private List<Object> listaRelatorioTmpProcesso;
	private List<Object> listaRelatorioTmpArea;
	private List<Object> listaRelatorioTmpProcTmp;
	private List<Area>   listaArea;
	private List<Tmp>    listaTmp;
	private List<Status> listaStatus;
	
	private BarChartModel barModel;	
	private int           numProcesso;
	private int           numArea;
	private int           numTmp;
	private int           numStatus;
	private String        tipo;
	private Date          dtOperacaoIni;
	private Date          dtOperacaoFim;
	private boolean       flag;
	
	private Area area;
	private Tmp tmp;
	private Status status;
	
    @PostConstruct
    public void inicializar() {
    	numProcesso   = 0;
    	numTmp        = 0;
    	tipo          = "A";
    	dtOperacaoIni = null;
    	dtOperacaoFim = null;
    	flag          = false;
    	
    	listaArea   = new ArrayList<Area>();
    	listaTmp    = new ArrayList<Tmp>();
    	listaStatus = new ArrayList<Status>();
    	
    	carregarDados();
    }
    
    private void carregarDados(){
    	listaArea   = mantemAreaDLO.carregarDados();
    	listaTmp    = mantemTmpDLO.carregarDados();
    	listaStatus = mantemStatusDLO.carregarDados(); 
    }
    
 
	private void criarBarModelTmpProcTmp() {
		barModel = initBarModelTmpProcTmp();
		
		if (listaRelatorioTmpProcTmp.size() > 0) {
			barModel.setTitle("Gráfico de Tempo do Processo por Tmp");
			barModel.setLegendPosition("ne");

			Axis xAxis = barModel.getAxis(AxisType.X);
			xAxis.setLabel("Tmp's");

			Axis yAxis = barModel.getAxis(AxisType.Y);
			yAxis.setLabel("Dias");
			yAxis.setMin(0);
			Integer maxEstimado = maxNum(listaRelatorioTmpProcTmp, 1);
			Integer maxFinalizado = maxNum(listaRelatorioTmpProcTmp, 2);
			if (maxEstimado > maxFinalizado) {
				yAxis.setMax(maxEstimado + 30);
			} else {
				yAxis.setMax(maxFinalizado + 30);
			}
		} else {
			limpar();
		}
	}  
    
	private BarChartModel initBarModelTmpProcTmp() {
		try{
			numTmp = tmp.getIdTmp();
		}catch(Exception e){
			numTmp = 0;
		}
		
		try{
			listaRelatorioTmpProcTmp = relatorioGerencialDLO.consultarRelGerencialTmpProcTmp(numProcesso, numTmp, dtOperacaoIni,
				dtOperacaoFim, numStatus);
		}catch(Exception e){
			msgErro("Erro ao realizar pesquisar com a seguinte mensagem: " + e.getMessage());
		}
		
		BarChartModel model  = new BarChartModel();		
		ChartSeries estimado = new ChartSeries();
		estimado.setLabel("Tempo Estimado");
		for (int i = 0; i < listaRelatorioTmpProcTmp.size(); i++)
			estimado.set(((Object[]) listaRelatorioTmpProcTmp.get(i))[0].toString(),
					(Number) ((Object[]) listaRelatorioTmpProcTmp.get(i))[1]);

		ChartSeries finalizado = new ChartSeries();
		finalizado.setLabel("Tempo Finalizado");
		for (int i = 0; i < listaRelatorioTmpProcTmp.size(); i++)
			finalizado.set(((Object[]) listaRelatorioTmpProcTmp.get(i))[0].toString(),
					(Number) ((Object[]) listaRelatorioTmpProcTmp.get(i))[2]);

		model.addSeries(estimado);
		model.addSeries(finalizado);

		return model;
	} 
    
	private void criarBarModelTmpProcesso() {
		barModel = initBarModelTmpProcesso();
		
		if (listaRelatorioTmpProcesso.size() > 0) {
			barModel.setTitle("Gráfico de Tempo por Processo");
			barModel.setLegendPosition("ne");

			Axis xAxis = barModel.getAxis(AxisType.X);
			xAxis.setLabel("Áreas");

			Axis yAxis = barModel.getAxis(AxisType.Y);
			yAxis.setLabel("Dias");
			yAxis.setMin(0);
			Integer maxEstimado = maxNum(listaRelatorioTmpProcesso, 2);
			Integer maxFinalizado = maxNum(listaRelatorioTmpProcesso, 3);
			if (maxEstimado > maxFinalizado) {
				yAxis.setMax(maxEstimado + 30);
			} else {
				yAxis.setMax(maxFinalizado + 30);
			}
		} else {
			limpar();
		}
	}
    
    private BarChartModel initBarModelTmpProcesso() {
    	try{
    		listaRelatorioTmpProcesso = relatorioGerencialDLO.consultarRelGerencialTmpProcesso(numProcesso, dtOperacaoIni, dtOperacaoFim, numStatus);
		}catch(Exception e){
			msgErro("Erro ao realizar pesquisar com a seguinte mensagem: " + e.getMessage());
		}
		
        BarChartModel model = new BarChartModel();
        ChartSeries estimado = new ChartSeries();
        estimado.setLabel("Tempo Estimado");
        for (int i = 0; i < listaRelatorioTmpProcesso.size(); i++) 
        	estimado.set( ((Object[])listaRelatorioTmpProcesso.get(i))[1].toString(), (Number) ((Object[])listaRelatorioTmpProcesso.get(i))[2] );
 
        ChartSeries finalizado = new ChartSeries();
        finalizado.setLabel("Tempo Finalizado");
        for (int i = 0; i < listaRelatorioTmpProcesso.size(); i++) 
        	finalizado.set( ((Object[])listaRelatorioTmpProcesso.get(i))[1].toString(), (Number) ((Object[])listaRelatorioTmpProcesso.get(i))[3] );        	

        model.addSeries(estimado);
        model.addSeries(finalizado);
         
        return model;
    }
    
    
	private void criarBarModelTmpArea() {
		try {
			numArea = area.getIdArea();
		} catch (Exception e) {
			numArea = 0;
		}

		if (numArea == 0) {
			msgInfo("Escolha uma área para realizar a pesquisa.");
			flag = false;
		} else {
			barModel = initBarModelTmpArea();

			if (listaRelatorioTmpArea.size() > 0) {
				barModel.setTitle("Gráfico de Tempo do Processo por Área");
				barModel.setLegendPosition("ne");

				Axis xAxis = barModel.getAxis(AxisType.X);
				xAxis.setLabel("Processos");

				Axis yAxis = barModel.getAxis(AxisType.Y);
				yAxis.setLabel("Dias");
				yAxis.setMin(0);
				Integer maxEstimado = maxNum(listaRelatorioTmpArea, 2);
				Integer maxFinalizado = maxNum(listaRelatorioTmpArea, 3);
				if (maxEstimado > maxFinalizado) {
					yAxis.setMax(maxEstimado + 30);
				} else {
					yAxis.setMax(maxFinalizado + 30);
				}
			} else {
				limpar();
			}
		}
	}
    
	private BarChartModel initBarModelTmpArea() {

		try {
			listaRelatorioTmpArea = relatorioGerencialDLO.consultarRelGerencialTmpArea(area.getIdArea(), dtOperacaoIni,
				dtOperacaoFim, numStatus);
		}catch(Exception e){
			msgErro("Erro ao realizar pesquisar com a seguinte mensagem: " + e.getMessage());
		}
		
		BarChartModel model = new BarChartModel();
		ChartSeries estimado = new ChartSeries();
		estimado.setLabel("Tempo Estimado");
		for (int i = 0; i < listaRelatorioTmpArea.size(); i++)
			estimado.set(((Object[]) listaRelatorioTmpArea.get(i))[1].toString(),
					(Number) ((Object[]) listaRelatorioTmpArea.get(i))[2]);

		ChartSeries finalizado = new ChartSeries();
		finalizado.setLabel("Tempo Finalizado");
		for (int i = 0; i < listaRelatorioTmpArea.size(); i++)
			finalizado.set(((Object[]) listaRelatorioTmpArea.get(i))[1].toString(),
					(Number) ((Object[]) listaRelatorioTmpArea.get(i))[3]);

		model.addSeries(estimado);
		model.addSeries(finalizado);

		return model;
	}

    
	public void pesquisar() {
		try {
			numStatus = status.getIdStatus();
		} catch (Exception e) {
			numStatus = 0;
		}
		
		if (tipo.equals("P")) {
			criarBarModelTmpProcesso();
		} else if (tipo.equals("A")) {
			criarBarModelTmpArea();
		} else {
			criarBarModelTmpProcTmp();
		}
	}
    
    public void setFlagToTrue(){
    	flag = true;
    	pesquisar();
    }  
    
    private Integer maxNum(List<Object> list, int pos){
    	Integer temp  = 0;
    	Integer valor = 0;
    	for (int i = 0; i < list.size(); i++){
    		try{
        		valor = Integer.valueOf(((Object[])list.get(i))[pos].toString());    	    	
    	    }catch (Exception e){
    	    	valor = 0;
    	    }
            if( valor > temp){
                temp = valor;
            }
        }
    	return temp;
    }
    
    public void limpar(){
    	numProcesso = 0;
    	numTmp      = 0;
    	dtOperacaoFim = null;
    	dtOperacaoIni = null;
    	flag = false;
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

	public List<Tmp> getListaTmp() {
		return listaTmp;
	}

	public void setListaTmp(List<Tmp> listaTmp) {
		this.listaTmp = listaTmp;
	}

	public boolean isFlag() {
		return flag;
	}

	public Tmp getTmp() {
		return tmp;
	}

	public void setTmp(Tmp tmp) {
		this.tmp = tmp;
	}

	public List<Area> getListaArea() {
		return listaArea;
	}

	public void setListaArea(List<Area> listaArea) {
		this.listaArea = listaArea;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public BarChartModel getBarModel() {
			return barModel;			
	}

	public void setBarModel(BarChartModel barModel) {
		this.barModel = barModel;
	}

	public int getNumProcesso() {
		return numProcesso;
	}

	public void setNumProcesso(int numProcesso) {
		this.numProcesso = numProcesso;
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
	
	
    
    
}
