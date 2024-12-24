package com.kh.hyper.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kh.hyper.common.ModelAndViewUtil;
import com.kh.hyper.member.model.service.MemberService;
import com.kh.hyper.member.model.vo.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller // bean등록을 위해 이 애노테이션을 달음
//@Component도 있지만, 이렇게 달면 Controller라고 인식
// servlet-context에 <annotation-driven>이라는 태그를 달아놓았기 때문에 이렇게 쓸 수 있다
//@RequestMapping(value="member") // contextRoot/member/insert 이런 식으로 요청들어올 때 추가가 됨.???????????
/*
 * Component는 말 그대로 빈등록, 크게 빈 등록하는 것 
 * Controller가 더 세부적인 기능임. 빈 등록인데 컨트롤러 빈으로 등록할 것이라는 것
 * 
 * 컴포넌트로 등록하면 컨트롤러인 것을 모르니까 추가적인 것이 더 필요함.
 * 그래서 컨트롤러로 세부적으로 애노테이션 붙여주어야한다.
 */
@RequiredArgsConstructor
@Slf4j // 이 애노테이션으로 log를 사용할 수 있따. 출력문을 사용하는 데 대신 쓸 수 있음
public class MemberController {
	
	private final MemberService memberService;// 인터페이스니까 부모자료형으로 사용할 수있다.
	// 스프링이 이제 서비스도 빈으로 관리하니까, 여기 필드로 두면 스프링이 여기에 대입하면 되겠다고 판단을 할 수 있다.
//	private final BCryptPasswordEncoder passwordEncoder; // 스프링이 관리하는 빈을 주입받아서 써야하니까 일단 필드로 추가하고
	// 이렇게하면 하면 null이니까 생성자주입을 해야함. 생성자에 매개변수 추가를 해줌. 아래 mv를 필드로 추가하면서 얘는 할 일이 사라져서 주석
	private final ModelAndViewUtil mv;
	
