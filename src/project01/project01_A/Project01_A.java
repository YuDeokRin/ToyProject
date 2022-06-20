package project01.project01_A;
/**
 *  Gson과 Json의 이해
 *
 * 1. BookDTO <-> Json  (Gson활용해서 객체를 Json으로, Json을 객체로 변경하기)
 *
 * 2. List<BookDTO> <-> Json (Gson활용해서 List<BookDTO>를 Json으로, Json을 List<BookDTO>로 변경하기)
 *    TypeToken
 */

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import project01.Book.BookDTO;

import java.util.ArrayList;
import java.util.List;

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
        System.out.println(T.ObjectChangeJson(g, dto).getTitle() + "\t" + dto.getPrice()); // 안에 개별 검색


        System.out.println("---------------복수 데이터---------------");
        //복수의 데이터
        //Object(List<BookDTO>)를 json [{  }, {  } .....]으로 바꾸기 출력(복수 데이터)
        List<BookDTO> list = new ArrayList<BookDTO>();  // 객체를 여러 개 넣을 수 있는 ArrayList 생성, 타입은 <BookDTO>
        list.add(new BookDTO("자바1", 21000, "에이콘", 570));
        list.add(new BookDTO("자바2", 31000, "에이콘", 670));
        list.add(new BookDTO("자바3", 11000, "에이콘", 370));


        String listJson = g.toJson(list);
        System.out.println(listJson);

        //Json[{   }, {  }....] (json은 String type)의 구조를 Object(List<BookDTO>)으로 바꾸기 출력 (복수 데이터)
        //조금 어려움 -> 이유 : Json(String) 타입인데 그것을 BookDTO타입으로 바꿔야하기 때문이다.


        //g.fromJson(listJson, List<BookDTO>.class);  List<BooDTO>에 있는 타입이 한개가 아니라서 안된다.

        //해결방안 :
        //배열구조처럼 되어있는 Json구조를 List<BookDTO> 타입으로 바꾸려면 TypeToken을 이용해서 getTyep()이용해서 타입정보를 내부적으로 만든다.
        List<BookDTO> list2  = g.fromJson(listJson, new TypeToken<List<BookDTO>>(){}.getType()); // 객체생성하고 그 안에 것을 구현해주기 위해서 {}를 서야한다.
        for (BookDTO vo  : list2) {
            System.out.println(vo);
        }




    }
}
