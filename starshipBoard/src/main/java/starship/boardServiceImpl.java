package starship;

import static org.junit.Assert.fail;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.markdown4j.Markdown4jProcessor;
import org.springframework.transaction.annotation.Transactional;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import starship.dao.boardDAOImpl;
import starship.vo.boardVO;
import starship.vo.memberVO;
import starship.vo.replyVO;

public class boardServiceImpl implements boardService{
	
	private boardDAOImpl boardDao;
	
	public boardServiceImpl()
	{
		
	}
	
	public boardServiceImpl(boardDAOImpl boardDao)
	{
		this.boardDao = boardDao;
	}
	
	public void setBoardDao(boardDAOImpl boardDao)
	{
		this.boardDao = boardDao;
	}
	
	public boardDAOImpl getBoardDao()
	{
		return this.boardDao;
	}
	
	@Override
	public List getBoardList(int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		int allBoardCount = getAllBoardCount();
		
		Map paramMap = new HashMap<String, Integer>();
		paramMap.put("startNum", (pageNum-1)*pageSize + 1);
		paramMap.put("endNum", pageNum*pageSize);
		
		List<boardVO> boardList = null;
		
		try
		{
			if(allBoardCount > 0)
			{
				boardList = boardDao.getBoardList(paramMap);
			}
			
			else
			{
				boardList = Collections.emptyList();
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return boardList;
	}

	@Override
	public boardVO getBoardView(String member_id, String board_id) {
		// TODO Auto-generated method stub
		HashMap paramMap = new HashMap();
		paramMap.put("member_id", member_id);
		paramMap.put("board_id", board_id);
		
		boardVO board = null;
		
		try
		{
			
			board = boardDao.getBoardView(paramMap);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return board;
	}

	@Override
	public int getAllBoardCount() {
		// TODO Auto-generated method stub
		return boardDao.getAllBoardCount();
	}

	@Override
	public int getBoardCount(int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int setBoardWrite(boardVO paramVO) {
		// TODO Auto-generated method stub
		int result = -1;
		
		try
		{
			paramVO.setContents(new Markdown4jProcessor().process(paramVO.getContents()));
			result = boardDao.setBoardWrite(paramVO);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int setBoardUpdate(boardVO paramVO, XmlResult xml) {
		// TODO Auto-generated method stub
		int result = -1;
		try
		{
			if(getCheckBoardPassword(paramVO))
			{
				xml.setMessage("PASSWORD is incorrected");
				xml.setError(true);
			}
			else
			{
				result = boardDao.setBoardUpdate(paramVO);
				xml.setMessage("Board Update Success");
				xml.setError(false);
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean getCheckBoardPassword(boardVO paramVO) {
		// TODO Auto-generated method stub
		if(boardDao.getCheckBoardPassword(paramVO).size() == 1)
			return true;
		return false;
	}

	@Override
	public List getReplyList(String member_id, String board_id) {
		// TODO Auto-generated method stub
		HashMap paramMap = new HashMap();
		paramMap.put("member_id", member_id);
		paramMap.put("board_id", board_id);
		return boardDao.getReplyList(paramMap);
	}

	@Override
	public int setReplyWrite(replyVO paramVO, XmlResult xml) {
		// TODO Auto-generated method stub
		int result = -1;
		try
		{
			result = boardDao.setReplyWrite(paramVO);
			xml.setMessage("Reply Write Success");
			xml.setError(false);
		}catch(Exception e)
		{
			e.printStackTrace();
			xml.setMessage(e.getMessage());
			xml.setError(true);
		}
		
		return result;
	}

	@Override
	public int setReReplyWrite(replyVO paramVO, XmlResult xml) {
		// TODO Auto-generated method stub
		int result = -1;
		try
		{
			result = boardDao.setReReplyWrite(paramVO);
			xml.setMessage("ReReply Write Success");
			xml.setError(false);
		}catch(Exception e)
		{
			e.printStackTrace();
			xml.setMessage(e.getMessage());
			xml.setError(true);
		}
		return result;
	}

	@Override
	public int setBoardDelete(boardVO paramVO, XmlResult xml) {
		// TODO Auto-generated method stub
		int result = -1;
		try
		{
			//비밀번호 체크
			if(getCheckBoardPassword(paramVO))
			{
				xml.setMessage("PASSWORD is incorrected");
				xml.setError(true);
			}
			else
			{
				//비밀번호가 맞으면 Update 실행
				result = boardDao.setBoardDelete(paramVO);
				if(result == 1)
				{
					xml.setMessage("Board Delete Success");
					xml.setError(false);
				}
				else
				{
					xml.setMessage("Error");
					xml.setError(true);
				}
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
			fail(e.getMessage());
		}
		
		return result;
	}

	@Override
	public memberVO getLoginCheck(String member_id, String password, XmlResult xml) {
		// TODO Auto-generated method stub
		HashMap paramMap = new HashMap();
		paramMap.put("member_id", member_id);
		paramMap.put("password", password);
		memberVO member = null;
		
		try
		{
			member = boardDao.getLoginCheck(paramMap);
			
			if(member!=null)
			{
				xml.setMessage("Login Success");
				xml.setError(false);
			}
			else
			{
				xml.setMessage("ID or PASSWORD is incorrected");
				xml.setError(true);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return member;
	}

	@Override
	public int setJoin(memberVO paramVO, XmlResult xml) {
		// TODO Auto-generated method stub
		int isJoin = -1;
		
		try
		{
			HashMap paramMap = new HashMap();
			paramMap.put("member_id", paramVO.getMember_id());
			isJoin = isJoined(paramMap);
			
			if(isJoin == 1)
			{
				xml.setMessage("해당 ID는 사용할 수 없습니다.");
				xml.setError(true);
			}
			else
			{
				boardDao.setJoin(paramVO);
				xml.setMessage("Join Success");
				xml.setError(false);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			xml.setMessage(e.getMessage());
			xml.setError(true);
		}
		
		return isJoin;
	}

	@Override
	public int isJoined(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		List<memberVO> temp = boardDao.isJoined(paramMap);
		if(temp.size() == 0)
			return -1;
		else
			return 1;
	}

}