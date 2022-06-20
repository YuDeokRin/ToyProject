package project01.project01_F.naver;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import project01.addressVO.AddressVO;
import project01.project01_F.Project01_F;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

public class NaverMap implements ActionListener { // Button이 눌렀을 때, 실행되야하므로 implements를 해준다.
    Project01_F naverMap;
    public NaverMap(Project01_F naverMap) { // this 객체를 받아서 naverMap에 넣어준다.
        this.naverMap = naverMap;
    }

    //actionPerformed 구현부분이 위도와 경도를 가져오는 부분이다.
    @Override
    public void actionPerformed(ActionEvent e) {
        String clientId = "";
        String clientSecret = "";
        AddressVO vo = null;

        try {
            String address = naverMap.address.getText(); // 주소객체 address에 getText()한다.
            String addr = URLEncoder.encode(address, "UTF-8");
            String apiURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + addr; // geocoding에 필요한 openapi
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) { // 응답이 잘 왔을 경우
                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

            }else{ // 응답이 잘 안왔을 경우
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) { // 200을 성공하면 while문을 반복하면서 Json코드를 가져온다.
                response.append(inputLine);
            }

            br.close();

            //JSONTokener를 이용해서 문자열 데이터를 메모리에 올려준다.
            JSONTokener tokener = new JSONTokener(response.toString());
            JSONObject object = new JSONObject(tokener);
            System.out.println(object);


            //for문으로 JSON안에 있는 위도, 경도, 도로주소, 지번주소를 가져온다.
            JSONArray arr = object.getJSONArray("addresses");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject temp = (JSONObject) arr.get(i);
                vo = new AddressVO(); // AddressVO에서  public AddressVO() {  } <- 디폴트 생성자 안해주면 오류뜬다.
                vo.setRoadAddress((String)temp.get("roadAddress")); // vo에 roadAddressd 변수에 주소를 넣는다.
                vo.setJibunAddress((String)temp.get("jibunAddress")); // vo에 jibunAddress 변수에 지번주소를 넣는다.
                vo.setX((String) temp.get("x")); // 경도를 vo에 넣는다
                vo.setY((String) temp.get("y")); // 위도를 vo에 넣는다.
                System.out.println(vo);
            }

            //지도의 정보를 받아서 이미지로 변환하는 것
            //지도 이미지 생성 메서드
            //주소의 위치를 이미지로 생성하는 메소드
            map_service(vo);

        } catch (Exception err) {
            System.out.println(err);
        }
    }

    public void map_service(AddressVO vo) {
        String URL_STATICMAP = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?";

        try {
            String pos = URLEncoder.encode(vo.getX() + " " + vo.getY(), "UTF-8");
            URL_STATICMAP += "center=" + vo.getX() + "," + vo.getY();
            URL_STATICMAP += "&level=16&w=700&h=500";
            URL_STATICMAP += "&markers=type:t|size:mid|pos:" + pos + "|label:" + URLEncoder.encode(vo.getRoadAddress(), "UTF-8");
            URL url = new URL(URL_STATICMAP);
            HttpURLConnection con = (HttpURLConnection) url.openConnection(); //연결 시도
            con.setRequestMethod("GET"); //GET방식 요청
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", "yh1oswg56e");
            con.setRequestProperty("X-NCP-APIGW-API-KEY", "cXPkfCfTFfgEMajDhSZaP0FPnxy9fwWNr3UfcvpK");
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) { // 정상적으로 작동될 때
                InputStream is = con.getInputStream();
                int read = 0;
                byte[] bytes = new byte[1024];
                String tempname = Long.valueOf(new Date().getTime()).toString();

                File f = new File(tempname + ".jpg"); // 다운로드 될 이미지 생성
                f.createNewFile(); // 파일 생성
                OutputStream outputStream = new FileOutputStream(f);

                //지도 이미지 생성
                while ((read = is.read(bytes)) != -1) {// 서버에서 JSON데이터를 outputStream을 이용해서 jpg파일에 저장
                    outputStream.write(bytes, 0, read);
                }
                is.close();

                //파일로 만들어진 이미지를 f.getName의 이름으로 객체 생성
                ImageIcon img = new ImageIcon(f.getName());
                naverMap.imageLabel.setIcon(img);
                naverMap.resAddress.setText(vo.getRoadAddress());
                naverMap.jibunAddress.setText(vo.getJibunAddress());
                naverMap.resX.setText(vo.getX());
                naverMap.resY.setText(vo.getY());

            }else{
                System.out.println(responseCode);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }


}
