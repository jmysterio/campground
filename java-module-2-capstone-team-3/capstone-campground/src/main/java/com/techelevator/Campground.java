package com.techelevator;

public class Campground {
	
	private int campgroundId;
	private int parkId;
	private String name;
	private int monthOpens;
	private int monthCloses;
	private int dailyFee;
	
	public Campground() {
		
	}
	
	public Campground(int campgroundId, int parkId, String name, int monthOpens, int monthCloses, int dailyFee) {
		this.campgroundId = campgroundId;
		this.parkId = parkId;
		this.name = name;
		this.monthOpens = monthOpens;
		this.monthCloses = monthCloses;
		this.dailyFee = dailyFee;
	}
	
	@Override
	public boolean equals(Object o) {
		if (! (o instanceof Campground)) return false;
		else {
			Campground other = (Campground)o;
			return name.equals(other.getName()) && parkId == other.getParkId()
					&& campgroundId == other.getCampgroundId() && monthOpens == other.getMonthOpens()
					&& monthCloses == other.getMonthCloses() && dailyFee == other.getDailyFee();
		}
	}
	
	@Override
	public int hashCode() {
		return name.hashCode() + campgroundId + monthOpens + monthCloses
			+ parkId;
	}
	
	public int getCampgroundId() {
		return campgroundId;
	}
	public void setCampgroundId(int campgroundId) {
		this.campgroundId = campgroundId;
	}
	public int getParkId() {
		return parkId;
	}
	public void setParkId(int parkId) {
		this.parkId = parkId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMonthOpens() {
		return monthOpens;
	}
	public void setMonthOpens(int monthOpens) {
		this.monthOpens = monthOpens;
	}
	public int getMonthCloses() {
		return monthCloses;
	}
	public void setMonthCloses(int monthCloses) {
		this.monthCloses = monthCloses;
	}
	public int getDailyFee() {
		return dailyFee;
	}
	public void setDailyFee(int dailyFee) {
		this.dailyFee = dailyFee;
	}
	
	
}
