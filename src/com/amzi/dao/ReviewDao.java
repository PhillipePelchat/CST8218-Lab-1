package com.amzi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.amzi.models.Review;

public class ReviewDao {

	private static DatabaseDao db;
	private static Connection conn = null;

	/**
	 * Add a Review to the database table
	 * 
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
			String sql = "INSERT INTO Review (ReviewBody, idUser, idRestaurant, DateCreated) VALUES"
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
	 * Delete review by Id
	 * @param reviewId
	 * @return The number of rows affected
	 */
	public static int deleteReview(int reviewId){
		db = new DatabaseDao();
		conn = db.getConnection();
		PreparedStatement ps = null;
		int rowCount = 0;
		
		String deleteRowSql = "DELETE FROM Review WHERE idReview=?";
		
		try {
			ps = conn.prepareStatement(deleteRowSql);
			ps.setInt(1, reviewId);

			rowCount = ps.executeUpdate();
			

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
		return rowCount;
	}
	
	/**
	 * Fetch ONE Review model for rendering its own page
	 * 
	 * @param int
	 *            reviewId
	 * @return Review, null if no review with that ID exists
	 */
	public static Review getReview(int reviewId) {
		Review review = null;

		db = new DatabaseDao();
		conn = db.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "SELECT idReview, idUser, idRestaurant, ReviewBody, DateCreated, Username FROM Review "
				+ "INNER JOIN Account " + "ON Account.idAccount=idUser " + "WHERE Review.idReview=?;";

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
				int idReview = rs.getInt("idReview");

				review = new Review(idReview, body, dateCreated, authorName, restaurantId, authorId);
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
	 * Fetch ALL the reviews in the database. <br/>
	 * Columns: idUser, idRestaurant, ReviewBody, DateCreated, Username
	 * 
	 * @return ResultSet, null if no rows found (shouldn't happen, otherwise
	 *         something is very wrong)
	 */
	public static ArrayList<Review> getReviewResultSet() {
		db = new DatabaseDao();
		conn = db.getConnection();
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		ArrayList<Review> result = new ArrayList<>();

		String sql = "SELECT idReview, idUser, idRestaurant, ReviewBody, DateCreated, Username FROM Review "
				+ "INNER JOIN Account " + "ON Account.idAccount=idUser "
				+ "AND DateCreated BETWEEN '1970-01-010 00:00:00' AND NOW() " + "ORDER BY DateCreated desc;";

		try {
			ps = conn.prepareStatement(sql);
			resultSet = ps.executeQuery();
			while (resultSet.next()) {
				Review review = new Review(resultSet.getInt("idReview"), resultSet.getString("ReviewBody"), resultSet.getString("DateCreated"),
						resultSet.getString("Username"), resultSet.getInt("idRestaurant"), resultSet.getInt("idUser"));
				result.add(review);
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

		return result;
	}

	/**
	 * Get Reviews from User based on ID. Columns: idUser, idRestaurant,
	 * ReviewBody, DateCreated, Username
	 * 
	 * @return ResultSet, null if no rows found
	 */
	public static ArrayList<Review> getReviewResultSetByUser(int UserId) {
		db = new DatabaseDao();
		conn = db.getConnection();
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		ArrayList<Review> result = new ArrayList<>();
		String sql = "SELECT idReview, idUser, idRestaurant, ReviewBody, DateCreated, Username FROM Review "
				+ "INNER JOIN Account " + "ON Account.idAccount=idUser " + "WHERE idUser=? "
				+ "AND DateCreated BETWEEN '1970-01-00 00:00:00' AND NOW() " + "ORDER BY DateCreated desc;";

		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, UserId);

			resultSet = ps.executeQuery();

			while (resultSet.next()) {
				Review review = new Review(resultSet.getInt("idReview"), resultSet.getString("ReviewBody"), resultSet.getString("DateCreated"),
						resultSet.getString("Username"), resultSet.getInt("idRestaurant"), resultSet.getInt("idUser"));
				result.add(review);
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

		return result;
	}

	/**
	 * Returns all reviews tied to a restaurant
	 * 
	 * @param restaurantId
	 * @return ArrayList<Review>
	 */
	public static ArrayList<Review> getReviewResultSetByRestaurant(int restaurantId) {
		db = new DatabaseDao();
		conn = db.getConnection();
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		ArrayList<Review> reviewList = new ArrayList<>();

		String sql = "SELECT idReview, idUser, idRestaurant, ReviewBody, DateCreated, Username FROM Review "
				+ "INNER JOIN Account " + "ON Account.idAccount=idUser " + "WHERE idRestaurant=? "
				+ "AND DateCreated BETWEEN '1970-01-00 00:00:00' AND NOW() " + "ORDER BY DateCreated desc;";

		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, restaurantId);

			resultSet = ps.executeQuery();
			while (resultSet.next()) {
				Review review = new Review(resultSet.getInt("idReview"), resultSet.getString("ReviewBody"), resultSet.getString("DateCreated"),
						resultSet.getString("Username"), resultSet.getInt("idRestaurant"), resultSet.getInt("idUser"));
				reviewList.add(review);
			}
		} catch (Exception e) {
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
		return reviewList;
	}

	/**
	 * Sends a vote update to the Vote table. If the voter already voted with
	 * that value, remove the vote from the table<br>
	 * If the value is the opposite, update the row instead
	 * 
	 * @param value
	 * @param accountId
	 * @param restaurantId
	 * @return How many rows were updated
	 */
	public static int doVote(int value, int accountId, int restaurantId) {
		db = new DatabaseDao();
		conn = db.getConnection();
		PreparedStatement ps = null;
		ResultSet r = null;

		int rowCount = 0, voteValue = 0;
		boolean delete = false;

		String voteInsertsql = "INSERT INTO Vote (VoteValue, idVoterAccount, idVotedRestaurant)"
				+ "VALUES (?,?,?)";

		String updateRowSql = "UPDATE Vote SET VoteValue=? WHERE idVoterAccount=? AND idVotedRestaurant=?";

		String deleteRowSql = "DELETE FROM Vote WHERE idVoterAccount=? AND idVotedRestaurant=?";

		String voteCheckSql = "SELECT * FROM Vote WHERE idVoterAccount=? AND idVotedRestaurant=?";

		try {

			ps = conn.prepareStatement(voteCheckSql);
			ps.setInt(1, accountId);
			ps.setInt(2, restaurantId);
			r = ps.executeQuery();

			voteValue = value;
			
			if (r.next()) {// If the vote exists, check what to do
				int curVote = r.getInt("VoteValue");
				delete = (curVote > 0 && value > 0) || (curVote < 0 && value < 0);

				ps.close(); // Cleanup

				if (delete) { // Vote is the same, remove it
					ps = conn.prepareStatement(deleteRowSql);
					ps.setInt(1, accountId);
					ps.setInt(2, restaurantId);
					rowCount = ps.executeUpdate();
				} else { // Vote is new value, update it
					ps = conn.prepareStatement(updateRowSql);
					ps.setInt(1, value);
					ps.setInt(2, accountId);
					ps.setInt(3, restaurantId);
					rowCount = ps.executeUpdate();
				}

			}

			else {
				// Set new vote
				ps.close();
				
				ps = conn.prepareStatement(voteInsertsql);

				ps.setInt(1, voteValue);
				ps.setInt(2, accountId);
				ps.setInt(3, restaurantId);

				rowCount = ps.executeUpdate();

			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (r != null) {
				try {
					r.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			db.closeConnection();
		} // end try/catch/finally
		return rowCount;
	}

	public static int hasVoted(int accountId, int restaurantId) {
		db = new DatabaseDao();
		conn = db.getConnection();
		PreparedStatement ps = null;
		ResultSet r = null;
		int voteValue = 0;

		String sql = "SELECT * FROM `mealreview`.`vote`" + "WHERE idVoterAccount=? AND idVotedRestaurant=?";
		try {
			ps = conn.prepareStatement(sql);
			r = ps.executeQuery();

			if (r.next()) {
				voteValue = r.getInt("VotedValue");
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (r != null) {
				try {
					r.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			db.closeConnection();
		} // end try/catch/finally
		return voteValue;
	}
}
