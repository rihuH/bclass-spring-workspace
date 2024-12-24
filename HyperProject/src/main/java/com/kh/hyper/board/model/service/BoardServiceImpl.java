package com.kh.hyper.board.model.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.hyper.board.model.dao.BoardMapper;
import com.kh.hyper.board.model.dao.BoardNotFoundException;
import com.kh.hyper.board.model.vo.Board;
import com.kh.hyper.board.model.vo.Reply;
import com.kh.hyper.common.model.vo.PageInfo;
import com.kh.hyper.common.template.Pagination;
import com.kh.hyper.exception.BoardNoValueException;
import com.kh.hyper.exception.FailToFileUploadException;
import com.kh.hyper.exception.InvalidParameterException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService {
	
	private final BoardMapper mapper;
	private final ServletContext context;
	
	private int getTotalCount() {
		int totalCount = mapper.selectTotalCount();
		if(totalCount == 0) {
			throw new BoardNotFoundException("게시글 없음");
		}
		return totalCount;
	}
	
	private PageInfo getPageInfo(int totalCount, int page) {
		return Pagination.getPageInfo(totalCount, page, 5, 5);
	}
	
	private List<Board> getBoardList(PageInfo pi){
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return mapper.selectBoardList(rowBounds);
	}
	
	private void validateBoard(Board board) {
		if(board == null || 
		  board.getBoardTitle() == null || board.getBoardTitle().trim().isEmpty() ||
		  board.getBoardContent() == null || board.getBoardContent().trim().isEmpty() ||
		  board.getBoardWriter() == null || board.getBoardWriter().trim().isEmpty()) {
			throw new BoardNoValueException("부적절한 입력값");
		}
		
		// XSS(크로스사이드스크립트)공격방지를 위한 처리 <script>태그
		/*
		 * < == &lt;
		 * > == &gt;
		 * \ == &quot;
		 * & == &amp 
		 * 
		 */
		
		String boardTitle = escapeHtml(board.getBoardTitle());
		String boardContent = escapeHtml(board.getBoardContent());
		board.setBoardTitle(convertNewLineToBr(boardTitle));
		board.setBoardContent(convertNewLineToBr(boardContent));
	}
	
	private String escapeHtml(String value) {
		return value.replaceAll("<", "&lt").replaceAll(">", "&gt");
	}
	
	private String convertNewLineToBr(String value) {
		return value.replaceAll("\n", "<br>");
	}
	
	private void handleFileUpload(Board board, MultipartFile upfile) {
		// 파일 유무 체크 / 업로드
				// 코드작성은 방어적으로. NullPointException방지
		String fileName = upfile.getOriginalFilename();
		log.info(fileName);
		if(fileName.equals("")) {
			return;
		}
		String ext = fileName.substring(fileName.lastIndexOf("."));
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		int randomNum = (int)Math.random()*90000 + 10000;
		String changeName = currentTime + randomNum + ext;
		
		String savePath = context.getRealPath("/resources/upload_files/");
		
		try {
			upfile.transferTo(new File(savePath + changeName));
		} catch (IllegalStateException | IOException e) {
			throw new FailToFileUploadException("파일 이상해~~");
		}
		// 첨부파일이 존재했다 => 업로드 + Board객체에 originName + changeName
		board.setOriginName(fileName);
		board.setChangeName("/hyper/resources/upload_files/" + changeName);
	}

	@Override
	public Map<String, Object> selectBoardList(int currentPage) {
		// 총 개수 == DB가서 조회
		// 요청 페이지 == currentPage
		// 한 페이지에 게시글 몇 개?  == 5개
		// 페이징바 몇 개? == 5개
		
		/*
		int totalCount = mapper.selectTotalCount();
		if(totalCount == 0) {
			throw new BoardNotFoundException("게시글 없음");
		} 책임분리를 위해 getTotalCount메소드를 생성해서 붙여넣기*/
		int totalCount = getTotalCount();
		
/*		PageInfo pi = Pagination.getPageInfo(totalCount, currentPage, 5, 5); 책임분리
		PageInfo pi = getPageInfo(totalCount, currentPage);
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
	얘도 책임 분리*/
		PageInfo pi = getPageInfo(totalCount, currentPage);
		List<Board> boards = getBoardList(pi);
		// 클래스도 하나의 책임, 메소드도 하나의 책임을 가져야함
		
//		log.info("게시글 목록 : {}", boards);

		Map<String, Object> map = new HashMap();
		map.put("boards", boards);
		map.put("pageInfo", pi);
		return map;
	}

	@Override
	public void insertBoard(Board board, MultipartFile upfile) {
		
		validateBoard(board); // 책임분리를 위해 여기서 유효성검증
		
		// 첨부파일이 존재하지 않을 경우 : board == 제목, 내용, 작성자
		// 첨부파일이 존재할 경우 : board == 제목, 내용, 작성자, 원본명, 변경명
		
		if(!!!"".equals(upfile.getOriginalFilename())) {
			handleFileUpload(board, upfile);
		}
		
		mapper.insertBoard(board);
	}

	
	
	/*
	 * 아래 selectById에서 사용할 게시글 번호 검증 메소드
	 */
	private void validateBoardNo(Long boardNo) {
		// 번호가 0보다 큰 수인지 검증
		if(boardNo < 1) {
			throw new InvalidParameterException("유효하지 않는 게시글 번호입니다.");
		}
	}
	private void incrementViewCount(Long boardNo) {
		// 조회수 증가 - 가져온 출력화면에서 조회수도 보여주기 때문에
		int result = mapper.inceaseCount(boardNo);
		if(result < 1) {
			throw new BoardNotFoundException("게시글이 존재하지 않음");
		}
	}
	private Board findBoardById(Long boardNo) {
		Board board = mapper.selectById(boardNo);
		if(board == null) {
			throw new BoardNotFoundException("게시글을 찾을 수 없습니다");
		}
		return board;
	}
	@Override
	public Map<String, Object> selectById(Long boardNo) {
		log.info("누른 번호  : {}",boardNo);
		validateBoardNo(boardNo);
		incrementViewCount(boardNo);
		 
		 //사용자가 요청 보낸 게시글이 있는지
		 Board board = findBoardById(boardNo);
		 Map<String, Object> responseData = new HashMap();
		 responseData.put("board", board);
		 
		return responseData;
	}



	@Override
	public void deleteBoard(Long boardNo, String changeName) {
		validateBoardNo(boardNo);
		findBoardById(boardNo);
		// board의 BoardWriter랑 login유저의 userId랑 비교하는 로직 있어야하는데 생략
		int result = mapper.deleteBoard(boardNo);
		
		if(result <= 0) {
			throw new BoardNotFoundException("게시글 삭제 실패");
		}
		
		// 파일 삭제. input요소로 값을 넘겼으니까 파일이 없으면 ""가 넘어옴
		if(!!!"".equals(changeName)) {
			try {
				new File(context.getRealPath(changeName)).delete();
			} catch(RuntimeException e) { // 파일경로가 이상해서 오류 발생할 수 있는데unchecked라서 직접 해줌
				throw new BoardNotFoundException("파일을 찾을 수 없습니다.");
			}
		}
		

	}

/*
	@Override
	public void updateBoard(Board board, MultipartFile upfile, String userId) {
		validateBoardNo(board.getBoardNo());
		findBoardById(board.getBoardNo());
		if(!!!board.getBoardWriter().equals(userId)) {
			throw new BoardNotFoundException("게시글 수정 권한 없음");
		}
		
		handleFileUpload(board, upfile);
		int result = mapper.update(board);
		if(result < 1) {
			throw new BoardNotFoundException("보드업데이트실패");
		}
	}
	*/
	
	@Override
	public void updateBoard(Board board, MultipartFile upfile) {
		validateBoardNo(board.getBoardNo());
		findBoardById(board.getBoardNo());
		if(!!!"".equals(upfile.getOriginalFilename())) {
			
			if(board.getChangeName() != null) {
				new File(context.getRealPath(board.getChangeName())).delete();
			}
			handleFileUpload(board, upfile);
		}
		int result = mapper.updateBoard(board);
		
		if(!!!(result > 0)) {
			throw new BoardNotFoundException("업데이트 실패");
		}
		
	}

	@Override
	public int insertReply(Reply reply) {
		return mapper.insertReply(reply);
	}

	@Override
	public List<Reply> selectReplyList(Long boardNo) {
		return mapper.selectReplyList(boardNo);
	}

}
