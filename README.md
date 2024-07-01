# JPA의 기본 공부</br>
JPA의 모든 속성에 대해 공부하여 REST API 개발에 사용하고자 한다.</br>
</br>
## JPA란? (Java Persistence API)
JPA는 자바에서 사용하는 ORM 기술 표준이다.</br>
JPA는 자바 어플리케이션과 JDBC 사이에서 동작하며, 자바 인터페이스로 정의되어 있다. </br>
</br>
## ORM: Object-Relational Mapping(객체 관계 매핑)
- 객체와 관계형 데이터베이스의 데이터를 매핑하는 기술
- ORM 프레임워크가 객체와 데이터베이스 중간에서 매핑
- 객체와 테이블을 매핑하여 패러다임 불일치 문제를 해결
</br>

## JPA 사용 이유
기존의 개발 방식은 SQL 중심적인 개발이었다.
JPA를 사용하면 객체 중심으로 어플리케이션 개발이 가능하다.
쿼리문을 별도로 작성할 필요가 없기 때문에 생산성이 높아진다.</br>

- 저장 : jpa.persist(member)
- 조회 : Member member = jpa.find(memberId)
- 수정 : member.setName("변경 이름")
- 삭제 : jpa.remove(member)</br>

유지보수 면에서는 필드가 변경되더라도 매핑 정보만 잘 연결되면 SQL문은 자동으로 작성된다.</br>
상속, 연관관계, 객체 그래프 탐색, 비교 등의 설계 차이로 인해 발생하는 패러다임 불일치 문제가 해결된다.
