package project01;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/*
주소를 입력하여 위도와 경도를 추출하는 프로젝트

## Nanver Map API

-사용하는 API
geocoding : 주소 검색 API는 지번, 도로명를 질의어로 사용해서 주소 정보를 검색합니다. 검색 결과로 주소 목록과 세부 정보를 JSON 형태로 반환합니다.
static Map : 요청된 URL 매개변수를 기반으로 웹 페이지에 표시할 수 있는 이미지로 지도를 반환합니다.


HttpURLConnection이용해서 접근(URL 연결) -> Naver Maps API를 사용한다.


 */
public class Project01_D {

    public static void main(String[] args) {

        //주소 -> 위도, 경도 좌표 얻어오기   io로 얻어오기
        BufferedReader io = new BufferedReader(new InputStreamReader(System.in));

        String clientId = "{앱 등록 시 발급받은 Client ID}";
        String clientSecret = "{앱 등록 시 발급 받은 Client Secret}";

        try {
            System.out.print("주소를 입력하세요 : ");
            String address = io.readLine(); //io에서 주소를 읽어온다.
            String addr = URLEncoder.encode(address, "UTF-8"); //address를 인코딩 이유 :  입력 공백도 문자처리 해줘야함
            String apiURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + addr; // jso

            URL url = new URL(apiURL); // URL이 정확한 것인지 확인하는 URL객체 생성
            HttpURLConnection con = (HttpURLConnection) url.openConnection(); // 변수 url애 담겨있는 주소를 갖고 openConnection을 한다. 즉,연결한다.
            con.setRequestMethod("GET"); // GET방식으로 요청
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId); //X-NCP-APIGW-API-KEY-ID <- 헤더의 이름
            con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret); // X-NCP-APIGW-API-KEY <- 헤더의 이름


            int responseCode = con.getResponseCode(); // 보낸 신호를 정상적으로 연결이되면 200이라는 신호를 보내준다.
            BufferedReader br;
            if (responseCode == 200) { // 200 -> 정상 호출
                //한 라인씩

                //con(네이버 지도 API 연결된 변수)에서 데이터를 읽는다. getInputStream()을 통해서  byteStream으로 읽는다. 그리고 한글데이터가 있을 경우 깨지기 때문에 UTF-8을 옵션으로 넣어준다.
                //byteStream로 받은 데이터를 문자단위로 바꿔줘야한다 따라서 InputStreamReader를하면 문자스트림으로 바꾸는 역할을 해준다.
                // BufferReader쓴 이유는 라인 단위로 읽어드리기 위해서 썻다.
                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            } else {
                //에러가 날 경우 : 에러 메시지 사용
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine; //한 라인씩 저장할 변수
            //서버에서 넘겨주는 데이터를 받는 역할
            StringBuffer response = new StringBuffer(); // 문자열 추가 변경시 사용 , response에 데이터를 넣어준다.

            while ((inputLine = br.readLine()) != null) { // 반복문으로 한 줄씩 읽어서 처리한다.
                response.append(inputLine); //한 줄씩 넣어준다.
            }
            br.close();


            //지도 이미지 생성 메소드 호출
            JSONTokener tokener = new JSONTokener(response.toString()); //
            JSONObject object = new JSONObject(tokener); //object로 바꿔준다.
            //System.out.println(object.toString());

            JSONArray arr = object.getJSONArray("addresses"); // addresses를 키값으로 빼낸다.
            for (int i = 0; i < arr.length(); i++) {
                JSONObject temp = (JSONObject) arr.get(i); //다운캐스팅
                System.out.println("address : " + temp.get("roadAddress"));
                System.out.println("JibunAddress : " + temp.get("jibunAddress"));
                System.out.println("경도 : " + temp.get("x"));
                System.out.println("위도 : " + temp.get("y"));
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}