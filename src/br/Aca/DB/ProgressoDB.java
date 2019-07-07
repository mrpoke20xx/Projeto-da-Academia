package br.Aca.DB;

import java.sql.*;
import java.util.*;

import br.Aca.Entity.*;
import br.Aca.Exception.DataBaseGenericException;
import br.Aca.Exception.DataBaseNotConnectedException;
import br.Aca.Exception.EntityAlreadyExistException;
import br.Aca.Exception.EntityNotExistException;
import br.Aca.Exception.EntityTableIsEmptyException;

public class ProgressoDB {

	private Conexao cnx;
	private ResultSet rs;
	private ClienteDB cdb;

	public ProgressoDB(Conexao cnx){
		this.cnx = cnx;
		cdb = new ClienteDB(this.cnx);
	}

	public boolean addProgresso(Progresso p) throws 
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityAlreadyExistException
	{

		String strIncluir = "INSERT INTO progresso ("
				+ "pro_cli_codigo, pro_data, pro_cli_massa, pro_cli_altura) "
				+ "VALUES (" + p.getCliente().getCodigo() 	+ ","
				+ "" + p.getData() 				+ ","
				+ "" + p.getMassa() 				+ ","
				+ "" + p.getAltura()	+ ");";

		try {
			getProgresso(p.getCliente().getCodigo());
			throw new EntityAlreadyExistException("Progresso (pro_codigo=" + p.getCliente().getCodigo() + ")"); 
		} catch (EntityNotExistException ex) {
			return cnx.atualize(strIncluir) > 0;
		}

	}

	public Progresso getProgresso(int pro_codigo) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityNotExistException
	{

		String strBusca = "SELECT pro_cli_codigo, pro_data, pro_cli_massa, pro_cli_altura "
				+ " FROM progresso "
				+ " WHERE pro_cli_codigo = " + pro_codigo + ";";

		Progresso progresso = null;
		Cliente cliente = null;

		rs = cnx.consulte(strBusca);
		try{

			if (rs.next()){

				cliente = cdb.getCliente(rs.getInt(4));				

				progresso = new Progresso(cliente, rs.getDate(2), rs.getDouble(3), rs.getDouble(4));

			}else {
				throw new EntityNotExistException("Progresso (pro_cli_codigo=" + pro_codigo + ")");
			}
		}catch(SQLException sqle){
			throw new DataBaseGenericException(sqle.getErrorCode(), 
					sqle.getMessage());
		}
		return progresso;
	}
	// PAREI AQUI
	public boolean updProgresso(Progresso p) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityNotExistException 
	{

		String strAtualizar = "UPDATE progresso "
				+ " SET pro_data = '" + p.getData() 			+ "',"
				+ " pro_cli_massa = '" + p.getMassa()					+ "', "
				+ " pro_cli_altura = " + p.getAltura()	+ " "
				+ " WHERE pro_cli_codigo = " + p.getCliente().getCodigo() 	+ ";";

		getProgresso(p.getCliente().getCodigo());
		return cnx.atualize(strAtualizar) > 0;
	}

	public boolean delProgresso(Progresso p) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityNotExistException
	{

		String strDeletar = "DELETE FROM progresso "
				+ " WHERE pro_cli_codigo = " + p.getCliente().getCodigo() + ";";

		getProgresso(p.getCliente().getCodigo());
		return cnx.atualize(strDeletar) > 0;
	}

	public List<Progresso> getProgressos() throws
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityTableIsEmptyException,
		EntityNotExistException
	{

		List<Progresso> listaDeProgressos = new ArrayList<Progresso>();

		String strBusca = "SELECT pro_cli_codigo, pro_data, pro_cli_massa, pro_cli_altura "
				+ " FROM progresso;";

		Progresso progresso;
		Cliente cliente;

		rs = cnx.consulte(strBusca);
		try{
			if (rs.next()) {
				rs.beforeFirst();			
				while(rs.next()){
	
					cliente = cdb.getCliente(rs.getInt(4));				
	
					progresso = new Progresso(cliente, rs.getDate(2), rs.getDouble(3), rs.getDouble(4));
	
					listaDeProgressos.add(progresso);
				}
			}else {
				throw new EntityTableIsEmptyException("Progresso");
			}
		}catch(SQLException sqle){
			throw new DataBaseGenericException(sqle.getErrorCode(), 
					sqle.getMessage());
		}
		return listaDeProgressos;
	}

	public List<Progresso> getProgressosPorNome(int pro_cli_codigo) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityTableIsEmptyException,
		EntityNotExistException
	{

		List<Progresso> listaDeProgressos = new ArrayList<Progresso>();

		String strBusca = "SELECT pro_cli_codigo, pro_data, pro_cli_massa, pro_cli_altura "
				+ " FROM progresso "
				+ " WHERE pro_cli_codigo LIKE '%" + pro_cli_codigo + "%';";

		Progresso progresso;
		Cliente cliente;

		rs = cnx.consulte(strBusca);

		try{
			if (rs.next()) {
				rs.beforeFirst();			
				while(rs.next()){
					cliente = cdb.getCliente(rs.getInt(4));				
	
					progresso = new Progresso(cliente, rs.getDate(2), rs.getDouble(3), rs.getDouble(4));
	
					listaDeProgressos.add(progresso);
				}
			}else {
				throw new EntityTableIsEmptyException("Progresso");
			}
		}catch(SQLException sqle){
			throw new DataBaseGenericException(sqle.getErrorCode(), 
					sqle.getMessage());
		}
		return listaDeProgressos;
	}
}