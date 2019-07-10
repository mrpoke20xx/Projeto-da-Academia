package br.Aca.Entity;

public class Equipamento {
	private int codigo;
	private String nome;
	private double pesoMax;
	private String descricao;
	private Academia academia;
	public Equipamento(int codigo, String nome, double pesoMax, String descricao, Academia academia){
		this.codigo = codigo;
		this.nome = nome;
		this.pesoMax = pesoMax;
		this.descricao = descricao;
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
	public double getPesoMax() {
		return pesoMax;
	}
	public void setPesoMax(double pesoMax) {
		this.pesoMax = pesoMax;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Academia getAcademia() {
		return academia;
	}
	public void setAcademia(Academia academia) {
		this.academia = academia;
	}
}
