package br.Aca.Entity;

public class Exercicio {
	private int codigo;
	private String nome;
	private String descricao;
	private Equipamento equipamento;
	public Exercicio (int codigo, String nome, String descricao, Equipamento equipamento){
		this.codigo = codigo;
		this.nome = nome;
		this.descricao = descricao;
		this.equipamento = equipamento;
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
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Equipamento getEquipamento() {
		return equipamento;
	}
	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}
}
