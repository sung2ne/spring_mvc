# PART 05. 게시글 목록 기능 개선하기

이 파트에서는 게시글 목록 기능을 개선하는 방법을 다룹니다.
페이징 처리와 검색 기능을 추가하여 더욱 효율적인 데이터 조회와 사용자 경험 향상을 목표로 합니다.

## 01. 게시글 목록 페이징 처리하기

게시글 목록에 페이징 기능을 추가하여 한 번에 불러오는 데이터의 양을 조절합니다.
LIMIT 및 OFFSET을 활용한 SQL 페이징 처리 방식을 설명합니다.
MyBatis를 활용하여 페이징 쿼리를 구성하고, 프론트엔드에서 페이지네이션 UI를 구현하는 방법을 다룹니다.

- 📖 교재: [https://wikidocs.net/267708](https://wikidocs.net/267708)
- 💻 코드: [https://github.com/spring-mvc-textbook/part05-01](https://github.com/spring-mvc-textbook/part05-01)

## 02. 게시글 검색 처리하기

게시글 목록에서 검색 기능을 구현합니다.
제목, 내용 또는 작성자 기준으로 게시글을 검색할 수 있도록 SQL WHERE 조건을 활용합니다.
MyBatis의 Dynamic SQL을 적용하여 검색 조건이 있을 때만 쿼리가 달라지도록 설정하는 방법을 설명합니다.
프론트엔드에서 검색 입력값을 받아 서버로 전달하는 방식과 검색 결과를 출력하는 방법을 다룹니다.

- 📖 교재: [https://wikidocs.net/267709](https://wikidocs.net/267709)
- 💻 코드: [https://github.com/spring-mvc-textbook/part05-02](https://github.com/spring-mvc-textbook/part05-02)