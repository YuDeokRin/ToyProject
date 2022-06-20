# 6. GUI로 주소입력해서 주소찾기 만들기


1. GUI 만들기
   1. JFrame으로 큰 틀 만들어준다. 
   2. 종료버튼을 만들어준다. (EXIT_ON_CLOSE) 
   3. JFrame안에 Container를 만들어준다.
   4. Label : 주소입력, 지도보기, 도로명, 지번주소, 경도, 위도 만든다.
   5. JButton : 검색 <- 검색 클릭하면 naverMap 연결 

   
<img width="972" alt="스크린샷 2022-06-20 오후 3 51 32" src="https://user-images.githubusercontent.com/56623911/174555179-313d1c39-220d-49e8-a9e2-78bee82ac701.png">

2. naverMap  ->public class NaverMap implements ActionListener로 구현체를 받는다.
3. naverMap에서 geocoding, static map 사용


### 결과 

<img width="912" alt="스크린샷 2022-06-20 오후 4 45 40" src="https://user-images.githubusercontent.com/56623911/174555150-0975afcd-d272-429d-88bb-aa3b92e984a5.png">


네이버 open API를 이용하여서 JSon데이터 핸들링과 그것을 불러와 GUI로 구현해보았다. 

이 토이 프로젝트의 의미는 나중에 프로젝트를 할 때 지도 API를 이용할 때 활용하기 위해서이다.

GUI말고도 주소데이터를 이용하여 회원가입 때 필요한 주소검색에 이용할 생각이다.



