package br.Aca.Gui;

import javax.imageio.ImageIO;
import javax.swing.*; 					//importando classes do Swing
import java.awt.event.*; 				//importando classes de EVENTOS do AWT
import java.io.File;
import java.io.IOException;

import br.Aca.DB.*;
import br.Aca.Exception.*;

class Academia extends JFrame {

	private Conexao cnx = null;
	
	ConsultaCliente ccad;
	TrainerConsulta tc;
	TreinoConsulta toc;
	
	
	AcaoCliente actCliente = new AcaoCliente();
	AcaoTreino actTreino = new AcaoTreino();
	AcaoTrainer actTreiner = new AcaoTrainer();	
	AcaoSair actSair = new AcaoSair();	

	static final String imagesPath = new String("images/");
	
	JMenuBar mbOpcoes;
	JMenu mnCadastro;

	Academia(Conexao conexao){ // método construtor
		super("Sistema de Gerenciamento da Academia"); // chamando construtor da classe mãe
		setSize(800, 400);				// definindo dimensões da janela
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		cnx = conexao;
		
		ccad = new ConsultaCliente(this, cnx);
		tc = new TrainerConsulta(this, cnx);
		toc = new TreinoConsulta(this, cnx);
		
		mbOpcoes = new JMenuBar();
		
		mnCadastro = new JMenu("Cadastro");
		mnCadastro.setMnemonic(KeyEvent.VK_D);
		
		mnCadastro.add(actCliente);
		mnCadastro.add(actTreino);
		mnCadastro.add(actTreiner);
		mnCadastro.addSeparator();
		mnCadastro.add(actSair);
		
		mbOpcoes.add(mnCadastro);

		setJMenuBar(mbOpcoes);
		try {
			setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("src/Cadimia.png")))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	} //Fim do método construtor

	class AcaoCliente extends AbstractAction{

		AcaoCliente(){
			super("Cliente");
			putValue(MNEMONIC_KEY, KeyEvent.VK_C);
			putValue(SHORT_DESCRIPTION, 
					"Cadastrar Cliente");

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			ccad.setVisible(true);
			Academia.this.setVisible(false);
			
		}
	}
	
	class AcaoTrainer extends AbstractAction{

		AcaoTrainer(){
			super("Trainer");
			putValue(MNEMONIC_KEY, KeyEvent.VK_P);
			putValue(SHORT_DESCRIPTION, 
					"Gerenciar Trainers");

		}
		
		

		@Override
		public void actionPerformed(ActionEvent e) {

			tc.setVisible(true);
			Academia.this.setVisible(false);
			
		}
	}
	
	class AcaoTreino extends AbstractAction{

		AcaoTreino(){
			super("Treino");
			putValue(MNEMONIC_KEY, KeyEvent.VK_O);
			putValue(SHORT_DESCRIPTION, 
					"Gerenciar Treinos (conjuntos de exercícios)");

		}
		
		

		@Override
		public void actionPerformed(ActionEvent e) {

			toc.setVisible(true);
			Academia.this.setVisible(false);
			
		}
	}
	
	class AcaoSair extends AbstractAction{

		AcaoSair(){
			super("Sair");
			putValue(MNEMONIC_KEY, KeyEvent.VK_R);
			putValue(SHORT_DESCRIPTION, 
					"Sair da aplicação!");
//			putValue(SMALL_ICON, 
//					new ImageIcon(imagesPath+"general/Stop24.gif"));

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			try {
				cnx.desconecte();
			} catch (DataBaseNotConnectedException | DataBaseGenericException e1) {
				// OBSERVEM QUE FOI CRIADA A INSTANCIA e1, POIS J�? EXISTE e
				JOptionPane.showMessageDialog(null, e1.getMessage(), 
						"Falha na autenticação", JOptionPane.ERROR_MESSAGE);

			}
			System.exit(0);
		}
	}

}
