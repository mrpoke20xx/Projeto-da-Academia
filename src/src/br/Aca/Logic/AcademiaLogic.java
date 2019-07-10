package br.Aca.Logic;

import java.util.*;
import br.Aca.DB.*;
import br.Aca.Entity.*;
import br.Aca.Exception.*;

public class AcademiaLogic {

	private AcademiaDB adb;
	
	public AcademiaLogic(Conexao cnx) {
		adb = new AcademiaDB(cnx);
	}
	
	public boolean addAcademia(int codigo, String nome, String endereco, String fone) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException, 
		EntityAlreadyExistException,
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
			throw new InvalidFieldException("Academia", camposInvalidos);
		}			
		
		Academia c = new Academia(codigo, nome, endereco, fone);
		return adb.addAcademia(c);
		
	}
	
	public Academia getAcademia(int codigo) throws
		DataBaseGenericException,
		DataBaseNotConnectedException,
		EntityNotExistException 
	{

		return adb.getAcademia(codigo);
		
	}
	
	public boolean updAcademia(int codigo, String nome, String endereco, String fone) throws
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
		throw new InvalidFieldException("Academia", camposInvalidos);
	}	

	if (haCamposInvalidos){
		throw new InvalidFieldException("Academia", camposInvalidos);
	}	
		Academia c = new Academia(codigo, nome, endereco, fone);
		return adb.updAcademia(c);	
	}
	
	public boolean delAcademia(int codigo, String nome, String endereco, String fone) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException, 
		EntityNotExistException 
	{
		Academia c = new Academia(codigo, nome, endereco, fone);
		return adb.delAcademia(c);
	}
	
	public List<Academia> getAcademias() throws
		DataBaseGenericException,
		DataBaseNotConnectedException, 
		EntityTableIsEmptyException
	{
		return adb.getAcademias();
	}
	
	public List<Academia> getAcademiaPorNome(String nome) throws
		DataBaseGenericException,
		DataBaseNotConnectedException,
		EntityTableIsEmptyException
	{
		return adb.getAcademiaPorNome(nome);
	}
}