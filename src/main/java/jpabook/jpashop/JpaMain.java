package jpabook.jpashop;

import jpabook.jpashop.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 값 타입 컬렉션 사용
            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(new Address("home", "street","10000"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("피자");
            member.getFavoriteFoods().add("햄버거");

            member.getAddressHistory().add(new AddressEntity("city2", "street2","20000"));
            member.getAddressHistory().add(new AddressEntity("old1", "street2","20000"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("=========== start ===========");
            Member findMember = em.find(Member.class, member.getId());

//            Address a = findMember.getHomeAddress();
//            findMember.setHomeAddress(new Address("newCity", a.getStreet(), a.getZipcode()));
//
//            // 치킨 - > 한식
//            findMember.getFavoriteFoods().remove("치킨");
//            findMember.getFavoriteFoods().add("한식");
//
//            findMember.getAddressHistory().remove(new AddressEntity("city2", "street2","20000"));
//            findMember.getAddressHistory().add(new AddressEntity("newCity", "street2","20000"));


            // 값 타입 공유 참조
//            Address address = new Address("city", "street","10000");
//
//            Member member = new Member();
//            member.setUsername("member1");
//            member.setHomeAddress(address);
//            em.persist(member);

            // 인스턴스를 복사해서 사용해야함
//            Address copyAddress = new Address(address.getCity(), address.getStreet(), address.getZipcode());
//
//            Member member2 = new Member();
//            member2.setUsername("member2");
//            member2.setHomeAddress(copyAddress);
//            em.persist(member2);

            // 이렇게 쓰면 member 데이터의 homeaddress의 city 값이 모두 변경됨. 이런 의도였다면 값 타입이 아닌 entity를 사용해야 함
            // 값 타입은 이런식으로 공유되면 안됨
//            member.getHomeAddress().setCity("newCity");

            // setter를 사용하지 않고 (일부분만 변경 X)
            // 데이터를 total로 바꾼다.
            // *** 값 타입은 모두 불변으로 만든다. ***
//            Address newAddress = new Address("NewCity", address.getStreet(), address.getZipcode());
//            member.setHomeAddress(newAddress);

            // 영속성 전이
//            Child child1 = new Child();
//            Child child2 = new Child();
//
//            Parent parent = new Parent();
//            parent.addChild(child1);
//            parent.addChild(child2);
//
//            em.persist(parent);
//            em.persist(child1);
//            em.persist(child2);

//            Order order = new Order();
//            order.addOrderItem(new OrderItem());

//            Order order = em.find(Order.class, 1L);
//            Long memberId = order.getMemberId();
//            Member member = em.find(Member.class, memberId);
//            // 위 내용은 객체 설계를 테이블 설계에 맞춘 방식
//
//            Member findMember = order.getMember();

            // << 객체에 맞춰서 모델링 >> ===> 이렇게 하면 안됨
            // 객체를 테이블에 맞추어 데이터 중심으로 모델링하면 협력 관계를 만들 수 없다
//            Team team = new Team();
//            team.setName("TeamA");
//            em.persist(team);
//
//            Member member = new Member();
//            member.setUsername("member1");
//            member.setTeamId(team.getId());
//            em.persist(member);

            // << 객체 지향 모델링 >>
            // 저장
//            Team team = new Team();
//            team.setName("TeamA");
//            em.persist(team);
//
//            Member member = new Member();
//            member.setUsername("member1");
          //  member.setTeam(team);
         //   team.getMembers().add(member); // 읽기 전용이기 때문에 jpa에서는 읽지 않음. 이 로직은 반드시 해줘야되기 때문에, Member.setTeam에 넣어주기
//            member.changeTeam(team);
//            em.persist(member);

           // team.addMember(member); // 양쪽에 모두 해주면 문제가 있을 수 있기 때문에, 한쪽은 지워주기

            // -> 현재는 1차 캐시에서 값을 가져오기 때문에, db에서 가져오는걸 보고 싶다면 아래를 추가하기
//            em.flush();
//            em.clear();

            // 조회
//            Member findMember = em.find(Member.class, member.getId());
//            Team findTeam = findMember.getTeam();
//            System.out.println("findTeam.name : "+findTeam.getName());

            // 가져온 팀에서 값 바꾸기
//            Team newTeam = em.find(Team.class, 5L);
//            findMember.setTeam(newTeam);

            // ==> 양방향 관계
            // 테이블의 개념에서는 방향성에 대한 개념이 없음. join 하면 서로의 대한 값을 확인할 수 있기 때문
            // 객체의 경우에서는 member가 team을 가졌기 때문에 team에서는 member를 확인할 수 없음.
            // 그래서 team에도 member를 넣어줘야 함
            // 하지만 개발하기에는 단방향이 좋음. 신경쓸것이 적어지기 때문.

            // 객체의 양방향은 단방향을 서로 엮은 것이기 때문에, 테이블의 fk를 관리할 '주인'을 지정해야함 => 외래키가 있는 곳을 "주인"으로 정할 것
            // 등록, 수정을 사용하는 쪽이어야 하며, 주인이 아닌 쪽은 읽기만 가능
            // 주인은 mappedBy 속성 X, 주인이 아닌 쪽이 mappedBy로 주인을 지정함
//            Member findMember = em.find(Member.class, member.getId());
//            List<Member> members = findMember.getTeam().getMembers();
//
//            for(Member m : members) {
//                System.out.println("m = "+m.getUsername());
//            }

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
