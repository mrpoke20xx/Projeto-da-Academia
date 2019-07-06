package br.Aca.Exception;

public class AccessDeniedForUserException extends Exception {

	public AccessDeniedForUserException(String usuario){
		super("Acesso negado para o usuário '"+ usuario + "'!");
	}
}
