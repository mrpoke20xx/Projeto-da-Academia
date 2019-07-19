package br.Aca.Logic;

import java.util.*;
import br.Aca.DB.*;
import br.Aca.Entity.*;
import br.Aca.Exception.*;
import java.sql.Date;

public class TreinoLogic {

	private TreinoDB edb;
	private ExercicioLogic ec1;
	private ClienteLogic cl2;
	
	public TreinoLogic(Conexao cnx) {
		edb = new TreinoDB(cnx);
		ec1 = new ExercicioLogic(cnx);
		cl2 = new ClienteLogic(cnx);
	}
	
	public boolean addTreino(int codigo, Date vencimento, int cliente, int exercicio) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException, 
		EntityAlreadyExistException,
		InvalidFieldException
	{
		List<String> camposInvalidos = new ArrayList<String>();
		boolean haCamposInvalidos = false;
		Exercicio exe = null;
		Cliente cli = null;
		try {
			exe = ec1.getExercicio(exercicio);
		} catch (EntityNotExistException e) {
			haCamposInvalidos = true;
			camposInvalidos.add("Exercicio");
		}	
		try {
			cli = cl2.getCliente(cliente);
		} catch (EntityNotExistException e) {
			haCamposInvalidos = true;
			camposInvalidos.add("Cliente");
		}	
		if(codigo < 1){
			haCamposInvalidos = true;
			camposInvalidos.add("Codigo");
		}
		if (haCamposInvalidos){
			throw new InvalidFieldException("Treino", camposInvalidos);
		}			
		
		Treino c = new Treino(codigo, vencimento, cli, exe);
		return edb.addTreino(c);
	}
	
	public Treino getTreino(int codigo) throws
		DataBaseGenericException,
		DataBaseNotConnectedException,
		EntityNotExistException 
	{

		return edb.getTreino(codigo);
		
	}
	
	public boolean updTreino(int codigo, Date vencimento, int cliente, int exercicio) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException, 
		EntityNotExistException,
		InvalidFieldException
	{
		List<String> camposInvalidos = new ArrayList<String>();
		boolean haCamposInvalidos = false;
		Exercicio exe = null;
		Cliente cli = null;
		try {
			exe = ec1.getExercicio(exercicio);
		} catch (EntityNotExistException e) {
			haCamposInvalidos = true;
			camposInvalidos.add("Exercicio");
		}	
		try {
			cli = cl2.getCliente(cliente);
		} catch (EntityNotExistException e) {
			haCamposInvalidos = true;
			camposInvalidos.add("Cliente");
		}	
		if(codigo < 1){
			haCamposInvalidos = true;
			camposInvalidos.add("Codigo");
		}
		if (haCamposInvalidos){
			throw new InvalidFieldException("Treino", camposInvalidos);
		}			
		
		Treino c = new Treino(codigo, vencimento, cli, exe);
		return edb.updTreino(c);
	}
	
	public boolean delTreino(int codigo, Date vencimento, int cliente, int exercicio) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException, 
		EntityNotExistException 
	{
		Exercicio exe = ec1.getExercicio(exercicio);
		Cliente cli = cl2.getCliente(cliente);
		Treino c = new Treino(codigo, vencimento, cli, exe);
		return edb.delTreino(c);
	}
	
	public List<Treino> getTreinos() throws
		DataBaseGenericException,
		DataBaseNotConnectedException, 
		EntityTableIsEmptyException, EntityNotExistException
	{
		return edb.getTreinos();
	}
}