package com.amzi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.amzi.models.Review;

public class ReviewDao {

	private static DatabaseDao db;
	private static Connection conn = null;

	/**
	 * Add a Review to the database table
	 * @param body
	 * @param userId
	 * @param restaurantId
	 * @return true if row was added, false if nothing changed.
	 */
	public static boolean postReview(String body, int userId, int restaurantId) {
		conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int rowCount = 0;

		try {
			db = new DatabaseDao();
			conn = db.getConnection();

			// TODO: Post Review
			String sql = "INSERT INTO `mealreview`.`Review` " + "(ReviewBody, idUser, idRestaurant, DateCreated) VALUES"
					+ "(?, ?, ?, NOW()); ";
			pst = conn.prepareStatement(sql);
			pst.setString(1, body);
			pst.setInt(2, userId);
			pst.setInt(3, restaurantId);

			rowCount = pst.executeUpdate();

		} catch (Exception e) {
			System.err.println(e);
		} finally {
			if (pst != null) {
				try {
					pst.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			db.closeConnection();

		}
		return rowCount > 0;
	}
	/**
	 * Fetch ONE Review model for rendering its own page
	 * @param int reviewId
	 * @return Review, null if no review with that ID exists 
	 */
	public static Review getReview(int reviewId) {
		Review review = null;

		db = new DatabaseDao();
		conn = db.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "SELECT idUser, idRestaurant, ReviewBody, DateCreated, Username FROM Review "
				+ "INNER JOIN Account "
				+ "ON Account.idAccount=idUser "
				+ "WHERE Review.idReview=?;";

		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, reviewId);

			rs = ps.executeQuery();
			if (rs.getRow() != 0) {
				String body = rs.getString("ReviewBody");
				String dateCreated = rs.getString("DateCreated");
				String authorName = rs.getString("Username");
				int restaurantId = rs.getInt("restaurantId");
				int authorId = rs.getInt("idUser");

				review = new Review(body, dateCreated, authorName, restaurantId, authorId);
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
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			db.closeConnection();
		}

		return review;
	}

	/**
	 * Fetch ALL the reviews in the database
	 * @return ResultSet, null if no rows found (shouldn't happen, otherwise something is very wrong)
	 */
	public static ResultSet getReviewResultSet() {
		db = new DatabaseDao();
		conn = db.getConnection();
		PreparedStatement ps = null;
		ResultSet resultSet = null;

		String sql = "SELECT idUser, idRestaurant, ReviewBody, DateCreated, Username FROM Review"
				+ "INNER JOIN Account "
				+ "ON Account.idAccount=idUser "
				+ "AND DateCreated BETWEEN '1970-01-010 00:00:00' AND NOW() "
				+ "ORDER BY DateCreated desc;";

		try {
			ps = conn.prepareStatement(sql);
			resultSet = ps.executeQuery();
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
			db.closeConnection();
		}

		return resultSet;
	}
	/**
	 * Get Reviews from User based on ID
	 * @return ResultSet, null if no rows found
	 */
	public static ResultSet getReviewResultSetByUser(int UserId) {
		db = new DatabaseDao();
		conn = db.getConnection();
		PreparedStatement ps = null;
		ResultSet resultSet = null;

		String sql = "SELECT idUser, idRestaurant, ReviewBody, DateCreated, Username FROM Review"
				+ "INNER JOIN Account "
				+ "ON Account.idAccount=idUser "
				+ "WHERE idUser=? "
				+ "AND DateCreated BETWEEN '1970-01-010 00:00:00' AND NOW() "
				+ "ORDER BY DateCreated desc;";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, UserId);
			
			resultSet = ps.executeQuery();
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
			db.closeConnection();
		}

		return resultSet;
	}
}