	/*	
	@Autowired // 스프링에게 생성자 호출할 때 이거 넣어서 만들어달라고 하는 것.
	public MemberController(MemberService memberService, BCryptPasswordEncoder passwordEncoder) {
		this.memberService = memberService;
		this.passwordEncoder = passwordEncoder;
	}
	// wire 연결하는 작업을 wiring이라고 한다.
	// 스프링은 얘를 만들려면 생성자 호출해야하는데 , 기본생성자가 없으니까 얘만 호출할 수 있고 
	// 그러면 인자가 필요하니까 스프링이 자기가 가진 빈 중에서 저 타입의 빈을 찾아서 주입해준다.
	// 생성자주입이라고 한다.
	 * 
	 * 그런데 이러면 필드 추가될 때마다 필요한 생성자를 변경해주어야함.
	 * 
	 * 그래서 롬복을 배우고 옴
	 * 
	 * 무조건 값을 대입받고 싶은 필드는 final필드로 만들어준다.
	 * final필드는 선언과 동시에 초기화해야하는데, 우리는 생성자 호출할 때 대입받고싶음.
	 * 또 롬복에서 
	 * @RequiredArgsConstructor를 사용
	 * 그럼 final로 선언된 필드들은 생성자에 자동으로 추가해줌
	 * 
	 * final 키워드가 붙지 않으면 생성자 매개변수로 포함되지 않음
	 * 
	 * 상속은 의존성이 너무 높아서 쓰지 않는 편이 좋음
	 * 상속구조를 거치지 않고 컨트롤러가 서비스에 있는 멤버를 어떻게 사용할 수 있는지
	 * 
	 * 자바에서는 사용하고 싶은 멤버를 가지고 있는 객체를 필드에 담아서 사용했음
	 * 위 방식에서 다음 단계로 생성자 주입방식인 것이 디자인패턴 중 전략패턴이라고 함
	 * 스프링프레임워크는 거의 모든 코드가 전략패턴을 적극적으로 사용하고있따
	 * 실제 구현되는 객체는 빈으로 등록해줘야함
	 * 다형성을 적용한 것이다. service등에 상속받을 인터페이스를 먼저 만들고
	 * 그 인터페이스를 인자로 넘기면
	 * 자식인 service들의 버전이 달라져서 새로운 서비스로 변경되더라도 계속 사용할 수 있따
	 * 
	 * 스프링이 가진 빈들 중 타입에 맞는 빈을 인자로 전달해줌
	 *
	 * 이걸 의존성주입이라 함(DI)
	 * 
	 * 의존성 주입 방법도 3가지 방법이 있음. 하나는 지금처러 ㅁ생성자에
	 * @Autowired
	 * private MemberService memberService
	 * 로 필드 위에 적을 수 있음. 이건 필드주입이라고 함
	 * 다른 하나는 세터르 ㄹ만드는 건데 구려서 안함
	 * 
	 * 필드주입의 장점은 생성자주입보다 쓸 게 적다는 것 하나
	 * 가장 큰 단점은 순환의존성이 생길 수 있다는 것.
	 * 필드를 여러가지 써서 여러 서비스를 사용할 수 있는데, 그 서비스 사이에서도 의존성이 있을 수 있따
	 * 또 그에 맞는 빈이 없었을 때 미리 체크하기가 힘들다 코드 실행 전까지 모르고 코드 실행하면 nullpointexception 생김
	 * 생성자는 실행하자마자 빈으로 등록되면서 체크가 바로 가능하다
	 * 또 아직 안 배웠지만 테스트코드 작성할 때도 필드주입은 체크하기 힘든 경우가 많다
	 * 
	 * 필드주입/ 세터주입/ 생성자주입 방법 중 생성자주입 방법이 권장됨
	 * 
	 * 인텔리제이??를 사용할 경우 생성자주입 쓰지 않으면 안 됨
	 * 
	 * 생성자주입 : 매개변수와 일치하는 타입의 Bean객체를 검색해서 인자값으로 주입을 해줌!
	 * Spring의 D.I(Dependency Injection)
	 * 
	 * 만약 멤버서비스같은 걸 2개impliment하는 서비스면 오류남
	 * 오류 안 나려면 @qualify..??같은거 해서 우선순위를 알려줘야함
	 * 하지만 보통 그렇게 여러개를 상속받아서 사용하지 않음

	/*
	 * 내가 만들지 않은 클래스들은 @를 달 수 없어서
	 * <bean>으로 등록해주어야함
	 * 이건 내가 만든 클래스로 애노테이션 달 수 있으니까 이렇게 할 수 있따
	 * 
	 * 그럼 DispatcherServlet이 어떤 클래스가 애노테이션이 달린 것인지 어떻게 판결???
	 * servlet-context때문에 알 수 있게된다/
	
	/*
	 * 그럼 여기 메소드들 중 어느 핸드러랑 매핑할 지 어떻게 아는지?
	 * 또 애노테이션을 달아줌
	 * @RequestMapping(value="login.me") - login.me라는 요청이 오면 출동하는 메소드
	 * public void login(){ 
	 * RequestMapping타입의 애노테이션을 등록함으로서 HandlerMapping을 등록
	 * 이 애노테이션으로 얘가 또 bean으로 등록 되어버림.
	
/*	@RequestMapping(value="login.me")
	public void login() {
		System.out.println("로그인 요청");
	}
	
	 * 만약 매핑값이 겹치면 서버 켤 때 에러매세지로 알려준다. Ambiguous mapping
	 * @RequestMapping을 controller에도 달 수 있다.
	
	/*
	 * Spring에서 요청 시 전달값(Parameter)을 받아서 사용하는 방법
	 * 
	 * 1. HttpServletRequest를 이용해서 전달받기(기존의 JSP/ Servlet방식)
	 * 
	 * 핸들러의 매개변수로 HttpServletRequest타입을 작성해두면
	 * DispatcherServlet이 해당 메서드를 호출할 때 request객체를 전달해서 매개변수로 주입해줌!
	 * 
	@RequestMapping(value="login.me")
	public String login(HttpServletRequest request) {
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		
//		System.out.println(id); 영어는 인코딩 안 해도 뽑을 수 있다는데???????
		
		return "main";
		
	}*/
	
	
	/*
	 * 2. @RequestParam 애노테이션을 이용하는 방법
	 * request.getParameter("키")로 밸류를 뽑아오는 역할을 대신해주는 애노테이션
	 * value=이거는 생략 가능. 그런데 속성이 여러개면 value=, produce=... 생략 불가능
	 * 
	 * value속성의 값으로 jsp에 작성한 키 값을 적으면 알아서 해당 매개변수에 주입을 해줌.
	 * 근데 안 적어도 해줌. 대신 키 값이랑 똑같이 변수명을 작성해주어야함
	 * 
	 * 그리고 원래 input에 숫자 받았는데 입력 안 하면 ""빈문자열 넘어와서 이걸 그대로 파싱하며녀 NumberFormatException이 발생함
	 * 그런데 이 RequestParam은 defaultValue라는 속성이 있어서, 아무 값이 넘어오지 않았을 때 기본값을 지정할 수 있다.
	 * defaultValue="0" ?? 같은 식으로 작성해서 그런 문제를 미연에 방지할 수 있다
	 
	@RequestMapping("login.me")
	public String login(@RequestParam(value="id", defaultValue="user01") String id,@RequestParam(value="pwd") String pwd) {
		System.out.println(id + pwd);
		return "main";
	}*/
	
