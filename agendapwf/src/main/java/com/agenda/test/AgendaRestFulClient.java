package com.agenda.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ComponentSystemEvent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@ManagedBean(name = "service", eager = true)
@SessionScoped
public class AgendaRestFulClient {

	private static final String targetURL = "https://desa03.konecta.com.py/pwf/rest/agenda";

	public Contacto contacto;
	public boolean	editar = false;
	public List<Contacto> listaContactos;
	public int cantidadContactos;
	public Contacto seleccionado;
	public String filtro="";
	public boolean showAlertSucces;
	public boolean showAlertFailed;
	public boolean showAlertSuccesEliminar;
	public boolean showAlertFailedEliminar;
	
	
	public class JsonRecibido{
		public int total;
		public List<Contacto> lista;
		
		public JsonRecibido(){
			this.total=0;
			this.lista= new ArrayList<Contacto>();
		}
	}
	
	public boolean isShowAlertSucces() {
		return showAlertSucces;
	}

	public void setShowAlertSucces(boolean showAlertSucces) {
		this.showAlertSucces = showAlertSucces;
	}

	public boolean isShowAlertFailed() {
		return showAlertFailed;
	}
	
	public boolean isShowAlertSuccesEliminar() {
		return showAlertSuccesEliminar;
	}

	public void setShowAlertSuccesEliminar(boolean showAlertSuccesEliminar) {
		this.showAlertSuccesEliminar = showAlertSuccesEliminar;
	}

	public boolean isShowAlertFailedEliminar() {
		return showAlertFailedEliminar;
	}

	public void setShowAlertFailedEliminar(boolean showAlertFailedEliminar) {
		this.showAlertFailedEliminar = showAlertFailedEliminar;
	}

	public void setShowAlertFailed(boolean showAlertFailed) {
		this.showAlertFailed = showAlertFailed;
	}

	public int getCantidadContactos() {
		return cantidadContactos;
	}

	public void setCantidadContactos(int cantidadContactos) {
		this.cantidadContactos = cantidadContactos;
	}

	public String getFiltro() {
		return this.filtro;
	}
	
	public void setSelec(Contacto contacto){
		setSeleccionado(contacto);
	}
	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}
	
	
	public Contacto getContacto() {
		return contacto;
	}

	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}
	
	public Contacto getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(Contacto contacto) {
		this.seleccionado = contacto;
	}
	
	public void seleccionar(int id){
		this.seleccionado = this.getContactoID(id);
	}
	
	public void setListaContactos(List<Contacto> listaContactos) {
		this.listaContactos = listaContactos;
	}

	public List<Contacto> getListaContactos() {
		return listaContactos;
	}
	
	public void inicializar(ComponentSystemEvent event){
		this.listaContactos = new ArrayList<Contacto>();
		//this.filtro = "";
		setListaContactos(getListaContactosFromServer()); 
	}
	
	public void guardar(){
		if (editar==true)
		{
			this.putContacto();
		}else{
			this.postContacto();
		}
		editar = false;
	}
	
	public void inicializateFormulario(){
		if (!editar){
			this.contacto=new Contacto();
		}
	}
	
	public void limpiar(){
		this.filtro="";
		System.out.println("El filtro es: "+this.filtro);
		this.getListaContactosFromServer();
	}
	
	public void filtrar(){
		System.out.println("El filtro es: "+this.filtro);
		this.getListaContactosFromServer();
	}
	
	public void goEditar(int id){
		this.editar=true;
		setContacto(getContactoID(id));
	}
	
	public Contacto getContactoID(int id){
		Contacto contacto = new Contacto();
		try {
			URL url = new URL(targetURL+"/"+id);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			System.out.println("Recibido contacto .... \n");

			output = br.readLine();
			System.out.println(output);
			Gson gson = new GsonBuilder().create();
			contacto = gson.fromJson(output, new TypeToken<Contacto>() {
			}.getType());
			

			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return contacto;
	}
	
	public List<Contacto> getListaContactosFromServer() {
		JsonRecibido ag = new JsonRecibido();
		try {
			System.out.println(this.filtro);
			URL url = new URL(targetURL+"?filtro="+this.filtro);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");

			while ((output = br.readLine()) != null) {
				System.out.println(output);
				Gson gson = new GsonBuilder().create();
				ag = gson.fromJson(output, new TypeToken<JsonRecibido>() {
				}.getType());
			}
			
			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.cantidadContactos = ag.total;
		return ag.lista;
	}

	public void postContacto() {
		try {
			URL url = new URL(targetURL);

			HttpURLConnection httpConnection = (HttpURLConnection) url
					.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type",
					"application/json");
			this.contacto.setFechacreacion(new Timestamp(System.currentTimeMillis()).toString());

			String input = this.contacto.toPost();

			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(input.getBytes());
			outputStream.flush();

			if (httpConnection.getResponseCode() != 200) {
				this.showAlertFailed=true;
				throw new RuntimeException("Failed : HTTP error code : "
						+ httpConnection.getResponseCode());
			} else {
				this.showAlertSucces=true;
			}

			BufferedReader responseBuffer = new BufferedReader(
					new InputStreamReader((httpConnection.getInputStream())));

			String output;
			System.out.println("Output from Server:\n");
			while ((output = responseBuffer.readLine()) != null) {
				System.out.println(output);
			}

			httpConnection.disconnect();
			this.contacto = null;
			this.filtro = "";

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	public void putContacto() {
		try {
			URL url = new URL(targetURL+"/"+contacto.getId());

			HttpURLConnection httpConnection = (HttpURLConnection) url
					.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("PUT");
			httpConnection.setRequestProperty("Content-Type",
					"application/json");
			this.contacto.setFechamodificacion(new Timestamp(System.currentTimeMillis()).toString());
			
			String input = this.contacto.toPost();

			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(input.getBytes());
			outputStream.flush();

			if (httpConnection.getResponseCode() != 200) {
				this.showAlertFailed=true;
				throw new RuntimeException("Failed : HTTP error code : "
						+ httpConnection.getResponseCode());
			} else {
				this.showAlertSucces=true;
			}

			BufferedReader responseBuffer = new BufferedReader(
					new InputStreamReader((httpConnection.getInputStream())));

			String output;
			System.out.println("Output from Server:\n");
			while ((output = responseBuffer.readLine()) != null) {
				System.out.println(output);
			}

			httpConnection.disconnect();
			this.contacto = null;

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	public void deleteContacto(int id){
		URL url = null;
		try {
		    url = new URL(targetURL+"/"+id);
		} catch (MalformedURLException exception) {
		    exception.printStackTrace();
		}
		HttpURLConnection httpURLConnection = null;
		try {
		    httpURLConnection = (HttpURLConnection) url.openConnection();
		    httpURLConnection.setRequestProperty("Content-Type",
		                "application/json");
		    httpURLConnection.setRequestMethod("DELETE");
		    
		    if (httpURLConnection.getResponseCode() != 200) {
		    	this.showAlertFailed=true;
				throw new RuntimeException("Failed : HTTP error code : "
						+ httpURLConnection.getResponseCode());
			} else {
				this.showAlertSuccesEliminar=true;
			}
		} catch (IOException exception) {
		    exception.printStackTrace();
		} finally {         
		    if (httpURLConnection != null) {
		        httpURLConnection.disconnect();
		    }
		} 
	}
	
	public String formatDate(String date) throws ParseException
	{	if (date!=""){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
			Date temp= sdf.parse(date);
			sdf=new SimpleDateFormat("dd-MM-YYYY hh:mm");
			String dateString=sdf.format(temp);
			return dateString ;
		}
		else return date;
	}


}
