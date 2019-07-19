package br.Aca.Gui;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import br.Aca.DB.Conexao;
import br.Aca.Entity.Cliente;
import br.Aca.Exception.*;
import br.Aca.Logic.*;
import br.Aca.Gui.*;

public class CadastroCliente {

	JFrame janela = new JFrame("Cadastro de Novo Cliente");
	private JPanel p_controles, p_Operacoes, p_rotulos, p_campos;
	private JLabel lb1, lb2, lb3, lb4, lb5, lb6, lb7, lblImagem;
	private JTextField tf_tra_cod, tf_cod, tf_sexo, tf_nome, tf_endereco, tf_nasc, tf_necessidade;
	private JButton bt_confirmar, bt_cancelar;
	private SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");

	private final int INCLUSAO = 0;
	private final int EDICAO = 1;
	private final int EXCLUSAO = 2;

	private int acao;

	private ConsultaCliente pai;
	private Conexao cnx;
	private ResultSet rs;
	private AcademiaLogic cl;
	private ClienteLogic pl;

	private JComboBox cmbCentro;
	private JButton btnConfirmar, btnCancelar;

	AcaoConfirmar actConfirmar = new AcaoConfirmar();
//	AcaoCancelar actCancelar = new AcaoCancelar();

	public CadastroCliente(JFrame framePai, Conexao conexao) {
		
		pai = (ConsultaCliente)framePai;
		cnx = conexao;
		pl = new ClienteLogic(cnx);
		//cl = new ProfessorLogic(cnx);
		
		// Configuração da janela
		janela.setDefaultCloseOperation(janela.DISPOSE_ON_CLOSE);
		janela.setSize(380, 320);
		janela.setResizable(true);
		janela.setLayout(new BorderLayout());
		janela.setLocationRelativeTo(null);
//		janela.setVisible(true);

		// Criando os paineis
		p_controles = new JPanel(new GridLayout(1, 4));
		p_Operacoes = new JPanel(new GridLayout(7, 2));
		p_rotulos = new JPanel(new GridLayout(1, 2));

		// Criando as caixas de texto
		tf_tra_cod = new JTextField();
		tf_cod = new JTextField();
		tf_sexo = new JTextField();
		tf_nome = new JTextField();
		tf_endereco = new JTextField();
		tf_nasc = new JTextField();
		tf_necessidade = new JTextField();

		// Criando os labels
		lb2 = new JLabel("C�digo do cliente: ");
		lb4 = new JLabel("Nome: ");
		lb5 = new JLabel("Endere�o: ");
		lb6 = new JLabel("Data de Nascimento: ");
		lb3 = new JLabel("Sexo: ");
		lb7 = new JLabel("Necessidade espec�fica: ");
		lb1 = new JLabel("C�digo do personal trainer: ");
		
		// Criando os botoes
		// Configurando os botoe

		/****** Adicionando os componentes aos paineis e os paineis a janela *********/

		// Adicionando componentes ao painel info
		
		p_Operacoes.add(lb2);
		p_Operacoes.add(tf_cod);
		
		p_Operacoes.add(lb4);
		p_Operacoes.add(tf_nome);
		
		p_Operacoes.add(lb5);
		p_Operacoes.add(tf_endereco);
		
		p_Operacoes.add(lb6);
		p_Operacoes.add(tf_nasc);
		
		p_Operacoes.add(lb3);
		p_Operacoes.add(tf_sexo);
		
		p_Operacoes.add(lb7);
		p_Operacoes.add(tf_necessidade);
		
		p_Operacoes.add(lb1);
		p_Operacoes.add(tf_tra_cod);
		
		try {
			lblImagem = new JLabel(new ImageIcon(ImageIO.read(new File("src/Cadastro.png"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		bt_confirmar = new JButton(actConfirmar);

		// Adiconando componentes ao painel botao
		p_rotulos.add(bt_confirmar);

		// Adicionando os paineis a janela principal
		janela.add(p_Operacoes, BorderLayout.CENTER);
		janela.add(p_rotulos, BorderLayout.SOUTH);
		janela.add(lblImagem, BorderLayout.EAST);

		janela.pack();
	}
	

	class AcaoConfirmar extends AbstractAction {

		AcaoConfirmar() {
			super("Confirmar");
			putValue(MNEMONIC_KEY, KeyEvent.VK_C);
			putValue(SHORT_DESCRIPTION, "Confirmar opera��o!");

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			String strAtualize = "";
			int tra_cod, cli_cod;
			char sexo;
			Date nasc = null;
			String nome, endereco, necessidade;
			
			cli_cod = Integer.parseInt(tf_cod.getText());
			
			
			nome = tf_nome.getText();
			endereco = tf_endereco.getText();
			try {
				nasc = form.parse(tf_nasc.getText());
			} catch (ParseException e2) {
				e2.printStackTrace();
			}
			sexo = tf_sexo.getText().charAt(0);
			necessidade = tf_necessidade.getText();
			tra_cod = Integer.parseInt(tf_tra_cod.getText());
			try {
				//int codigo, String nome, String endereco, Date data_nasc, char sexo, String necessidade, int trainer
				switch (acao) {
				case INCLUSAO:
					pl.addCliente(cli_cod, nome, endereco, nasc, sexo, necessidade, tra_cod);
					break;
				case EDICAO:
					pl.updCliente(cli_cod, nome, endereco, nasc, sexo, necessidade, tra_cod);
					break;
				case EXCLUSAO:
					pl.delCliente(cli_cod, nome, endereco, nasc, sexo, necessidade, tra_cod);
					break;
			}
				limparCampos();
				janela.setVisible(false);
				pai.setVisible(true);
				pai.buscar();
			} catch (DataBaseGenericException | DataBaseNotConnectedException | EntityAlreadyExistException
					| InvalidFieldException | EntityNotExistException e1) {
			JOptionPane.showMessageDialog(janela, e1.getMessage(), "Cadastro de Cliente", JOptionPane.ERROR_MESSAGE);
			}

		}
	}

	class AcaoCancelar extends AbstractAction {

		AcaoCancelar() {
			super("Cancelar");
			putValue(MNEMONIC_KEY, KeyEvent.VK_L);
			putValue(SHORT_DESCRIPTION, "Cancelar opera��o!");

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			limparCampos();
//			janela.setVisible(false);
//			pai.setVisible(true);
//			pai.buscar();
		}

	}

	public void incluir() {

		acao = INCLUSAO;
		janela.setTitle("Inclus�o de cliente"); // supondo que no codigo Academico fosse um "this.setTitle"
		tf_tra_cod.setEnabled(true);
		tf_cod.setEnabled(true);
		tf_sexo.setEnabled(true);
		tf_nome.setEnabled(true);
		tf_endereco.setEnabled(true);
		tf_nasc.setEnabled(true);
		tf_necessidade.setEnabled(true);

		limparCampos();

//		pai.setVisible(false);
		janela.setVisible(true);

	}

	public void editar(int cod) {

		acao = EDICAO;
		janela.setTitle("Edi��o de informa��es do cliente");

		tf_tra_cod.setEnabled(true);
		tf_cod.setEnabled(true);
		tf_sexo.setEnabled(true);
		tf_nome.setEnabled(true);
		tf_endereco.setEnabled(true);
		tf_nasc.setEnabled(true);
		tf_necessidade.setEnabled(true);

		carregarCampos(cod);

//		pai.setVisible(false);
		janela.setVisible(true);

	}

	public void excluir(int cod) {

		acao = EXCLUSAO;
		janela.setTitle("Exclus�o de Cliente");

		tf_tra_cod.setEnabled(false);
		tf_cod.setEnabled(false);
		tf_sexo.setEnabled(false);
		tf_nome.setEnabled(false);
		tf_endereco.setEnabled(false);
		tf_nasc.setEnabled(false);
		tf_necessidade.setEnabled(false);

		carregarCampos(cod);

//		pai.setVisible(false);
		janela.setVisible(true);

	}

	public void limparCampos() {

		tf_tra_cod.setText("");
		tf_cod.setText("");
		tf_sexo.setText("");
		tf_nome.setText("");
		tf_endereco.setText("");
		tf_nasc.setText("");
		tf_necessidade.setText("");

	}

	public void carregarCampos(int cod) {

		Cliente p;

		try {
			p = pl.getCliente(cod);

			tf_tra_cod.setText(String.valueOf(p.getTrainer()));
			tf_cod.setText(String.valueOf(p.getCodigo()));
			tf_sexo.setText((String.valueOf(p.getSexo())));
			tf_nome.setText((p.getNome()));
			tf_endereco.setText(p.getEndereco());
			tf_nasc.setText(String.valueOf(p.getDataNasc()));
			tf_necessidade.setText(p.getNecessidade());

		} catch (DataBaseGenericException | DataBaseNotConnectedException | EntityNotExistException e) {
			JOptionPane.showMessageDialog(janela, e.getMessage(), "Cadastro de Cliente", JOptionPane.ERROR_MESSAGE);
		}

	}
	
//	public static void main(String []args) {
//		CadastroCliente c = new CadastroCliente();
//	}

}
