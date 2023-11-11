package club.demo.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SecurityConfigTest {


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    public void testEncode() {
        String password = "1111";
        String enPw = passwordEncoder.encode(password);
        System.out.println("enPw: " + enPw);
        boolean matchResult = passwordEncoder.matches(password, enPw);
        System.out.println("matchResult = " + matchResult);
    }
}

//해당 테스트를 작성한 이유는 BCryptPasswordEncoder의 동작을 확인하기 위한 용도
//testEncode()는 내부적으로 1111이라는 문자열을 암호화하고, 해당 문자열을 암호화한 결과가 '1111'에 맞는지 확인하는 것임
//testEncode()를 실행하면 매번 다른 결과가 만들어지는 것을 확인할 수 있는데 이에 대한 matches()의 결과는 늘 true로 나오는 것을 확인할 수 있습니다
//