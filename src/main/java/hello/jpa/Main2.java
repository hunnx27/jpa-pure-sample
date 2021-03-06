package hello.jpa;

import hello.jpa.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main2 {

    public static void main(String args[]){
        // 1. classpath:META-INF/persistence.xml 에서 hello 얼라이어스의 설정정보 가져오기
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("hello"); // 무조건 한번만 생성해서 애플리케이션 전체에서 공유해야함.

        EntityManager em = emf.createEntityManager(); // 엔티티 매니저는 쓰레드간에 공유하면 안됨(사용하고 버려야함)
        EntityTransaction tx = em.getTransaction();

        tx.begin(); //트랜잭션 시작

        try{
            Member member = new Member();
            member.setId(100l);
            member.setName("홍길동");

            em.persist(member); // 영구 저장하다.(어플리케이션 -> 영속성Context)
            em.flush();//(어플리케이션 -> 영속성Context -> SQL)

            member.setName("헬로~!");
            em.clear();//(영속성Context의 캐시 삭제: 캐시가 삭제되면 SQL은 실행되지않음)

            tx.commit(); //트랜잭션 저장후 종료(flush하고 Commit함)
        }catch(Exception e){
            e.printStackTrace();
            tx.rollback(); //트랜잭션 원복후 종료
        }finally {
            em.close();
        }

        emf.close();
    }
}
