package com.btxy.basis.morphia.model.test;

import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.Key;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;


@Entity(value="test_hotel", noClassnameStored=true)
public class Hotel {
	@Id private Long id;                   
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}


	private String name;                       
	private int stars;                         
	
	
	
	@Reference(lazy = true,ignoreMissing=true)  
	public List<Address> addressList = new ArrayList<Address>();

	@Reference(lazy = true,ignoreMissing=true)  
	Address address;

	public List<Address> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<Address> addressList) {
		this.addressList = addressList;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	

	/*public List<Address> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<Address> addressList) {
		this.addressList = addressList;
	}*/

	
	          
	                                           
	/*-----------gettters & setters----------*/

}
