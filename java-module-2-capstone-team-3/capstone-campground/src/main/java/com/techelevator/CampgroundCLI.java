package com.techelevator;

import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

public class CampgroundCLI {
	final static int[] DISPLAY_CAMPGROUNDS_COL_WIDTH = { 35, 10, 10, 10 };
	final static int[] DISPLAY_SITES_COL_WIDTH = { 15, 15, 15, 15, 15, 15 };
	//final static int[] DISPLAY_RES_SEARCH_COL_WIDTH = { 20, 15, 15, 15, 15, 15, 15 };
	final static int PARK_INFO_COL_WIDTH = 25;

	private Menu menu = new Menu(System.in, System.out);
	private ParkDAO parkDao;
	private CampgroundDAO campgroundDao;
	private SiteDAO siteDao;
	private ReservationDAO reservationDao;

	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run();
	}

	public CampgroundCLI(DataSource datasource) {
		parkDao 		= new JDBCParkDAO(datasource);
		campgroundDao 	= new JDBCCampgroundDAO(datasource);
		siteDao 		= new JDBCSiteDAO(datasource);
		reservationDao 	= new JDBCReservationDAO(datasource);
	}

	public void run() {
		mainMenu();

	}

	private void mainMenu() {
		System.out.println("\nSelect A Park For Further Details");
		List<Park> allParks = parkDao.getAllParks();
		String[] parkNames = new String[allParks.size() + 1];
		int i = 0;
		for (Park p : allParks) {
			parkNames[i] = p.getName();
			i++;
		}
		parkNames[i] = "Quit";
		int choice = menu.getIntChoiceFromOptions(parkNames);
		if (choice == i) {
			System.out.println("GoodBye!!!!");
			System.exit(0);
		} else {
			Park selectedPark = allParks.get(choice);
			parkInfoMenu(selectedPark);
		}

	}

	private void parkInfoMenu(Park park) {
		System.out.println("\n" + displayParkInformation(park));
		System.out.println("\n " + wrap(park.getDescription(), 100) + "\n");
		System.out.println("Select a Command");
		String[] options = { "View Campgrounds", "Search for Reservation", "Return to Previous Screen" };
		int choice = menu.getIntChoiceFromOptions(options);

		switch (choice) {
		case 0:
			viewCampgroundsMenu(park);
			break;
		case 1:
			searchForReservation(park);
			break;
		case 2:
			mainMenu();
			break;
		}

	}

	private void viewCampgroundsMenu(Park park) {
		System.out.println("\n" + park.getName() + " National Park Campgrounds \n");
		List<Campground> campgroundList = campgroundDao.getAllCampgroundsByParkId(park.getParkId());
		displayAllCampgrounds(campgroundList);
		System.out.println("\nSelect a Command");
		String[] options = { "Search for Available Reservation", "Return to Previous Screen" };
		int choice = menu.getIntChoiceFromOptions(options);

		switch (choice) {
		case 0:
			searchForReservation(park);
			break;
		case 1:
			parkInfoMenu(park);
			break;
		}
	}

	private void searchForReservation(Park park) {
		System.out.println("\n" + park.getName() + " National Park Campgrounds \n");
		List<Campground> campgroundList = campgroundDao.getAllCampgroundsByParkId(park.getParkId());
		int numberOfCampgrounds = campgroundDao.getAllCampgroundsByParkId(park.getParkId()).size();
		displayAllCampgrounds(campgroundList);
		System.out.println();

		int campgroundChoice = menu.getIntChoiceFromPrompt("Which campground (enter 0 to cancel)?", 0,
				numberOfCampgrounds);
		if (campgroundChoice == 0) {
			parkInfoMenu(park);
		} else {
			Campground choice = campgroundList.get(campgroundChoice - 1);
			LocalDate fromDate = menu.getFutureDateFromPrompt("What is the arrival date? (mm/dd/yyyy)");
			LocalDate toDate = fromDate;
			while (toDate.compareTo(fromDate) <= 0) {
				toDate = menu.getFutureDateFromPrompt("What is the departure date? (mm/dd/yyyy)");

				if (toDate.compareTo(fromDate) <= 0) {
					System.out.println("Departure date must be after arrival date.\n");
				}
			}
			siteSearchResultsMenu(park, choice, fromDate, toDate);
		}
	}

	private void siteSearchResultsMenu(Park park, Campground campground, LocalDate fromDate, LocalDate toDate) {
		List<Site> siteSearchResults = siteDao.getSitesByDateRange(campground.getCampgroundId(), fromDate, toDate);
		if (siteSearchResults.isEmpty()) {
			System.out.println("No sites available.");
			searchForReservation(park);
		}
		
		System.out.println("\nResults Maching Your Search Criteria\n");
		String[] columnHeaders = {"Site No.", "Max Occup.", "Accessible?", "Max RV Length", "Utilities", "Cost"};
		System.out.println(displayColumns(DISPLAY_SITES_COL_WIDTH, columnHeaders));
		for (Site s : siteSearchResults) {
			System.out.println(displaySiteWithPrice(s, campground.getDailyFee()));
		}
		Site selectedSite = null;
		while (selectedSite == null) {
			int choice = menu.getIntChoiceFromPrompt("\nWhich site should be reserved (enter 0 to cancel)?", 0,
					Integer.MAX_VALUE);
			if (choice == 0) {
				searchForReservation(park);
			}
			for (Site s : siteSearchResults) {
				if (s.getSiteId() == choice) {
					selectedSite = s;
					break;
				}
			}
			if (selectedSite == null) {
				System.out.println("\nInvalid site number choice.\n");
			}
		}

		String reservationName = menu.getUserStringInput("What name should the reservation be made under?");
		Reservation reservation = new Reservation(selectedSite.getSiteId(), reservationName, fromDate, toDate);
		int resId = reservationDao.createReservation(reservation);

		System.out.println("The reservation has been made and the confirmation id is " + resId);

		mainMenu();
	}

	private static String displayParkInformation(Park park) {
		int width = PARK_INFO_COL_WIDTH;

		StringBuilder sb = new StringBuilder();
		sb.append(park.getName() + " National Park\n\n");
		sb.append(padColumn("Location:", width) + park.getLocation() + "\n");
		sb.append(padColumn("Established:", width) + park.getEstablishDate() + "\n");
		sb.append(padColumn("Area:", width) + park.getArea() + " sq km\n");
		sb.append(padColumn("Annual Visitors:", width) + park.getVisitors() + "\n");

		return sb.toString();
	}

	private static String displayCampground(Campground campground) {
		String[] items = { campground.getName(), getMonthFromInt(campground.getMonthOpens()),
				getMonthFromInt(campground.getMonthCloses()), formatMoney(campground.getDailyFee()) };
		return displayColumns(DISPLAY_CAMPGROUNDS_COL_WIDTH, items);
	}

	private void displayAllCampgrounds(List<Campground> campgroundList) {
		int numberColWidth = 6;
		String[] columnHeaders = { "Name", "Open", "Close", "Daily Fee" };
		System.out.print(padColumn("", numberColWidth));
		System.out.println(displayColumns(DISPLAY_CAMPGROUNDS_COL_WIDTH, columnHeaders));

		int i = 1;
		for (Campground c : campgroundList) {
			System.out.print(padColumn("#" + i, numberColWidth));
			System.out.println(displayCampground(c));
			i++;
		}
	}

	private static String displaySiteWithPrice(Site site, int dollarPrice) {
		String[] items = { "" + site.getSiteId(), "" + site.getMaxOccupancy(), site.isAccessible() ? "Yes" : "No",
				site.getMaxRVLength() > 0 ? "" + site.getMaxRVLength() : "N/A", site.isUtilities() ? "Yes" : "N/A",
				formatMoney(dollarPrice) };
		return displayColumns(DISPLAY_SITES_COL_WIDTH, items);
	}

	/*
	 private static String displayReservationSearch(Campground campground, Site site) {
	 
		String[] items = { campground.getName(), "" + site.getSiteId(), "" + site.getMaxOccupancy(),
				site.isAccessible() ? "Yes" : "No", site.getMaxRVLength() > 0 ? "" + site.getMaxRVLength() : "N/A",
				site.isUtilities() ? "Yes" : "N/A", formatMoney(campground.getDailyFee()) };
		return displayColumns(DISPLAY_RES_SEARCH_COL_WIDTH, items);
	}
	 */
	private static String wrap(String str, int width) {
		/*
		 * int index = 0; StringBuilder sb = new StringBuilder(); while (index <
		 * str.length()){ int lastIndexOfSpace = 0; for (int i = index; i < index +
		 * width && i < str.length(); i++){ if (str.charAt(i) == ' ') lastIndexOfSpace =
		 * i; } if (lastIndexOfSpace == 0) lastIndexOfSpace = index + width;
		 * sb.append(str.subSequence(index, lastIndexOfSpace + 1)); index =
		 * lastIndexOfSpace; } return sb.toString();
		 */
		return str;
	}

	// Formats the given strings and column widths as a single table row padded with
	// spaces.
	private static String displayColumns(int[] colWidths, String[] items) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < colWidths.length && i < items.length; i++) {
			sb.append(padColumn(items[i], colWidths[i]));
		}
		return sb.toString();
	}

	// Produces a column of width exactly 'width', padded on the right with spaces.
	// if str.length() > width, the end of the string will be cut off.
	// A valid width is between 1 and 200.
	// If an invalid width is provided, 30 is used by default.
	private static String padColumn(String str, int width) {
		if (width <= 0 || width > 200)
			width = 30;
		return String.format("%1$-" + width + "." + width + "s", str);
	}

	// 01 -> "January"
	// 02 -> "Feburary"
	// etc.
	// returns "?MONTH?" if monthOfYear is not between 1 and 12, inclusive
	private static String getMonthFromInt(int monthOfYear) {
		if (monthOfYear < 1 || monthOfYear > 12)
			return "?MONTH?";
		java.time.Month month = java.time.Month.of(monthOfYear);
		return month.getDisplayName(java.time.format.TextStyle.FULL, java.util.Locale.ENGLISH);
	}

	// 5 -> "$5.00"
	private static String formatMoney(int dollars) {
		return String.format("$%.2f", (double) dollars);
	}
}
