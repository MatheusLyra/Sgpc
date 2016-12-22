package br.sgpc.dlo;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Representação mais genérica de um DLO da aplicação. 
 * 
 */
public abstract class DLO {
  
   static final String TIPO_EIS = "TIPO_EIS";

  /** Encapsula um mapa das propriedades gerais do sistema. */
  protected static Properties propriedades;

  public DLO() {
    if (propriedades == null) {
      propriedades = new Properties();
      try {
        propriedades.load(DLO.class.getResourceAsStream("settings.properties"));
      } catch (IOException ex) {
        Logger.getLogger(DLO.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }
}
