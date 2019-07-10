package br.Aca.DB;

import java.sql.*;
import java.util.*;

import br.Aca.Entity.*;
import br.Aca.Exception.DataBaseGenericException;
import br.Aca.Exception.DataBaseNotConnectedException;
import br.Aca.Exception.EntityAlreadyExistException;
import br.Aca.Exception.EntityNotExistException;
import br.Aca.Exception.EntityTableIsEmptyException;

public class TreinoDB {

	private Conexao cnx;
	private ResultSet rs;
	private ExercicioDB edb;
	private ClienteDB cdb;

	public TreinoDB(Conexao cnx){
		this.cnx = cnx;
		edb = new ExercicioDB(this.cnx);
	}

	public boolean addTreino(Treino t) throws 
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityAlreadyExistException
	{

		String strIncluir = "INSERT INTO treino ("
				+ "tre_codigo, tre_vencimento, tre_cli_codigo, tre_exe_codigo) "
				+ "VALUES (" + t.getCodigo() 	+ ","
				+ "" + t.getVencimento() + ","
				+ "" +t.getCliente().getCodigo() + ","
				+ "" + t.getExercicio().getCodigo() + ");";

		try {
			getTreino(t.getCodigo());
			throw new EntityAlreadyExistException("Treino (tre_codigo=" + t.getCodigo() + ")"); 
		} catch (EntityNotExistException ex) {
			return cnx.atualize(strIncluir) > 0;
		}

	}

	public Treino getTreino(int tre_codigo) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityNotExistException
	{

		String strBusca = "SELECT tre_codigo, tre_vencimento, tre_cli_codigo, tre_exe_codigo "
				+ " FROM treino "
				+ " WHERE tre_codigo = " + tre_codigo + ";";

		Treino treino = null;
		Exercicio exercicio = null;
		Cliente cliente = null;

		rs = cnx.consulte(strBusca);
		try{

			if (rs.next()){

				exercicio = edb.getExercicio(rs.getInt(4));	
				cliente = cdb.getCliente(rs.getInt(3));

				treino = new Treino(rs.getInt(1), rs.getDate(2), cliente, exercicio);

			}else {
				throw new EntityNotExistException("Treino (tre_codigo=" + tre_codigo + ")");
			}
		}catch(SQLException sqle){
			throw new DataBaseGenericException(sqle.getErrorCode(), 
					sqle.getMessage());
		}
		return treino;
	}
	// PAREI AQUI
	public boolean updTreino(Treino t) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityNotExistException 
	{

		String strAtualizar = "UPDATE treino "
				+ " SET tre_vencimento = " + t.getVencimento()	+ ", "
				+ " tre_cli_codigo = " + t.getCliente().getCodigo()	+ ", "
				+ " tre_exe_codigo = " + t.getExercicio().getCodigo() +","
				+ " WHERE tre_codigo = " + t.getCodigo() 	+ ";";

		getTreino(t.getCodigo());
		return cnx.atualize(strAtualizar) > 0;
	}

	public boolean delTreino(Treino t) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityNotExistException
	{

		String strDeletar = "DELETE FROM treino "
				+ " WHERE tre_codigo = " + t.getCodigo() + ";";

		getTreino(t.getCodigo());
		return cnx.atualize(strDeletar) > 0;
	}

	public List<Treino> getTreinos() throws
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityTableIsEmptyException,
		EntityNotExistException
	{

		List<Treino> listaDeTreinos = new ArrayList<Treino>();

		String strBusca = "SELECT tre_codigo, tre_vencimento, tre_cli_codigo, tre_exe_codigo "
				+ " FROM treino;";

		Treino treino;
		Exercicio exercicio;
		Cliente cliente;

		rs = cnx.consulte(strBusca);
		try{
			if (rs.next()) {
				rs.beforeFirst();			
				while(rs.next()){
	
					exercicio = edb.getExercicio(rs.getInt(4));	
					cliente = cdb.getCliente(rs.getInt(3));
	
					treino = new Treino(rs.getInt(1), rs.getDate(2), cliente, exercicio);
	
					listaDeTreinos.add(treino);
				}
			}else {
				throw new EntityTableIsEmptyException("Treino");
			}
		}catch(SQLException sqle){
			throw new DataBaseGenericException(sqle.getErrorCode(), 
					sqle.getMessage());
		}
		return listaDeTreinos;
	}

	public List<Treino> getTreinosPorNome(int tre_codigo) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityTableIsEmptyException,
		EntityNotExistException
	{

		List<Treino> listaDeTreinos = new ArrayList<Treino>();

		String strBusca = "SELECT tre_codigo, tre_vencimento, tre_cli_codigo, tre_exe_codigo "
				+ " FROM treino "
				+ " WHERE tre_codigo LIKE '%" + tre_codigo + "%';";

		Treino treino;
		Exercicio exercicio;
		Cliente cliente;

		rs = cnx.consulte(strBusca);

		try{
			if (rs.next()) {
				rs.beforeFirst();			
				while(rs.next()){
					exercicio = edb.getExercicio(rs.getInt(4));
					cliente = cdb.getCliente(rs.getInt(3));
	
					treino = new Treino(rs.getInt(1), rs.getDate(2), cliente, exercicio);
	
					listaDeTreinos.add(treino);
				}
			}else {
				throw new EntityTableIsEmptyException("Exercicio");
			}
		}catch(SQLException sqle){
			throw new DataBaseGenericException(sqle.getErrorCode(), 
					sqle.getMessage());
		}
		return listaDeTreinos;
	}

}