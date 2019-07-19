package br.Aca.Gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;

import br.Aca.DB.*;
import br.Aca.Entity.*;
import br.Aca.Exception.*;
import br.Aca.Logic.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TrainerCadastro extends JFrame{
	
	private final int INCLUSAO = 0;
	private final int EDICAO = 1;
	private final int EXCLUSAO = 2;
	
	private int acao;
	
	private TrainerConsulta pai;
	private Conexao cnx;
	private ResultSet rs;
	private TrainerLogic tl;
	
	private JPanel pControles, pOperacoes, pRotulos, pCampos;
	private JComboBox cbox;
	private JTextField tfCodigo, tfNome, tfDataNasc, tfSexo, tfAcademia;
	private JButton btConfirmar, btCancelar;
	private SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
	private JLabel lblImagem;
	
	AcaoConfirmar actConfirmar = new AcaoConfirmar();
	AcaoCancelar actCancelar = new AcaoCancelar();
	
	static final String imagesPath = new String("images/");
	
	TrainerCadastro(JFrame framePai, Conexao essacnx) { //método construtor
		super(""); //Chamando o construtor da classe mae
		setSize(800,600); //Dimensões da janela
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		pai = (TrainerConsulta)framePai;
		cnx = essacnx;
		
		tl = new TrainerLogic(cnx);
		
		pRotulos = new JPanel(new GridLayout(7,1,5,5));
		pRotulos.add(new JLabel("Código: "));
		pRotulos.add(new JLabel("Nome: "));
		pRotulos.add(new JLabel("Data de Nascimento: "));
		pRotulos.add(new JLabel("Sexo: "));
		pRotulos.add(new JLabel("Academia: ")); //Filial da academia que o trainer pertence?
	
		tfCodigo = new JTextField();
		tfNome = new JTextField();
		tfDataNasc = new JTextField();
		tfSexo = new JTextField();
		tfAcademia = new JTextField();
		
		pCampos = new JPanel(new GridLayout(7,1,5,5));
		pCampos.add(tfCodigo);
		pCampos.add(tfNome);
		pCampos.add(tfDataNasc);
		pCampos.add(tfSexo);
		pCampos.add(tfAcademia);
		
		pControles = new JPanel(new BorderLayout(5,5));
		pControles.add(pRotulos, BorderLayout.WEST);
		pControles.add(pCampos);
		
		btConfirmar = new JButton(actConfirmar);
		btCancelar = new JButton(actCancelar);
		
		pOperacoes = new JPanel(new FlowLayout(FlowLayout.CENTER,5,5));
		pOperacoes.add(btConfirmar);
		pOperacoes.add(btCancelar);
		
		try {
			lblImagem = new JLabel(new ImageIcon(ImageIO.read(new File("src/Trainer.png"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.add(pControles);
		this.add(pOperacoes, BorderLayout.SOUTH);
		this.add(lvlImagem.BorderLayout.EAST);
		
		this.pack();
		
	} //Fim do método construtor
	
	class AcaoConfirmar extends AbstractAction{

		AcaoConfirmar(){
			super("Confirmar");
			putValue(MNEMONIC_KEY, KeyEvent.VK_C);
			putValue(SHORT_DESCRIPTION, 
					"Confirmar operação!");
			putValue(SMALL_ICON, 
					new ImageIcon(imagesPath+"general/Save24.gif"));

		}
		
		@Override
		public void actionPerformed(ActionEvent e) {

			String strAtualize = "";
			int codigo;
			String nome;
			Date dataNasc = null;
			String sexo;
			int academia;
			

			codigo = Integer.parseInt(tfCodigo.getText());
			nome = tfNome.getText();
			try {
				dataNasc = (Date) form.parse(tfDataNasc.getText());
			} catch (ParseException e2) {
				e2.printStackTrace();
			}
			sexo = (tfSexo.getText());                             //Em TrainerLogic sexo tá como String 
			academia = Integer.parseInt(tfAcademia.getText());     //e academia como int
			

			try {			

				switch (acao) {
				case INCLUSAO:
					tl.addTrainer(codigo, nome, dataNasc, sexo, academia);
					break;
				case EDICAO:
					tl.updTrainer(codigo, nome, dataNasc, sexo, academia);
					break;
				case EXCLUSAO:
					tl.delTrainer(codigo, nome, dataNasc, sexo, academia);				
					break;
				}
				limparCampos();
				TrainerCadastro.this.setVisible(false);
				pai.setVisible(true);
				pai.buscar();				
			} catch (DataBaseGenericException | 
					DataBaseNotConnectedException | 
					EntityAlreadyExistException| 
					InvalidFieldException | 
					EntityNotExistException e1) 
			{
				JOptionPane.showMessageDialog(TrainerCadastro.this, e1.getMessage(), 
						"Cadastro de Centro", JOptionPane.ERROR_MESSAGE);
			}
			
		}
	}
	class AcaoCancelar extends AbstractAction{

		AcaoCancelar(){
			super("Cancelar");
			putValue(MNEMONIC_KEY, KeyEvent.VK_L);
			putValue(SHORT_DESCRIPTION, 
					"Cancelar operação!");
			putValue(SMALL_ICON, 
					new ImageIcon(imagesPath+"general/Stop24.gif"));

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			limparCampos();
			TrainerCadastro.this.setVisible(false);
			pai.setVisible(true);
			pai.buscar();
		}

	}

	public void incluir() {

		acao = INCLUSAO;
		setTitle("Inclusão de Personal Trainer");

		tfCodigo.setEnabled(true);
		tfNome.setEnabled(true);
		tfDataNasc.setEnabled(true);
		tfSexo.setEnabled(true);
		tfAcademia.setEnabled(true);
		

		limparCampos();

		pai.setVisible(false);
		setVisible(true);

	}

	public void editar(int codigo) {

		acao = EDICAO;
		setTitle("Edição de Personal Trainer");

		tfCodigo.setEnabled(true);
		tfNome.setEnabled(true);
		tfDataNasc.setEnabled(true);
		tfSexo.setEnabled(true);
		tfAcademia.setEnabled(true);

		carregarCampos(codigo);

		pai.setVisible(false);
		setVisible(true);

	}

	//PRATICAMENTE IGUAL AO editar
	public void excluir(int codigo) {

		acao = EXCLUSAO;
		setTitle("Exclusão de Personal Trainer");

		tfCodigo.setEnabled(true);
		tfNome.setEnabled(true);
		tfDataNasc.setEnabled(true);
		tfSexo.setEnabled(true);
		tfAcademia.setEnabled(true);

		carregarCampos(codigo);

		pai.setVisible(false);
		setVisible(true);

	}	

	public void limparCampos() {

		tfCodigo.setText("");
		tfNome.setText("");
		tfDataNasc.setText("");
		tfSexo.setText("");
		tfAcademia.setText("");

	}

	public void carregarCampos(int codigo) {

		Trainer p;
				
		try {
			p = tl.getTrainer(codigo);
			
			tfCodigo.setText(String.valueOf(p.getCodigo()));
			tfNome.setText(p.getNome());
			tfDataNasc.setText(String.valueOf(p.getDataNasc()));
			tfSexo.setText(String.valueOf(p.getSexo()));
			tfAcademia.setText(String.valueOf(p.getAcademia()));
			//Em trainerlogic academia está como int e em trainer está como academia
											 
			

		} catch (DataBaseGenericException | 
				DataBaseNotConnectedException | 
				EntityNotExistException e) 
		{
			JOptionPane.showMessageDialog(this, e.getMessage(), 
					"Cadastro de Centro", JOptionPane.ERROR_MESSAGE);
		}



	}
	
	
	
}
