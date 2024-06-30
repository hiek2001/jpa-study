package jpabook.jpashop;

import jpabook.jpashop.domain.Member;

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
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Member> query = cb.createQuery(Member.class);

            Root<Member> m = query.from(Member.class);

            CriteriaQuery<Member> cq = query.select(m).where(cb.equal(m.get("username"), "kim"));
            List<Member> resultList = em.createQuery(cq).getResultList();

            // 아래 쿼리에서 Member는 테이블이 아닌 "객체"다.
//            List<Member> result = em.createQuery("select m from Member m where m.username like '%kim%'",
//                    Member.class
//            ).getResultList();
//
//            for(Member member : result) {
//                System.out.println("member = "+member);
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
