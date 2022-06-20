package project01.project01_F;

import project01.project01_F.naver.NaverMap;

import javax.swing.*;
import java.awt.*;

/*

 GUI로 주소입력해서 주소찾기 만들기

    1.GUI에서 주소입력 부분에서 입력 후 -> 검색 부분을 누른다.
    2. openAPI를 이용해서 naver 서버에서 요청을 한다. -> (geocoding) 요청
    3. 위도, 경도의 정보를 받는다. -> geocoding을 통해서 위도, 경도의 정보를 제공받는다.
    4. 위도, 경도를 받은 정보를 static map이라는 지도를 이미지파일로 만들어준다.
    5. 이미지 파일을 GUI에서 지도 보여주는 부분에 보여준다.
    6. 하단에는 도로명, 지번주소, 경도, 위도를 알려준다.
 */
public class Project01_F {
    public JTextField address;
    public JLabel resAddress;
    public JLabel resX;
    public JLabel resY;
    public JLabel jibunAddress;

    public JLabel imageLabel;

    public void initGUI() {
        //frame 과 Container  그리고 종료 버튼
        JFrame frm = new JFrame("Map View"); //GUI 전체 프레임 객체 생성
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 종료 버튼이 생기고 누르면 종료되는 것
        Container c = frm.getContentPane();  // JFrame 안에서 컨테이너를 가져오기 위해서 getContentPane();을 쓴다.

        //중간에 지도보기 JLabel
        imageLabel = new JLabel("지도보기"); // 지도 이미지들어 갈 객체 생성


        JPanel pan = new JPanel(); // JPanel 객체생성  상단(주소입력, 주소쓰는곳, 검색버튼) 들어가는 패널
        //컨테이너 안에 상단의 위치한 주소입력, JTextField, 검색버튼
        JLabel addressLbl = new JLabel("주소입력");
        address = new JTextField(50);
        JButton btn = new JButton("검색");

        //위의 것들을 패널에 넣어준다.
        pan.add(addressLbl); // 패널에 넣어준다.
        pan.add(address); // 패널에 넣어준다.
        pan.add(btn); // 패널에 넣어준다.
        btn.addActionListener(new NaverMap(this)); // 검색 버튼을 누르면 ->  NaverMap(this)를

        JPanel pan1 = new JPanel(); // JPanel 객체생성 -> 하단( 도로명, 지번주소, 경도, 위도) 들어가는 패널
        pan1.setLayout(new GridLayout(4, 1));//  패널은 GridLayout으로 4행 1열
        resAddress = new JLabel("도로명");
        jibunAddress = new JLabel("지번주소");
        resX = new JLabel("경도");
        resY = new JLabel("위도");

        //위의 것들을 패널에 넣어준다.
        pan1.add(resAddress); // 패널에 넣어준다.
        pan1.add(jibunAddress);// 패널에 넣어준다.
        pan1.add(resX);// 패널에 넣어준다.
        pan1.add(resY);// 패널에 넣어준다.

        //마지막으로 pan, imageLabel,pan1을
        c.add(BorderLayout.NORTH, pan); //상단
        c.add(BorderLayout.CENTER, imageLabel); // 중간
        c.add(BorderLayout.SOUTH, pan1); // 하단
        frm.setSize(800, 660); // frame size 넣기 (width(가로), height(세로))
        frm.setVisible(true); // 화면 뜨게하는 것
    }
    public static void main(String[] args) {
        new Project01_F().initGUI();

    }
}