	/*
	 * 3. RequestParam애노테이션을 생략하는 방법 
	 * 안 쓰면 스프링이 자동으로 앞에 @~~를 달아줌
	 * 단, 매개변수 명을 jsp에서 전달한 키 값과 동일하게 적어야한다.
	 * 단점. 앞에서 전달한 키값의 의미가 명확하지 않을 수 있고, 디폴트밸류 속성을 쓰지 못함.
	@RequestMapping("login.me")
	public String login(String id, String pwd) {
//		System.out.println(id+pwd);
		
		Member member = new Member();
		member.setUserId(id);
		member.setUserPwd(pwd);
		
		return "main";
	}
	
	/*
	 * 4. 커맨드 객체 방식
	 * 
	 * 1)전달되는 키값과 객체의 필드명이 동일해야함.
	 * 2)매개변수인 객체에 기본생성자가 반드시 존재해야함
	 * 3)setter메소드가 반드시 존재해야함
	 * 
	 * 호출시 매개변수를 분석하는 과정을 가진다. 분석할 때 타입을 확인함
	 * 자바의 기본자료형(request포함)인 경우 알아서 requestParam이런거 붙여줌
	 * 우리가 만든 새로운 객체인 경우 스프링이 가진 클래스 중에서 찾음.
	 * 개발자가 만들어둔 클래스라고 인식하면 앞에 애노테이션을 붙여서
	 * 매개변수 객체에 기본생성자가 있는지 판단을 함
	 * 기본생성자가 있으면 그걸 이용해서 객체로 생성함
	 * 그 다음에 필드를 확인함
	 * 필드값이랑 앞에서 넘어온 키값이 일치하면 그 이름을 가진 setter를 찾아서 그 메소드를 이용해서 값을 대입해줌.
	 * 
	 * 스프링에서 해당 객체를 기본생성자를 통해서 생성한 후 내부적으로 setter메소드를 찾아서 요청시 전달값을 해당 필드에 담아줌
	 * setter injection이라고 한다.
	 * 
	 */
	/*
	@RequestMapping("login.me")
	public ModelAndView login(Member member) {
//		System.out.println(member);
		
		// new Service하면 문제점. service에 의존하는 관계가 되어버림
		// Service클래스 수정이 일어날 경우 이에 의존하고 있던 Controller가 영향을 받게 됨!! 아 어이없네
		// 그래서 의존해야 하는 애를 밖에서 인자로 받아옴. 이걸 의존성 주입이라고 함.
		Member loginMember = memberService.login(member);
		/*
		 * 돌아온 정보를 어디에 담아서 돌려줘야함, 이건 로그인정보니까 session일 것
		 * request가 필요한데 그건 어디에 있는것인지?????
		
		/*
		if(loginMember != null) {
		} else {
		}
		return "main";*/
		/*
		 * 클라이언트가 크롬에서 login.me요청을 보내면 톰캣에서 대장 어플리케이션인 DispatcherSevlet이 요청을 받는다.
		 * HandlerMapping 해야함 @RequestMapping 으로 함
		 * 어떤 컨트롤러인지 알아서 옴
		 * 
		 *  DispatcherServlet이 그걸 알아왔으면
		 *  이제 그 컨트롤러로 요청을 보내야함
		 *  그 안에 있는 handler에게 요청을 보내야함
		 *  
		 *  중요한건 그 요청이라는 건 종류가 아주 많음
		 *  http도 있지만 ajax, json을 돌려줘야 한다거나 하는 요청일 수도 이음.
		 *  동기식, 비동기식, 소켓통신인지에 따라 넘기는 형태가 변해야함.
		 *  다 똑같이 값이 넘어갈 수 없음.
		 *  따라서 컨트롤러를 호출할 때 바로 부르지 않음.
		 *  중간다리가 있다
		 *  중간다리는 Handler Adaptor라고 한다.
		 *  얘가 요청을 맞는 형태로 변환해서 컨트롤러를 호출함
		 *  
		 *  return값은 컨트롤러를 부른 그 Handler Adaptor,,,,,,
		 *  정확하게 컨트롤러를 부른 ModelAndView로 돌아감.??????????
		 *  
		 *  반환된 스트링은 ModelAndView의 필드, 인터페이스인 view라는 친구로 들어가는데, 그 안에는 viewName이라는 필드가 또 있다.
		 *  그, viewName필드로 스트링이 대입된다.
		 *  그리고 반환은 ModelAndView로 반환이 된다.
		 *  그러니까
		 *  Handler Adapert에서
		 *  viewName="main";으로 대입된다음에
		 *  이 필드가 담긴 ModelAndView를 DispatcherServlet으로 돌려준다.
		 *  이 반환받은 모델앤드뷰를 가지고 
		 *  render메소드로 감.
		 *  여기서 가공을 하는데 => servlet-context에 필기 있음
	}*/
	
