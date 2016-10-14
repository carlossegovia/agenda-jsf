package com.agenda.test;

import java.util.Date;


public class Agenda {
	
	private String nombre;
	private String apellido;
	private String alias;
	private String telefono;
	private String email;
	private String direccion;
	private Date fechacreacion;
	private Date fechamodificacion;
	
	public Agenda(String nombre, String apellido, String alias,
			String telefono, String email, String direccion,
			Date fechacreacion, Date fechamodificacion) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.alias = alias;
		this.telefono = telefono;
		this.email = email;
		this.direccion = direccion;
		this.fechacreacion = fechacreacion;
		this.fechamodificacion = fechamodificacion;
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
	
	public Date getFechacreacion() {
		return fechacreacion;
	}
	
	public void setFechacreacion(Date fechacreacion) {
		this.fechacreacion = fechacreacion;
	}
	
	public Date getFechamodificacion() {
		return fechamodificacion;
	}
	
	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}
	
}
