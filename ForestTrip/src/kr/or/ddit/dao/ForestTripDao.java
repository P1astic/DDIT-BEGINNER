package kr.or.ddit.dao;

public class ForestTripDao {
	private static ForestTripDao instance;

	private ForestTripDao() {

	}

	public static ForestTripDao getInstance() {
		if (instance == null) {
			instance = new ForestTripDao();
		}
		return instance;
	}
}
