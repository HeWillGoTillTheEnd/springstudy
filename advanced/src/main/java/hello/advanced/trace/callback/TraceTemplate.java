package hello.advanced.trace.callback;

import hello.advanced.logtrace.LogTrace;
import hello.advanced.trace.TraceStatus;
import org.springframework.stereotype.Component;

public class TraceTemplate {

    private final LogTrace trace;

    public TraceTemplate(LogTrace trace) {
        this.trace = trace;
    }

    public <T> T execute(String message, TraceCallback<T> callback) {
            TraceStatus status = null;
            try {
                status = trace.begin(message);
                //로직 호출
                T result = callback.call();
                trace.end(status);
                return result;
            } catch (Exception e) {
                trace.exception(status, e);
                throw e; //예외를 꼭 다시 던져주어야 한다
        }
    }
}
//TraceTemplate는 탬플릿 역할을 한다
//execute를 보면 callback.call() 사용
