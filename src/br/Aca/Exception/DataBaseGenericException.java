package br.Aca.Exception;

public class DataBaseGenericException extends Exception {

	public DataBaseGenericException(int codigo, String mensagem){
		super("Exceção genérica do servidor: \n\t[" 
				+ codigo+ "] -> "
				+ mensagem+"!");
	}
}
