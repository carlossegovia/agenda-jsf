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
	public Contacto seleccionado;
	
	public class JsonRecibido{
		public int total;
		public List<Contacto> lista;
		
		public JsonRecibido(){
			this.total=0;
			this.lista= new ArrayList<Contacto>();
		}
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
			URL url = new URL(targetURL);
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
				throw new RuntimeException("Failed : HTTP error code : "
						+ httpConnection.getResponseCode());
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
				throw new RuntimeException("Failed : HTTP error code : "
						+ httpConnection.getResponseCode());
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
				throw new RuntimeException("Failed : HTTP error code : "
						+ httpURLConnection.getResponseCode());
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
