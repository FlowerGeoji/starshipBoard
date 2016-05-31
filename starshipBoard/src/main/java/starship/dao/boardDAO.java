package starship.dao;

import java.util.List;
import java.util.Map;

import starship.vo.*;

public interface boardDAO {
	public List getBoardList(Map paramMap);
	public boardVO getBoardView(Map<String, Object> paramMap);
	public int getAllBoardCount();
	public int getBoardCount(int pageNum, int pageSize);
	public int setBoardWrite(boardVO paramVO);
	public int setBoardUpdate(boardVO paramVO);
	public List getCheckBoardPassword(boardVO paramVO);
	public List getReplyList(Map<String, Object> paramMap);
	public int setReplyWrite(replyVO paramVO);
	public int setReReplyWrite(replyVO paramVO);
	public int setBoardDelete(boardVO paramVO);
	public memberVO getLoginCheck(Map<String, Object> paramMap);
	public int setJoin(memberVO paramVO);
	public List isJoined(Map<String, Object> paramMap);
}
