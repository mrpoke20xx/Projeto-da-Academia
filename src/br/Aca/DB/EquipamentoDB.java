package br.Aca.DB;

import java.sql.*;
import java.util.*;

import br.Aca.Entity.*;
import br.Aca.Exception.DataBaseGenericException;
import br.Aca.Exception.DataBaseNotConnectedException;
import br.Aca.Exception.EntityAlreadyExistException;
import br.Aca.Exception.EntityNotExistException;
import br.Aca.Exception.EntityTableIsEmptyException;

public class EquipamentoDB {

	private Conexao cnx;
	private ResultSet rs;
	private AcademiaDB adb;

	public EquipamentoDB(Conexao cnx){
		this.cnx = cnx;
		adb = new AcademiaDB(this.cnx);
	}

	public boolean addEquipamento(Equipamento e) throws 
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityAlreadyExistException
	{

		String strIncluir = "INSERT INTO equipamento ("
				+ "equ_codigo, equ_nome, equ_pesomax, equ_descricao, equ_aca_codigo) "
				+ "VALUES (" + e.getCodigo() 	+ ","
				+ "'" + e.getNome() 				+ "',"
				+ e.getPesoMax() 						+ ","
				+ "'" +e.getDescricao() 				+ "',"
				+ "" + e.getAcademia().getCodigo()	+ ");";

		try {
			getEquipamento(e.getCodigo());
			throw new EntityAlreadyExistException("Equipamento (equ_codigo=" + e.getCodigo() + ")"); 
		} catch (EntityNotExistException ex) {
			return cnx.atualize(strIncluir) > 0;
		}

	}

	public Equipamento getEquipamento(int equ_codigo) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityNotExistException
	{

		String strBusca = "SELECT equ_codigo, equ_nome, equ_pesomax, equ_descricao, equ_aca_codigo "
				+ " FROM equipamento "
				+ " WHERE equ_codigo = " + equ_codigo + ";";

		Equipamento equipamento = null;
		Academia academia = null;

		rs = cnx.consulte(strBusca);
		try{

			if (rs.next()){

				academia = adb.getAcademia(rs.getInt(5));				

				equipamento = new Equipamento(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4), academia);

			}else {
				throw new EntityNotExistException("Equipamento (equ_codigo=" + equ_codigo + ")");
			}
		}catch(SQLException sqle){
			throw new DataBaseGenericException(sqle.getErrorCode(), 
					sqle.getMessage());
		}
		return equipamento;
	}
	// PAREI AQUI
	public boolean updEquipamento(Equipamento e) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityNotExistException 
	{

		String strAtualizar = "UPDATE equipamento "
				+ " SET equ_nome = '" + e.getNome() 			+ "',"
				+ " equ_peso_max = " + e.getPesoMax()						+ ", "
				+ " equ_descricao = '" + e.getDescricao()					+ "', "
				+ " equ_aca_codigo = " + e.getAcademia().getCodigo()	+ " "
				+ " WHERE equ_codigo = " + e.getCodigo() 	+ ";";

		getEquipamento(e.getCodigo());
		return cnx.atualize(strAtualizar) > 0;
	}

	public boolean delEquipamento(Equipamento e) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityNotExistException
	{

		String strDeletar = "DELETE FROM equipamento "
				+ " WHERE equ_codigo = " + e.getCodigo() + ";";

		getEquipamento(e.getCodigo());
		return cnx.atualize(strDeletar) > 0;
	}

	public List<Equipamento> getEquipamentos() throws
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityTableIsEmptyException,
		EntityNotExistException
	{

		List<Equipamento> listaDeEquipamentos = new ArrayList<Equipamento>();

		String strBusca = "SELECT equ_codigo, equ_nome, equ_pesomax, equ_descricao, equ_aca_codigo "
				+ " FROM equipamento;";

		Equipamento equipamento;
		Academia academia;

		rs = cnx.consulte(strBusca);
		try{
			if (rs.next()) {
				rs.beforeFirst();			
				while(rs.next()){
	
					academia = adb.getAcademia(rs.getInt(5));				
	
					equipamento = new Equipamento(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4), academia);
	
					listaDeEquipamentos.add(equipamento);
				}
			}else {
				throw new EntityTableIsEmptyException("Equipamento");
			}
		}catch(SQLException sqle){
			throw new DataBaseGenericException(sqle.getErrorCode(), 
					sqle.getMessage());
		}
		return listaDeEquipamentos;
	}

	public List<Equipamento> getEquipamentosPorNome(String tra_nome) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityTableIsEmptyException,
		EntityNotExistException
	{

		List<Equipamento> listaDeEquipamentos = new ArrayList<Equipamento>();

		String strBusca = "SELECT equ_codigo, equ_nome, equ_pesomax, equ_descricao, equ_aca_codigo "
				+ " FROM equipamento "
				+ " WHERE equ_nome LIKE '%" + tra_nome + "%';";

		Equipamento equipamento;
		Academia academia;

		rs = cnx.consulte(strBusca);

		try{
			if (rs.next()) {
				rs.beforeFirst();			
				while(rs.next()){
					academia = adb.getAcademia(rs.getInt(5));				
	
					equipamento = new Equipamento(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4), academia);
	
					listaDeEquipamentos.add(equipamento);
				}
			}else {
				throw new EntityTableIsEmptyException("Equipamento");
			}
		}catch(SQLException sqle){
			throw new DataBaseGenericException(sqle.getErrorCode(), 
					sqle.getMessage());
		}
		return listaDeEquipamentos;
	}

}