package br.sgpc.dao;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Representação mais genérica de um banco na aplicação.
 * 
 */
public class MySql {

	/** Encapsula um mapa das propriedades gerais do sistema. */
	public static Properties propriedades;

	public MySql() {
		if (propriedades == null) {
			propriedades = new Properties();
			try {
				propriedades.load(MySql.class.getResourceAsStream("db_mysql.properties"));
			} catch (IOException ex) {
				Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}

