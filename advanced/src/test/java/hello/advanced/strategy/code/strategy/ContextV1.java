package hello.advanced.strategy.code.strategy;

import hello.advanced.strategy.ContextV1Test;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * 필드에 전략을 ㄱ보관하는 방식
 */
@Slf4j
public class ContextV1 {

    private Strategy strategy;
    public ContextV1(Strategy strategy) {
        this.strategy = strategy;
    }

    public void execute() {
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        strategy.call();//상속
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }

}
//ContextV1은 변하지 않는 로직을 가지고 있는 템플릭 역할을 하는 코드이다
//전략 패턴에서는 이것을 컨텍스트라 한다
//쉽게 이야기해서 문맥은 변하지 않지만 그 문맥 속에서 strategy를 통해 일부 전략이 변경된다 생각하면된다
//Context는 내부에 Strategy strategy 필드를 가지고 있다. 이 필드에 변하는 부분인 Strategy의 구현체를 주입하면 된다.
//전략 패턴의 핵심은 Context 는 Strategy 인터페이스에만 의존한다는 점이다. 덕분에 Strategy의 구현체를 변경하거나 새로 만들어도
// Context 코드에는 영향을 주지 않는다
//스프링에서 의존관게 주입에서 사용하는 방식이 바로 전략패턴이다.

