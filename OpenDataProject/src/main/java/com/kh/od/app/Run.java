package com.kh.od.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Run {

	// 네이버 개발자에서 복붙해온 코드
	public static void main(String[] args) {
		
		
		
		
        String clientId = "g2BGDpQBYAxrkPaiA5oP"; //애플리케이션 클라이언트 아이디
        String clientSecret = "ok1CWLUfEv"; //애플리케이션 클라이언트 시크릿
        
        /*
         * 위에까지 채우면 이렇게 응답이 돌아온다 Json 객체 모양으로 (키-밸류형태
         * 
         * {	"lastBuildDate":
         * "Mon, 20 Jan 2025 19:04:48 +0900",	
         * "total":10922,	
         * "start":1,	
         * "display":10,	
         * "items":[		
         * {			
         * "title":"국내 최고 IT 기업 네이버 본사 방문: <b>그린팩토리<\/b> 내부... ",			
         * "link":"https:\/\/blog.naver.com\/sjn1203\/223343131294",			
         * "description":"<b>그린팩토리<\/b>는 지하 1층부터 27층까지 있으며, 1층 네이버 라이브러리, 그린 카페를 일반인도 이용할 수 있다는 소문을 들었습니다. 1784에서는 지하 1층부터 28층까지 있으며 2층에는 스타벅스와 브랜드스토어가... ",			
         * "bloggername":"나미의 관심, 사고, 생활",			
         * "bloggerlink":"blog.naver.com\/sjn1203",			
         * "postdate":"20240203"		
         * },		
         * {			
         * "title":"네이버 <b>그린팩토리<\/b> 1784 브랜드 스토어 방문하기",			
         * "link":"https:\/\/blog.naver.com\/moms-sewing\/223305752056",			
         * "description":"네이버 본사 <b>그린팩토리<\/b> 1784 브랜드 스토어에 방문했어요. 올해 말까지 사용할수 있는 쿠폰을 사용하기 위해서 출발합니다. 솔직히 살것도 없어서 쿠폰을 버릴까도 했는데 4장이나 있으니 아들이랑 구경삼아... ",			"bloggername":"맘스쏘잉 프랑스자수",			"bloggerlink":"blog.naver.com\/moms-sewing",			"postdate":"20231228"		},		{			"title":"매직캔 리필봉투 저렴한 곳 <b>그린팩토리<\/b>",			"link":"https:\/\/blog.naver.com\/s_1004_hm\/223683802509",			"description":"매직캔 리필봉투 저렴한 곳 <b>그린팩토리<\/b> #매직캔리필봉투 #매직캔봉투저렴한곳 #<b>그린팩토리<\/b> #이지리필... 좋으니 그린팩코리아에서 주문하시는 걸 추천해요! 대량 주문이나 규격조정이 필요한 경우는 따로... ",			"bloggername":"하르미 Blog",			"bloggerlink":"blog.naver.com\/s_1004_hm",			"postdate":"20241205"		},		{			"title":"아이와 가볼만한 곳 한독의약박물관 <b>그린팩토리<\/b> 무료체험",			"link":"https:\/\/blog.naver.com\/ysj114\/223651516469",			"description":"있어요 '<b>그린팩토리<\/b> 카페' '<b>그린팩토리<\/b>카페'에선 오전에 무료 체험(사랑의 묘약 만들기 등)이 진행되고요 커피를 비롯해 모든 메뉴가 2.500원인 단, 테이크아웃은 안돼요(개인 텀블러는 가능) 충북 음성 실내... ",			"bloggername":"여행하기 좋은날",			"bloggerlink":"blog.naver.com\/ysj114",			"postdate":"20241108"		},		{			"title":"미니 크로와상 생지 맛 좋고 가성비 좋은 더<b>그린팩토리<\/b> 냉동생지",			"link":"https:\/\/blog.naver.com\/zxcv7971\/223163770149",			"description":"더 <b>그린 팩토리<\/b> 미니 크로와상 생지 너무 좋아요~~ 집에 있는 와플펜이 작아서 저는 늘 크로와상... 보통 빵은 냉동보관해도 2주 안에 먹어야 하는데 더 <b>그린팩토리<\/b> 제빵소에 미니 크루와상 생지는... ",			"bloggername":"Love House님의 블로그",			"bloggerlink":"blog.naver.com\/zxcv7971",			"postdate":"20230723"		},		{			"title":"<b>그린팩토리<\/b>에 이지박스 뽑아쓰는 분리수거 비닐봉투",			"link":"https:\/\/blog.naver.com\/hi_siru\/223555509167",			"description":"만들어진 <b>그린팩토리<\/b> 이지박스 뽑아쓰는 분리수거 비닐봉투 50L에 대한 솔직한 후기를 들려드리려고... 다양한 활용도를 자랑하는 이지박스 뽑아쓰는 분리수거 비닐봉투 <b>그린팩토리<\/b> 이지박스는 분리수거를... ",			"bloggername":"Minimal Life",			"bloggerlink":"blog.naver.com\/hi_siru",			"postdate":"20240821"		},		{			"title":"조수용 &lt;일의 감각&gt; 중 네이버 사옥 <b>그린팩토리<\/b> 주차장의 경험... ",			"link":"https:\/\/blog.naver.com\/8ight_studio\/223677232524",			"description":"사옥인 <b>그린팩토리<\/b> 주차장 얘기가 있어 언급해 보고자 한다. 조수용 대표님도 경험 디자인 측면에서 마음에 드는 사례로 기억하시는가 보다. 필자는 2010년 초 네이버와 미투데이 프로젝트를 위해 <b>그린팩토리<\/b>에... ",			"bloggername":"B디자이너의 글 공간",			"bloggerlink":"blog.naver.com\/8ight_studio",			"postdate":"20241129"		},		{			"title":"오늘은 내가 약사 (알약만들기 프로그램) :: <b>그린팩토리<\/b>",			"link":"https:\/\/blog.naver.com\/sh2824004\/223253020170",			"description":"<b>그린팩토리<\/b>로 가보았습니다. 사랑의 묘약 만들기 체험을 할 수 있었습니다. 약사 가운도 입어 볼수 있구요. 캡슐편지지에 편지도 쓰고 사랑의 묘약도 만들고 색칠하기도 하니 시간이 딱이였습니다. <b>그린 팩토리<\/b>... ",			"bloggername":"쪼꼬MILK & 하양MILK",			"bloggerlink":"blog.naver.com\/sh2824004",			"postdate":"20231101"		},		{			"title":"네이버 신사옥과 옛 <b>그린팩토리<\/b> (쿠키: 판교... ",			"link":"https:\/\/blog.naver.com\/manhattan212\/223441020464",			"description":"한창 공사중인 네이버 <b>그린팩토리<\/b>에 이란성 쌍둥이 빌딩이 하나 더 생겼다. 인근 주거지역에서 확... 같은 <b>그린팩토리<\/b>1 공사 현장 뉴욕퀸은 판교 벤처밸리 뿐 아니라 네이버 본사 투어도 몇 번 해봤었다. 2010년... ",			"bloggername":"뉴욕퀸닷컴 NEWYORKQUEEN.COM & STUDIO212MANHATTAN",			"bloggerlink":"blog.naver.com\/manhattan212",			"postdate":"20240509"		},		{			"title":"한독제약 <b>그린팩토리<\/b> 카페",			"link":"https:\/\/blog.naver.com\/mbc1906\/223647839923",			"description":"<b>그린팩토리<\/b> 카페 앞에 주차를 하라고 알려주셨다. 한독제약 건물 안으로 차를 타고 이동하는데 공장... 그린라떼(2,500원)을 주문했다. 음료 맛에 대해 큰 기대를 하면 즐겁게 먹을 수 있다. 회사에선 회사대로... ",			"bloggername":"성수동 사는 한다강",			"bloggerlink":"blog.naver.com\/mbc1906",			"postdate":"20241105"		}	]}

         */


        String text = null;
        try {
            text = URLEncoder.encode("크리스마스", "UTF-8"); // 이 그린팩토리를 보냈기 때문에 위와 같은 응답이 돌아온다. KH정보교육원으로 바꾸면
            // 이게 밑의 apiURL 뒤에 쿼리스트링으로 붙어 그 값이 돌아옴
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }


        //String apiURL = "https://openapi.naver.com/v1/search/blog?query=" + text;    // 블로그의 JSON 결과
        String apiURL = "https://openapi.naver.com/v1/search/kin.json?query=" + text;    //지식인의 JSON 결과
        //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // 블로그의 XML 결과


        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL,requestHeaders);


        System.out.println(responseBody);
    }


    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            } // 키 밸류 형태로 요청 보낼 준비를 하는 코드


            int responseCode = con.getResponseCode(); // 응답코드를 받아온다
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 오류 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }


    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }


    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);


        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();


            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            } // xml일 경우 문자열을 합쳐줌


            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }
}
