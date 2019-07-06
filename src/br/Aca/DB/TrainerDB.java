package br.Aca.DB;

import java.sql.*;
import java.util.*;

import br.Aca.Entity.*;
import br.Aca.Exception.DataBaseGenericException;
import br.Aca.Exception.DataBaseNotConnectedException;
import br.Aca.Exception.EntityAlreadyExistException;
import br.Aca.Exception.EntityNotExistException;
import br.Aca.Exception.EntityTableIsEmptyException;

public class TrainerDB {

	private Conexao cnx;
	private ResultSet rs;
	private AcademiaDB adb;

	public TrainerDB(Conexao cnx){
		this.cnx = cnx;
		adb = new AcademiaDB(this.cnx);
	}

	public boolean addTrainer(Trainer t) throws 
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityAlreadyExistException
	{

		String strIncluir = "INSERT INTO trainer ("
				+ "tra_codigo, tra_nome, tra_data_nasc, tra_sexo, tra_aca_codigo) "
				+ "VALUES (" + t.getCodigo() 	+ ","
				+ "'" + t.getNome() 				+ "',"
				+ t.getDataNasc() 						+ ","
				+ "'" +t.getSexo() 						+ "',"
				+ "" + t.getAcademia().getCodigo()	+ ");";

		try {
			getTrainer(t.getCodigo());
			throw new EntityAlreadyExistException("Trainer (tra_codigo=" + t.getCodigo() + ")"); 
		} catch (EntityNotExistException e) {
			return cnx.atualize(strIncluir) > 0;
		}

	}

	public Trainer getTrainer(int tra_codigo) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityNotExistException
	{

		String strBusca = "SELECT tra_codigo, tra_nome, tra_data_nasc, tra_sexo, tra_aca_codigo "
				+ " FROM trainer "
				+ " WHERE tra_codigo = " + tra_codigo + ";";

		Trainer trainer = null;
		Academia academia = null;

		rs = cnx.consulte(strBusca);
		try{

			if (rs.next()){

				academia = adb.getAcademia(rs.getInt(5));				

				trainer = new Trainer(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getString(4), academia);

			}else {
				throw new EntityNotExistException("Trainer (tra_codigo=" + tra_codigo + ")");
			}
		}catch(SQLException sqle){
			throw new DataBaseGenericException(sqle.getErrorCode(), 
					sqle.getMessage());
		}
		return trainer;
	}
	// PAREI AQUI
	public boolean updTrainer(Trainer t) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityNotExistException 
	{

		String strAtualizar = "UPDATE trainer "
				+ " SET tra_nome = '" + t.getNome() 			+ "',"
				+ " tra_data_nasc = " + t.getDataNasc()						+ ", "
				+ " tra_sexo = '" + t.getSexo()					+ "', "
				+ " tra_aca_codigo = " + t.getAcademia().getCodigo()	+ " "
				+ " WHERE tra_codigo = " + t.getCodigo() 	+ ";";

		getTrainer(t.getCodigo());
		return cnx.atualize(strAtualizar) > 0;
	}

	public boolean delTrainer(Trainer t) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityNotExistException
	{

		String strDeletar = "DELETE FROM trainer "
				+ " WHERE tra_codigo = " + t.getCodigo() + ";";

		getTrainer(t.getCodigo());
		return cnx.atualize(strDeletar) > 0;
	}

	public List<Trainer> getTrainers() throws
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityTableIsEmptyException,
		EntityNotExistException
	{

		List<Trainer> listaDeTrainers = new ArrayList<Trainer>();

		String strBusca = "SELECT tra_codigo, tra_nome, tra_data_nasc, tra_sexo, tra_aca_codigo "
				+ " FROM trainer;";

		Trainer trainer;
		Academia academia;

		rs = cnx.consulte(strBusca);
		try{
			if (rs.next()) {
				rs.beforeFirst();			
				while(rs.next()){
	
					academia = adb.getAcademia(rs.getInt(5));				
	
					trainer = new Trainer(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getString(4), academia);
	
					listaDeTrainers.add(trainer);
				}
			}else {
				throw new EntityTableIsEmptyException("Trainer");
			}
		}catch(SQLException sqle){
			throw new DataBaseGenericException(sqle.getErrorCode(), 
					sqle.getMessage());
		}
		return listaDeTrainers;
	}

	public List<Trainer> getTrainersPorNome(String tra_nome) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityTableIsEmptyException,
		EntityNotExistException
	{

		List<Trainer> listaDeTrainers = new ArrayList<Trainer>();

		String strBusca = "SELECT tra_codigo, tra_nome, tra_data_nasc, tra_sexo, tra_aca_codigo "
				+ " FROM trainer "
				+ " WHERE tra_nome LIKE '%" + tra_nome + "%';";

		Trainer trainer;
		Academia academia;

		rs = cnx.consulte(strBusca);

		try{
			if (rs.next()) {
				rs.beforeFirst();			
				while(rs.next()){
					academia = adb.getAcademia(rs.getInt(5));				
	
					trainer = new Trainer(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getString(4), academia);
	
					listaDeTrainers.add(trainer);
				}
			}else {
				throw new EntityTableIsEmptyException("Trainer");
			}
		}catch(SQLException sqle){
			throw new DataBaseGenericException(sqle.getErrorCode(), 
					sqle.getMessage());
		}
		return listaDeTrainers;
	}

}