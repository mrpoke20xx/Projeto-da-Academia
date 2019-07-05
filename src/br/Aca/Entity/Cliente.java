package br.Aca.Entity;

import java.sql.Date;

public class Cliente {
	private int codigo;
	private String nome;
	private String endereco;
	private Date dataNasc;
	private String sexo;
	private String necessidade;
	private Trainer trainer;
	public Cliente(int codigo, String nome, String endereco, Date dataNasc, String sexo, String necessidade, Trainer trainer){
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
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getNecessidade() {
		return necessidade;
	}
	public void setNecessidade(String necessidade) {
		this.necessidade = necessidade;
	}
	public Trainer getTrainer() {
		return trainer;
	}
	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}
}
