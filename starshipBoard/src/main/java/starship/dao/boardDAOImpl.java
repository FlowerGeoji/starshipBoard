package starship.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import starship.vo.*;

public class boardDAOImpl extends SqlSessionDaoSupport implements boardDAO{

	@Override
	public List getBoardList(int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		Map paramMap = new HashMap<String, Integer>();
		paramMap.put("startNum", (pageNum-1)*pageSize + 1);
		paramMap.put("endNum", pageNum*pageSize);
		//sqlSession = sqlSessionFactory.openSession();
		return getSqlSession().selectList("board.getBoardList", paramMap);
	}

	@Override
	public boardVO getBoardView(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("board.getBoard", paramMap);
	}

	@Override
	public int getAllBoardCount() {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("board.getAllBoardCount");
	}
	
	@Override
	public int getBoardCount(int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public memberVO getLoginCheck(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("board.getLoginCheck",paramMap);
	}

	@Override
	public int setBoardWrite(boardVO paramVO) {
		// TODO Auto-generated method stub
		//paramVO.setBoard_id((int)getSqlSession().selectOne("board.getMaxBoardId",paramVO));
		return getSqlSession().insert("board.setBoardWrite",paramVO);
	}

	@Override
	public int setBoardUpdate(boardVO paramVO) {
		// TODO Auto-generated method stub
		return getSqlSession().update("board.setBoardUpdate", paramVO);
	}

	@Override
	public boolean getCheckBoardPassword(boardVO paramVO) {
		// TODO Auto-generated method stub
		if(getSqlSession().selectOne("board.getCheckBoardPassword", paramVO) != null)
			return true;
		return false;
	}

	@Override
	public List getReplyList(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList("board.getReplyList",paramMap);
	}

	@Override
	public int setReplyWrite(replyVO paramVO) {
		// TODO Auto-generated method stub
		return getSqlSession().insert("board.setReplyWrite", paramVO);
	}

	@Override
	public int setReReplyWrite(replyVO paramVO) {
		// TODO Auto-generated method stub
		return getSqlSession().insert("board.setReReplyWrite", paramVO);
	}

	@Override
	public int setJoin(memberVO paramVO) {
		// TODO Auto-generated method stub
		return getSqlSession().insert("board.setJoin", paramVO);
	}

	@Override
	public int isJoined(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		List<memberVO> temp = getSqlSession().selectList("board.isJoined", paramMap);
		if(getSqlSession().selectList("board.isJoined", paramMap).size() == 0)
			return -1;
		else
			return 1;
	}

	@Override
	public int setBoardDelete(boardVO paramVO) {
		// TODO Auto-generated method stub
		return getSqlSession().update("board.setBoardDelete", paramVO);
	}
	
}
