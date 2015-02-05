package org.mongodb.citybike.entities;

import java.util.LinkedList;
import java.util.List;

public class Contract {
	private String name = null;
	private String commercial_name = null;
	private String country_code = null;
	private List<String> cities = new LinkedList<String>(); 
	
	
	public Contract() {
		super();
	}
	
	

	public Contract(String name, String commercialName, String countryCode) {
		super();
		this.name = name;
		this.commercial_name = commercialName;
		this.country_code = countryCode;
	}



	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCommercial_name() {
		return commercial_name;
	}


	public void setCommercial_name(String commercial_name) {
		this.commercial_name = commercial_name;
	}


	public String getCountry_code() {
		return country_code;
	}


	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}


	public List<String> getCities() {
		return cities;
	}


	public void setCities(List<String> cities) {
		this.cities = cities;
	}
	
	public void addCity(String city) {
		this.cities.add(city);
	}
	
}
