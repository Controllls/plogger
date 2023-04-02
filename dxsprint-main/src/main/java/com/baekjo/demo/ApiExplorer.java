package com.baekjo.demo;
//
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.io.BufferedReader;
//import java.io.IOException;
//
//public class ApiExplorer {
//    public static void main(String[] args) throws IOException {
//        StringBuilder urlBuilder = new StringBuilder("http://api.data.go.kr/openapi/tn_pubr_public_cty_park_info_api"); /*URL*/
////        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + URLEncoder.encode("+Ze+bYxxJLfg4GU3pEVowLpnlckYxXikp44xOsuubQ0H8i8DVEBr/I6qNfeqGW58qXirkTMWsCnKIktDyRlMfQ==","UTF-8")); /*Service Key*/
//        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + URLEncoder.encode("UCitYWu9q9F7hVybg4d6UaMtXYe0lX2yDHYS/KG14FBYl7L+DpgkS9z3PWNYteqXNF97ZPHiNUGSK9gNf7y0og==","UTF-8")); /*Service Key*/
////        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + URLEncoder.encode("%2BZe%2BbYxxJLfg4GU3pEVowLpnlckYxXikp44xOsuubQ0H8i8DVEBr%2FI6qNfeqGW58qXirkTMWsCnKIktDyRlMfQ%3D%3D","UTF-8")); /*Service Key*/
////        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=UCitYWu9q9F7hVybg4d6UaMtXYe0lX2yDHYS%2FKG14FBYl7L%2BDpgkS9z3PWNYteqXNF97ZPHiNUGSK9gNf7y0og%3D%3D");
//        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("0", "UTF-8")); /*페이지 번호*/
//        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*한 페이지 결과 수*/
//        urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*XML/JSON 여부*/
//        urlBuilder.append("&" + URLEncoder.encode("manageNo","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*관리번호*/
//        urlBuilder.append("&" + URLEncoder.encode("parkNm","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*공원명*/
//        urlBuilder.append("&" + URLEncoder.encode("parkSe","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*공원구분*/
//        urlBuilder.append("&" + URLEncoder.encode("rdnmadr","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*소재지도로명주소*/
//        urlBuilder.append("&" + URLEncoder.encode("lnmadr","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*소재지지번주소*/
//        urlBuilder.append("&" + URLEncoder.encode("latitude","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*위도*/
//        urlBuilder.append("&" + URLEncoder.encode("longitude","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*경도*/
//        urlBuilder.append("&" + URLEncoder.encode("parkAr","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*공원면적*/
//        urlBuilder.append("&" + URLEncoder.encode("mvmFclty","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*공원보유시설(운동시설)*/
//        urlBuilder.append("&" + URLEncoder.encode("amsmtFclty","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*공원보유시설(유희시설)*/
//        urlBuilder.append("&" + URLEncoder.encode("cnvnncFclty","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*공원보유시설(편익시설)*/
//        urlBuilder.append("&" + URLEncoder.encode("cltrFclty","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*공원보유시설(교양시설)*/
//        urlBuilder.append("&" + URLEncoder.encode("etcFclty","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*공원보유시설(기타시설)*/
//        urlBuilder.append("&" + URLEncoder.encode("appnNtfcDate","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*지정고시일*/
//        urlBuilder.append("&" + URLEncoder.encode("institutionNm","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*관리기관명*/
//        urlBuilder.append("&" + URLEncoder.encode("phoneNumber","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*전화번호*/
//        urlBuilder.append("&" + URLEncoder.encode("referenceDate","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*데이터기준일자*/
//        urlBuilder.append("&" + URLEncoder.encode("instt_code","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*제공기관코드*/
//        URL url = new URL(urlBuilder.toString());
//        System.out.println(url);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("GET");
//        conn.setRequestProperty("Content-type", "application/json");
//        System.out.println("Response code: " + conn.getResponseCode());
//        BufferedReader rd;
//        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
//            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//        } else {
//            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
//        }
//        StringBuilder sb = new StringBuilder();
//        String line;
//        while ((line = rd.readLine()) != null) {
//            sb.append(line);
//        }
//        rd.close();
//        conn.disconnect();
//        System.out.println(sb.toString());
//    }
//}

import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.URL;
        import java.net.URLEncoder;
        import java.io.BufferedReader;
        import java.io.IOException;

public class ApiExplorer {
    public static void main(String[] args) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=UCitYWu9q9F7hVybg4d6UaMtXYe0lX2yDHYS%2FKG14FBYl7L%2BDpgkS9z3PWNYteqXNF97ZPHiNUGSK9gNf7y0og%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode("20220809", "UTF-8")); /*‘21년 6월 28일 발표*/
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode("0600", "UTF-8")); /*06시 발표(정시단위) */
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode("98", "UTF-8")); /*예보지점의 X 좌표값*/
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode("78", "UTF-8")); /*예보지점의 Y 좌표값*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
    }
}