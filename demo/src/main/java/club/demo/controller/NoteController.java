package club.demo.controller;

import club.demo.security.dto.NoteDTO;
import club.demo.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<Long> register(@RequestBody NoteDTO noteDTO){

        log.info(" --------------------------- register ---------------------------");
        log.info("{}",noteDTO);

        Long num = noteService.register(noteDTO);
        return new ResponseEntity<>(num, HttpStatus.OK);
    }

    @GetMapping(value = "/{num}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NoteDTO> read(@PathVariable("num") Long num){
        log.info("------------------ read --------------------------------");
        log.info("num");
        return new ResponseEntity<>(noteService.get(num), HttpStatus.OK);
    }
    //read()는 @PathVariable 방식을 사용해서 경로에 있는 Note의 num을 얻어서 해당 노트 정보를 반환, GET 방식은 브라우저 확인가능하지만
    //REST 테스트 도구 이용도 가능

    @GetMapping(value = "/all",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NoteDTO>> getList(String email){
        log.info("------------------ getList --------------------------------");
        log.info("email");

        return new ResponseEntity<>(noteService.getAllWithWriter(email), HttpStatus.OK);
    }
    //getList()는 파라미터로 전달되는 이메일 주소를 통해서 해당 회원이 작성한 모든 Note에 대한 정보를 JSON으로 반환
    //여러 개의 Note를 추가한 상태에서 GET 방식으로 확인하면 아래와 같이 JSON의 배열을 확인할 수 있음

    //Note의 삭제는 delete 방식으로 처리

    @DeleteMapping(value = "/{num}",produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> remove(@PathVariable("num") Long num){
        log.info("------------------ remove --------------------------------");
        log.info("num");

        noteService.remove(num);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PutMapping(value = "/{num}",produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> modify(@RequestBody NoteDTO noteDTO, @PathVariable("num") Long num){
        log.info("------------------ modify --------------------------------");
        log.info("num");
        noteDTO.setNum(num);
        noteService.modify(noteDTO);
        return new ResponseEntity<>("modified", HttpStatus.OK);

    }

}
