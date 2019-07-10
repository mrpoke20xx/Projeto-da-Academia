package br.Aca.Exception;

import java.util.*;

public class InvalidFieldException extends Exception {

	public InvalidFieldException(String entidade, List<String> listaDeCampos){
		super("Campos inválidos para '"
				+ entidade + "' " 
				+ listaDeCampos.toString());
	}
}
