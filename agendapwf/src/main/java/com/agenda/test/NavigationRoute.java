package com.agenda.test;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "navigation", eager = true)
@SessionScoped
public class NavigationRoute {
	String formulario;
	String home;
	
	public String getFormulario(){
		return formulario="formulario";
	}
	
	public String getHome(){
		return home="home";
	}
}
