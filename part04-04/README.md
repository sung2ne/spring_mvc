# PART 04. 게시글 기능 개선하기

이 파트에서는 기존 게시판 기능을 더욱 효율적이고 유지보수하기 쉬운 구조로 개선하는 방법을 다룹니다. 
Lombok, MyBatis 등을 적용하여 코드의 간결성을 높이고, 게시글 등록, 조회, 수정, 삭제 기능을 개선합니다.

## 01. Lombok 적용하기

Lombok을 활용하여 Getter, Setter, ToString, 생성자 등을 자동 생성하도록 설정합니다.

- 📖 교재: [https://wikidocs.net/267684](https://wikidocs.net/267684)
- 💻 코드: [https://github.com/spring-mvc-textbook/part04-01](https://github.com/spring-mvc-textbook/part04-01)

## 02. MyBatis 적용하기

기존 JDBC 기반 데이터 접근 방식을 MyBatis로 변경합니다. 
MyBatis의 Mapper 인터페이스와 XML 매퍼를 활용하여 SQL 쿼리를 분리하는 방법을 설명합니다.

- 📖 교재: [https://wikidocs.net/268522](https://wikidocs.net/268522)
- 💻 코드: [https://github.com/spring-mvc-textbook/part04-02](https://github.com/spring-mvc-textbook/part04-02)

## 03. 게시글 등록 수정하기

Lombok과 MyBatis를 활용하여 게시글 등록 로직을 개선합니다.
INSERT SQL을 MyBatis Mapper에 정의하고, 게시글을 데이터베이스에 저장하는 기능을 다룹니다.

- 📖 교재: [https://wikidocs.net/267690](https://wikidocs.net/267690)
- 💻 코드: [https://github.com/spring-mvc-textbook/part04-03](https://github.com/spring-mvc-textbook/part04-03)

## 04. 게시글 목록 수정하기

MyBatis를 활용하여 기존 게시글 목록 조회 기능을 개선합니다.

- 📖 교재: [https://wikidocs.net/267691](https://wikidocs.net/267691)
- 💻 코드: [https://github.com/spring-mvc-textbook/part04-04](https://github.com/spring-mvc-textbook/part04-04)

## 05. 게시글 보기 수정하기

MyBatis를 활용하여 특정 게시글의 상세 정보를 조회하는 기능을 개선합니다.

- 📖 교재: [https://wikidocs.net/267692](https://wikidocs.net/267692)
- 💻 코드: [https://github.com/spring-mvc-textbook/part04-05](https://github.com/spring-mvc-textbook/part04-05)

## 06. 게시글 수정 수정하기

MyBatis를 활용하여 기존 게시글 수정 기능을 개선합니다.

- 📖 교재: [https://wikidocs.net/267693](https://wikidocs.net/267693)
- 💻 코드: [https://github.com/spring-mvc-textbook/part04-06](https://github.com/spring-mvc-textbook/part04-06)

## 07. 게시글 삭제 수정하기

MyBatis를 활용하여 게시글 삭제 기능을 개선합니다.

- 📖 교재: [https://wikidocs.net/267694](https://wikidocs.net/267694)
- 💻 코드: [https://github.com/spring-mvc-textbook/part04-07](https://github.com/spring-mvc-textbook/part04-07)