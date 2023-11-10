package hello.advanced.trace.threadlocal;

import hello.advanced.trace.threadlocal.code.FieldService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class FieldServiceTest {

    private FieldService fieldService = new FieldService();

    @Test
    void field(){
        log.info("main start");
        Runnable userA = () -> {
            fieldService.logic("userA");
        };
        Runnable userB = () -> {
            fieldService.logic("userB");
        };

        Thread threadA = new Thread(userA);
        threadA.setName("thread-A");
        Thread threadB = new Thread(userB);
        threadB.setName("thread-B");

        threadA.start();
        sleep(2000); // 동시성 문제 발생X
        threadB.start();

        sleep(3000); //메인 쓰레드 종료 대기
        log.info("main exit");
    }
    @Test
    void field1(){
        log.info("main start");
        Runnable userA = () -> {
            fieldService.logic("userA");
        };
        Runnable userB = () -> {
            fieldService.logic("userB");
        };

        Thread threadA = new Thread(userA);
        threadA.setName("thread-A");
        Thread threadB = new Thread(userB);
        threadB.setName("thread-B");

        threadA.start();
        sleep(100); // 동시성 문제 발생
        threadB.start();

        sleep(3000); //메인 쓰레드 종료 대기
        log.info("main exit");
    }
    private void sleep(int millis) {
        try{
            Thread.sleep(millis);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

//sleep를 설정해서 스레드 a가 끝나고 스레드 b가 실행되도록함
//메인스레드가 종료될경우 threadB.start()가 끝나기 전에 메인메서드가 종료되면 정상적으로 로그가 안나올수 있으므로 뒤에 sleep를 추가해줌
//동시성 문제는 지역 변수에서는 발생하지 않는다. 지역변수는 쓰레드마다 각각 다른 메모리 영역이 할당된다
// 동시성 문제가 발생하는 곳은 같은 인스턴스의 필드(주로 싱글톤에서 자주 발생), 또는 static 같은 공용 필드에 접근할 때 발생한다.
//동시성 문제는 값을 읽기만 하면 발생하지 않는다. 값을 변경하기 때문에 발생함

//싱글톤 객체의 필드를 사용하면서 동시성 문제를 해결하려면 어떻게 해야할까? -> 이럴때 사용하는것이 쓰레드 로컬이다

