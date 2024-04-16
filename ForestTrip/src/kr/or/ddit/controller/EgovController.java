package kr.or.ddit.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import kr.or.ddit.dao.ForestTripDao;
import kr.or.ddit.view.ForestTripView;

public class EgovController {

	ForestTripDao forestDao = ForestTripDao.getInstance();

	
	public void EgovController(String word) throws IOException {
		String currentPage = "1"; // 예시로 현재 페이지와 페이지당 출력 개수를 지정, 변수 넣어서 호출하게하기
		String countPerPage = "100";
		String resultType = "json";
		String confmKey = "devU01TX0FVVEgyMDI0MDQwNDIwMzkyMjExNDY2NDE="; // 승인키
		String keyword = word; // 검색할 키워드, null일 경우 경고메시지 try catch로

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
			JSONArray jsonArray = new JSONArray();

			jsonArray.add(jsonObject.get("results"));

			JSONObject juso = (JSONObject) jsonArray.get(0);
			JSONArray jsonArray_juso = new JSONArray();
			jsonArray_juso.add(juso.get("juso"));
			JSONArray jsonArray_totalCount = new JSONArray();
			jsonArray_totalCount.add(juso.get("common"));

			JSONObject totalCount = (JSONObject) jsonArray_totalCount.get(0);
			JSONArray juso_detail = (JSONArray) jsonArray_juso.get(0);

			// totalCount get 알고리즘
//			System.out.println(totalCount.get("totalCount"));

			JSONObject jibunAddr = new JSONObject();
			JSONObject roadAddr = new JSONObject();
			JSONArray jibunAddr_array = new JSONArray();
			JSONArray roadAddr_array = new JSONArray();
			// 상세주소 출력 알고리즘
			for (int i = 0; i < juso_detail.size(); i++) {

//				System.out.println(juso_detail.get(i));
				jibunAddr = (JSONObject) juso_detail.get(i);
				roadAddr = (JSONObject) juso_detail.get(i);
//				System.out.println(i + 1 + "번행 " + jibunAddr.get("jibunAddr"));
//				jibunAddr_array.add(jibunAddr.get("jibunAddr"));
//				System.out.println(i + 1 + "번행 " + roadAddr.get("roadAddr"));
				roadAddr_array.add(roadAddr.get("roadAddr"));
				ForestTripView.address_output.add(roadAddr_array.get(i));
			}
			reader.close();
			// 연결 종료
			connection.disconnect();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
