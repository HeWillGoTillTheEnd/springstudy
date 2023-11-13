package club.demo.repository;

import club.demo.entity.ClubMember;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClubMemberRepository extends JpaRepository<ClubMember, String> {

    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select m from ClubMember m where m.fromSocial = :social and m.email = :email")
    Optional<ClubMember> findByEmail(String email, boolean social);
}


//findByEmail()은 사용자의 이메일과 소셜로 추가된 회원 여부를 선택해서 동작하도록 설계
//@EntityGraph를 이용해서 'left outer join'으로 ClubMemberRole이 처리될 수 있도록 함
