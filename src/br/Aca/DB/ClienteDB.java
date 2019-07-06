package br.Aca.DB;

import java.sql.*;
import java.util.*;

import br.Aca.Entity.*;
import br.Aca.Exception.DataBaseGenericException;
import br.Aca.Exception.DataBaseNotConnectedException;
import br.Aca.Exception.EntityAlreadyExistException;
import br.Aca.Exception.EntityNotExistException;
import br.Aca.Exception.EntityTableIsEmptyException;

public class ClienteDB {

	private Conexao cnx;
	private ResultSet rs;
	private TrainerDB tdb;

	public ClienteDB(Conexao cnx){
		this.cnx = cnx;
		tdb = new TrainerDB(this.cnx);
	}

	public boolean addCliente(Cliente c) throws 
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityAlreadyExistException
	{

		String strIncluir = "INSERT INTO cliente ("
				+ "cli_codigo, cli_nome, cli_endereco, cli_data_nasc, cli_sexo, cli_necessidade, cli_tra_cod) "
				+ "VALUES (" + c.getCodigo() 	+ ","
				+ "'" + c.getNome() 				+ "',"
				+ "'" + c.getEndereco() + "',"
				+ c.getDataNasc() 						+ ","
				+ "'" +c.getSexo() 						+ "',"
				+ "'" +c.getNecessidade() + "',"
				+ "" + c.getTrainer().getCodigo()	+ ");";

		try {
			getCliente(c.getCodigo());
			throw new EntityAlreadyExistException("Cliente (cli_codigo=" + c.getCodigo() + ")"); 
		} catch (EntityNotExistException e) {
			return cnx.atualize(strIncluir) > 0;
		}
	}

	public Cliente getCliente(int cli_codigo) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityNotExistException
	{
		String strBusca = "SELECT cli_codigo, cli_nome, cli_endereco, cli_data_nasc, cli_sexo, cli_necessidade, cli_tra_codigo "
				+ " FROM cliente "
				+ " WHERE cli_codigo = " + cli_codigo + ";";

		Cliente cliente = null;
		Trainer trainer = null;

		rs = cnx.consulte(strBusca);
		try{

			if (rs.next()){

				trainer = tdb.getTrainer(rs.getInt(6));				

				cliente = new Cliente(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(3), rs.getString(4), rs.getString(5), trainer);

			}else {
				throw new EntityNotExistException("Cliente (cli_codigo=" + cli_codigo + ")");
			}
		}catch(SQLException sqle){
			throw new DataBaseGenericException(sqle.getErrorCode(), 
					sqle.getMessage());
		}
		return cliente;
	}
	// PAREI AQUI
	public boolean updCliente(Cliente c) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityNotExistException 
	{

		String strAtualizar = "UPDATE cliente "
				+ " SET cli_nome = '" + c.getNome() 			+ "',"
				+ " cli_endereco = '" + c.getEndereco() + "',"
				+ " cli_data_nasc = " + c.getDataNasc()						+ ", "
				+ " cli_sexo = '" + c.getSexo()					+ "', "
				+ " cli_necessidade =" + c.getNecessidade() + "',"
				+ " cli_tra_cod = " + c.getTrainer().getCodigo()	+ " "
				+ " WHERE cli_codigo = " + c.getCodigo() 	+ ";";

		getCliente(c.getCodigo());
		return cnx.atualize(strAtualizar) > 0;
	}

	public boolean delCliente(Cliente c) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityNotExistException
	{

		String strDeletar = "DELETE FROM cliente "
				+ " WHERE cli_codigo = " + c.getCodigo() + ";";

		getCliente(c.getCodigo());
		return cnx.atualize(strDeletar) > 0;
	}

	public List<Cliente> getCLientes() throws
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityTableIsEmptyException,
		EntityNotExistException
	{

		List<Cliente> listaDeClientes = new ArrayList<Cliente>();

		String strBusca = "SELECT cli_codigo, cli_nome, cli_endereco, cli_data_nasc, cli_sexo, cli_necessidade, cli_tra_codigo"
				+ " FROM cliente;";

		Cliente cliente;
		Trainer trainer;

		rs = cnx.consulte(strBusca);
		try{
			if (rs.next()) {
				rs.beforeFirst();			
				while(rs.next()){
	
					trainer = tdb.getTrainer(rs.getInt(6));				
	
					cliente = new Cliente(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(3), rs.getString(4), rs.getString(5), trainer);
	
					listaDeClientes.add(cliente);
				}
			}else {
				throw new EntityTableIsEmptyException("Cliente");
			}
		}catch(SQLException sqle){
			throw new DataBaseGenericException(sqle.getErrorCode(), 
					sqle.getMessage());
		}
		return listaDeClientes;
	}

	public List<Cliente> getClientesPorNome(String cli_nome) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityTableIsEmptyException,
		EntityNotExistException
	{

		List<Cliente> listaDeClientes = new ArrayList<Cliente>();

		String strBusca = "SELECT cli_codigo, cli_nome, cli_endereco, cli_data_nasc, cli_sexo, cli_necessidade, cli_tra_codigo "
				+ " FROM cliente "
				+ " WHERE cli_nome LIKE '%" + cli_nome + "%';";

		Cliente cliente;
		Trainer trainer;

		rs = cnx.consulte(strBusca);

		try{
			if (rs.next()) {
				rs.beforeFirst();			
				while(rs.next()){
					trainer = tdb.getTrainer(rs.getInt(6));				
	
					cliente = new Cliente(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(3), rs.getString(4), rs.getString(5), trainer);
	
					listaDeClientes.add(cliente);
				}
			}else {
				throw new EntityTableIsEmptyException("Cliente");
			}
		}catch(SQLException sqle){
			throw new DataBaseGenericException(sqle.getErrorCode(), 
					sqle.getMessage());
		}
		return listaDeClientes;
	}

}