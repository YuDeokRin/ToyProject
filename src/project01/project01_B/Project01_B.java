package project01.project01_B;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 2. JSON-Java(org.json) 사용하는 방법
 *
 *
 */
public class Project01_B {
    public static void main(String[] args) {
        JSONArray students = new JSONArray(); //학생들의 정보를 배열로 묶기 위해서 JSONArray 객체 생성

        JSONObject student = new JSONObject(); //객체(단일)1 생성
        student.put("name", "홍길동"); // student의 "name"(key) : "홍길동"(value)
        student.put("phone", "010-1111-1111");
        student.put("address", "서울");
        System.out.println(student); // {"address":"서울","phone":"010-1111-1111","name":"홍길동"}

        students.put(student); // student를 students(JSONArray)에 넣기


        student = new JSONObject(); //추가 : 객체(단일)2 생성
        student.put("name", "청길동");
        student.put("phone", "010-2222-2222");
        student.put("address", "광주");
        System.out.println(student);
        students.put(student);

        JSONObject object = new JSONObject(); //JSONArray를 Object에 넣어준다.
        object.put("students", students);
        System.out.println(object.toString(2)); // indentFactor는 들여쓰기 몇번 할지 설정할 수 있는 옵션
    }
}
