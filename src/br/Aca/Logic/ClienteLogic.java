package br.Aca.Logic;

import java.util.*;
import br.Aca.DB.*;
import br.Aca.Entity.*;
import br.Aca.Exception.*;
import java.util.Date;

public class ClienteLogic {

	private ClienteDB cdb;
	private TrainerLogic ac1;
	
	public ClienteLogic(Conexao cnx) {
		cdb = new ClienteDB(cnx);
		ac1 = new TrainerLogic(cnx);
	}
	
	public boolean addCliente(int codigo, String nome, String endereco, Date data_nasc, char sexo, String necessidade, int trainer) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException, 
		EntityAlreadyExistException,
		InvalidFieldException
	{
		List<String> camposInvalidos = new ArrayList<String>();
		boolean haCamposInvalidos = false;
		String newsexo = String.valueOf(sexo);
		
		if(trainer < 1) {
			haCamposInvalidos = true;
			camposInvalidos.add("Trainer");
		}	
		if(codigo < 1){
			haCamposInvalidos = true;
			camposInvalidos.add("Codigo");
		}
		if(nome.isEmpty() || nome.length() > 60){
			haCamposInvalidos = true;
			camposInvalidos.add("Nome");
		}
		if(newsexo.isEmpty()){
			haCamposInvalidos = true;
			camposInvalidos.add("Sexo");
		}
		if (haCamposInvalidos){
			throw new InvalidFieldException("Cliente", camposInvalidos);
		}
		
		//int codigo, String nome, String endereco, Date dataNasc, String sexo, String necessidade, int trainer
		Cliente c = new Cliente(codigo, nome, endereco, data_nasc, sexo, necessidade, trainer);
		return cdb.addCliente(c);
	}
	
	public Cliente getCliente(int codigo) throws
		DataBaseGenericException,
		DataBaseNotConnectedException,
		EntityNotExistException 
	{

		return cdb.getCliente(codigo);
		
	}
	
	public boolean updCliente(int codigo, String nome, String endereco, Date data_nasc, char sexo, String necessidade, int trainer) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException, 
		EntityNotExistException,
		InvalidFieldException
	{
		List<String> camposInvalidos = new ArrayList<String>();
		boolean haCamposInvalidos = false;
		String newsexo = String.valueOf(sexo);
		if(trainer < 1) {
			haCamposInvalidos = true;
			camposInvalidos.add("Trainer");
		}
		if(codigo < 1){
			haCamposInvalidos = true;
			camposInvalidos.add("Codigo");
		}
		if(nome.isEmpty() || nome.length() > 60){
			haCamposInvalidos = true;
			camposInvalidos.add("Nome");
		}
		if(newsexo.isEmpty()){
		haCamposInvalidos = true;
		camposInvalidos.add("Sexo");
		}
		if (haCamposInvalidos){
			throw new InvalidFieldException("Cliente", camposInvalidos);
		}			
		//		int codigo, String nome, String endereco, Date data_nasc, char sexo, String necessidade, int trainer	
		Cliente c = new Cliente(codigo, nome, endereco, data_nasc, sexo, necessidade, trainer);
		return cdb.updCliente(c);
	}
	
	public boolean delCliente(int codigo, String nome, String endereco, Date data_nasc, char sexo, String necessidade, int trainer) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException, 
		EntityNotExistException 
	{
		Trainer tra = ac1.getTrainer(trainer);
		Cliente c = new Cliente(codigo, nome, endereco, data_nasc, sexo, necessidade, trainer);
		return cdb.delCliente(c);
	}
	
	public List<Cliente> getClientes() throws
		DataBaseGenericException,
		DataBaseNotConnectedException, 
		EntityTableIsEmptyException, EntityNotExistException
	{
		return cdb.getClientes();
	}
	
	public List<Cliente> getClientePorNome(String nome) throws
		DataBaseGenericException,
		DataBaseNotConnectedException,
		EntityTableIsEmptyException, EntityNotExistException
	{
		return cdb.getClientesPorNome(nome);
	}
}