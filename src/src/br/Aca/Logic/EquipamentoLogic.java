package br.Aca.Logic;

import java.util.*;
import br.Aca.DB.*;
import br.Aca.Entity.*;
import br.Aca.Exception.*;
import java.sql.Date;

public class EquipamentoLogic {

	private EquipamentoDB edb;
	private AcademiaLogic ac1;
	
	public EquipamentoLogic(Conexao cnx) {
		edb = new EquipamentoDB(cnx);
		ac1 = new AcademiaLogic(cnx);
	}
	
	public boolean addEquipamento(int codigo, String nome, double pesomax, String descricao, int academia) throws
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
		if(descricao.isEmpty()){
			haCamposInvalidos = true;
			camposInvalidos.add("Descricao");
		}
		if (haCamposInvalidos){
			throw new InvalidFieldException("Equipamento", camposInvalidos);
		}			
		
		Equipamento c = new Equipamento(codigo, nome, pesomax, descricao, aca);
		return edb.addEquipamento(c);
	}
	
	public Equipamento getEquipamento(int codigo) throws
		DataBaseGenericException,
		DataBaseNotConnectedException,
		EntityNotExistException 
	{

		return edb.getEquipamento(codigo);
		
	}
	
	public boolean updEquipamento(int codigo, String nome, double pesomax, String descricao, int academia) throws
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
		if(descricao.isEmpty()){
			haCamposInvalidos = true;
			camposInvalidos.add("Descricao");
		}
		if (haCamposInvalidos){
			throw new InvalidFieldException("Equipamento", camposInvalidos);
		}			
		
		Equipamento c = new Equipamento(codigo, nome, pesomax, descricao, aca);
		return edb.updEquipamento(c);
	}
	
	public boolean delEquipamento(int codigo, String nome, double pesomax, String descricao, int academia) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException, 
		EntityNotExistException 
	{
		Academia aca = ac1.getAcademia(academia);
		Equipamento c = new Equipamento(codigo, nome, pesomax, descricao, aca);
		return edb.delEquipamento(c);
	}
	
	public List<Equipamento> getEquipamentos() throws
		DataBaseGenericException,
		DataBaseNotConnectedException, 
		EntityTableIsEmptyException, EntityNotExistException
	{
		return edb.getEquipamentos();
	}
	
	public List<Equipamento> getEquipamentoPorNome(String nome) throws
		DataBaseGenericException,
		DataBaseNotConnectedException,
		EntityTableIsEmptyException, EntityNotExistException
	{
		return edb.getEquipamentosPorNome(nome);
	}
}