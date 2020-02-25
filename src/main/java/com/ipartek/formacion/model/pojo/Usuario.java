package com.ipartek.formacion.model.pojo;

import java.io.Serializable;

public class Usuario implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 3062181847034258509L;
	private int id;
	private String nombre;
	private String password;

	public Usuario() {
		this.id = 0;
		this.nombre = "";
		this.password = "";
	}

	public Usuario(int id, String nombre, String password) {
		this();
		this.id = id;
		this.nombre = nombre;
		this.password = password;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", password=" + password + "]";
	}


}
