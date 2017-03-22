package com.amzi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.amzi.models.Restaurant;
import com.amzi.dao.ReviewDao;

public class RestaurantDao {

	private static DatabaseDao db;
	private static Connection conn = null;

	/**
	 * Fetches a restaurant row from the database
	 * @param restaurantId
	 * @return If the SQL query returns a restaurant, a Restaurant class is
	 *         returned.<br> NULL if no restaurant is found
	 */
	public static Restaurant getRestaurant(int restaurantId) {
		db = new DatabaseDao();
		conn = db.getConnection();
		PreparedStatement ps = null;
		ResultSet rs, rs2 = null;
		Restaurant r = null;

		String sql =  "SELECT * FROM Restaurant WHERE idRestaurant=?"; // Query to find restaurant
		String sql2 = "SELECT * FROM Vote WHERE idRestaurant=?"; //Query to fetch votes for restaurant
		try {
			db = new DatabaseDao();
			conn = db.getConnection();

			ps = conn.prepareStatement(sql);
			ps.setInt(1, restaurantId);
			rs = ps.executeQuery();
			
			// Populate review list
			if (rs.next()) {
				r = new Restaurant(rs.getInt("idRestaurant"), rs.getString("Name"), rs.getString("Address"),
						rs.getString("PhoneNum"), rs.getString("Website"), null);

				r.setReviews(ReviewDao.getReviewResultSetByRestaurant(r.getId()));
			}
			
			// Set up vote and down vote counts
			if (r != null){
				ps.close();
				ps = conn.prepareStatement(sql2);
				ps.setInt(1, restaurantId);
				
				rs2 = ps.executeQuery();
				
				int upVote = 0, downVote = 0;
				
				while (rs2.next()){
					 if (rs2.getInt("VoteValue") > 0)
						 upVote++;
					 else downVote++;
				}
				r.setUpVote(upVote);
				r.setDownVote(downVote);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return r;
	}
}
