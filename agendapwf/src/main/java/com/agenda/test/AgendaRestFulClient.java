package com.agenda.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;


@ManagedBean(name = "service", eager = true)
public class AgendaRestFulClient {
	public void getRequest() {
		System.out.println("probando");
		try {
			URL url = new URL(
					"https://desa03.konecta.com.py/pwf/rest/agenda");
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
			List<Agenda> ag = new ArrayList<Agenda>(); 

			while ((output = br.readLine()) != null) {
				System.out.println(output);
				String output1 = output.substring(23,output.length()-1);//output.length()-2);
				System.out.println(output1);
				Gson gson = new GsonBuilder().create();
				ag = gson.fromJson(output1, new TypeToken<List<Agenda>>(){}.getType());
			}
			
			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public String getMessage() {
	      return "Click aqui";
	   }
	
}
