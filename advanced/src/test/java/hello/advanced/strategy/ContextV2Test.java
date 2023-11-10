package hello.advanced.strategy;

import hello.advanced.strategy.code.strategy.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV2Test {

    /**
     * 전략 패턴 적용
     */
    @Test
    void strategyV1() {
        ContextV2 context = new ContextV2();
        context.execute(new StrategyLogic1());
        context.execute(new StrategyLogic2());
    }
    /**
     * 전략 패턴 익명 내부 클래스 적용
     */
    @Test
    void strategyV2() {
        ContextV2 context = new ContextV2();
        context.execute(new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직1 실행");
            }
        });
        context.execute(new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직2 실행");
            }
        });
    }
    /**
     * 전략 패턴 람다 적용
     */
    @Test
    void strategyV3() {
        ContextV2 context = new ContextV2();
        context.execute(() -> log.info("비즈니스 로직1 실행"));
        context.execute(() -> log.info("비즈니스 로직2 실행"));
    }
    //실행할 코드 조각을 넘긴다고 생각하면된다

}

//컨텍스트를 실행할 때마다 전략을 인수로 전달한다
//클라이언트는 컨텍스트를 실행하는 시점에 원하는 전략을 전달할 수 있다. 이전 방식과 비교해서 원하는 전략을 더욱 유연하게 변경 가능
//테스트 코드를 보면 하나의 컨텍스트만 생성한다.

//ContextV1은 필드에 전략을 저장하는 방식으로 전략 패턴을 구사했다
//선 조립 후 실행방법에 적절하다
//컨텍스트를 실행하는 시점에는 이미 조합이 끝났기 때문에 전략을 신경쓰지 않고 단순히 실행만 하면 된다.
//컨텍스트2는 파라미터에 전략을 전달받는 방식으로 전략 패턴을 구사했다
//실행할 때 마다 전략을 유연하게 변경할 수 있다.
// 단점 역시 실행할 때 마다 전략을 계속 지정해주어야 한다는 점이다.

//우리가 해결하고 싶은 문제는 변하는 부분과 변하지 않는 부분을 분리하는 것이다
//변하지 않는 부분을 템플릿이라고하고, 그 템플릿 안에서 변하는 부분에 약간 다른 코드 조각을 넘겨서 실행하는 것이 목적이다
//ContextV1, ContextV2 두 가지 방식 다 문제를 해결할 수 있지만, 어떤 방식이 좀더 나아보일까?
//우리가 원하는 것은 애플리케이션 의존 관게를 설정하는 것 처럼 선 조립 후 실행이 아니다. 단순히 코드를 실행할 때 변하지 안흔 템플릿이 있고
//그 탬플릿 안에서 원하는 부분만 살짝 다른 코드를 실행하고 싶을 뿐이다
// 따라서 우리가 고민하는 문제는 실행 시점에 유연하게 실행 코드 조각을 전달하는 ContextV2가 더 적합하다.

//디자인 패턴을 고민할 때 모양이 디자인 패턴이라고 생각하게 됨
//하지만 디자인 패턴은 의도가 중요함
//전략 패턴의 의도를 구현하는데는 컨텍스트 안에 필드로 전략을 가지고 있어도 해결할 수 있고, 파라미터를 사용을 해도 똑같이 의도를 해결할 수 있다.
//이러한 의존관계는 모양이 같다. (인수를 파라미터를 쓰나, 안에서 필드에 의존관계 주입식으로하나 모양은 같음
//모양은 두가지다 전략 패턴이라고 보면 된다.
