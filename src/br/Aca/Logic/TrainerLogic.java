package br.Aca.Logic;

import java.util.*;
import br.Aca.DB.*;
import br.Aca.Entity.*;
import br.Aca.Exception.*;

public class TrainerLogic {

	private TrainerDB tdb;
	private AcademiaLogic ac1;
	
	public TrainerLogic(Conexao cnx) {
		tdb = new TrainerDB(cnx);
		ac1 = new AcademiaLogic(cnx);
	}
	
	public boolean addTrainer(int codigo, String nome, java.sql.Date data_nasc, String sexo, int academia) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException, 
		EntityAlreadyExistException,
		InvalidFieldException 
	{

		List<String> camposInvalidos = new ArrayList<String>();
		boolean haCamposInvalidos = false;
		Academia aca = null;

		if(codigo < 1){
			haCamposInvalidos = true;
			camposInvalidos.add("Codigo");
		}
		
		if(nome.isEmpty() || nome.length() > 60){
			haCamposInvalidos = true;
			camposInvalidos.add("Nome");
		}

		if (haCamposInvalidos){
			throw new InvalidFieldException("Trainer", camposInvalidos);
		}			
		aca = ac1.getAcademia(academia);
		Trainer c = new Trainer(codigo, nome, data_nasc, sexo, aca);
		return tdb.addTrainer(c);
	}
	
	public Trainer getTrainer(int codigo) throws
		DataBaseGenericException,
		DataBaseNotConnectedException,
		EntityNotExistException 
	{

		return tdb.getTrainer(codigo);
		
	}
	
	public boolean updTrainer(int codigo, String nome, Date data_nasc, String sexo, int academia) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException, 
		EntityNotExistException,
		InvalidFieldException
	{

	List<String> camposInvalidos = new ArrayList<String>();
	boolean haCamposInvalidos = false;

	if(codigo < 1){
		haCamposInvalidos = true;
		camposInvalidos.add("Codigo");
	}
	
	if(nome.isEmpty() || nome.length() > 60){
		haCamposInvalidos = true;
		camposInvalidos.add("Nome");
	}

	if(endereco.isEmpty() || endereco.length() > 60){
		haCamposInvalidos = true;
		camposInvalidos.add("Endereco");
	}

	if (haCamposInvalidos){
		throw new InvalidFieldException("Trainer", camposInvalidos);
	}	

	if (haCamposInvalidos){
		throw new InvalidFieldException("Trainer", camposInvalidos);
	}	
		Trainer c = new Trainer(codigo, nome, data_nasc, sexo, academia);
		return tdb.updTrainer(c);	
	}
	
	public boolean delTrainer(int codigo, String nome, Date data_nasc, String sexo, int academia) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException, 
		EntityNotExistException 
	{
		Trainer c = new Trainer(codigo, nome, data_nasc, sexo, academia);
		return tdb.delTrainer(c);
	}
	
	public List<Trainer> getTrainers() throws
		DataBaseGenericException,
		DataBaseNotConnectedException, 
		EntityTableIsEmptyException
	{
		return tdb.getTrainers();
	}
	
	public List<Trainer> getTrainerPorNome(String nome) throws
		DataBaseGenericException,
		DataBaseNotConnectedException,
		EntityTableIsEmptyException
	{
		return tdb.getTrainerPorNome(nome);
	}
}