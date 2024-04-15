package kr.or.ddit.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import kr.or.ddit.dao.ForestTripDao;

public class EgovController {
	public static void main(String[] args) throws IOException {

		String currentPage = "1"; // 예시로 현재 페이지와 페이지당 출력 개수를 지정
		String countPerPage = "10";
		String hstryYn = "Y";
		String resultType = "json";
		String confmKey = "devU01TX0FVVEgyMDI0MDQwNDIwMzkyMjExNDY2NDE="; // 승인키
		String keyword = "도움 1로"; // 검색할 키워드

		String apiUrl = "https://business.juso.go.kr/addrlink/addrLinkApi.do?currentPage=" + currentPage
				+ "&countPerPage=" + countPerPage + "&keyword=" + URLEncoder.encode(keyword, "UTF-8") + "&confmKey="
				+ confmKey + "&resultType=" + resultType;
//                    "&rhstryYn =" + hstryYn;
//                    + resultType;

		URL url = new URL(apiUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");

		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
//		System.out.println(reader);
		try {

			// JSON 파싱
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
			System.out.println(jsonObject.size());
			reader.close();
			// 연결 종료
			connection.disconnect();
		} catch (ParseException e) {
			e.printStackTrace();
		}


	}

	private static EgovController instance;
	ForestTripDao forestDao = ForestTripDao.getInstance();

	private EgovController() {

	}

	public static EgovController getInstance() {
		if (instance == null) {
			instance = new EgovController();
		}
		return instance;
	}

}
