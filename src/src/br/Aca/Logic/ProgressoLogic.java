package br.Aca.Logic;

import java.util.*;
import br.Aca.DB.*;
import br.Aca.Entity.*;
import br.Aca.Exception.*;
import java.sql.Date;

public class ProgressoLogic {

	private ProgressoDB pdb;
	private ExercicioLogic ec1;
	private ClienteLogic cl2;
	
	public ProgressoLogic(Conexao cnx) {
		pdb = new ProgressoDB(cnx);
		ec1 = new ExercicioLogic(cnx);
		cl2 = new ClienteLogic(cnx);
	}
	
	public boolean addProgresso(int cliente, Date data, double massa, double altura) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException, 
		EntityAlreadyExistException,
		InvalidFieldException
	{
		List<String> camposInvalidos = new ArrayList<String>();
		boolean haCamposInvalidos = false;
		Cliente cli = null;
		try {
			cli = cl2.getCliente(cliente);
		} catch (EntityNotExistException e) {
			haCamposInvalidos = true;
			camposInvalidos.add("Cliente");
		}	
		if(cliente < 1){
			haCamposInvalidos = true;
			camposInvalidos.add("Codigo");
		}
		if (haCamposInvalidos){
			throw new InvalidFieldException("Progresso", camposInvalidos);
		}			
		
		Progresso c = new Progresso(cli, data, massa, altura);
		return pdb.addProgresso(c);
	}
	
	public Progresso getProgresso(int codigo) throws
		DataBaseGenericException,
		DataBaseNotConnectedException,
		EntityNotExistException 
	{

		return pdb.getProgresso(codigo);
		
	}
	
	public boolean updProgresso(int cliente, Date data, double massa, double altura) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException, 
		EntityNotExistException,
		InvalidFieldException
	{
		List<String> camposInvalidos = new ArrayList<String>();
		boolean haCamposInvalidos = false;
		Cliente cli = null;	
		try {
			cli = cl2.getCliente(cliente);
		} catch (EntityNotExistException e) {
			haCamposInvalidos = true;
			camposInvalidos.add("Cliente");
		}	
		if(cliente < 1){
			haCamposInvalidos = true;
			camposInvalidos.add("Codigo");
		}
		if (haCamposInvalidos){
			throw new InvalidFieldException("Progresso", camposInvalidos);
		}			
		
		Progresso c = new Progresso(cli, data, massa, altura);
		return pdb.updProgresso(c);
	}
	
	public boolean delProgresso(int cliente, Date data, double massa, double altura) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException, 
		EntityNotExistException 
	{
		Cliente cli = cl2.getCliente(cliente);
		Progresso c = new Progresso(cli, data, massa, altura);
		return pdb.delProgresso(c);
	}
	
	public List<Progresso> getProgressos() throws
		DataBaseGenericException,
		DataBaseNotConnectedException, 
		EntityTableIsEmptyException, EntityNotExistException
	{
		return pdb.getProgressos();
	}
}