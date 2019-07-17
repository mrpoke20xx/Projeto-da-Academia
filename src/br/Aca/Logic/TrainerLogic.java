package br.Aca.Logic;

import java.util.*;
import br.Aca.DB.*;
import br.Aca.Entity.*;
import br.Aca.Exception.*;
import java.util.Date;

public class TrainerLogic {

	private TrainerDB tdb;
	private AcademiaLogic ac1;
	
	public TrainerLogic(Conexao cnx) {
		tdb = new TrainerDB(cnx);
		ac1 = new AcademiaLogic(cnx);
	}
	
	public boolean addTrainer(int codigo, String nome, Date data_nasc, String sexo, int academia) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException, 
		EntityAlreadyExistException,
		InvalidFieldException
	{
		List<String> camposInvalidos = new ArrayList<String>();
		boolean haCamposInvalidos = false;
		Academia aca = null;
		try {
			aca = ac1.getAcademia(academia);
		} catch (EntityNotExistException e) {
			haCamposInvalidos = true;
			camposInvalidos.add("Academia");
		}	
		if(codigo < 1){
			haCamposInvalidos = true;
			camposInvalidos.add("Codigo");
		}
		if(nome.isEmpty() || nome.length() > 60){
			haCamposInvalidos = true;
			camposInvalidos.add("Nome");
		}
		if(sexo.isEmpty()){
			haCamposInvalidos = true;
			camposInvalidos.add("Sexo");
		}
		if (haCamposInvalidos){
			throw new InvalidFieldException("Trainer", camposInvalidos);
		}			
		
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
		Academia aca = null;
		try {
			aca = ac1.getAcademia(academia);
		} catch (EntityNotExistException e) {
			haCamposInvalidos = true;
			camposInvalidos.add("Academia");
		}	
		if(codigo < 1){
			haCamposInvalidos = true;
			camposInvalidos.add("Codigo");
		}
		if(nome.isEmpty() || nome.length() > 60){
			haCamposInvalidos = true;
			camposInvalidos.add("Nome");
		}
		if(sexo.isEmpty()){
			haCamposInvalidos = true;
			camposInvalidos.add("Sexo");
		}
		if (haCamposInvalidos){
			throw new InvalidFieldException("Trainer", camposInvalidos);
		}			
		
		Trainer c = new Trainer(codigo, nome, data_nasc, sexo, aca);
		return tdb.updTrainer(c);
	}
	
	public boolean delTrainer(int codigo, String nome, Date data_nasc, String sexo, int academia) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException, 
		EntityNotExistException 
	{
		Academia aca = ac1.getAcademia(academia);
		Trainer c = new Trainer(codigo, nome, data_nasc, sexo, aca);
		return tdb.delTrainer(c);
	}
	
	public List<Trainer> getTrainers() throws
		DataBaseGenericException,
		DataBaseNotConnectedException, 
		EntityTableIsEmptyException, EntityNotExistException
	{
		return tdb.getTrainers();
	}
	
	public List<Trainer> getTrainerPorNome(String nome) throws
		DataBaseGenericException,
		DataBaseNotConnectedException,
		EntityTableIsEmptyException, EntityNotExistException
	{
		return tdb.getTrainersPorNome(nome);
	}
}