package br.Aca.Gui;

import javax.swing.JOptionPane;
import br.Aca.DB.Conexao;
import br.Aca.Exception.*;

public class AcademiaApp {

	//static final String DB_URL = "jdbc:mysql://localhost/academico";
	
	//PARA RODAR NO WINDOWS
	static final String DB_URL = "jdbc:mysql://localhost/academia?useTimezone=true&serverTimezone=UTC";
	
	// Método main inicia execução do aplicativo
	public static void main(String args[]){
	
		String dbUsuario, dbSenha;
		int qtdTentativas=0;
		boolean conectado=false;	
		
		Conexao cnx =  new Conexao();
		
		do {
			dbUsuario = "aluno"; //JOptionPane.showInputDialog("Usuário:");
			dbSenha = "aluno"; //JOptionPane.showInputDialog("Senha:");
			try {
				conectado = cnx.conecte(DB_URL, dbUsuario, dbSenha);
			} catch (DataBaseAlreadyConnectedException | 
					AccessDeniedForUserException | 
					DataBaseGenericException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), 
						"Falha na autenticação", JOptionPane.ERROR_MESSAGE);
			}	
			qtdTentativas ++;
		} while (!conectado && qtdTentativas < 3);
		
		if (conectado){
			// Criado o objeto pc do tipo Academico.
			Academia a = new Academia(cnx);
			// Exibindo a janela
			a.setVisible(true);
		}else{
			JOptionPane.showMessageDialog(null, "Você excedeu o número de tentativas!", 
					"Falha na autenticação", JOptionPane.ERROR_MESSAGE);		}
   } //Fim do método main
}//Fim da classe ProfessorApp
