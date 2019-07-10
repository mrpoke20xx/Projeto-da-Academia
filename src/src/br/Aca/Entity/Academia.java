package br.Aca.Entity;

public class Academia {
	private int codigo;
	private String nome, endereco, fone;
	public Academia(int codigo, String nome, String endereco, String fone){
		this.codigo = codigo;
		this.nome = nome;
		this.endereco = endereco;
		this.fone = fone;
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
	public String getFone() {
		return fone;
	}
	public void setFone(String fone) {
		this.fone = fone;
	}
}
