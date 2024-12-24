package com.kh.hyper.member.model.service;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.kh.hyper.member.model.dao.MemberMapper;
import com.kh.hyper.member.model.vo.Member;

import lombok.RequiredArgsConstructor;

@EnableTransactionManagement // 트랜잭션매니저 사용하기 위한 태그. => 묶으려는 트랜잭션이 있는 join메소드로 이동
@Service // Component보다 더 구체적으로 ServiceBean으로 등록하겠다.
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	
	/*
	 * Mapper 애노테이션 배워서 이제 주석처리
	private final MemberDao memberDao;
	private final SqlSessionTemplate sqlSession;// 기존의 myBatis의 sqlSession을 대체
	
	private final MemberMapper mapper;
	private final BCryptPasswordEncoder passwordEncoder; // 서비스에서 예외처리하기위해 컨트롤러에서 해놨던거 여기에 추가
	
	@Autowired
	public MemberServiceImpl(MemberDao memberDao, SqlSessionTemplate sqlSession) {
		this.memberDao = memberDao;
		this.sqlSession = sqlSession;
	}
*/

	/*@Override
	public Member login(Member member) {
		/*
		 * SqlSession sqlSession = getSqlSession();
		 * Member member = new MembarDao()......
		 * sqlSession.close();
		 * return member;
		
		 * SqlSession은 SqlSessionTemplate으로 대체됨.
		 * Spring에 빈으로 등록해놨음

//		return memberDao.login(sqlSession, member);
		// 스프링이 사용 후 자동으로 객체를 반납해주기 때문에 close() 호출하지 않음
		// 따라서 바로 돌려주어도 되므로 
		
		
		// 매퍼애노테이션 버전은 sqlSession 전달하지 않는다.
		return mapper.login(member);
	}
	
	// 새로운 방식의 로그인. 예외처리도 진행할 로그인
	@Override
	public Member login(Member member) {
		Member loginMember = mapper.login(member);
		// 1. 아이디가 존재하지 않는 경우
		if(loginMember == null) {
			throw new UserIdNotFoundException("존재하지 않는 아이디로 접속요청"); //예외발생시키기
		}
		// 스프링은 예외발생시 이를 처리할 수 있는 익셉션 핸들러를 만들 수 있다.
		// hyper에 새로운 exception.controller패키지를 만들 것
		
		// 2. 비밀번호가 잘못된 경우
		if(!!!passwordEncoder.matches(member.getUserPwd(), loginMember.getUserPwd())) {
			throw new ComparePasswordException("비밀번호가 일치하지 않습니다");
		} else {
			return loginMember;
			// 3. 둘 다 통과해서 정상적으로 조회
		}
	}


	@Override
	@Transactional // 하나의 트랜잭션으로 묶겠다는 의미의 태그. 이 태그 후 다시 회원가입하ㅣ면 500오류는 동일하게 뜨고, 회원가입도 되지 않는다
	public int /*새로운 조인에서는 메소드끝까지 읽으면 성공이므로 뭘 돌려주지 않을 것 void로 할것이지만
	지금 인터페이스에 int 해놔서 그냥 아무 정수나 돌리 join(Member member) {
		/*
		 * 커넥션만들기 / DAO호출/ 트랜잭션처리/ 자원반납/ 결과 반환
		 * 
		 */
//		return memberDao.join(sqlSession, member);
		// 트랜잭션 처리 sqlSessionTemplate가 자동 commit
		// 자원반납도 알아서 해줌
		
