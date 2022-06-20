package project01.project01_E;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

/*
5. 위도와 경도를 추출 후 지도를 이미지로 저장하기 (Console Version)

static map 사용한다.

-static map 이란?
요청된 URL 매개변수를 기반으로 웹 페이지에 표시할 수 있는 이미지로 지도를 반환합니다.

네이버 지도에서는 JavaScript를 사용하지 않고, 웹 페이지에서 네이버 지도를 이용해 쉽게 위치를 표시하고 안내할 수 있는 Static Map(정적 지도) 서비스를 제공합니다.
HTML 페이지 내에 원하는 이미지를 가져오려면 요청 형식에 맞는 URL을 만들어 <img> 태그에 배치하면 됩니다.



 */
public class Project01_E {

    //지도 이미지 생성 메서드
    //주소의 위치를 이미지로 생성하는 메소드
    public static void map_service(String point_x, String point_y,  String address) {
        //
        String URL_STATICMAP = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?";
        try {
            String pos=URLEncoder.encode(point_x + " " + point_y, "UTF-8");
            String url = URL_STATICMAP;
            url += "center=" + point_x + "," + point_y;
            url += "&level=16&w=700&h=500";
            url += "&markers=type:t|size:mid|pos:"+pos+"|label:"+URLEncoder.encode(address, "UTF-8");

            //
            URL u = new URL(url);
            HttpURLConnection con = (HttpURLConnection)u.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", "{앱 등록 시 발급받은 Client ID");
            con.setRequestProperty("X-NCP-APIGW-API-KEY", "{앱 등록 시 발급 받은 Client Secret}");
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) {
                InputStream is = con.getInputStream();
                int read = 0;
                byte[] bytes = new byte[1024];

                String tempname = Long.valueOf(new Date().getTime()).toString();
                File f = new File(tempname + ".jpg");
                f.createNewFile();
                OutputStream outputStream = new FileOutputStream(f);
                while ((read =is.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
                is.close();
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                System.out.println(response.toString());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        //Systme.in 으로 입력된 값은 byteStream이다.
        //그래서 InputStreamReader로 써서 문자스트림으로 바꾼다.
        //문자스트림을 읽을 때, BufferReader로 읽는다.
        BufferedReader io = new BufferedReader(new InputStreamReader(System.in));

        String clientId = "yh1oswg56e"; // API인증요청 ID
        String clientSecret = "cXPkfCfTFfgEMajDhSZaP0FPnxy9fwWNr3UfcvpK"; // API 인증 요청 비밀번호

        try {
            System.out.print("주소를 입력하세요 : ");
            String address = io.readLine(); // io 데이터를 한 줄씩 읽어 간다
            String addr = URLEncoder.encode(address, "UTF-8"); // 인코딩해주는 이유:  공백도 주소로 만들어야하기 때문이다.
            String apiURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + addr; // api 주소

            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            con.setRequestProperty("X-NCP-APIGW-API-KEY",clientSecret);

            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) { // 신호 제대로 받음
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            }else{
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            String line ; //한 라인씩 추출한것을 저장하는 변수

            StringBuffer response = new StringBuffer();


            // 추가된 부분 -> 이미지를 출력하기 위해서 추가되었다.
            String x = ""; // 경도
            String y = ""; // 위도
            String z = ""; // 주소
            while((line = br.readLine()) != null){
                response.append(line); //한줄 씩 respose 에 넣는다
            }
            br.close(); // BufferedReader 닫기


            JSONTokener tokener = new JSONTokener(response.toString());
            JSONObject object = new JSONObject(tokener); // JSONTokener를 JSONObject로 바꿔준다.
            System.out.println(object.toString());

            JSONArray arr = object.getJSONArray("addresses");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject temp = (JSONObject) arr.get(i); // 다운캐스팅해줘야한다.
                System.out.println("address : " + temp.get("roadAddress")); //주소
                System.out.println("JinbunAddress : " + temp.get("roadAddress")); // 지번
                System.out.println("경도 : " + temp.get("x"));
                System.out.println("위도 : " + temp.get("y"));
                //추가된 부분
                x = (String) temp.get("x"); // object type 이므로 String type으로 캐스팅해준다.
                y = (String) temp.get("y");
                z = (String) temp.get("roadAddress");
            }

            //추가된 부분
            // x,y,z의 값을 지도이미지로
            map_service(x, y, z); // 지도를 이미지화 시키는 메소드


        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
