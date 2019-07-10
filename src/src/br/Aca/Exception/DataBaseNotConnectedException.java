package br.Aca.Exception;

public class DataBaseNotConnectedException extends Exception {

	public DataBaseNotConnectedException(String db){
		super("Banco de dados '"+ db + "' não está conectado!");
	}
}
