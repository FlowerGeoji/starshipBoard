package starship;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.markdown4j.Markdown4jProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import starship.dao.boardDAOImpl;
import starship.vo.*;

@Controller
@RequestMapping("/board")
public class boardController {
	
	private static final Logger logger = LoggerFactory.getLogger(boardController.class);
	
	@Autowired
	private boardServiceImpl boardService;
	
//	@Resource(name="boardDAO")
//	private boardDAOImpl boardDAO;
	
	@Resource(name="xmlView")
	private View xmlView;

	/**
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return boardList.jsp
	 * 페이징 처리와 관련된 변수들을 처리하고, 현재 페이지에 해당하는 게시글 목록을 가져온다.
	 */
	@RequestMapping("/getBoardList")
	public ModelAndView getBoardList(@RequestParam(value="pageNum",defaultValue="1")int pageNum
									, @RequestParam(value="pageSize",defaultValue="5")int pageSize)
	{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("boardList");
		
		try
		{
			/*
			 * 페이징 처리
			 */
			int allBoardCount = boardService.getAllBoardCount();
			int pageBlockCount = allBoardCount/pageSize + (allBoardCount%pageSize==0 ? 0:1);
			int startPageNum = (((pageNum-1)/pageSize)*pageSize) + 1;
			int endPageNum = startPageNum + pageSize - 1;
			
			if(endPageNum > pageBlockCount)
				endPageNum = pageBlockCount;
			
			/*
			 * 게시글 목록 가져오기
			 */
			List<boardVO> boardList = boardService.getBoardList(pageNum, pageSize);
			
			mav.addObject("allBoardCount",allBoardCount);
			mav.addObject("boardList", boardList);
			mav.addObject("currentPageNum",pageNum);
			mav.addObject("startPageNum",startPageNum);
			mav.addObject("endPageNum",endPageNum);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return mav;
	}
	
	/**
	 * 
	 * @param member_id
	 * @param board_id
	 * @return boardView.jsp
	 * 게시물 하나에 대한 내용을 가져온다.
	 */
	@RequestMapping("/getBoardView")
	public ModelAndView getBoardView(@RequestParam(value="member_id")String member_id
								, @RequestParam(value="board_id")String board_id)
	{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("boardView");
		
		/*
		 * 게시판 내용 가져오기
		 */
		boardVO board = boardService.getBoardView(member_id, board_id);
		mav.addObject("board",board);
		
		/*
		 * 게시글 댓글 내용 가져오기
		 */
		List<replyVO> replyList = boardService.getReplyList(member_id, board_id);
		mav.addObject("replyList", replyList);
		
		return mav;
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("/getBoardWrite")
	public ModelAndView getBoardWrite()
	{
		
		ModelAndView mav = new ModelAndView();
		try
		{
			mav.setViewName("boardWrite");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return mav;
	}
	
	/**
	 * 
	 * @param board
	 * @return
	 * 게시판 입력.
	 */
	@RequestMapping("/setBoardWrite")
	public ModelAndView setBoardWrite(@ModelAttribute("boardVO")boardVO board)
	{
		ModelAndView mav = new ModelAndView();
		
		boardService.setBoardWrite(board);
		
		mav.setViewName("redirect:getBoardView.do?member_id="+board.getMember_id()+"&board_id="+board.getBoard_id());
		
		return mav;
	}
	
	/**
	 * 
	 * @param member_id
	 * @param board_id
	 * @param session
	 * @return
	 */
	@RequestMapping("/getBoardUpdate")
	public ModelAndView getBoardUpdate(@RequestParam(value="member_id")String member_id
									, @RequestParam(value="board_id")String board_id
									, HttpSession session)
	{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("boardUpdate");
		if(session.getAttribute("member") == null)
			return null;
		if(!((memberVO)session.getAttribute("member")).getMember_id().equals(member_id))
			return null;
		
		boardVO board = boardService.getBoardView(member_id, board_id);
		
		mav.addObject("board", board);
		
		return mav;
	}
	
	/**
	 * 
	 * @param board
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/setBoardUpdate")
	public View setBoardUpdate(@ModelAttribute("boardVO")boardVO board
										, HttpSession session
										, Model model)
	{
		XmlResult xml = new XmlResult();
		
		boardService.setBoardUpdate(board, xml);
		
		model.addAttribute("xmlData", xml);
		return xmlView;
	}
	
	/**
	 * 
	 * @param reply
	 * @param model
	 * @return
	 */
	@RequestMapping("/setReplyWrite")
	public View setReplyWrite(@ModelAttribute("replyVO")replyVO reply
							, Model model)
	{
		XmlResult xml = new XmlResult();
		
		boardService.setReplyWrite(reply, xml);
		
		model.addAttribute("xmlData", xml);
		return xmlView;
	}
	
	@RequestMapping("/setReReplyWrite")
	public View setReReplyWrite(@ModelAttribute("replyVO")replyVO reply
								, Model model)
	{
		XmlResult xml = new XmlResult();
		
		boardService.setReReplyWrite(reply, xml);
		
		model.addAttribute("xmlData", xml);
		return xmlView;
	}
	
	/**
	 * 
	 * @param board
	 * @param model
	 * @return
	 */
	@RequestMapping("/setBoardDelete")
	public View setBoardDelete(@ModelAttribute("boardVO")boardVO board
								, Model model)
	{
		XmlResult xml = new XmlResult();
		
		boardService.setBoardDelete(board, xml);
		
		model.addAttribute("xmlData", xml);
		return xmlView;
	}
	
	/**
	 * 
	 * @param member_id
	 * @param password
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("/loginProc")
	public View loginProcess(@RequestParam(value="member_id")String member_id
									, @RequestParam(value="password")String password
									, Model model
									, HttpSession session)
	{
		
		
		XmlResult xml = new XmlResult();
		
		memberVO member = boardService.getLoginCheck(member_id, password, xml);
		//new XmlResult();
		
		if(!xml.isError())
			session.setAttribute("member", member);
		
		model.addAttribute("xmlData", xml);
		return xmlView;
	}
	
	/**
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/logoutProc")
	public View logoutProcess(HttpSession session, Model model)
	{
		XmlResult xml = new XmlResult();
		xml.setMessage("Logout Success");
		xml.setError(false);
		
		session.removeAttribute("member");
		session.invalidate();
		
		model.addAttribute("xmlData", xml);
		return xmlView;
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("/getJoin")
	public ModelAndView getJoin()
	{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("join");
		return mav;
	}
	
	/**
	 * 
	 * @param member
	 * @param model
	 * @return
	 */
	@RequestMapping("/setJoin")
	public View setJoin(@ModelAttribute("memberVO")memberVO member
								, Model model)
	{
		XmlResult xml = new XmlResult();
		
		boardService.setJoin(member, xml);
		
		model.addAttribute("xmlData", xml);
		return xmlView;
	}
}

@XStreamAlias("result")
class XmlResult
{
	private String message;
	private boolean error = false;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
}