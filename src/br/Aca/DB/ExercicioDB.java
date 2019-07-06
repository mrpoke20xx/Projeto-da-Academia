package br.Aca.DB;

import java.sql.*;
import java.util.*;

import br.Aca.Entity.*;
import br.Aca.Exception.DataBaseGenericException;
import br.Aca.Exception.DataBaseNotConnectedException;
import br.Aca.Exception.EntityAlreadyExistException;
import br.Aca.Exception.EntityNotExistException;
import br.Aca.Exception.EntityTableIsEmptyException;

public class ExercicioDB {

	private Conexao cnx;
	private ResultSet rs;
	private EquipamentoDB edb;

	public ExercicioDB(Conexao cnx){
		this.cnx = cnx;
		edb = new EquipamentoDB(this.cnx);
	}

	public boolean addExercicio(Exercicio e) throws 
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityAlreadyExistException
	{

		String strIncluir = "INSERT INTO exercicio ("
				+ "exe_codigo, exe_nome, exe_descricao, exe_equ_codigo) "
				+ "VALUES (" + e.getCodigo() 	+ ","
				+ "'" + e.getNome() 				+ "',"
				+ "'" +e.getDescricao() 				+ "',"
				+ "" + e.getEquipamento().getCodigo()	+ ");";

		try {
			getExercicio(e.getCodigo());
			throw new EntityAlreadyExistException("Exercicio (exe_codigo=" + e.getCodigo() + ")"); 
		} catch (EntityNotExistException ex) {
			return cnx.atualize(strIncluir) > 0;
		}

	}

	public Exercicio getExercicio(int exe_codigo) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityNotExistException
	{

		String strBusca = "SELECT exe_codigo, exe_nome, exe_descricao, exe_equ_codigo "
				+ " FROM exercicio "
				+ " WHERE exe_codigo = " + exe_codigo + ";";

		Exercicio exercicio = null;
		Equipamento equipamento = null;

		rs = cnx.consulte(strBusca);
		try{

			if (rs.next()){

				equipamento = edb.getEquipamento(rs.getInt(4));				

				exercicio = new Exercicio(rs.getInt(1), rs.getString(2), rs.getString(3), equipamento);

			}else {
				throw new EntityNotExistException("Exercicio (exe_codigo=" + exe_codigo + ")");
			}
		}catch(SQLException sqle){
			throw new DataBaseGenericException(sqle.getErrorCode(), 
					sqle.getMessage());
		}
		return exercicio;
	}
	// PAREI AQUI
	public boolean updExercicio(Exercicio e) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityNotExistException 
	{

		String strAtualizar = "UPDATE exercicio "
				+ " SET exe_nome = '" + e.getNome() 			+ "',"
				+ " exe_descricao = '" + e.getDescricao()					+ "', "
				+ " exe_aca_codigo = " + e.getEquipamento().getCodigo()	+ " "
				+ " WHERE exe_codigo = " + e.getCodigo() 	+ ";";

		getExercicio(e.getCodigo());
		return cnx.atualize(strAtualizar) > 0;
	}

	public boolean delExercicio(Exercicio e) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityNotExistException
	{

		String strDeletar = "DELETE FROM exercicio "
				+ " WHERE exe_codigo = " + e.getCodigo() + ";";

		getExercicio(e.getCodigo());
		return cnx.atualize(strDeletar) > 0;
	}

	public List<Exercicio> getExercicios() throws
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityTableIsEmptyException,
		EntityNotExistException
	{

		List<Exercicio> listaDeExercicios = new ArrayList<Exercicio>();

		String strBusca = "SELECT exe_codigo, exe_nome, exe_descricao, exe_equ_codigo "
				+ " FROM exercicio;";

		Exercicio exercicio;
		Equipamento equipamento;

		rs = cnx.consulte(strBusca);
		try{
			if (rs.next()) {
				rs.beforeFirst();			
				while(rs.next()){
	
					equipamento = edb.getEquipamento(rs.getInt(4));				
	
					exercicio = new Exercicio(rs.getInt(1), rs.getString(2), rs.getString(3), equipamento);
	
					listaDeExercicios.add(exercicio);
				}
			}else {
				throw new EntityTableIsEmptyException("Exercicio");
			}
		}catch(SQLException sqle){
			throw new DataBaseGenericException(sqle.getErrorCode(), 
					sqle.getMessage());
		}
		return listaDeExercicios;
	}

	public List<Exercicio> getExerciciosPorNome(String exe_nome) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException,
		EntityTableIsEmptyException,
		EntityNotExistException
	{

		List<Exercicio> listaDeExercicios = new ArrayList<Exercicio>();

		String strBusca = "SELECT exe_codigo, exe_nome, exe_descricao, exe_equ_codigo "
				+ " FROM exercicio "
				+ " WHERE exe_nome LIKE '%" + exe_nome + "%';";

		Exercicio exercicio;
		Equipamento equipamento;

		rs = cnx.consulte(strBusca);

		try{
			if (rs.next()) {
				rs.beforeFirst();			
				while(rs.next()){
					equipamento = edb.getEquipamento(rs.getInt(4));				
	
					exercicio = new Exercicio(rs.getInt(1), rs.getString(2), rs.getString(3), equipamento);
	
					listaDeExercicios.add(exercicio);
				}
			}else {
				throw new EntityTableIsEmptyException("Exercicio");
			}
		}catch(SQLException sqle){
			throw new DataBaseGenericException(sqle.getErrorCode(), 
					sqle.getMessage());
		}
		return listaDeExercicios;
	}

}