	/*  
	 * Client의 요청 처리 후 응답데이터를 담아서 응답페이지로 포워딩 또는 URL재요청하는 방법
	 * 사실 RequestMapping은 메소드에는 잘 안 쓰고 클래스에 많이 쓴다
	 * 메소드에는 조금 더 구체적으로 해준다.
	 * 
	 * 1) 스프링에서 제공하는 Model객체를 사용하는 방법(ModelAndView에서 Model담당)
	 * model은 데이터를 의미함
	 * 포워딩할 응답 뷰로 전달하고자 하는 데이터를 맵형식(key-value)으로 담을 수 있는 영역
	 * Model객체는 requestScope
	 * 
	 * 단, setAttribute()가 아닌 addAttribute()를 호출해야함
	 * 
	 * 모델객체는 모델 달라고 하면 됨. 인자로 넣어두면 스프링이 생성해서 같이 줌. 얘가 리퀘스트를 대신할 것
	
	/*
	@PostMapping("login.me") // post 방식으로 온 것 매핑처리
	public String login(Member member, Model model, HttpSession session) { 리퀘스트 대신 모델을 쓰라고 권장
		
		Member loginMember = memberService.login(member);
		if(loginMember != null) { // 정보가 있다=> loginMember를 sessionScope에 담기
			
			session.setAttribute("loginUser", loginMember);
			
			// senRedirect
			// localhost/spring / 이렇게 만들고싶음
			
			// redirect:요청할URL???????????????
			return "redirect:/";
			redirect하라는 뜻 
			뷰리졸브가 달라짐 다른곳으로 감
		} else { // 로그인 실패 => 에러문구를 requestScope에 담아서 에러페이지로 포워딩
			model.addAttribute("errorMsg", "로그인 실패!");
			/*
			 *\/WEB-INF/views/common/error_page.jsp
			 *
			 *String Type return -> viewName에 대입
			 *==> DispatcherServlet으로 감.
			 *여기서 아까 본 서블릿컨텍스트에 있는 뷰리졸버에게 감
			 *뷰리졸버는 앞, 뒤에 스트링 붙여서 가공해줌
			 *뷰네임에 담긴 것을 사이에 끼워넣음
			 *
			return "common/error_page";
		}
	}
	
	@GetMapping("board") // 매핑값이 중복될 수 없는데, 애노테이션을 세부분류하면 이런 것도 가능하다.
	// board에 대한 요청으로 받을 수도 있다. 장점은 명확하다는 것. board 달라고 하는 것이 명확함
	// @PutMapping, DeleteMapping 등등 있다 아직은 못 함
	public String login() {
		return "main";
	}
	
	/*
	 * 2. ModelAndView타입으로 사용하는 방법
	 * 
	 * Model은 실체가 있어서 값을 받을 수 있지만 view는 인터페이스만 있고 실체가 없어서 그렇게 쓸 수 없다
	 * 
	 * Model은 데이터를 key-value세트로 담을 수 있는 객체
	 * view는 응답 뷰에 대한 정보를 담을 수 있음
	 * 
	 * Model객체와 View가 결합된 형태의 객체
	 
	@PostMapping("login.me")
	public ModelAndView login(Member member,
							HttpSession session, ModelAndView mv) {
		/*
		 * 사용자가 입력한 비밀번호 memberPwd필드. 평문 == plaintext
		 * Member Table의 USER_PWD컬럼에는 암호문이 들어있기 때문에
		 * where조건절 결과가 절대 true가 될 수 없음.
		
		Member loginMember = memberService.login(member);
		// member타입의 loginMember의 userPwd필드 : DB에 기록된 암호화된 비밀번호
		// Member타입의 member의 userPwd필드 : 사용자가 입력한 평문 비밀번호
		
		//$2a$10$c2j3lTCGuwbznz3JaIb/ieEJsFlh7Yd2Yk8gyiHm2GkHjQCxH.1Xe
		// $버전 $반복횟수 $salt값 ==> 을 똑같이 하면 같은 암호문이 나온다.
		// 이걸 비교할 수 있는 메소드가 BCryptPasswordEncoder객체가 가진 : matches()
		// matches(평문, 암호문)
		// 암호문에 포함되어 있는 버전과 반복횟수와 소금값을 가지고 인자로 전달된 평문을 다시 암호화를 거쳐서
		// 두 번째 인자로 전달된 암호문과 같은지 다른지를 비교한 결과값을 반환
		
		// 아래 이 기능은 로그인이라는 기능에 포함되어야하니까 서비스에 있어야 하는 작업이다
		if(loginMember != null && passwordEncoder.matches(member.getUserPwd(), loginMember.getUserPwd())) {
			session.setAttribute("loginUser", loginMember);
			session.setAttribute("alertMsg", "로그인에 성공했습니다");
			mv.setViewName("redirect:/");
		} else {
			// 여긴 리퀘스트가 필요한데, 모델앤드뷰에 모델에 담으면 된다. 다만 메소드명이 좀 다름
			mv.addObject("errorMsg", "로그인실패...").setViewName("common/error_page");
			// mv를 다시 반환해주기 때문에 메소드체이닝을 할 수 있다.
		}
		return mv;
	}*/
	// 새로운 버전의 login
	@PostMapping("login.me")
	public ModelAndView login(Member member,
							HttpSession session/*, ModelAndView mv*/) {
		session.setAttribute("loginUser", memberService.login(member));
		session.setAttribute("alertMsg", "로그인에 성공했습니다");
		/*mv.setViewName("redirect:/");
		return mv;*/
		return mv.setViewNameAndData("redirect:/", null);
	}
	
