package com.techelevator;

import java.time.LocalDate;

public class Reservation {
	
	private int reservationId;
	private int siteId;
	private String name;
	private LocalDate fromDate;
	private LocalDate toDate;
	private LocalDate createdDate;
	
	public Reservation() {
		
	}
	
	public Reservation(int siteId, String name, LocalDate fromDate, LocalDate toDate) {
		this.siteId = siteId;
		this.name = name;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}
	
	@Override
	public boolean equals(Object o) {
		if (! (o instanceof Reservation)) return false;
		else {
			Reservation other = (Reservation)o;
			return reservationId == other.getReservationId() && siteId == other.getSiteId()
					&& name.equals(other.getName()) && fromDate.equals(other.getFromDate())
					&& toDate.equals(other.getToDate()) && createdDate.equals(other.getCreatedDate());
		}
	}
	
	@Override
	public int hashCode() {
		return reservationId + siteId + name.hashCode() + fromDate.hashCode() + toDate.hashCode() + createdDate.hashCode();  
	}
	
	public int getReservationId() {
		return reservationId;
	}
	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}
	public int getSiteId() {
		return siteId;
	}
	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getFromDate() {
		return fromDate;
	}
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}
	public LocalDate getToDate() {
		return toDate;
	}
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}
	public LocalDate getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}
	
	
}
