package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component
@Scope(value = "request",proxyMode = ScopedProxyMode.TARGET_CLASS)
//CGLIB바이트 코드를 조작하는 라이브러리를 사용해 가짜 프록시 객체를 생성
//스프링 컨테이너에 myLogger라는 이름으로 가짜 프록시 객체를 등록
//의존관계 주입도 가짜 프록시 객체가 주입됨
//가짜 프록시 객체에는 요청이 오면 내부에서 진짜 빈을 요청하는 위임로직이 들어있음
//내부에 실제 MyLogger를 찾아오는 방법을 가지고 있음
//가짜 프록시 객체는 원본 클래스를 상속받아서 만들어졌기 때문에 이 객체를 사용한는 클라이언트 입장에서는
//원본인지 아닌지도 모르게 동일하게 사용할 수 있다(다형성)
//프록시는 진짜 객체 조회를 꼭 필요한 시점까지 지연처리한다는 것이 핵심 아이디어
public class MyLogger {

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message){
        System.out.println("[" + uuid + "]" + "[" + requestURL + "]" + message);
    }

    @PostConstruct
    public void init(){
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean create:" + this);

    }

    @PreDestroy
    public void close(){
        System.out.println("[" + uuid + "] request scope bean close:" + this);

    }
}
