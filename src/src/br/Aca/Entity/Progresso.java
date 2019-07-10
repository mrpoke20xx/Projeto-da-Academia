package br.Aca.Entity;

import java.sql.Date;

public class Progresso {
	private Cliente cliente;
	private Date data;
	private double massa;
	private double altura;
	public Progresso (Cliente cliente, Date data, double massa, double altura){
		this.cliente = cliente;
		this.data = data;
		this.massa = massa;
		this.altura = altura;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public double getMassa() {
		return massa;
	}
	public void setMassa(double massa) {
		this.massa = massa;
	}
	public double getAltura() {
		return altura;
	}
	public void setAltura(double altura) {
		this.altura = altura;
	}
}
