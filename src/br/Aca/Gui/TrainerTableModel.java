package br.Aca.Gui;

import javax.swing.table.*;

import br.Aca.Entity.*;

import java.sql.*;
import java.util.List;

public class TrainerTableModel extends AbstractTableModel{
	List<Trainer> trainers;
	
	public TrainerTableModel(List<Trainer> trainers) {
		this.trainers = trainers;
	}
	@Override
	public int getRowCount() {
		return trainers.size();
	}

	@Override
	public int getColumnCount() {		
		return 7;	
	}
	@Override	
	public Object getValueAt(int rowIndex, int columnIndex) {		

		Trainer p;
		Object dado = null;
		
		p = trainers.get(rowIndex);
		
		switch (columnIndex) {
		case 0: dado = p.getCodigo(); break;
		case 1: dado = p.getNome(); break;		
		case 2: dado = p.getDataNasc(); break;
		case 3: dado = p.getSexo(); break;
		case 4: dado = p.getAcademia(); break;
		
		}
		
		return dado;		
	}

	@Override
	public String getColumnName(int columnIndex) {

		String nome="";
		
		switch (columnIndex) {
		case 0: nome = "C�digo"; break;
		case 1: nome = "Nome"; break;		
		case 2: nome = "Data de Nascimento"; break;
		case 3: nome = "Sexo"; break;		
		case 4: nome = "Academia"; break;
				
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
		default: obj = null; break;
		}		

		return obj.getClass();
	}

}
