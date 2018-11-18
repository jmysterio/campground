package com.techelevator;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCCampgroundDAO implements CampgroundDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCCampgroundDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);

	}

	@Override
	public List<Campground> getAllCampgroundsByParkId(int parkId) {
		String sqlQuery = "SELECT * FROM campground WHERE park_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery, parkId);
		List<Campground> campgrounds = new ArrayList<>();
		while (results.next()) {
			Campground campground = new Campground();
			campground.setCampgroundId(results.getInt("campground_id"));
			campground.setParkId(results.getInt("park_id"));
			campground.setName(results.getString("name"));
			String fromMonth = results.getString("open_from_mm");
			String toMonth = results.getString("open_to_mm");
			try {
				campground.setMonthOpens(Integer.parseInt(fromMonth));
			} catch (NumberFormatException e) {
			}
			try {
				campground.setMonthCloses(Integer.parseInt(toMonth));
			} catch (NumberFormatException e) {
			}
			int feeInDollars = (int) (results.getFloat("daily_fee"));
			campground.setDailyFee(feeInDollars);
			campgrounds.add(campground);

		}

		return campgrounds;
	}

}
