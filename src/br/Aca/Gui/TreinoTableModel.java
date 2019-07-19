package br.Aca.Gui;

import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import br.Aca.Entity.Treino;

public class TreinoTableModel implements TableModel {

	List<Treino> treinos;

	public TreinoTableModel(List<Treino> treinos){

		this.treinos = treinos;
		
	}
	
	@Override
	public int getRowCount() {
		return treinos.size();
	}

	@Override
	public int getColumnCount() {		
		return 2;	
	}
	@Override	
	public Object getValueAt(int rowIndex, int columnIndex) {		

		Treino c;
		Object dado = null;
		
		c = treinos.get(rowIndex);
		
		switch (columnIndex) {
		case 0: dado = c.getCodigo(); break;
		case 1: dado = c.getExercicio(); break;		
		}
		
		return dado;		
	}

	@Override
	public String getColumnName(int columnIndex) {

		String nome="";
		
		switch (columnIndex) {
		case 0: nome = "Codigo"; break;
		case 1: nome = "Exercicios"; break;		
		}		
		return nome; 	
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {

		Object obj;
		
		switch (columnIndex) {
		case 0: obj = String.class; break;
		case 1: obj = String.class; break;
		default: obj = null; break;
		}		

		return obj.getClass();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}
}
