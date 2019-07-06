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
				+ "tra_codigo, tra_nome, tra_data_nasc, tra_sexo, academia) "
				+ "VALUES (" + t.getMatricula() 	+ ","
				+ "'" + t.getNome() 				+ "',"
				+ t.getRg() 						+ ","
				+ t.getCpf() 						+ ","
				+ "'" + t.getAcademia().getSigla()	+ "');";

		try {
			getTrainer(t.getMatricula());
			throw new EntityAlreadyExistException("Trainer (tra_codigo=" + t.getMatricula() + ")"); 
		} catch (EntityNotExistException e) {
			return cnx.atualize(strIncluir) > 0;
		}

	}

	public Trainer getTrainer(int tra_codigo) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityNotExistException
	{

		String strBusca = "SELECT tra_codigo, tra_nome, tra_data_nasc, tra_sexo, academia "
				+ " FROM trainer "
				+ " WHERE tra_codigo = " + tra_codigo + ";";

		Trainer trainer = null;
		Academia academia = null;

		rs = cnx.consulte(strBusca);
		try{

			if (rs.next()){

				academia = adb.getAcademia(rs.getString(7));				

				trainer = new Trainer(rs.getInt(1), 
						rs.getString(2),
						rs.getInt(3),
						rs.getInt(4),
						rs.getString(5),
						rs.getString(6),
						academia);

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
	public boolean updProfessor(Trainer t) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityNotExistException 
	{

		String strAtualizar = "UPDATE trainer "
				+ " SET tra_nome = '" + t.getNome() 			+ "',"
				+ " tra_data_nasc = " + t.getRg()						+ ", "
				+ " tra_sexo = " + t.getCpf()					+ ", "
				+ " academia = '" + t.getAcademia().getSigla()	+ "' "
				+ " WHERE tra_codigo = " + t.getMatricula() 	+ ";";

		getTrainer(t.getMatricula());
		return cnx.atualize(strAtualizar) > 0;
	}

	public boolean delProfessor(Trainer t) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityNotExistException
	{

		String strDeletar = "DELETE FROM trainer "
				+ " WHERE tra_codigo = " + t.getMatricula() + ";";

		getTrainer(t.getMatricula());
		return cnx.atualize(strDeletar) > 0;
	}

	public List<Trainer> getTrainers() throws
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityTableIsEmptyException,
		EntityNotExistException
	{

		List<Trainer> listaDeTrainers = new ArrayList<Trainer>();

		String strBusca = "SELECT tra_codigo, tra_nome, tra_data_nasc, tra_sexo, academia "
				+ " FROM trainer;";

		Trainer trainer;
		Academia academia;

		rs = cnx.consulte(strBusca);
		try{
			if (rs.next()) {
				rs.beforeFirst();			
				while(rs.next()){
	
					academia = adb.getAcademia(rs.getString(7));				
	
					trainer = new Trainer(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6), academia);
	
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

	public List<Trainer> getProfessoresPorNome(String tra_nome) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityTableIsEmptyException,
		EntityNotExistException
	{

		List<Trainer> listaDeTrainers = new ArrayList<Trainer>();

		String strBusca = "SELECT tra_codigo, tra_nome, tra_data_nasc, tra_sexo, academia "
				+ " FROM trainer "
				+ " WHERE tra_nome LIKE '%" + tra_nome + "%';";

		Trainer trainer;
		Academia academia;

		rs = cnx.consulte(strBusca);

		try{
			if (rs.next()) {
				rs.beforeFirst();			
				while(rs.next()){
					academia = adb.getAcademia(rs.getString(7));				
	
					trainer = new Trainer(rs.getInt(1), 
							rs.getString(2),
							rs.getInt(3),
							rs.getInt(4),
							rs.getString(5),
							rs.getString(6),
							academia);
	
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