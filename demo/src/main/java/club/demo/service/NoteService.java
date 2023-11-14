package club.demo.service;

import club.demo.entity.ClubMember;
import club.demo.entity.Note;
import club.demo.security.dto.NoteDTO;

import java.util.List;

public interface NoteService {
    Long register(NoteDTO noteDTO);

    NoteDTO get(Long num);

    void modify(NoteDTO noteDTO);

    void remove(Long num);
    List<NoteDTO> getAllWithWriter(String writerEmail);

    default Note dtoToEntity(NoteDTO noteDTO){
        return Note.builder()
                .num(noteDTO.getNum())
                .title(noteDTO.getTitle())
                .content(noteDTO.getContent())
                .writer(ClubMember.builder().email(noteDTO.getWriterEmail()).build()).build();

    }

    default NoteDTO entityToDTO(Note note){
        NoteDTO noteDTO = NoteDTO.builder()
                .num(note.getNum())
                .title(note.getTitle())
                .content(note.getContent())
                .writerEmail(note.getWriter().getEmail())
                .regDate(note.getRegDate())
                .modDate(note.getModDate())
                .build();
        return noteDTO;
    }
}
