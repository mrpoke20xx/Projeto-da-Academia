package br.Aca.Gui;

import javax.swing.table.*;

import br.Aca.Entity.*;
import java.sql.*;
import java.util.*;

public class ClienteTableModel extends AbstractTableModel{

	List<Cliente> centros;

	public ClienteTableModel(List<Cliente> centros){

		this.centros = centros;
		
	}
	
	@Override
	public int getRowCount() {
		return centros.size();
	}

	@Override
	public int getColumnCount() {		
		return 2;	
	}
	@Override	
	public Object getValueAt(int rowIndex, int columnIndex) {		

		Cliente c;
		Object dado = null;
		
		c = centros.get(rowIndex);
		
		switch (columnIndex) {
		case 0: dado = c.getCodigo() ;break;
		case 1: dado = c.getNome(); break;
		case 2: dado = c.getEndereco(); break;
		case 3: dado = c.getDataNasc(); break;
		case 4: dado = c.getSexo(); break;
		case 5: dado = c.getNecessidade(); break;
		case 6: dado = c.getTrainer(); break;
		
		}
		
		return dado;		
	}

	@Override
	public String getColumnName(int columnIndex) {

		String nome="";
		
		switch (columnIndex) {
		case 0: nome = "cli_codigo"; break;
		case 1: nome = "cli_nome"; break;
		case 2: nome = "cli_endereco"; break;
		case 3: nome = "cli_data_nasc"; break;
		case 4: nome = "cli_sexo"; break;
		case 5: nome = "cli_necessidade"; break;
		case 6: nome = "cli_tra_codigo"; break;
		
		}		
		return nome; 	
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {

		Object obj;
		
		switch (columnIndex) {
		case 0: obj = String.class; break;
		case 1: obj = String.class; break;
		case 2: obj = String.class; break;
		case 3: obj = String.class; break;
		case 4: obj = String.class; break;
		case 5: obj = String.class; break;
		case 6: obj = String.class; break;
		default: obj = null; break;
		}		

		return obj.getClass();
	}
}