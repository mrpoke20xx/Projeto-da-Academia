package br.Aca.Exception;

public class EntityTableIsEmptyException extends Exception {

	public EntityTableIsEmptyException(String entidade){
		super("Tabela da entidade '" + entidade + "' está vazia!");
	}
}
