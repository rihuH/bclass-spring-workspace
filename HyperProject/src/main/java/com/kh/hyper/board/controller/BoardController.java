package com.kh.hyper.board.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.hyper.board.model.service.BoardService;
import com.kh.hyper.board.model.vo.Board;
import com.kh.hyper.common.ModelAndViewUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin("*")
@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {

	private final BoardService boardService;
	private final ModelAndViewUtil mv;
	
	/*
	 * 스프링 특징 PSA 
	 * portable service abstraction
	 */
	
	// menubar.jsp에서 게시판 클릭하면 boards //  page == 1
	// 페이징바 버튼ㅇ늘 누르면 boards?page=요청페이지
	@GetMapping("boards")
	public ModelAndView selectBoardList(@RequestParam(value="page", defaultValue="1") int page) {
		Map<String, Object> map = boardService.selectBoardList(page);
		return mv.setViewNameAndData("board/list", map);
	}
	
	@GetMapping("insertForm")
	public String insertForm() {
		return "board/insert_form";
	}
	
	@PostMapping("boards")// 보통 요청에는 조회요청이 대부분을 차지하는데 그건 복수가 대부분이므로(여러 자원에 대한 요청이므로) 
	//일반적으로 boards 같은 복수형이 권장되어,
	// 지금처럼 인서트 하나만 하더라도 통일성을 위해 복수로 작성하는 것이 권장된다.
	// 하나로 통일해야 @RequestMapping을 컨트롤러에 달아서생략하기도 하고 그럴수 있으니까 웬만하면 복수형으로
	public ModelAndView save(Board board, MultipartFile upfile, HttpSession session) { // jsp의 input 타입=file의 네임속성값과 MultipartFile의 파일명을 똑같이 맞춰줘야한다.
				// 파일 여러개 받고 싶으면 MultipartFile[] 로 하면 됨
		log.info("게시글정보 : {}, 파일정보 : {}", board, upfile);
		/*
		 * 첨부파일의 존재유무를 upfile로 구분할 수 없음. 무조건 객체가 생성되기 때문!!
		 * 그래서 contentType은 좀 그렇고
		 * 파일이름이나 size 둘 중 하나로 판단을 해야하는데
		 * 
		 * 일단
		 * 
		 * 폴더와 같이 파일도 생성해놓고 안에 아무것도 없으면
		 * 예를 들어서 txt 파일 만들어만놓고 안에 아무것도 없으면 크기가 0임
		 * 그래서 파일은 데이터가 없을 수도 있어서 사이즈를 가지고 비교할 수 없음
		 * 
		 * => 파일첨부/ 미첨부 차이점
		 * => filename필드에 원본명이 존재하는가 ""인가
		 * 
		 * 전달된 파일이 존재할 경우=> 파일명 수정 후 업로드
		 * 
		 */
		boardService.insertBoard(board, upfile);
		session.setAttribute("alertMsg", "게시글 등록 성공");
		return mv.setViewNameAndData("redirect:boards", null);
	}
	
	@GetMapping("boards/{id}")// 달러$표시는 쓰면 안됨. 보드 요청하는게 명확하고 뒤에 id가 13번이나 10번으로 식별값이 되어 뭘 요청하는지 정확히 알 수 있어서 좋은 매핑값이 된다
	// @GetMapping("boards/{category}/{id}")같은 계층구조도 가능하다  / 는 하나의 값에 대한 계층구조로만 가능하다. 키밸류를 여러개 넘길때는 본래 했던 것처럼 ? = && && 키밸류 여러개로 넘겨야한다
	// public ModelAndView selectById(@PathVariable(name="category") String category, @PathVariable(name="id") Long id){
	public ModelAndView selectById(@PathVariable(name="id") Long id) {
//		log.info("{}", id);
		Map<String, Object> responseData = boardService.selectById(id);
		return mv.setViewNameAndData("board/detail", responseData);
	}
	
	@PostMapping("boards/delete")
	public ModelAndView deleteBoard(Long boardNo, String changeName) {
		log.info("누른 보드 {} 네임 {}", boardNo, changeName);
		boardService.deleteBoard(boardNo, changeName);
		return mv.setViewNameAndData("redirect:/boards", null);
	}
	
	@PostMapping("boards/update-form")
	public ModelAndView updateForm(Long boardNo) {
		Map<String, Object> responseData = boardService.selectById(boardNo);
		return mv.setViewNameAndData("board/update", responseData);
	}
	
	
	
/*	
	@PostMapping("boards/update")
	public ModelAndView updateBoard(Board board, @RequestParam("upfile") MultipartFile upfile, HttpSession session) {
		String userId = ((Member)session.getAttribute("loginUser")).getUserId();
		if(!!!"".equals(upfile.getOriginalFilename())) {
			board.setOriginName(upfile.getOriginalFilename());
		}
		boardService.updateBoard(board, upfile, userId);
		return null;
	}
*/	
	@PostMapping("boards/update")
	public ModelAndView update(Board board, MultipartFile upfile) {
		log.info("{} / {}", board, upfile);
		/*
		 * 첨부파일??
		 * 1. 기존x 새 x => 그렇구나
		 * 2. 기존 o 새 o => origin 새거 change 새거
		 * 3. 기존 x 새 o => origin 새 change 새
		 * 4. 기존 o 새 x => origin 기존 change 기존
		 * 
		 * 
		 */
		boardService.updateBoard(board, upfile);
		return mv.setViewNameAndData("redirect:/boards/" + board.getBoardNo(), null);
	}
	
	
	/*
	@ResponseBody
	@PostMapping(value = "reply", produces="text/html; charset=UTF-8")
	public String ajaxInsertReply(Reply reply) {
		log.info("리플라이컨트롤러 {}", reply);
		return String.valueOf(boardService.insertReply(reply));
	}
	
	@ResponseBody
	@GetMapping(value="reply", produces="application/json; charset=UTF-8") // JSON형태로{ replyNo : 1, replyWriter : 'admin',...} {replyNo......}...
	public List<Reply> ajaxSelectReply(Long boardNo){
		log.info("리스트 리플라이 컨트롤러 {}", boardNo);
		return boardService.selectReplyList(boardNo);
	} 시즌 2 컨트롤러 BoardApiController로 이동
	*/
	/*
	 * 암묵적인 약속
	 * SELECT : GET
	 * INSERT : POST
	 * UPDATE : PUT
	 * DELETE : DELETE
	 * 로 보내자는 것이 약속
	 * 
	 * reply면 reply로 요청 보내고 위의 crud는 각 요청으로 보내 처리
	 * 
	 */
	
	@GetMapping("map")
	public String mapForward() {
		return "common/map";
	}
	
}
