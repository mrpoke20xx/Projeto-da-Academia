package br.Aca.DB;

import java.sql.*;
import java.util.*;

import br.Aca.Entity.Academia;
import br.Aca.Exception.DataBaseGenericException;
import br.Aca.Exception.DataBaseNotConnectedException;
import br.Aca.Exception.EntityAlreadyExistException;
import br.Aca.Exception.EntityNotExistException;
import br.Aca.Exception.EntityTableIsEmptyException;

public class AcademiaDB {
	
	private Conexao cnx;
	private ResultSet rs;
	
	public AcademiaDB(Conexao cnx) {
		this.cnx = cnx;
	}

	public boolean addCentro(Academia c) throws 
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityAlreadyExistException
	{

		String strInsercao = 
				"INSERT INTO academia (codigo, nome, endereco, fone) "
				+ "VALUES ('" + c.getCodigo() + "', "
				+ "'" + c.getNome() + "',"
				+ "'" + c.getEndereco() + "',"
				+ "'" + c.getFone() + "');";

		try {
			getAcademia(c.getCodigo());
			throw new EntityAlreadyExistException("Academia (codigo='" + c.getCodigo() + "')"); 
		} catch (EntityNotExistException e) {
			return cnx.atualize(strInsercao) > 0;
		}
		
	}
	
	public Academia getAcademia (int codigo) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityNotExistException
	{

		Academia c = null;
		
		String strBusca = "SELECT codigo, nome, endereco, fone"
				+ "FROM academia "
				+ "WHERE codigo = '" + codigo + "';";
		
		rs = cnx.consulte(strBusca);
		
		try {
			if (rs.next()) {
				c = new Academia(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)); 
			}else {
				throw new EntityNotExistException("Academia (codigo='" + codigo + "')");
			}
		} catch (SQLException sqle) {
			throw new DataBaseGenericException(sqle.getErrorCode(), 
					sqle.getMessage());
		}
		
		return c;
	}
	
	public boolean updAcademia(Academia c) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityNotExistException
	{
		
		String strAtualizao = 
				"UPDATE academia "
				+ "SET "
				+ "nome = '" + c.getNome() + "',"
				+ "endereco = '" + c.getEndereco() + "',"
				+ "fone ='" + c.getFone() + "',"
				+ "WHERE codigo = '" + c.getCodigo() + "';";

		getAcademia(c.getCodigo());
		return cnx.atualize(strAtualizao) > 0;
		
	}
	
	public boolean delAcademia(Academia c) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException, 
		EntityNotExistException 
	{
		
		String strDelecao = 
				"DELETE FROM academia "
				+ "WHERE codigo = '" + c.getCodigo() + "';";
		
		getAcademia(c.getCodigo());		
		return cnx.atualize(strDelecao) > 0;
		
	}		
	
	public List<Academia> getAcademia () throws
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityTableIsEmptyException
	{

		Academia c = null;
		List<Academia> academia = new ArrayList<Academia>();
		
		String strBusca = "SELECT codigo, nome, endereco, fone "
				+ "FROM academia;";
		
		rs = cnx.consulte(strBusca);
		
		try {
			if (rs.next()) {
				rs.beforeFirst();
				while (rs.next()) {
					c = new Academia(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
					academia.add(c);
				}
			}else {
				throw new EntityTableIsEmptyException("Academia");
			}
		} catch (SQLException sqle) {
			throw new DataBaseGenericException(sqle.getErrorCode(), 
					sqle.getMessage());
		}
		
		return academia;
	}	
	
	public List<Academia> getAcademiaPorNome (String nome) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityTableIsEmptyException
	{

		Academia c = null;
		List<Academia> academia = new ArrayList<Academia>();
		
		String strBusca = "SELECT codigo, nome, endereco, fone "
				+ "FROM academia "
				+ "WHERE nome LIKE '%" + nome + "%';";
		
		rs = cnx.consulte(strBusca);
		
		try {
			if (rs.next()) {
				rs.beforeFirst();
				while (rs.next()) {
					c = new Academia(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
					academia.add(c);
				}
			}else {
				throw new EntityTableIsEmptyException("Academia");
			}
		} catch (SQLException sqle) {
			System.out.printf("Erro # %d (%s)\n", 
					sqle.getErrorCode(), 
					sqle.getMessage());
		}
		
		return academia;
	}	

}