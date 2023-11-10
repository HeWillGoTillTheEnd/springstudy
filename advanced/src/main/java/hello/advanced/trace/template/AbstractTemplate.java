package hello.advanced.trace.template;

import hello.advanced.logtrace.LogTrace;
import hello.advanced.trace.TraceStatus;

public abstract class AbstractTemplate<T> {

    private final LogTrace trace;

    public AbstractTemplate(LogTrace trace) {
        this.trace = trace;
    }

    public T execute(String message) {
        TraceStatus status = null;
        try {
            status = trace.begin(message);
            //로직 호출
            T result = call();
            trace.end(status);
            return result;
        } catch (Exception e) {
            trace.exception(status, e);
            throw e; //예외를 꼭 다시 던져주어야 한다
        }
    }

    protected abstract T call();
}
//AbstractTemplate는 탬플릿 메서드 패턴에서 부모클래스이고 탬플릿 역할을 함
//로그에 출력할 message를 외부에서 파라미터로 전달받는다.
//탬플릿 코드 중간에 call() 메서드를 통해서 변하는 부분을 처리한다
