package br.Aca.Exception;

public class EntityAlreadyExistException extends Exception {

	public EntityAlreadyExistException(String entidade){
		super("Entidade já existe: " + entidade);
	}
}
