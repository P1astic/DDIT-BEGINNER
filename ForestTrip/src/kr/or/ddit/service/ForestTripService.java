package kr.or.ddit.service;

public class ForestTripService {
	
	private static ForestTripService instance;

	private ForestTripService() {

	}

	public static ForestTripService getInstance() {
		if (instance == null) {
			instance = new ForestTripService();
		}
		return instance;
	}
	
}
