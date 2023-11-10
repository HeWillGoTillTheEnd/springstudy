package hello.advanced.app.v5;

import hello.advanced.logtrace.LogTrace;
import hello.advanced.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV5 {

    private final OrderRepositoryV5 orderRepository;
    private final LogTrace trace;


    public void orderItem(String itemId){
        AbstractTemplate<Void> template = new AbstractTemplate<>(trace) {
            @Override
            protected Void call() {
                orderRepository.save(itemId);
                return null;
            }
        };
        template.execute("OrderService.orderItem()");
    }
}

//traceIdHolder 필드를 사용한 덕분에 파라미터 추가 없는 깔끔한 로그 추적기를 완성하고 실제 서비스에 배포한다면 동시성 문제가 발생함

//로그추적기가 실제 서비스에서는 잘 되는거같지만
//심각한 동시성 문제를 가지고있다
//1초에 두번 돌리면?
//동시에 여러 사용자가 요청하면 여러 쓰레드가 동시에 애플리케이션 로직을 호출하게 된다. 따라서 로그가 섞ㅇ여서 출력됨
//FieldLogTrace는 싱글톤으로 등록된 스프링 빈이다. 이 객체의 인스턴스가 애플리케이션에 딱 1개 존재한다는 뜻
//이렇게 하나만 있는 인스턴스의 FieldLogTrace.traceHolder 필드를 여러 쓰레드가 동시에 접근하기 때문에 문제가 발생함
//실무에서 한번 나타나면 개발자를 가장 괴롭히는것도 동시성문제
