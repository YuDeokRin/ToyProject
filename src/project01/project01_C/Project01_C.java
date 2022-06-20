package project01.project01_C;

/*

3.JSON-java(org-json) 이용해서 데이터 핸들링해보기

Json 파일로된 것, 서버에 있는 Json 데이터를 처리하기
*/
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;

public class Project01_C {
    public static void main(String[] args) {
        String src = "info.json"; // 같은 package에 있는 info.json파일을 src 변수에 넣어준다.
        //데이터를 읽으려면 IO -> stream(스트림)이 필요하다.


        //InputSteam을 사용해서 파일 연결을 한다.
        //InputStream 변수 = 경로.class.getResourceAsStream(src <-- file    );
        InputStream is = Project01_C.class.getResourceAsStream(src);

        //파일 안에 데이터가 있는지 확인 하는 로직
        if (is == null) {
            throw new NullPointerException("Can't find resource file");
        }


        /*
        JSONTokener 클래스란?
        A JSONTokener takes a source string and extracts characters and tokens from it.
        It is used by the JSONObject and JSONArray constructors to parse JSON source strings.

        JSONTokener는 소스 문자열을 가져와서 문자와 토큰을 추출합니다.
        JSONObject 및 JSONArray 생성자가 JSON 소스 문자열을 구문 분석하는 데 사용됩니다.
         */

        //JSONTokener는 문자열 데이터를 메모리에 올리는 역할
        JSONTokener tokener = new JSONTokener(is); //JSONTokener을 객체를 만들 때 is라는 스트림 객체를 넣어준다.
        //객체타입으로 바꿔준다.
        JSONObject object = new JSONObject(tokener); // tokener에 저장되어있는 Json데이터를 오브젝트(객)형식으로 바꿔준다
        JSONArray students  = object.getJSONArray("students"); //JSONArray로 바꿔준다.


        for (int i = 0; i < students.length(); i++) {
            JSONObject student = (JSONObject)students.get(i); // (JSONObject) 캐스팅해주는 이유 : get의 타입이 Object이기 때문이다.
            System.out.print(student.get("name")+ "\t");
            System.out.print(student.get("address")+ "\t");
            System.out.println(student.get("phone")+ "\t");
        }
    }
}
