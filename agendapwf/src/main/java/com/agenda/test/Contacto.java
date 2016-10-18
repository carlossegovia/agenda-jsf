package com.agenda.test;

public class Contacto {

	private int id;
	private String nombre;
	private String apellido;
	private String alias;
	private String telefono;
	private String email;
	private String direccion;
	private String fechacreacion;
	private String fechamodificacion;

	public Contacto() {
		super();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getFechacreacion() {
		return fechacreacion;
	}

	public void setFechacreacion(String fechacreacion) {
		this.fechacreacion = fechacreacion;
	}

	public String getFechamodificacion() {
		return fechamodificacion;
	}

	public void setFechamodificacion(String fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Agenda [id=" + id + ", nombre=" + nombre + ", apellido="
				+ apellido + ", alias=" + alias + ", telefono=" + telefono
				+ ", email=" + email + ", direccion=" + direccion
				+ ", fechacreacion=" + fechacreacion + ", fechamodificacion="
				+ fechamodificacion + "]";
	}

	public String toPost() {
		return "{\"nombre\":\"" + nombre + "\",\"apellido\":\"" + apellido
				+ "\",\"alias\":\"" + alias + "\",\"telefono\":\"" + telefono
				+ "\",\"email\":\"" + email + "\",\"direccion\":\"" + direccion
				+ "\"}";
	}
}