//		return mapper.join(member); // 아래것이 실패해도 얘는 스프링이 오토커밋으로 커밋해버려서, 500오류가 발생하지만 테이블에 얘는 들어간다
//		return mapper.test(); // 얘는 실패
	/*
	 * 현재까지의 코드의 문제점- > 예외처리를 안 했음
	 * 
	 * test()에서 unchecked exception 이 발생하면 아예 result가 돌아오지 않으므로****************** 
	 * 조건문으로 해결할 수 없다
	 * 무슨 예외가 나오는지 확인하고 그에 맞는 예외처리구문을 작성해서 실패시 0를 리턴해주어야한다
	 * 
	 * try{
	 * mapper.test();
	 * mapper.join(member);
	 * return 1;
	 * } catch(DuplicateKeyException e){
	 * return 0;
	 * }
		
		 * 새로운 join
		 * 
		 * 앞단에서 하는 유효성검사의 의의 : 오직 사용자 편의.
		 * 
		 * 그런데 위를 우회해서
		 * 이상한 값이 넘어올 수도 있고 html 스크립트코드는 지우면 그만이라 백에서도 해주어야함
		 * 
		 * 예를 들어서
		 * case1. 아이디 중복 안됨
		 * 2 아이디 30글자 넘어가면안됨
		 * 3 비밀번호값을 암호화해서 insert해야한다는 조건들이 있따고 침
		 * 
		Member userInfo = mapper.login(member);
		
		if(userInfo != null && member.getUserId().equals(userInfo.getUserId())) {
			// 아이디가 이미 존재하는 경우
			throw new UserFoundException("이미 존재하는 아이디이니다.");
		}
		
		if(member.getUserId().length() > 30 || member.getUserId().equals("")) {
			throw new TooLargeValueException("값의 길이가 너무 깁니다");
		}
		
		member.setUserPwd(passwordEncoder.encode(member.getUserPwd()));
		mapper.join(member);
//		return 0; 여기까지 왔으면 인서트 안 될 수 없으므로 정수르 ㄹ돌려줄 필요가 없음.
		// 참고 자바에서도 정규표현식 쓸 수 있으니까 비밀번호 검사 등 할 때 참고할것
		return 1;
	}

	@Override
	public int updateMember(Member member) {
		
		/*
		 * 새로운 update
		 * 
		 * 기존 html에 붙어있는 readonly는 사용자가 지울 수 있으므로 별로 의미가 없다
		 * 사용ㅇ자는 값을 변경해서 다른 회원인 척 하고 들어올수있음
		 * 
		 * 세션도 같이 받아서 앞단에서 넘어온 userId와 session의 loginUser키값의 userId필드값이 동일한지 확인해야하고
		 * => 사용자가 입력한 userID값이 DB에 존재하는지 확인도 해야함
		 * 사용자가 입력한 업데이트 값들이 컬럼크기에 넘치지 않는지 || 제약조건 부합하는지 등등..
		 * 다해야하지만 시간 없으므로 id가 존재하는지마나 확인하곘음
		 * 
		 * 
		Member userInfo = mapper.login(member);
		if(userInfo == null) {
			throw new UserIdNotFoundException("존재하지 않는 사용자입니다");
		}
		
		mapper.updateMember(member);
		return 0; // 쓰지 않을 값이므로 아무거나 돌려줌
	}

	@Override
	public int delete(String userPwd, HttpSession session) {
		Member loginUser = (Member)session.getAttribute("loginUser");
		loginUser.setUserPwd(userPwd);
		Member userInfo = mapper.login(loginUser);
		
		if(userInfo == null) {
			throw new UserIdNotFoundException("존재하지 않는 사용자입니다.");
		}
		
		if(!!!passwordEncoder.matches(loginUser.getUserPwd(), userInfo.getUserPwd())) {
			throw new ComparePasswordException("비밀번호가 일치하지 않습니다");
		}
		
		mapper.delete(userInfo);
		return 0;
	}
	
	1절의 코드!!!!!
	*/
	
	
	/*
	 * 지금까지 코드의 문제점
	 * 
	 *  Single Responsibility Principle => SRP 단일책임원칙 위반
	 *  객체설계원칙 중 하나
	 *  하나의 클래스는 하나의 책임만 가져야함
	 *  이 클래스에 변경이 일어나야 하면, 코드를 고쳐야하는 이유는 딱 한가지여야 한다는 것.
	 *  위에까지 코드는 너무 많은 책임을 가지고 있음.
	 *  예를 들면 비크립트도 바꾸고 싶으면 그것때문에 이걸 바꿔야함
	 *  
	 *  책임을 넘기고 싶으면 다른 클래스를 만들어야함
	 *  
	 * 
	 * 
	 * 아무튼 잘 바꿔보기
	 */
	
	private final PasswordEncryptor passwordEncoder;
	private final MemberValidator validator;
	private final MemberMapper mapper;
	
	@Override
	public Member login(Member member) {
		return validator.validateMemberExists(member);
	}
	
	
	@Override
	public void join(Member member) {
		validator.validateJoinMember(member); // 각 메소드를 따로 2번 호출하면 그 메소드가 수정됐을 때 여기를 또 수정해야하므로
		// 이런 식으로 또 하나의 메소드를 만들어서 이것만 호출하고, 진짜 코드가 수정되었을때는 밸리데이터클래스의 코드만 수정할 수 있도록
		// 하면 의존성을 더 줄일 수 있다
		member.setUserPwd(passwordEncoder.encode(member.getUserPwd()));
		mapper.join(member);
	}
	
	
	@Override
	public void updateMember(Member member, HttpSession session) {
		validator.validateMemberExists(member);
		mapper.updateMember(member);
	}
	
	
	@Override
	public void delete(Member member) {
		Member userInfo = validator.validateMemberExists(member);
	}


	@Override
	public String checkId(String userId) {
		return mapper.checkId(userId) > 0 ? "NNNNN" : "NNNNY";
	}
		
}


