package com.techelevator;

import java.time.LocalDate;

public class Park {
	
	private String name;
	private int parkId;
	private String location;
	private LocalDate establishDate;
	private int area;
	private int visitors;
	private String description;
	
	public Park() {
		
	}
	
	public Park(String name, int parkId, String location, LocalDate establishDate, int area, int visitors, String description) {
		this.name = name;
		this.parkId = parkId;
		this.location = location;
		this.establishDate = establishDate;
		this.area = area;
		this.visitors = visitors;
		this.description = description;
	}
	
	@Override
	public boolean equals(Object o) {
		if (! (o instanceof Park)) return false;
		else {
			Park other = (Park)o;
			return name.equals(other.getName()) && parkId == other.getParkId()
					&& location.equals(other.getLocation()) && establishDate.equals(other.getEstablishDate())
					&& area == other.getArea() && visitors == other.getVisitors() && description.equals(other.getDescription());
		}
	}
	
	@Override
	public int hashCode() {
		return name.hashCode() + location.hashCode() + establishDate.hashCode() + description.hashCode()
			+ parkId + area + visitors;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getParkId() {
		return parkId;
	}
	public void setParkId(int parkId) {
		this.parkId = parkId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public LocalDate getEstablishDate() {
		return establishDate;
	}
	public void setEstablishDate(LocalDate establishDate) {
		this.establishDate = establishDate;
	}
	public int getArea() {
		return area;
	}
	public void setArea(int area) {
		this.area = area;
	}
	public int getVisitors() {
		return visitors;
	}
	public void setVisitors(int visitors) {
		this.visitors = visitors;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
