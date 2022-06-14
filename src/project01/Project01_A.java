package project01;

import com.google.gson.Gson;
import project01.Book.BookDTO;

import java.awt.print.Book;

public class Project01_A {

    //Oject(객체)를 json으로 바꿔주는 함수 (데이터 하나 일 때)
    public String jsonChangeObject(Gson g, BookDTO dto) {
        String json = g.toJson(dto); // 타입은 String, Gson안에 toJson()메서드를 사용해서 Object를 Json으로 바꿔준다.
        return json;//  바뀐 값을 출력  : {"title":"자바","price":21000,"company":"에이콘","page":670}
    }

    // json을 object로 바꾸기 (데이터 하나 일 때)
    public BookDTO ObjectChangeJson(Gson g, BookDTO dto) {
        String json = g.toJson(dto); // json 형식으로 바꿈
        BookDTO changeDto = g.fromJson(json, BookDTO.class);
        return changeDto;
    }

    public static void main(String[] args) {

        Project01_A T = new Project01_A(); // Project01_A 안에 메소드를 불러내기 위해서 객체 생성
        BookDTO dto = new BookDTO("자바", 21000, "에이콘", 670);
        Gson g = new Gson();
        //Object를 json으로 바꾸기 출력(단일 데이터 )
        System.out.println(T.jsonChangeObject(g, dto)); //{"title":"자바","price":21000,"company":"에이콘","page":670}
        //Json을 object로 바꾸기 출력 (단일 데이터 )
        System.out.println(T.ObjectChangeJson(g, dto)); //BookDTO{title='자바', price=21000, company='에이콘', page=670}
        System.out.println(T.ObjectChangeJson(g, dto).getTitle() + "\n" + dto.getPrice()); // 안에 개별 검색

        //복수의 데이터
        //Object(List<BookDTO>)를 json [{...}, {}]으로 바꾸기 출력(복수 데이터)
        System.out.println();
        //Json을 object(List<BookDTO>)로 바꾸기 출력 (복수 데이터)
        System.out.println();


    }
}