	@GetMapping("logout.me")
	public String logout(HttpSession session) {// 이번엔 모델에 담을 게 없으므로 그냥 스트링으로 돌림
		session.removeAttribute("loginUser");
		return "redirect:/";
	}
	// 모델을 딱히 쓸 곳이 없으면 스트링으로 돌려주는 편이
	@GetMapping("enrollForm.me")
	public String enrollForm() {
		//    /WEB-INF/views/member/enroll_form.jsp
		return "member/enroll_form";
	}
	
	@PostMapping("sign-up.me")
	public ModelAndView signUp(Member member, /*ModelAndView mv, */HttpSession session/*, HttpServletRequest request*/) {
//			System.out.println(member);
//		log.info("{}", member);
		//1. 인코딩 안 해서 한글 문자가 깨짐(영어는 괜찮)
		/*try {
//			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			/*
			 * 갑자기 checkedExcetpion??
			 * 원래 서블릿에서는 throws ServletException, IOException이 있었음
			 * UnsupportedEncodingException의 부모클래스는 IOException
			 * 
			 * 그리고 여기서 인코딩 해도 값이 뽑아서 나옴.
			 * 인코딩은 '값을 뽑기 전에'!!!! 해주어야함
			 * 그런데 이미 spring이 값 전부 뽑아서 멤버에 대입한 상태.
			 * 그러면 어떻게???
			 * 
			 * ==> 필터를 사용.
			 * 스프링에서 제공하는 '인코딩 필터' 를 사용
			 * => web.xml 에서 스프링에서 제공하는 인코딩 필터를 등록 => web.xml *필터 파트로 이동!
			 
			e.printStackTrace();
		}*/
		// 2. 나이를 입력하지 않았을 경우 "" 빈문자열이 넘어와 int에 setter하려고 할 때 빈문자열이 Bind되는 Parsing과정에서
		// String-> int의 NumberFormatException이 발생한다.
		// 클라이언트에게 400 Bad Request가 뜸. 사실은 클라이언트 잘못이 아니고 이걸 고려하지 않은 개발자 잘못임
		
			
		//3. 비밀번호가 사용자가 입력한 그대로의 '평문(plain text)'
		// 암호화를 해줘야함 ==> 메이븐레포지토리 + pom.xml의 dependency태그 로 이동!
		
			// Bcrypt 이용해서 암호화 => Spring Security Modules에서 제공(pom.xml)
			// PasswordEncoder를 .xml파일을 이용해서 configurationBean으로 Bean으로 등록
			// ==> web.xml에서 spring-security.xml파일을 로딩할 수 있도록 추가
			
			// 평문 출력
//			System.out.println("평문 : " + member.getUserPwd());
			// 암호화 작업
			//String encPwd = passwordEncoder.encode(member.getUserPwd());
			// 암호문 출력
//			System.out.println("암호문 : " + encPwd);
			// 이 System.. 구문도 마음에 안 듦. 스프링+스프링을 해서 불변객체로 자리를 또 차지하고있음
			
//			log.info("평문 : {}", member.getUserPwd());
			// log4j.xml 보면 console에 찍으라고 되어있으므로 System,,, 과 동일한 곳에 찍힌다.
			//그리고 어디서 찍었는지도 같이 나옴
//			log.info("암호문 : {}", encPwd);
			//member.setUserPwd(encPwd);
			// Member 객체 userPwd 필드에 평문이 아닌 암호문을 담아서 INSERT수행
		
		// 암호화작업은 이제 서비스에서 해주기로 함
		
			/*
			if(memberService.join(member) > 0) {
				session.setAttribute("alertMsg", "회원가입에 성공했습니다");
				mv.setViewName("redirect:/");
			} else {
				mv.addObject("errorMsg", "회원가입실패").setViewName("common/error_page");
			}
			원래 있었던 이 구문을 서비스로 다 넘겼음
			*/
		memberService.join(member);
		session.setAttribute("alertMsg", "가입성공성공");
		/*mv.setViewName("redirect:/");
		return mv;*/
		return mv.setViewNameAndData("redirect:/", null);
	}
	
