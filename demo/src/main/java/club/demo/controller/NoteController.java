package club.demo.controller;

import club.demo.security.dto.NoteDTO;
import club.demo.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping(value = "")
    public ResponseEntity<Long> register(@RequestBody NoteDTO noteDTO){

        log.info(" --------------------------- register ---------------------------");
        log.info("{}",noteDTO);

        Long num = noteService.register(noteDTO);
        return new ResponseEntity<>(num, HttpStatus.OK);
    }
}
