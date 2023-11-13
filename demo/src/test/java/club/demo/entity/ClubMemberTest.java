package club.demo.entity;

import club.demo.repository.ClubMemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClubMemberTest {

    @Autowired
    private ClubMemberRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertDummies(){
        //1 - 80 까지는 USER만 지정
        //81 - 90 까지는 USER, MANAGER
        //91 - 100 까지는 USER,MANAGER, ADMIN

        IntStream.rangeClosed(1, 100).forEach(i -> {
            ClubMember clubMember = ClubMember.builder()
                    .email("user"+ i + "@zerock.org")
                    .name("사용자"+i)
                    .fromSocial(false)
                    .password(passwordEncoder.encode("1111"))
                    .build();
            //default role
            clubMember.addMemberRole(ClubMemberRole.USER);
            if(i > 80){
                clubMember.addMemberRole(ClubMemberRole.MANAGER);
            }
            if(i > 90){
                clubMember.addMemberRole(ClubMemberRole.ADMIN);
            }
//            repository.save(clubMember);
        });

    }

    @Test
    public void testRead(){
        Optional<ClubMember> result = repository.findByEmail("user95@zerock.org", false);

        ClubMember clubMember = result.get();
        System.out.println(clubMember);
    }

}

//Hibernate:
//    select
//        clubmember0_.email as email1_0_,
//        clubmember0_.from_social as from_soc2_0_,
//        clubmember0_.name as name3_0_,
//        clubmember0_.password as password4_0_,
//        roleset1_.club_member_email as club_mem1_1_0__,
//        roleset1_.role_set as role_set2_1_0__
//    from
//        club_member clubmember0_
//    left outer join
//        club_member_role_set roleset1_
//            on clubmember0_.email=roleset1_.club_member_email
//    where
//        clubmember0_.from_social=?
//        and clubmember0_.email=?
//엔티티 그래프 이용하니까 left outer join으로 처리되는 것을 확인할 수 있고, 권한도 역시 같이 로딩하는 것을 확인할 수 있음
