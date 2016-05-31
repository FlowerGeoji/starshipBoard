package starship;

import java.util.List;
import java.util.Map;

import starship.vo.boardVO;
import starship.vo.memberVO;
import starship.vo.replyVO;

public interface boardService {
	public List getBoardList(int pageNum, int pageSize);
	public boardVO getBoardView(String member_id, String board_id);
	public int getAllBoardCount();
	public int getBoardCount(int pageNum, int pageSize);
	public int setBoardWrite(boardVO paramVO);
	public int setBoardUpdate(boardVO paramVO, XmlResult xml);
	public boolean getCheckBoardPassword(boardVO paramVO);
	public List getReplyList(String member_id, String board_id);
	public int setReplyWrite(replyVO paramVO, XmlResult xml);
	public int setReReplyWrite(replyVO paramVO, XmlResult xml);
	public int setBoardDelete(boardVO paramVO, XmlResult xml);
	public memberVO getLoginCheck(String member_id, String password, XmlResult xml);
	public int setJoin(memberVO paramVO, XmlResult xml);
	public int isJoined(Map<String, Object> paramMap);
}
