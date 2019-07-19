package br.Aca.Entity;

import java.util.Date;

public class Treino {
	private int codigo;
	private Date vencimento;
	private Cliente cliente;
	private Exercicio exercicio;
	public Treino (int codigo, Date vencimento, Cliente cliente, Exercicio exercicio){
		this.codigo = codigo;
		this.vencimento = vencimento;
		this.cliente = cliente;
		this.exercicio = exercicio;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public Date getVencimento() {
		return vencimento;
	}
	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Exercicio getExercicio() {
		return exercicio;
	}
	public void setExercicio(Exercicio exercicio) {
		this.exercicio = exercicio;
	}
}
