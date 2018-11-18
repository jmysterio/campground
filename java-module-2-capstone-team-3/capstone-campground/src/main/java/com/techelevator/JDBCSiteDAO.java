package com.techelevator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCSiteDAO implements SiteDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCSiteDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Site> getSitesByDateRange(int campgroundId, LocalDate fromDate, LocalDate toDate) {
		String sqlQueryNonOverlappingReservations = 
				"SELECT * FROM site where site.site_id NOT IN ( " +
		        "SELECT site.site_id from site " +
		        "INNER JOIN campground " +
		        "ON site.campground_id = campground.campground_id " +
		        "INNER JOIN reservation ON site.site_id = reservation.site_id " +
		        "WHERE site.campground_id = ? AND reservation_id IS NULL " +
		        "OR NOT" +
		        "(? NOT BETWEEN from_date AND to_date "  +
		        "AND ? NOT BETWEEN from_date AND to_date " +
		        "AND from_date NOT BETWEEN ? AND ?))" +
		        "AND site.campground_id = ? " +
				"ORDER BY site.site_id";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sqlQueryNonOverlappingReservations, campgroundId, fromDate, toDate, fromDate, toDate, campgroundId);
		List<Site> sites = new ArrayList<>();
		while (result.next()) {
			Site site = new Site();
			site.setSiteId(result.getInt("site_id"));
			site.setCampgroundId(result.getInt("campground_id"));
			site.setSiteNumber(result.getInt("site_number"));
			site.setMaxOccupancy(result.getInt("max_occupancy"));
			site.setAccessible(result.getBoolean("accessible"));
			site.setMaxRVLength(result.getInt("max_rv_length"));
			site.setUtilities(result.getBoolean("utilities"));
			sites.add(site);

		}
		return sites;
	}

}
