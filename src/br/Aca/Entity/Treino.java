package br.Aca.Entity;

import java.sql.Date;

public class Treino {
	private int codigo;
	private Date vencimento;
	private Cliente cliente;
	private Equipamento equipamento;
	public Treino (int codigo, Date vencimento, Cliente cliente, Equipamento equipamento){
		this.codigo = codigo;
		this.vencimento = vencimento;
		this.cliente = cliente;
		this.equipamento = equipamento;
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
	public Equipamento getEquipamento() {
		return equipamento;
	}
	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}
}
