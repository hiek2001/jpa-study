package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.MemberDTO;
import jpabook.jpashop.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class JpqlMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            // CriteriaBuilder
            // 쿼리를 코드로 짬
            // 장 : 오타가 나더라도 오류가 발생하기 때문에 발견하기 쉽고, 동적 쿼리에 유리
            // 실무에서는 안씀. => why? 유지보수가 어려움
//            CriteriaBuilder cb = em.getCriteriaBuilder();
//            CriteriaQuery<Member> query = cb.createQuery(Member.class);
//
//            Root<Member> m = query.from(Member.class);
//
//            CriteriaQuery<Member> cq = query.select(m).where(cb.equal(m.get("username"), "kim"));
//            List<Member> resultList = em.createQuery(cq).getResultList();

            // 아래 쿼리에서 Member는 테이블이 아닌 "객체"다.
//            List<Member> result = em.createQuery("select m from Member m where m.username like '%kim%'",
//                    Member.class
//            ).getResultList();
//
//            for(Member member : result) {
//                System.out.println("member = "+member);
//            }

            // team은 member 안에 있기 때문에 한눈에 보일수있도록 join문 작성하여 명시적으로 사용할 것
            // select m.team from Member m 로 작성하면 실제 쿼리는 join 하여 실행되지만, 눈에 보이지 않기 때문에 유지보수성이 떨어짐
            //List<Team> result = em.createQuery("select t from Member m join m.team t", Team.class).getResultList();

            // username과 age의 타입도 다른 경우, DTO를 생성하여 사용하기
            // 문자이기 때문에 DTO의 패키지명을 모두 작성해야함
            //List<MemberDTO> result2 = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class).getResultList();

            // 페이징 -> ORDER BY 반드시!
            List<Member> members = em.createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(0)
                    .setMaxResults(10)
                    .getResultList();

            for(Member member1 : members) {
                System.out.println("member1 = "+member1);
            }

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
