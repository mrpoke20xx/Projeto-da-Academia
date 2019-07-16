package br.Aca.Gui;

import javax.swing.*; 					//importando classes do Swing

import br.Aca.DB.Conexao;
import br.Aca.Entity.Trainer;
import br.Aca.Logic.TrainerLogic;
import br.Aca.Exception.*;

import java.awt.*; 						//importando classes do AWT
import java.awt.event.*; 				//importando classes de EVENTOS do AWT
import java.sql.*;						//importando classes do JDBC
import java.util.List;						//importando classes do JDBC
import java.util.ArrayList;						//importando classes do JDBC

class TrainerConsulta extends JFrame {

	private Conexao cnx = null;
	Academia pai;
	TrainerLogic tl;

	TrainerCadastro trainerCadastro;

	JTable tblQuery;
	JPanel pSuperior, pControles, pBotoes, pOperacoes, pRotulos, pChaves;
	JComboBox cmbChaves;
	JTextField tfValor;
	JButton btBuscar, btSair, btIncluir, btEditar, btExcluir;

	AcaoBuscar actBuscar = new AcaoBuscar();
	AcaoIncluir actIncluir = new AcaoIncluir();
	AcaoEditar actEditar = new AcaoEditar();	
	AcaoExcluir actExcluir = new AcaoExcluir();	
	AcaoSair actSair = new AcaoSair();	

	static final String imagesPath = new String("images/");	

	TrainerConsulta(JFrame framePai, Conexao conexao){ // método construtor
		super("Consulta de Trainer"); // chamando construtor da classe mãe
		setSize(800, 400);				// definindo dimensões da janela
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		pai = (Academia) framePai;		
		cnx = conexao;
		tl = new TrainerLogic(cnx);

		trainerCadastro = new TrainerCadastro(this, cnx);

		tblQuery = new JTable(0,0);
		tblQuery.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblQuery.addMouseListener(new HabilitarEdicaoExclusao());

		pRotulos = new JPanel(new GridLayout(2,1,5,5));
		pRotulos.add(new JLabel("Buscar por"));
		pRotulos.add(new JLabel("Valor"));

		cmbChaves = new JComboBox(new String[] {"Código", "Nome"});
		tfValor = new JTextField();

		pChaves = new JPanel(new GridLayout(2,1,5,5));
		pChaves.add(cmbChaves);
		pChaves.add(tfValor);

		pControles = new JPanel(new BorderLayout(5,5));
		pControles.add(pRotulos, BorderLayout.WEST);
		pControles.add(pChaves);

		btBuscar = new JButton(actBuscar);
		btSair = new JButton(actSair);
		btIncluir = new JButton(actIncluir);
		btEditar = new JButton(actEditar);
		btEditar.setEnabled(false);
		btExcluir = new JButton(actExcluir);
		btExcluir.setEnabled(false);

		pOperacoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		pOperacoes.add(btIncluir);
		pOperacoes.add(btEditar);
		pOperacoes.add(btExcluir);

		pBotoes = new JPanel(new GridLayout(2,1));
		pBotoes.add(btBuscar);
		pBotoes.add(btSair);

		pSuperior = new JPanel(new BorderLayout());
		pSuperior.add(pBotoes, BorderLayout.EAST);
		pSuperior.add(pControles);

		add(pSuperior, BorderLayout.NORTH);
		add(new JScrollPane(tblQuery));
		add(pOperacoes, BorderLayout.SOUTH);		

	} //Fim do método construtor

	class AcaoBuscar extends AbstractAction{

		AcaoBuscar(){
			super("Buscar");
			putValue(MNEMONIC_KEY, KeyEvent.VK_B);
			putValue(SHORT_DESCRIPTION, 
					"Buscar registros de Personal Trainers!");
			putValue(SMALL_ICON, 
					new ImageIcon(imagesPath+"general/Search24.gif"));

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			buscar();

		}

	}

	class AcaoIncluir extends AbstractAction{

		AcaoIncluir(){
			super("Incluir");
			putValue(MNEMONIC_KEY, KeyEvent.VK_I);
			putValue(SHORT_DESCRIPTION, 
					"Incluir registro de Centro!");
			putValue(SMALL_ICON, 
					new ImageIcon(imagesPath+"general/New24.gif"));

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			trainerCadastro.incluir();

		}

	}

	class AcaoEditar extends AbstractAction{

		AcaoEditar(){
			super("Editar");
			putValue(MNEMONIC_KEY, KeyEvent.VK_E);
			putValue(SHORT_DESCRIPTION, 
					"Editar registro de Centro!");
			putValue(SMALL_ICON, 
					new ImageIcon(imagesPath+"general/Edit24.gif"));

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			int codigo;
			codigo = (int) tblQuery.getValueAt(tblQuery.getSelectedRow(), 0);
			trainerCadastro.editar(codigo);

		}

	}

	class AcaoExcluir extends AbstractAction{

		AcaoExcluir(){
			super("Excluir");
			putValue(MNEMONIC_KEY, KeyEvent.VK_X);
			putValue(SHORT_DESCRIPTION, 
					"Excluir registro de Centro!");
			putValue(SMALL_ICON, 
					new ImageIcon(imagesPath+"general/Delete24.gif"));

		}

		// PATRICAMENTE IGUAL AO DA AcaoEditar
		@Override
		public void actionPerformed(ActionEvent e) {

			int codigo;
			codigo = (int) tblQuery.getValueAt(tblQuery.getSelectedRow(), 0);
			trainerCadastro.excluir(codigo);

		}

	}	

	class AcaoSair extends AbstractAction{

		AcaoSair(){
			super("Sair");
			putValue(MNEMONIC_KEY, KeyEvent.VK_R);
			putValue(SHORT_DESCRIPTION, 
					"Fecha consulta de Trainers!");
			putValue(SMALL_ICON, 
					new ImageIcon(imagesPath+"general/Stop24.gif"));

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			TrainerConsulta.this.setVisible(false);
			pai.setVisible(true);

		}

	}

	public void buscar() {

		List<Trainer> trainer = new ArrayList<Trainer>();
		int codigo;

		try {

			if(tfValor.getText().equals("")) {
				trainer = tl.getTrainers();
			}else{
				switch (cmbChaves.getSelectedIndex()) {
				case 0:
					codigo = Integer.parseInt(tfValor.getText());
					trainer.add(tl.getTrainer(codigo));
					break;
				case 1:
					trainer = tl.getTrainerPorNome(tfValor.getText());
					break;
				}

			}

			tblQuery.setModel(new TrainerTableModel(trainer));
			btEditar.setEnabled(false);
			btExcluir.setEnabled(false);

		} catch (DataBaseGenericException | 
				DataBaseNotConnectedException | 
				EntityTableIsEmptyException | 
				EntityNotExistException e) 
		{
			JOptionPane.showMessageDialog(this, e.getMessage(), 
					"Consulta de Trainer", JOptionPane.ERROR_MESSAGE);
		}

	}

	class HabilitarEdicaoExclusao extends MouseAdapter {

		public void mousePressed(MouseEvent e) {
			if (tblQuery.getSelectedRow() >= 0) {
				btEditar.setEnabled(true);
				btExcluir.setEnabled(true);
			}else {
				btEditar.setEnabled(false);
				btExcluir.setEnabled(false);
			}

		}

	}	

}//Fim da classe ProfessorConsulta
