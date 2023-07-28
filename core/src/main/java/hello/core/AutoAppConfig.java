package hello.core;

import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = Configuration.class)
)
public class AutoAppConfig {


    @Bean(name = "memoryMemberRepository")
    MemoryMemberRepository memberRepository(){
        return new MemoryMemberRepository(); //수동 빈 등록이 우선권을 가진다. 하지만 수동 설정으로 인해 찾기 어려운 오류가 되는 경우가 많아서
        //수동등록과 자동등록이 충돌할 시 에러가 발생하도록 변경되어있다.
        //만약 수동등록을 사용하고싶다면 spring.main.allow-bean-definition-overriding=true를 사용하면된다.
        //The bean 'memoryMemberRepository', defined in class path resource [hello/core/AutoAppConfig.class],
        // could not be registered. A bean with that name has already been defined

    }
}
