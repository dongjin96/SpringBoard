package board.Service;
import board.Domain.Dto.BoardDto;
import board.Domain.Entity.board.BoardEntity;
import board.Domain.Entity.board.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    @Autowired
    BoardRepository boardRepository;

    //글쓰기 메소드
    public void boardwrite(BoardDto boardDto){
        boardRepository.save(boardDto.toentity());
    }

    //모든글 출력 메소드
    public ArrayList<BoardDto>boardlist() {
        List<BoardEntity> boardEntities = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));
        ArrayList<BoardDto> boardDtos = new ArrayList<>();
        for (BoardEntity boardEntity : boardEntities) {
            String date = boardEntity.getCreatedDate().format(DateTimeFormatter.ofPattern("yy-MM-dd"));
            String nowdate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy-MM-dd"));
            if (date.equals(nowdate)) {
                date = boardEntity.getCreatedDate().format(DateTimeFormatter.ofPattern("hh:mm:ss"));
            }
            BoardDto boardDto = new BoardDto(
                    boardEntity.getBnum(),
                    boardEntity.getB_title(),
                    boardEntity.getB_write(),
                    boardEntity.getB_contents(),
                    date,
                    boardEntity.getB_password(),
                    boardEntity.getB_view(),
                    boardEntity.getB_img(),null);


            boardDtos.add(boardDto);

        }
        return boardDtos;
    }
    //게시물 view 출력
    public BoardDto getboard(int b_num){
        Optional<BoardEntity> entityoptional = boardRepository.findById(b_num);
        String date = entityoptional.get().getCreatedDate().format(DateTimeFormatter.ofPattern("yy-MM-dd"));
        return BoardDto.builder()
                .bnum(entityoptional.get().getBnum())
                .b_title(entityoptional.get().getB_title())
                .b_contents(entityoptional.get().getB_contents())
                .b_write(entityoptional.get().getB_write())
                .b_view(entityoptional.get().getB_view())
                .b_createDate(date)
                .b_img(entityoptional.get().getB_img())
                .build();
    }
    public boolean delete(int b_num){
        Optional<BoardEntity> entityOptional = boardRepository.findById(b_num);

        if(entityOptional.get() != null){
            boardRepository.delete(entityOptional.get());
            return true;
        }else{
            return false;
        }
    }
    //게시물 수정 처리
    @Transactional
    public boolean update(BoardDto boardDto) {
        try {
            Optional<BoardEntity> entityOptional = boardRepository.findById(boardDto.getBnum());
            entityOptional.get().setB_title(boardDto.getB_title());
            entityOptional.get().setB_contents(boardDto.getB_contents());
            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
    public boolean passwordcheck(int b_num ,String b_password ){
        Optional<BoardEntity>board=boardRepository.findById(b_num);
        if(board.get().getB_password().equals(b_password)) {

            return true;
        }
        return false;
    }


}