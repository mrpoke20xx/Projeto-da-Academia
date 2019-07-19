package br.Aca.Gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import br.Aca.DB.Conexao;
import br.Aca.Entity.Treino;
import br.Aca.Logic.TreinoLogic;
import br.Aca.Exception.*;



class TreinoConsulta extends JFrame {

	private Conexao cnx = null;
	
	Academia pai;
	TreinoLogic tl;

	TreinoCadastro treinoCadastro;

	JTable tblQuery;
	JPanel pnlSuperior, pnlControles, pnlBotoes, pnlOperacoes, pnlRotulos, pnlChaves;
	JComboBox cmbChaves;
	JTextField fldValor;
	JButton btnBuscar, btnSair, btnIncluir, btnEditar, btnExcluir;

	AcaoBuscar actBuscar = new AcaoBuscar();
	AcaoIncluir actIncluir = new AcaoIncluir();
	AcaoEditar actEditar = new AcaoEditar();	
	AcaoExcluir actExcluir = new AcaoExcluir();	
	AcaoSair actSair = new AcaoSair();	

	static final String imagesPath = new String("images/");	

	TreinoConsulta(JFrame framePai, Conexao conexao){ // metodo construtor
		super("Consulta de Treino"); // chamando construtor da classe mae
		setSize(800, 400);				// definindo dimensoes da janela
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		pai = (Academia) framePai;		
		cnx = conexao;
		tl = new TreinoLogic(cnx);

		treinoCadastro = new TreinoCadastro(this, cnx);

		tblQuery = new JTable(0,0);
		tblQuery.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblQuery.addMouseListener(new HabilitarEdicaoExclusao());

		pnlRotulos = new JPanel(new GridLayout(2,1,5,5));
		pnlRotulos.add(new JLabel("Buscar por"));
		pnlRotulos.add(new JLabel("Valor"));

		cmbChaves = new JComboBox(new String[] {"Codigo", "Exercicio"});
		fldValor = new JTextField();

		pnlChaves = new JPanel(new GridLayout(2,1,5,5));
		pnlChaves.add(cmbChaves);
		pnlChaves.add(fldValor);

		pnlControles = new JPanel(new BorderLayout(5,5));
		pnlControles.add(pnlRotulos, BorderLayout.WEST);
		pnlControles.add(pnlChaves);

		btnBuscar = new JButton(actBuscar);
		btnSair = new JButton(actSair);
		btnIncluir = new JButton(actIncluir);
		btnEditar = new JButton(actEditar);
		btnEditar.setEnabled(false);
		btnExcluir = new JButton(actExcluir);
		btnExcluir.setEnabled(false);

		pnlOperacoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		pnlOperacoes.add(btnIncluir);
		pnlOperacoes.add(btnEditar);
		pnlOperacoes.add(btnExcluir);

		pnlBotoes = new JPanel(new GridLayout(2,1));
		pnlBotoes.add(btnBuscar);
		pnlBotoes.add(btnSair);

		pnlSuperior = new JPanel(new BorderLayout());
		pnlSuperior.add(pnlBotoes, BorderLayout.EAST);
		pnlSuperior.add(pnlControles);

		add(pnlSuperior, BorderLayout.NORTH);
		add(new JScrollPane(tblQuery));
		add(pnlOperacoes, BorderLayout.SOUTH);		

	} //Fim do metodo construtor

	class AcaoBuscar extends AbstractAction{

		AcaoBuscar(){
			super("Buscar");
			putValue(MNEMONIC_KEY, KeyEvent.VK_B);
			putValue(SHORT_DESCRIPTION, 
					"Buscar registros de treinos!");
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
					"Incluir registro de treino!");
			putValue(SMALL_ICON, 
					new ImageIcon(imagesPath+"general/New24.gif"));

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			treinoCadastro.incluir();

		}

	}

	class AcaoEditar extends AbstractAction{

		AcaoEditar(){
			super("Editar");
			putValue(MNEMONIC_KEY, KeyEvent.VK_E);
			putValue(SHORT_DESCRIPTION, 
					"Editar registro de treino!");
			putValue(SMALL_ICON, 
					new ImageIcon(imagesPath+"general/Edit24.gif"));

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			int codigo;
			codigo = (int) tblQuery.getValueAt(tblQuery.getSelectedRow(), 0);
			treinoCadastro.editar(codigo);

		}

	}

	class AcaoExcluir extends AbstractAction{

		AcaoExcluir(){
			super("Excluir");
			putValue(MNEMONIC_KEY, KeyEvent.VK_X);
			putValue(SHORT_DESCRIPTION, 
					"Excluir registro de treino!");
			putValue(SMALL_ICON, 
					new ImageIcon(imagesPath+"general/Delete24.gif"));

		}

		// PATRICAMENTE IGUAL AO DA AcaoEditar
		@Override
		public void actionPerformed(ActionEvent e) {

			int codigo;
			codigo = (int) tblQuery.getValueAt(tblQuery.getSelectedRow(), 0);
			treinoCadastro.excluir(codigo);

		}

	}	

	class AcaoSair extends AbstractAction{

		AcaoSair(){
			super("Sair");
			putValue(MNEMONIC_KEY, KeyEvent.VK_R);
			putValue(SHORT_DESCRIPTION, 
					"Fecha consulta de treinos!");
			putValue(SMALL_ICON, 
					new ImageIcon(imagesPath+"general/Stop24.gif"));

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			TreinoConsulta.this.setVisible(false);
			pai.setVisible(true);

		}

	}

	public void buscar() {

		List<Treino> treinos = new ArrayList<Treino>();
		try {
			if(fldValor.getText().equals("")) {
				treinos = tl.getTreinos();
			}else{
				switch (cmbChaves.getSelectedIndex()) {
				case 0:
					treinos.add(tl.getTreino(Integer.parseInt(fldValor.getText())));
					break;
//				case 1:
//					treinos = tl.getTreinosPorNome(fldValor.getText());   //Metodo getTreinoPorNome não implementado 
//					break;												  //em TreinoLogic
				}

			}
			
			tblQuery.setModel(new TreinoTableModel(treinos));
			btnEditar.setEnabled(false);
			btnExcluir.setEnabled(false);

		} catch (DataBaseGenericException | 
				DataBaseNotConnectedException | 
				EntityTableIsEmptyException | 
				EntityNotExistException e) 
		{
			JOptionPane.showMessageDialog(this, e.getMessage(), 
					"Consulta de Treino", JOptionPane.ERROR_MESSAGE);
		}

	}

	class HabilitarEdicaoExclusao extends MouseAdapter {

		public void mousePressed(MouseEvent e) {
			if (tblQuery.getSelectedRow() >= 0) {
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
			}else {
				btnEditar.setEnabled(false);
				btnExcluir.setEnabled(false);
			}

		}

	}	

}
