package hello.advanced.trace.threadlocal.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalService {
    private ThreadLocal<String> nameStore = new ThreadLocal<>();


    public String logic(String name) {
        log.info("저장 name ={} -> nameStore={}", name, nameStore.get());
        nameStore.set(name);
        sleep(1000);
        log.info("nameStore ={}", nameStore.get());
        return nameStore.get();
    }

    private void sleep(int millis) {
        try{
            Thread.sleep(millis);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

//쓰레드 로컬을 사용하도록만 바뀜
//값 저장: ThreadLocal.set(xxx);
//값 조회 : ThreadLocal.get();
//값 제거 : ThreadLocal.remove();
//주의
//해당 쓰레드가 쓰레드 로컬을 모두 사용하고나면 ThreadLocal.remove()를 호출해서 쓰레드 로컬에 저장된 값을 제거해주어야함
// 메모리 누수발생의 위험이 존재함
