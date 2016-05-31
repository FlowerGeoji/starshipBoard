//package starship;
//
//import static org.junit.Assert.*;
//
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.markdown4j.Markdown4jProcessor;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.View;
//
//import starship.dao.*;
//import starship.vo.*;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/config/testmybatis-context.xml"
//								,"file:src/main/webapp/WEB-INF/config/servlet-context.xml"})
//public class boardControllerTest {
//	@Resource
//	boardDAO board;
//	@Resource(name="xmlView")
//	private View xmlView;
//	
//	private memberVO membervo;
//	private boardVO boardvo;
//	private replyVO replyvo;
//	
//	@Before
//	public void setUp() throws Exception {
//		membervo = new memberVO();
//		membervo.setMember_id("test2");
//		membervo.setPassword("test2");
//		membervo.setName("김규재");
//		
//		boardvo = new boardVO();
//		boardvo.setContents("contents");
//		boardvo.setMember_id("test");
//		boardvo.setPassword("1234");
//		boardvo.setSubject("test");
//		
//		replyvo = new replyVO();
//		replyvo.setBoard_id(7);
//		replyvo.setBoard_member_id("test");
//		replyvo.setGroup_id(1);
//		replyvo.setMember_id("test_memberId");
//		replyvo.setReply("reply");
//		replyvo.setReply_id(1);
//		replyvo.setRlevel(1);
//	}
//
//	@After
//	public void tearDown() throws Exception {
//	}
//
//	@Test
//	@Ignore("테스트 완료")
//	public void testGetBoardList() {
//		//페이지번호와 페이지사이즈를 입력받아 페이지에 해당하는 게시판 목록을 보여준다.
//		int pageNum = 1;
//		int pageSize = 5;
//		
//		//페이지번호(paginNum)와 페이지사이즈(pagesize)을 이력받아 페이징 처리
//		int allBoardCount = board.getAllBoardCount();
//		int pageBlockCount = allBoardCount/pageSize + (allBoardCount%pageSize==0 ? 0:1);
//		int startPageNum = (((pageNum-1)/pageSize)*pageSize) + 1;
//		int endPageNum = startPageNum + pageSize - 1;
//		
//		//최대페이지 넘어감
////		if(endPageNum > pageBlockCount)
////			endPageNum = pageBlockCount;
//		
//		assertEquals(startPageNum,1);
//		assertEquals(endPageNum,5);
//		
//		//게시글 목록 가져오기
//		List<boardVO> boardList =  null;
//		try
//		{
//			if(allBoardCount > 0)
//			{
//				boardList = board.getBoardList(pageNum, pageSize);
//				if(boardList == null)
//					fail("boardList is null");
//			}
//			else
//			{
//				boardList = Collections.emptyList();
//			}
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//			fail(e.getMessage());
//		}
//	}
//
//	@Test
//	@Ignore("테스트 완료")
//	public void testGetBoardView() {
//		//선택한 게시판과 댓글의 상세 내용을 가져온다
//		boardvo.setBoard_id(1);
//		
//		try
//		{
//			//게시글 내용 가져오기
//			HashMap paramMap = new HashMap();
//			paramMap.put("member_id", boardvo.getMember_id());
//			paramMap.put("board_id", boardvo.getBoard_id());
//			boardVO boardTemp = board.getBoardView(paramMap);
//			
//			if(boardTemp == null)
//				fail("boardTemp is null");
//			
//			//게시글 댓글 내용 가져오기
//			paramMap.clear();
//			paramMap.put("member_id", boardvo.getMember_id());
//			paramMap.put("board_id", boardvo.getBoard_id());
//			List<replyVO> replyList = board.getReplyList(paramMap);
//			
//			if(replyList == null)
//				fail("replyList is null");
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//			fail(e.getMessage());
//		}
//	}
//
//	@Test
//	@Ignore("boardWrite.jps")
//	public void testGetBoardWrite() {
//		
//	}
//
//	@Test
//	@Ignore("테스트 완료")
//	public void testSetBoardWrite() {
//		try
//		{
//			//markdown 게시판
//			String beforeContents = boardvo.getContents();
//			boardvo.setContents(new Markdown4jProcessor().process(boardvo.getContents()));
//			assertNotEquals(beforeContents, boardvo.getContents());
//			
//			//게시판 입력
//			int result = board.setBoardWrite(boardvo);//테스트 상 오류가 발생 : 이유?
//			assertEquals(result, 1);
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//			fail(e.getMessage());
//		}
//	}
//
//	@Test
//	@Ignore("테스트 완료")
//	public void testGetBoardUpdate() {
//		boardvo.setBoard_id(1);
//		try
//		{
//			HashMap paramMap = new HashMap();
//			paramMap.put("member_id", boardvo.getMember_id());
//			paramMap.put("board_id", boardvo.getBoard_id());
//			boardVO boardTemp = board.getBoardView(paramMap);
//			if(boardTemp == null)
//				fail("boardTemp is null");
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//			fail(e.getMessage());
//		}
//	}
//
//	@Test
//	@Ignore("테스트 완료")
//	public void testSetBoardUpdate() {
//		//게시판 수정 처리 후 결과 데이터를 xml에 담아 전송
//		XmlResult xml = new XmlResult();
//		
//		try
//		{
//			//비밀번호 체크
//			if(!board.getCheckBoardPassword(boardvo))
//			{
//				xml.setMessage("PASSWORD is incorrected");
//				xml.setError(true);
//			}
//			else
//			{
//				//비밀번호가 맞으면 Update 실행
//				int result = board.setBoardUpdate(boardvo);
//				xml.setMessage("Board Wirt Success");
//				xml.setError(false);
//				assertEquals(result, 1);
//			}
//			
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//			fail(e.getMessage());
//		}
//		
//		assertEquals(xml.getMessage(),"Board Wirt Success"); 
//	}
//
//	@Test
//	@Ignore("테스트 완료")
//	public void testSetReplyWrite() {
//		//댓글을 입력하고 창을 새로고침
//		XmlResult xml = new XmlResult();
//		try
//		{
//			//입력받은 댓글을 insert
//			//댓글의 rlevel은 1이다.
//			int result = board.setReplyWrite(replyvo);
//			assertEquals(result, 1);
//			
//			//결과 xml생성
//			xml.setMessage("Reply Write Success");
//			xml.setError(false);
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//			fail(e.getMessage());
//		}
//		
//		assertEquals(xml.getMessage(),"Reply Write Success");
//	}
//
//	@Test
//	@Ignore("테스트 완료")
//	public void testSetReReplyWrite() {
//		//답글을 입력하고 창을 새로고침
//		XmlResult xml = new XmlResult();
//		try
//		{
//			//입력받은 답글을 insert
//			//답글의 rlevel은 부모의 rleve+1이다.
//			//현데모는 최대 2까지만
//			int result = board.setReReplyWrite(replyvo);
//			assertEquals(result, 1);
//			
//			xml.setMessage("ReReply Write Success");
//			xml.setError(false);
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//			fail(e.getMessage());
//		}
//		
//		assertEquals(xml.getMessage(),"ReReply Write Success");
//	}
//
//	@Test
//	@Ignore("테스트 완료")
//	public void testLoginProcess() {
//		//아이디와 비번을 입력받아 체크 후 맞으면 로그인세션을 전송
//		XmlResult xml = new XmlResult();
//		
//		try
//		{
//			//아이디,비번 체크
//			HashMap paramMap = new HashMap();
//			paramMap.put("member_id", membervo.getMember_id());
//			paramMap.put("password", membervo.getPassword());
//			memberVO memberTemp = board.getLoginCheck(paramMap);
//			
//			if(memberTemp!=null)
//			{
//				//맞으면  xml에 데이터를 담아 전송
//				xml.setMessage("Login Success");
//				xml.setError(false);
//			}
//			else
//			{
//				fail("memberTemp is null");
//			}
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//			fail(e.getMessage());
//		}
//		
//		assertEquals(xml.getMessage(),"Login Success");
//	}
//
//	@Test
//	@Ignore("Session")
//	public void testLogoutProcess() {
//		
//	}
//
//	@Test
//	@Ignore("join.jsp")
//	public void testGetJoin() {
//		
//	}
//
//	@Test
//	@Ignore("테스트 완료")
//	public void testSetJoin() {
//		//memberVO를 입력받아 DB의 member테이블에 저장
//		memberVO joinMember = new memberVO();
//		joinMember.setMember_id("test_id");
//		joinMember.setPassword("test_password");
//		joinMember.setName("test_name");
//		
//		HashMap paramMap = new HashMap();
//		paramMap.put("member_id", joinMember.getMember_id());
//		int isJoin=1;
//		try
//		{
//			//동일한 아이디로 회원가입이 되어있는지 검사
//			isJoin = board.isJoined(paramMap);
//			assertEquals(isJoin, -1);
//			
//			//해당 아이디가 없으면 member테이블 insert
//			int result = 0;
//			if(isJoin == -1)
//				result = board.setJoin(joinMember);
//			assertEquals(result, 1);
//			
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//			System.out.println(e.getMessage());
//			fail(e.getMessage());
//		}
//	}
//	
//	@Test
//	public void testSetBoardDelete(){
//		//입력받은 게시판내용에 대한 삭제처리
//		XmlResult xml = new XmlResult();
//		
//		boardvo.setBoard_id(7);
//		try
//		{
//			//비밀번호 체크
//			if(!board.getCheckBoardPassword(boardvo))
//			{
//				xml.setMessage("PASSWORD is incorrected");
//				xml.setError(true);
//			}
//			else
//			{
//				//비밀번호가 맞으면 Update 실행
//				int result = board.setBoardDelete(boardvo);
//				xml.setMessage("Board Delete Success");
//				xml.setError(false);
//				assertEquals(result, 1);
//			}
//			
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//			fail(e.getMessage());
//		}
//		
//		assertEquals(xml.getMessage(),"Board Delete Success");
//	}
//}