	@GetMapping("mypage.me")
	public String mypage() {
		
		return "member/my_page";
	}
	
	@PostMapping("update.me")
	public ModelAndView update(Member member, /*ModelAndView mv, */HttpSession session) {
		/*if(memberService.updateMember(member) > 0) {
			
			// DB로부터 수정된 회원정보를 다시 조회해서
			// sessionScope의 loginUser라는 키값으로 덮어씌울 것!
			session.setAttribute("loginUser", memberService.login(member));
			session.setAttribute("alertMsg", "정보수정에 성공했습니다!");
			mv.setViewName("redirect:mypage.me");
		} else { // 수정실패 => 에러문구를 담아서 에러페이지로 포워딩
			mv.addObject("errorMsg", "정보수정에 실패했습니다").setViewName("common/error_page");
		}
		return mv;
		
		새로운 update
		 * 
		 * 성공했을 때만 서비스에서 돌아올 수 있음
		 */
		memberService.updateMember(member, session);
		//session.setAttribute("loginUser", memberService.updateMember(member));
		session.setAttribute("alertMsg", "정보수정에 성공했습니다!");
		/*mv.setViewName("redirect:/");
		return mv;*/
		return mv.setViewNameAndData("redirect:/", null);
	}
	
	// 오늘의 숙제
	// 수업시간에 한 SpringProject 회원탈퇴 구현 + 수퍼프로젝트 스프링버전
	
	@PostMapping("delete.me")
	public ModelAndView delete(String userPwd, HttpSession session/*, ModelAndView mv 메소드 만들어서 필요없어짐*/) {
		Member loginUser = ((Member)session.getAttribute("loginUser"));
		String encPwd = loginUser.getUserPwd();
		
		// 너무 많은 일을 하고 있는 딜리트.
		// 1 세션을 서비스로 넘기는 방법 
		
		/*새로운*/
		memberService.delete(loginUser);
		session.removeAttribute("loginUser");
		session.setAttribute("alertMsg", "잘가시오");
		/*mv.setViewName("redirect:/");
		return mv;*/
		return mv.setViewNameAndData("redirect:/", null);
	}
	
	private ModelAndView setViewNameAndData(String viewName, String key, Object data) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(viewName);
		if(key != null && data != null) {
			mv.addObject(key, data);
		}
		return mv;
	}
	
	// 새로 만든 거 오류가 많으니까 잘 생각하면서 복습할 것
	
	
	/*
	 * AJAX 
	 * 
	 * 
	 */
	
	@ResponseBody // 값만 돌려주려고 할 때 필요한 애노테이션
	@GetMapping("idcheck")
	public String checkId(String userId) {
		
		//String result = memberService.checkId(userId);
		//log.info("아이디 중복이 발생했는가 : {}", result);
		return memberService.checkId(userId);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
