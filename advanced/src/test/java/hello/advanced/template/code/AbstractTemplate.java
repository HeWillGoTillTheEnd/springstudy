package hello.advanced.template.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractTemplate {
    public void execute() {
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        call();//상속
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);

    }
    protected abstract void call();
}
//템플릿 메서드 패턴은 이름 그대로 템플릿을 사용하는 방식, 템플릿은 기준이 되는 거대한 틀
//템플릿이라는 트렝 변하지 않는 부분을 몰아둠. 그리고 일부 변하는 부분을 별도로 호출하여 해결함
//템플릿 메서드 패턴은 부모 클래스에 변하지 않는 템플릿 코드를 둠
