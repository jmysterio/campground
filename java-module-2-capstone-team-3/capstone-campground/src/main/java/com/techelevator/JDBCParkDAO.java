package com.techelevator;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCParkDAO implements ParkDAO {
	
	private JdbcTemplate jdbcTemplate;

	public JDBCParkDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		
	}

	@Override
	public List<Park> getAllParks() {
		String sqlQuery = "SELECT * FROM park ORDER BY name ASC";
		List<Park> parks = new ArrayList<>();
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery);
		while(results.next()) {
			Park currentPark = new Park();
			currentPark.setParkId(results.getInt("park_id"));
			currentPark.setName(results.getString("name"));
			currentPark.setLocation(results.getString("location"));
			currentPark.setEstablishDate(results.getDate("establish_date").toLocalDate());
			currentPark.setArea(results.getInt("area"));
			currentPark.setVisitors(results.getInt("visitors"));
			currentPark.setDescription(results.getString("description"));
			parks.add(currentPark);
		}
		return parks;
	}

}
