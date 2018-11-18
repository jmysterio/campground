package com.techelevator;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCReservationDAO implements ReservationDAO {
	
	private JdbcTemplate jdbcTemplate;

	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	//Returns -1 if reservation could not be created.
	@Override
	public int createReservation(Reservation reservation) {
		int reservationNumber = -1;
		String sqlQuery = "INSERT INTO reservation (site_id, name, from_date, to_date) " +
					" VALUES (?, ?, ?, ?)";
		jdbcTemplate.update(sqlQuery, reservation.getSiteId(), reservation.getName(),
				reservation.getFromDate(), reservation.getToDate());
		
		sqlQuery = "SELECT reservation_id from reservation WHERE site_id = ? AND "
				+ "name = ? AND from_date = ? AND to_date = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sqlQuery, reservation.getSiteId(), reservation.getName(),
				reservation.getFromDate(), reservation.getToDate());
		if (result.next())
			reservationNumber = result.getInt("reservation_id");
//		else {
//			throw new SQLException ("Could not create reservation");
//		}
		return reservationNumber;
	}

}
