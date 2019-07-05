package br.Aca.Entity;

import java.sql.Date;

public class Trainer {
	private int codigo;
	private String nome;
	private Date dataNasc;
	private String sexo;
	private Academia academia;
	public Trainer(int codigo, String nome, Date dataNasc, String sexo, Academia academia){
		this.codigo = codigo;
		this.nome = nome;
		this.dataNasc = dataNasc;
		this.sexo = sexo;
		this.academia = academia;
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
	public Academia getAcademia() {
		return academia;
	}
	public void setAcademia(Academia academia) {
		this.academia = academia;
	}
}
