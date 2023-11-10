package hello.advanced.logtrace;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
public class FieldLogTrace implements LogTrace {

    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    private TraceId traceIdHolder;//트레이스 아이디를 어딘가는 들고있어야함
    //트레이스ID 동기화, 동시성 이슈 발생
    @Override
    public TraceStatus begin(String message) {
        syncTraceId();
        TraceId traceId = traceIdHolder;
        Long startTime = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
        return new TraceStatus(traceId, startTime, message);
    }

    private void syncTraceId(){
        if(traceIdHolder == null){
            traceIdHolder = new TraceId();
        } else {
            traceIdHolder = traceIdHolder.createNextId();
        }
    }

    @Override
    public void end(TraceStatus status) {
        complete(status,null);

    }

    @Override
    public void exception(TraceStatus status, Exception e) {
        complete(status,e);
    }
    private void complete(TraceStatus status, Exception e) {
        Long stopTimeMs = System.currentTimeMillis();
        long resultTimeMs = stopTimeMs - status.getStartTimeMs();
        TraceId traceId = status.getTraceId();
        if (e == null) {
            log.info("[{}] {}{} time={}ms", traceId.getId(),
                    addSpace(COMPLETE_PREFIX, traceId.getLevel()), status.getMessage(),
                    resultTimeMs);
        } else {
            log.info("[{}] {}{} time={}ms ex={}", traceId.getId(),
                    addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs,
                    e.toString());
        }

        releaseTraceId();
    }

    private void releaseTraceId() {
        if(traceIdHolder.isFirstLevel()){
            //로그가 끝날 때
            traceIdHolder = null; //destroy
        } else {
            traceIdHolder = traceIdHolder.createPreviousId(); //들어갔다가 나올 때 하나씩 줄어듦
        }
    }

    private static String addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append( (i == level - 1) ? "|" + prefix : "| ");
        }
        return sb.toString();
    }
}
//TraceId를 동기화하는 부분인 파라미터를 사용하는 것에서 TraceId traceHolder 필드를 사용하도록 변경
//이제 직전 로그의 TraceId는 파라미터로 전달되는 것이 아니라 FieldLogTrace의 필드인 traceHolder에 저장된다
//여기서 중요한 부분은 로그를 시작할 때 호출하는 syncTraceId()와 로그를 종료할 떄 호출하는 releaseTraceId()임
//syncTraceId TraceId를 새로 만듦(최초 호출 시)
//호출되어 있는 경우 현재 ID를 유지한 상태로 level만 1증가시킴
//두번쨰 호출되었을땐 레벨만 증가
// 메서드를 호출할 떄는 level이 하나 증가해야하지만 호출이끝나면 level이하나 감사
//releaseTraceId() -> 레벨이 첫단계면 디스트로이 아니면 레벨이 -1 줄어듦
