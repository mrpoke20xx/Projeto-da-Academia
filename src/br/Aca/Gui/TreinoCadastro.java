package br.Aca.Gui;

import javax.imageio.ImageIO;
import javax.swing.*; 					//importando classes do Swing

import br.Aca.DB.Conexao;
import br.Aca.Entity.Treino;
import br.Aca.Logic.TreinoLogic;
import br.Aca.Exception.*;

import java.awt.*; 						//importando classes do AWT
import java.awt.event.*; 				//importando classes de EVENTOS do AWT
import java.io.File;
import java.io.IOException;
import java.sql.*;						//importando classes do JDBC
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class TreinoCadastro extends JFrame {

	private final int INCLUSAO = 0;
	private final int EDICAO = 1;
	private final int EXCLUSAO = 2;

	private int acao, numeroDeCentros;
	private String[] idCentros;

	private TreinoConsulta pai;
	private Conexao cnx;
	private ResultSet rs;
	private TreinoLogic tl;

	private JPanel pControles, pOperacoes, pRotulos, pCampos;
	private JLabel lblImagem;
	private JComboBox cmbCentro;
	private JTextField tfCodigo, tfVencimento, tfCliente, tfExercicio;
	private JButton btConfirmar, btCancelar;

	private SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
	
	AcaoConfirmar actConfirmar = new AcaoConfirmar();
	AcaoCancelar actCancelar = new AcaoCancelar();

	static final String imagesPath = new String("images/");	

	TreinoCadastro(JFrame framePai, Conexao conexao){ // metodo construtor
		super(""); // chamando construtor da classe mae
		setSize(1400, 600);				// definindo dimensoes da janela		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		pai = (TreinoConsulta)framePai;
		cnx = conexao;
		tl = new TreinoLogic(cnx);

		pRotulos = new JPanel(new GridLayout(4,1,5,5));
		pRotulos.add(new JLabel("Codigo"));
		pRotulos.add(new JLabel("Vencimento"));
		pRotulos.add(new JLabel("Cliente"));
		pRotulos.add(new JLabel("Exercicio"));

		tfCodigo = new JTextField();
		tfVencimento = new JTextField();
		tfCliente = new JTextField();
		tfExercicio = new JTextField();
		
		pCampos = new JPanel(new GridLayout(4,1,5,5));
		pCampos.add(tfCodigo);
		pCampos.add(tfVencimento);
		pCampos.add(tfCliente);
		pCampos.add(tfExercicio);
		
		pControles = new JPanel(new BorderLayout(5,5));
		pControles.add(pRotulos, BorderLayout.WEST);
		pControles.add(pCampos);

		btConfirmar = new JButton(actConfirmar);
		btCancelar = new JButton(actCancelar);
		
		try {
			lblImagem = new JLabel(new ImageIcon(ImageIO.read(new File("src/Treino.png"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		pOperacoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		pOperacoes.add(btConfirmar);
		pOperacoes.add(btCancelar);

		add(pControles);
		add(pOperacoes, BorderLayout.SOUTH);	
		add(lblImagem, BorderLayout.EAST);

		pack();

	} //Fim do mÃ©todo construtor

	class AcaoConfirmar extends AbstractAction{

		AcaoConfirmar(){
			super("Confirmar");
			putValue(MNEMONIC_KEY, KeyEvent.VK_C);
			putValue(SHORT_DESCRIPTION, 
					"Confirmar operaÃ§Ã£o!");
			putValue(SMALL_ICON, 
					new ImageIcon(imagesPath+"general/Save24.gif"));

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Date vencimento = null;
			String strAtualize = "";
			try {
				vencimento = form.parse(tfVencimento.getText());
			} catch (ParseException e2) {
				e2.printStackTrace();
			}
			
			try {
				switch (acao) {
				case INCLUSAO:
					 		   
					tl.addTreino(Integer.parseInt(tfCodigo.getText()), vencimento, Integer.parseInt(tfCliente.getText()), Integer.parseInt(tfExercicio.getText()));
					break;
				case EDICAO:
					tl.updTreino(Integer.parseInt(tfCodigo.getText()), vencimento, Integer.parseInt(tfCliente.getText()), Integer.parseInt(tfExercicio.getText()));
					break;
				case EXCLUSAO:
					tl.delTreino(Integer.parseInt(tfCodigo.getText()), vencimento, Integer.parseInt(tfCliente.getText()), Integer.parseInt(tfExercicio.getText()));
					break;
				}
				limparCampos();
				TreinoCadastro.this.setVisible(false);
				pai.setVisible(true);
				pai.buscar();				
			} catch (DataBaseGenericException | 
					DataBaseNotConnectedException | 
					EntityAlreadyExistException| 
					InvalidFieldException | 
					EntityNotExistException e1) 
			{
				JOptionPane.showMessageDialog(TreinoCadastro.this, e1.getMessage(), 
						"Cadastro de Centro", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	class AcaoCancelar extends AbstractAction{

		AcaoCancelar(){
			super("Cancelar");
			putValue(MNEMONIC_KEY, KeyEvent.VK_L);
			putValue(SHORT_DESCRIPTION, 
					"Cancelar operacao!");
			putValue(SMALL_ICON, 
					new ImageIcon(imagesPath+"general/Stop24.gif"));

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			limparCampos();
			TreinoCadastro.this.setVisible(false);
			pai.setVisible(true);
			pai.buscar();
		}

	}

	public void incluir() {

		acao = INCLUSAO;
		setTitle("Inclusao de Treino");
		
		tfCodigo.setEnabled(true);
		tfVencimento.setEnabled(true);
		tfCliente.setEnabled(true);
		tfExercicio.setEnabled(true);
		

		limparCampos();

		pai.setVisible(false);
		setVisible(true);

	}

	public void editar(int codigo) {

		acao = EDICAO;
		setTitle("Edicao de Treino");

		tfCodigo.setEnabled(false);
		tfVencimento.setEnabled(true);
		tfCliente.setEnabled(true);
		tfExercicio.setEnabled(true);

		carregarCampos(codigo);

		pai.setVisible(false);
		setVisible(true);

	}

	//PRATICAMENTE IGUAL AO editar
	public void excluir(int codigo) {

		acao = EXCLUSAO;
		setTitle("Exclusao de Treino");

		tfCodigo.setEnabled(false);
		tfVencimento.setEnabled(false);
		tfCliente.setEnabled(false);
		tfExercicio.setEnabled(false);

		carregarCampos(codigo);

		pai.setVisible(false);
		this.setVisible(true);

	}	


	public void limparCampos() {

		tfCodigo.setText("");
		tfVencimento.setText("");
		tfCliente.setText("");
		tfExercicio.setText("");

	}

	public void carregarCampos(int codigo) {

		Treino c;
		Date vencimento = null;
		
//		try {
//			vencimento = form.parse(tfVencimento.getText());
//		} catch (ParseException e2) {
//			e2.printStackTrace();
//		}
		
		try {
			c = tl.getTreino(codigo);
			tfCodigo.setText(Integer.toString(c.getCodigo()));
			tfVencimento.setText(form.format(c.getVencimento()));
			tfCliente.setText(c.getCliente().toString());
			tfExercicio.setText(c.getExercicio().toString());
		} catch (DataBaseGenericException | 
				DataBaseNotConnectedException | 
				EntityNotExistException e) 
		{
			JOptionPane.showMessageDialog(this, e.getMessage(), 
					"Cadastro de Treino", JOptionPane.ERROR_MESSAGE);
		}


	}


}
