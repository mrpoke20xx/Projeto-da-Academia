package br.Aca.Logic;

import java.util.*;
import br.Aca.DB.*;
import br.Aca.Entity.*;
import br.Aca.Exception.*;
import java.sql.Date;

public class ExercicioLogic {

	private ExercicioDB edb;
	private EquipamentoLogic ac1;
	
	public ExercicioLogic(Conexao cnx) {
		edb = new ExercicioDB(cnx);
		ac1 = new EquipamentoLogic(cnx);
	}
	
	public boolean addExercicio(int codigo, String nome, String descricao, int equipamento) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException, 
		EntityAlreadyExistException,
		InvalidFieldException
	{
		List<String> camposInvalidos = new ArrayList<String>();
		boolean haCamposInvalidos = false;
		Equipamento equ = null;
		try {
			equ = ac1.getEquipamento(equipamento);
		} catch (EntityNotExistException e) {
			haCamposInvalidos = true;
			camposInvalidos.add("Equipamento");
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
			throw new InvalidFieldException("Exercicio", camposInvalidos);
		}			
		
		Exercicio c = new Exercicio(codigo, nome, descricao, equ);
		return edb.addExercicio(c);
	}
	
	public Exercicio getExercicio(int codigo) throws
		DataBaseGenericException,
		DataBaseNotConnectedException,
		EntityNotExistException 
	{

		return edb.getExercicio(codigo);
		
	}
	
	public boolean updExercicio(int codigo, String nome, String descricao, int equipamento) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException, 
		EntityNotExistException,
		InvalidFieldException
	{
		List<String> camposInvalidos = new ArrayList<String>();
		boolean haCamposInvalidos = false;
		Equipamento equ = null;
		try {
			equ = ac1.getEquipamento(equipamento);
		} catch (EntityNotExistException e) {
			haCamposInvalidos = true;
			camposInvalidos.add("Equipamento");
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
			throw new InvalidFieldException("Exercicio", camposInvalidos);
		}			
		
		Exercicio c = new Exercicio(codigo, nome, descricao, equ);
		return edb.updExercicio(c);
	}
	
	public boolean delExercicio(int codigo, String nome, String descricao, int equipamento) throws
		DataBaseGenericException, 
		DataBaseNotConnectedException, 
		EntityNotExistException 
	{
		Equipamento equ = ac1.getEquipamento(equipamento);
		Exercicio c = new Exercicio(codigo, nome, descricao, equ);
		return edb.delExercicio(c);
	}
	
	public List<Exercicio> getExercicios() throws
		DataBaseGenericException,
		DataBaseNotConnectedException, 
		EntityTableIsEmptyException, EntityNotExistException
	{
		return edb.getExercicios();
	}
	
	public List<Exercicio> getExercicioPorNome(String nome) throws
		DataBaseGenericException,
		DataBaseNotConnectedException,
		EntityTableIsEmptyException, EntityNotExistException
	{
		return edb.getExerciciosPorNome(nome);
	}
}