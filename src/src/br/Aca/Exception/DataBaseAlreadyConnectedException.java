package br.Aca.Exception;

public class DataBaseAlreadyConnectedException extends Exception {

	public DataBaseAlreadyConnectedException(String db){
		super("Banco de dados '"+ db + "' já está conectado!");
	}
}
