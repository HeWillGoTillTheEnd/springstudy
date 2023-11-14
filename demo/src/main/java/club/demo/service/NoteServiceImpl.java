package club.demo.service;

import club.demo.entity.Note;
import club.demo.repository.NoteRepository;
import club.demo.security.dto.NoteDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    @Override
    public Long register(NoteDTO noteDTO) {
        Note note = dtoToEntity(noteDTO);
        noteRepository.save(note);
        return note.getNum();
    }

    @Override
    public NoteDTO get(Long num) {
        Optional<Note> withWriter = noteRepository.getWithWriter(num);
        if(withWriter.isPresent()) {
            return entityToDTO(withWriter.get());
        }
        return null;
    }

    @Override
    public void modify(NoteDTO noteDTO) {
        Long num = noteDTO.getNum();
        Optional<Note> result = noteRepository.findById(num);
        if(result.isPresent()){
            Note note = result.get();
            note.changeTitle(noteDTO.getTitle());
            note.changeContent(noteDTO.getContent());
            noteRepository.save(note);
        }
    }

    @Override
    public void remove(Long num) {
        noteRepository.deleteById(num);
    }

    @Override
    public List<NoteDTO> getAllWithWriter(String writerEmail) {
        List<Note> noteList = noteRepository.getList(writerEmail);
        return noteList.stream().map(this::entityToDTO).collect(Collectors.toList());
    }
}
