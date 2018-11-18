package com.techelevator;

public class Site {

	private int siteId;
	private int campgroundId;
	private int siteNumber;
	private int maxOccupancy;
	private boolean accessible;
	private int maxRVLength;
	private boolean utilities;

	public Site() {

	}

	public Site(int siteId, int campgroundId, int siteNumber, int maxOccupancy, boolean accessible, int maxRVLength,
			boolean utilities) {
		this.siteId = siteId;
		this.campgroundId = campgroundId;
		this.siteNumber = siteNumber;
		this.maxOccupancy = maxOccupancy;
		this.accessible = accessible;
		this.maxRVLength = maxRVLength;
		this.utilities = utilities;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Site))
			return false;
		else {
			Site other = (Site) o;
			return siteId == other.getSiteId() && campgroundId == other.getCampgroundId()
					&& siteNumber == other.getSiteNumber() && maxOccupancy == other.getMaxOccupancy()
					&& accessible == other.isAccessible() && maxRVLength == other.getMaxRVLength()
					&& utilities == other.isUtilities();
		}
	}

	@Override
	public int hashCode() {
		return siteId + campgroundId + siteNumber + maxOccupancy + maxRVLength;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public int getCampgroundId() {
		return campgroundId;
	}

	public void setCampgroundId(int campgroundId) {
		this.campgroundId = campgroundId;
	}

	public int getSiteNumber() {
		return siteNumber;
	}

	public void setSiteNumber(int siteNumber) {
		this.siteNumber = siteNumber;
	}

	public int getMaxOccupancy() {
		return maxOccupancy;
	}

	public void setMaxOccupancy(int maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}

	public boolean isAccessible() {
		return accessible;
	}

	public void setAccessible(boolean accessible) {
		this.accessible = accessible;
	}

	public int getMaxRVLength() {
		return maxRVLength;
	}

	public void setMaxRVLength(int maxRVLength) {
		this.maxRVLength = maxRVLength;
	}

	public boolean isUtilities() {
		return utilities;
	}

	public void setUtilities(boolean utilities) {
		this.utilities = utilities;
	}

}
