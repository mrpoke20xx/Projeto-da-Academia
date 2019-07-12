package br.Aca.Entity;

import java.sql.Date;

public class Cliente {
	private int codigo;
	private String nome;
	private String endereco;
	private Date dataNasc;
	private char sexo;
	private String necessidade;
	private int trainer;
	
	public Cliente(int codigo, String nome, String endereco, Date dataNasc, char sexo, String necessidade, int trainer){
		this.codigo = codigo;
		this.nome = nome;
		this.endereco = endereco;
		this.dataNasc = dataNasc;
		this.sexo = sexo;
		this.necessidade = necessidade;
		this.trainer = trainer;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public Date getDataNasc() {
		return dataNasc;
	}
	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}
	public char getSexo() {
		return sexo;
	}
	public void setSexo(char sexo) {
		this.sexo = sexo;
	}
	public String getNecessidade() {
		return necessidade;
	}
	public void setNecessidade(String necessidade) {
		this.necessidade = necessidade;
	}
	public int getTrainer() {
		return trainer;
	}
	public void setTrainer(int trainer) {
		this.trainer = trainer;
	}
}
