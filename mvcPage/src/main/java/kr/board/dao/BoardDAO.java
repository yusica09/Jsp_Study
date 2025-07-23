package kr.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.board.vo.BoardChatRoomVO;
import kr.board.vo.BoardChatVO;
import kr.board.vo.BoardFavVO;
import kr.board.vo.BoardReplyVO;
import kr.board.vo.BoardVO;
import kr.util.DBUtil;
import kr.util.DurationFromNow;
import kr.util.StringUtil;

public class BoardDAO {
	//싱글턴 패턴
	private static BoardDAO instance = new BoardDAO();
	public static BoardDAO getInstance() {
		return instance;
	}
	private BoardDAO() {}

	//글 등록
	public void insertBoard(BoardVO board)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int cnt = 0;

		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "INSERT INTO zboard (board_num,title,content,"
					+ "filename,ip,mem_num) VALUES (zboard_seq.nextval,"
					+ "?,?,?,?,?)";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(++cnt, board.getTitle());
			pstmt.setString(++cnt, board.getContent());
			pstmt.setString(++cnt, board.getFilename());
			pstmt.setString(++cnt, board.getIp());
			pstmt.setLong(++cnt, board.getMem_num());
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//총레코드수/검색 레코드수
	public int getBoardCount(String keyfield,String keyword)
			throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;

		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();

			if(keyword != null && !"".equals(keyword)) {
				if(keyfield.equals("1")) sub_sql += "WHERE title LIKE '%' || ? || '%'";
				else if(keyfield.equals("2")) sub_sql += "WHERE id LIKE '%' || ? || '%'";
				else if(keyfield.equals("3")) sub_sql += "WHERE content LIKE '%' || ? || '%'";
			}
			//SQL문 작성
			sql = "SELECT COUNT(*) FROM zboard JOIN zmember USING(mem_num) " + sub_sql;

			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(1, keyword);
			}
			//SQL문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}		
		return count;
	}
	//글 목록/검색 목록
	public List<BoardVO> getListBoard(int start,int end,
			String keyfield,String keyword)
					throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;

		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();

			if(keyword != null && !"".equals(keyword)) {
				if(keyfield.equals("1")) sub_sql += "WHERE title LIKE '%' || ? || '%'";
				else if(keyfield.equals("2")) sub_sql += "WHERE id LIKE '%' || ? || '%'";
				else if(keyfield.equals("3")) sub_sql += "WHERE content LIKE '%' || ? || '%'";
			}

			//SQL문 작성
			sql = "SELECT * FROM (SELECT a.*,rownum rnum FROM "
					+ "(SELECT * FROM zboard JOIN zmember USING(mem_num) "
					+ sub_sql + " ORDER BY board_num DESC)a) "
					+ "WHERE rnum >= ? AND rnum <= ?";

			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, keyword);
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);

			//SQL문 실행
			rs = pstmt.executeQuery();
			list = new ArrayList<BoardVO>();
			while(rs.next()) {
				BoardVO board = new BoardVO();
				board.setBoard_num(rs.getLong("board_num"));
				board.setTitle(StringUtil.useNoHtml(
						rs.getString("title")));
				board.setHit(rs.getInt("hit"));
				board.setReg_date(rs.getDate("reg_date"));
				board.setId(rs.getString("id"));//작성자의 아이디

				list.add(board);
			}			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}		
		return list;
	}
	//글 상세
	public BoardVO getBoard(long board_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardVO board = null;
		String sql = null;

		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM zboard JOIN zmember USING(mem_num) "
					+ "LEFT OUTER JOIN zmember_detail USING(mem_num) "
					+ "WHERE board_num=?";
			//PreparedSatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setLong(1, board_num);
			//SQL문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				board = new BoardVO();
				board.setBoard_num(rs.getInt("board_num"));
				board.setTitle(rs.getString("title"));
				board.setContent(rs.getString("content"));
				board.setHit(rs.getInt("hit"));
				board.setReg_date(rs.getDate("reg_date"));
				board.setModify_date(rs.getDate("modify_date"));
				board.setFilename(rs.getString("filename"));
				board.setMem_num(rs.getInt("mem_num"));
				board.setId(rs.getString("id"));
				board.setPhoto(rs.getString("photo"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}		
		return board;
	}
	//조회수 증가
	public void updateReadCount(long board_num)
			throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE zboard SET hit=hit+1 WHERE board_num=?";
			//PreparedSatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setLong(1, board_num);
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//파일 삭제
	public void deleteFile(long board_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE zboard SET filename='' WHERE board_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setLong(1, board_num);
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}		
	}
	//글 수정
	public void updateBoard(BoardVO board)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;

		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();

			if(board.getFilename()!=null 
					&& !"".equals(board.getFilename())) {
				sub_sql += ",filename=?";
			}
			//SQL문 작성
			sql = "UPDATE zboard SET title=?,content=?,"
					+ "modify_date=SYSDATE,ip=?" + sub_sql 
					+ " WHERE board_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(++cnt, board.getTitle());
			pstmt.setString(++cnt, board.getContent());
			pstmt.setString(++cnt, board.getIp());
			if(board.getFilename()!=null 
					&& !"".equals(board.getFilename())) {
				pstmt.setString(++cnt, board.getFilename());
			}
			pstmt.setLong(++cnt, board.getBoard_num());
			//SQL문 실행
			pstmt.executeUpdate();

		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}		
	}
	//글 삭제
	public void deleteBoard(long board_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		PreparedStatement pstmt5 = null;
		String sql = null;

		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//오토커밋 해제
			conn.setAutoCommit(false);
			//좋아요 삭제
			sql = "DELETE FROM zboard_fav WHERE board_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, board_num);
			pstmt.executeUpdate();
			//댓글 삭제
			sql = "DELETE FROM zboard_reply WHERE board_num=?";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setLong(1, board_num);
			pstmt2.executeUpdate();
			//채팅 메시지 삭제
			sql = "DELETE FROM zboard_chat WHERE chatroom_num IN ("
					+ "SELECT chatroom_num FROM zboard_chatroom WHERE "
					+ "board_num=?)";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setLong(1, board_num);
			pstmt3.executeUpdate();
			//채팅방 삭제
			sql = "DELETE FROM zboard_chatroom WHERE board_num=?";
			pstmt4 = conn.prepareStatement(sql);
			pstmt4.setLong(1, board_num);
			pstmt4.executeUpdate();
			//부모글 삭제
			sql = "DELETE FROM zboard WHERE board_num=?";
			pstmt5 = conn.prepareStatement(sql);
			pstmt5.setLong(1, board_num);
			pstmt5.executeUpdate();
			//예외 발생 없이 정상적으로 모든 SQL문 실행
			conn.commit();
		}catch(Exception e) {
			//예외 발생
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt5, null);
			DBUtil.executeClose(null, pstmt4, null);
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	//좋아요 개수
	public int selectFavCount(long board_num)
			throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;

		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT COUNT(*) FROM zboard_fav WHERE board_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setLong(1, board_num);
			//SQL문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}		
		return count;
	}
	//회원번호와 게시물 번호를 이용한 좋아요 정보(회원이 게시물을
	//호출했을 때 좋아요 선택 여부 표시)
	public BoardFavVO selectFav(BoardFavVO favVO)
			throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardFavVO fav = null;
		String sql = null;

		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM zboard_fav WHERE board_num=? AND mem_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setLong(1, favVO.getBoard_num());
			pstmt.setLong(2, favVO.getMem_num());
			//SQL문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				fav = new BoardFavVO();
				fav.setBoard_num(rs.getLong("board_num"));
				fav.setMem_num(rs.getLong("mem_num"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}		
		return fav;
	}

	//좋아요 등록
	public void insertFav(BoardFavVO favVO)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "INSERT INTO zboard_fav (board_num,mem_num)"
					+ " VALUES (?,?)";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setLong(1, favVO.getBoard_num());
			pstmt.setLong(2, favVO.getMem_num());
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//좋아요 삭제
	public void deleteFav(BoardFavVO favVO)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "DELETE FROM zboard_fav "
					+ "WHERE board_num=? AND mem_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, favVO.getBoard_num());
			pstmt.setLong(2, favVO.getMem_num());
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//내가 선택한 좋아요 목록
	public List<BoardVO> getListBoardFav(int start,
			int end, long mem_num)
					throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardVO> list = null;
		String sql = null;

		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//(주의)zboard_fav의 회원번호(좋아요 클릭한 회원번호)로
			//검색되어야 하기 때문에 f.mem_num으로 표기해야 함
			sql = "SELECT * FROM (SELECT a.*, rownum rnum "
					+ "FROM (SELECT * FROM zboard b JOIN zmember m "
					+ "USING(mem_num) JOIN zboard_fav f USING(board_num) "
					+ "WHERE f.mem_num = ? ORDER BY board_num DESC)a) "
					+ "WHERE rnum >= ? AND rnum <= ?";
			//PreparedSatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setLong(1, mem_num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			//SQL문 실행
			rs = pstmt.executeQuery();
			list = new ArrayList<BoardVO>();
			while(rs.next()) {
				BoardVO board = new BoardVO();
				board.setBoard_num(rs.getLong("board_num"));
				//HTML 태그를 허용하지 않음
				board.setTitle(StringUtil.useNoHtml(
						rs.getString("title")));
				board.setReg_date(rs.getDate("reg_date"));
				board.setId(rs.getString("id"));

				list.add(board);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}		
		return list;
	}

	//댓글 등록
	public void insertReplyBoard(BoardReplyVO vo) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int cnt = 0;

		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO zboard_reply (re_num, re_content, re_ip, "
					+ "mem_num, board_num) VALUES (zreply_seq.nextval,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(++cnt, vo.getRe_content());
			pstmt.setString(++cnt, vo.getRe_ip());
			pstmt.setLong(++cnt, vo.getMem_num());
			pstmt.setLong(++cnt, vo.getBoard_num());

			pstmt.executeUpdate();

		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}

	}

	//댓글 개수
	public int getReplyBoardCount(long board_num) throws Exception{

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;

		try {
			conn = DBUtil.getConnection();
			sql = "SELECT COUNT(*) FROM zboard_reply WHERE board_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, board_num);

			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}
	//댓글 목록
	public List<BoardReplyVO> getListReplyBoard(int start, int end, 
			long board_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardReplyVO> list = null;
		String sql = null;

		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM (SELECT a.*, rownum rnum "
					+ "FROM (SELECT * FROM zboard_reply JOIN zmember USING(mem_num) "
					+ "WHERE board_num=? ORDER BY re_num DESC)a) "
					+ "WHERE rnum >= ? AND rnum <= ?";
			pstmt = conn.prepareStatement(sql);
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setLong(1, board_num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			//SQL문 실행
			rs = pstmt.executeQuery();
			list = new ArrayList<BoardReplyVO>();
			while (rs.next()) {
				BoardReplyVO reply = new BoardReplyVO();
				reply.setRe_num(rs.getLong("re_num"));
				//날짜 -> 1분전, 1시간전, 1일전 형식의 문자열로 변환
				reply.setRe_date(DurationFromNow.getTimeDiffLabel(rs.getString("re_date")));
				if (rs.getString("re_modifydate")!=null) {
					reply.setRe_modifydate(DurationFromNow.getTimeDiffLabel(rs.getString("re_modifydate")));
				} // if
				reply.setRe_content(StringUtil.useBrNoHtml(rs.getString("re_content")));
				reply.setBoard_num(rs.getLong("board_num"));
				reply.setMem_num(rs.getLong("mem_num"));
				reply.setId(rs.getString("id"));

				list.add(reply);
			} // while

		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}

		return list;
	}
	//댓글 상세(댓글 수정, 삭제 시 작성자 회원번호 체크 용도로 사용)
	public BoardReplyVO getReplyBoard(long re_num) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardReplyVO reply = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM zboard_reply WHERE re_num=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, re_num);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				reply = new BoardReplyVO();
				reply.setRe_num(rs.getLong("re_num"));
				reply.setMem_num(rs.getLong("mem_num"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return reply;
	}
	
	//댓글 수정
	public void updateReplyBoard(BoardReplyVO reply)throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int cnt = 0;
		
		try {
			conn = DBUtil.getConnection();
			sql = "UPDATE zboard_reply SET re_content=?, re_modifydate=SYSDATE, re_ip=? "
					+ "WHERE re_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(++cnt, reply.getRe_content());
			pstmt.setString(++cnt, reply.getRe_ip());
			pstmt.setLong(++cnt, reply.getRe_num());
			
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	//댓글 삭제
	public void deleteReplyBoard(long re_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "DELETE FROM zboard_reply WHERE re_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, re_num);
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	
	//채팅 - 내가 쓴 게시글 총 레코드 수(검색 레코드 수)
	public int getBoardCountByUser(String keyfield, String keyword, 
																long mem_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			if(keyword != null && !"".equals(keyword)) {
				if(keyfield.equals("1")) {
					sub_sql += "AND title LIKE '%' || ? || '%'"; 
				}else if(keyfield.equals("2")) {
					sub_sql += "AND content LIKE '%' || ? || '%'"; 
				}
			}
			//SQL문 작성
			sql = "SELECT COUNT(*) FROM zboard JOIN "
					+ "(SELECT board_num, MAX(chat_num) chat_num FROM zboard_chatroom "
					+ "JOIN zboard_chat USING(chatroom_num) GROUP BY board_num) "
					+ "USING(board_num) WHERE mem_num=? " + sub_sql;
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바운딩
			pstmt.setLong(1, mem_num);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(2, keyword);
			}
			
			//SQL문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}
	
	//채팅 - 내가 쓴 게시글 글목록(검색글 목록)
	public List<BoardVO> getListBoardByUser(int start, int end, 
							String keyfield, String keyword, long mem_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		try {
			conn = DBUtil.getConnection();
			if(keyword != null && !"".equals(keyword)) {
				if(keyfield.equals("1"))
					sub_sql += "AND title LIKE '%' || ? || '%'";
				else if(keyfield.equals("2"))
					sub_sql += "AND content LIKE '%' || ? || '%'";
			}
			 sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM zboard JOIN "
		               + "(SELECT board_num, MAX(chat_num) chat_num FROM zboard_chatroom JOIN "
		               + "zboard_chat USING(chatroom_num) GROUP BY board_num) USING(board_num) "
		               + "LEFT OUTER JOIN (SELECT board_num, SUM(read_check) unread_cnt "
		               + "FROM zboard_chatroom JOIN zboard_chat USING(chatroom_num) "
		               + "WHERE mem_num!=? GROUP BY board_num) USING(board_num) WHERE mem_num=? " 
		               + sub_sql + " ORDER BY chat_num DESC)a) WHERE rnum >= ? AND rnum <= ?";
			
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setLong(++cnt, mem_num);
			pstmt.setLong(++cnt, mem_num);
			
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, keyword);
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			
			//SQL문 실행
			rs = pstmt.executeQuery();
			list = new ArrayList<BoardVO>();
			while(rs.next()) {
				BoardVO board = new BoardVO();
				board.setBoard_num(rs.getLong("board_num"));
				board.setTitle(StringUtil.useNoHtml(rs.getString("title")));
				board.setHit(rs.getInt("hit"));
				board.setReg_date(rs.getDate("reg_date"));
				//읽지 않은 채팅 수
				board.setUnread_cnt(rs.getInt("unread_cnt"));
				
				list.add(board);
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	//채팅 - 채팅방 개수(검색 갯수)
	public int getChatCount(String keyfield, String keyword, 
													long board_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			if(keyword != null && !"".equals(keyword)) {
				if(keyfield.equals("1"))
					sub_sql += "AND id LIKE '%' || ? || '%'";
				else if(keyfield.equals("2"))
					sub_sql += "AND message LIKE '%' || ? || '%'";
			}
			//SQL문 작성
			sql = "SELECT COUNT(*) FROM zboard_chat "
					+ "JOIN zboard_chatroom USING(chatroom_num) "
					+ "JOIN (SELECT SUM(read_check) cnt, "
					+ "MAX(chat_num) chat_num FROM zboard_chat "
					+ "GROUP BY chatroom_num) USING (chat_num) "
					+ "JOIN zmember USING(mem_num) "
					+ "WHERE board_num =? " + sub_sql;
			
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setLong(1, board_num);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(2, keyword);
			}
			
			//SQL문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}
	//채팅 - 채팅방 목록(검색 목록)
	public List<BoardChatVO> getChatList(int start, int end, String keyfield, 
													String keyword ,long board_num, long mem_num) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardChatVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		try {
			conn = DBUtil.getConnection();
			if(keyword != null && !"".equals(keyword)) {
				if(keyfield.equals("1")) {
					sub_sql = "AND id LIKE '%' || ? || '%'";
				}else if(keyfield.equals("2")){
					sub_sql = "AND message LIKE '%' || ? || '%'";
				}
			}
			//SQL문 작성
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
					+ "(SELECT * FROM zboard_chat JOIN zboard_chatroom c "
					+ "USING(chatroom_num) "
					+ "JOIN (SELECT MAX(chat_num) chat_num FROM zboard_chat "
					+ "GROUP BY chatroom_num) USING(chat_num) "
					+ "LEFT OUTER JOIN (SELECT SUM(read_check) unread_cnt, "
					+ "MAX(chat_num) chat_num FROM zboard_chat "
					+ "WHERE mem_num != ? GROUP BY chatroom_num) "
					+ "USING(chat_num) "
					+ "JOIN zmember z ON c.reader_num = z.mem_num "
					+ "WHERE board_num=? " + sub_sql
					+ " ORDER BY chat_num DESC)a) "
					+ "WHERE rnum >= ? AND rnum <= ?";
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setLong(++cnt, mem_num);
			pstmt.setLong(++cnt, board_num);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, keyword);
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			//SQL문 실행
			rs = pstmt.executeQuery();
			list = new ArrayList<BoardChatVO>();
			while(rs.next()) {
				BoardChatVO chat = new BoardChatVO();
				chat.setChat_num(rs.getLong("chat_num"));
				chat.setChatroom_num(rs.getLong("chatroom_num"));
				chat.setMem_num(rs.getLong("mem_num"));
				chat.setMessage(rs.getString("message"));
				chat.setChat_date(rs.getString("chat_date"));
				chat.setRead_check(rs.getInt("read_check"));
				chat.setId(rs.getString("id"));
				chat.setUnread_cnt(rs.getInt("unread_cnt"));
				
				list.add(chat);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	
	//채팅 - 내가 채팅한 게시글 총 레코드 수(검색 레코드 수)
	public int getBoardCountForMyChat(String keyfield,
														String keyword,
														long mem_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			if(keyword != null && !"".equals(keyword)) {
				if(keyfield.equals("1")) sub_sql += "AND title LIKE '%' || ? || '%'";
				else if(keyfield.equals("2")) sub_sql += "AND id LIKE '%' || ? || '%'";
				else if(keyfield.equals("3")) sub_sql += "AND content LIKE '%' || ? || '%'";
			}
			//SQL문 작성
			sql = "SELECT COUNT(*) FROM zboard "
					+ "JOIN zmember USING(mem_num) "
					+ "JOIN zboard_chatroom USING (board_num) "
					+ "JOIN (SELECT MAX(chat_num) chat_num, chatroom_num "
					+ "FROM zboard_chat GROUP BY chatroom_num) "
					+ "USING(chatroom_num) WHERE reader_num=? " + sub_sql;
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, mem_num);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(2, keyword);
			}
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return count;
	}		
	//채팅 - 내가 채팅한 게시글 목록(검색 목록)
	public List<BoardVO> getListBoardForMyChat(int start, int end, String keyfield, 
															String keyword, long mem_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs= null;
		String sql = null;
		List<BoardVO> list = null;
		String sub_sql = "";
		int cnt = 0;
		
		try {
			conn = DBUtil.getConnection();
			if(keyword != null && !"".equals(keyword)) {
				if(keyfield.equals("1")) sub_sql += "AND title LIKE '%' || ? || '%'";
				else if(keyfield.equals("2")) sub_sql += "AND id LIKE '%' || ? || '%'";
				else if(keyfield.equals("3")) sub_sql += "AND content LIKE '%' || ? || '%'";
			}
			//SQL문 작성
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
					+ "(SELECT * FROM zboard JOIN zmember USING(mem_num) "
					+ "JOIN zboard_chatroom USING(board_num) "
					+ "JOIN (SELECT MAX(chat_num) chat_num, "
					+ "chatroom_num FROM zboard_chat GROUP BY chatroom_num) "
					+ "USING(chatroom_num) "
					+ "LEFT OUTER JOIN (SELECT SUM(read_check) unread_cnt, "
					+ "chatroom_num FROM zboard_chat WHERE mem_num != ? "
					+ "GROUP BY chatroom_num) USING(chatroom_num) "
					+ "WHERE reader_num=? " + sub_sql
					+ " ORDER BY chat_num DESC)a) "
					+ "WHERE rnum >= ? AND rnum <= ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(++cnt, mem_num);
			pstmt.setLong(++cnt, mem_num);
			
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, keyword);
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<BoardVO>();
			while(rs.next()) {
				BoardVO board = new BoardVO();
				board.setBoard_num(rs.getLong("board_num"));
				board.setTitle(StringUtil.useNoHtml(rs.getString("title")));
				board.setHit(rs.getInt("hit"));
				board.setReg_date(rs.getDate("reg_date"));
				board.setId(rs.getString("id"));
				board.setUnread_cnt(rs.getInt("unread_cnt"));
				
				list.add(board);
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	
	//채팅 - 채팅방 생성 여부 체크
	public BoardChatRoomVO checkChatRoom(long board_num, long reader_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardChatRoomVO vo = null;
		String sql = null;
	
		try {
			conn =DBUtil.getConnection();
			sql = "SELECT c.*, (SELECT id FROM zmember WHERE mem_num=writer_num) writer_id, "
					+ "(SELECT id FROM zmember WHERE mem_num=reader_num) reader_id, "
					+ "z.title FROM zboard_chatroom c "
					+ "JOIN zboard z ON c.board_num=z.board_num "
					+ "WHERE c.board_num=? AND c.reader_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, board_num);
			pstmt.setLong(2, reader_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo = new BoardChatRoomVO();
				vo.setChatroom_num(rs.getLong("chatroom_num"));
				vo.setBoard_num(rs.getLong("board_num"));
				vo.setWriter_num(rs.getLong("writer_num"));
				vo.setReader_num(rs.getLong("reader_num"));
				vo.setWriter_id(rs.getString("writer_id"));
				vo.setReader_id(rs.getString("reader_id"));
				vo.setTitle(rs.getString("title"));
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return vo;
	}
	//채팅 - 채팅방 정보 반환
	public BoardChatRoomVO selectChatRoom(long chatroom_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardChatRoomVO vo = null;
		String sql = null;
		
		try {
			conn =DBUtil.getConnection();
			sql = "SELECT c.*, (SELECT id FROM zmember WHERE mem_num=writer_num) writer_id, "
					+ "(SELECT id FROM zmember WHERE mem_num=reader_num) reader_id, "
					+ "z.title FROM zboard_chatroom c "
					+ "JOIN zboard z ON c.board_num=z.board_num "
					+ "WHERE chatroom_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, chatroom_num);
			
			rs=pstmt.executeQuery();
			if(rs.next()) {
				vo = new BoardChatRoomVO();
				vo.setChatroom_num(rs.getLong("chatroom_num"));
				vo.setBoard_num(rs.getLong("board_num"));
				vo.setWriter_num(rs.getLong("writer_num"));
				vo.setReader_num(rs.getLong("reader_num"));
				vo.setWriter_id(rs.getString("writer_id"));
				vo.setReader_id(rs.getString("reader_id"));
				vo.setTitle(rs.getString("title"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return vo;
	}
	//채팅 - 채팅방 생성
	public void insertChatRoom(BoardChatRoomVO vo) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO zboard_chatroom "
					+ "(chatroom_num, board_num, writer_num, reader_num) "
					+ "VALUES (zchatroom_seq.nextval,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, vo.getBoard_num());
			pstmt.setLong(2, vo.getWriter_num());
			pstmt.setLong(3, vo.getReader_num());
			
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//채팅 - 채팅 등록
	public void insertChat(BoardChatVO chat) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO zboard_chat (chat_num, chatroom_num, mem_num, message) "
					+ "VALUES  (zchat_seq.nextval,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1,chat.getChatroom_num());
			pstmt.setLong(2,chat.getMem_num());
			pstmt.setString(3,chat.getMessage());
			
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//채팅 - 채팅메시지 읽기(목록)
	public List<BoardChatVO> getChatDetail(long user_num, long chatroom_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		List<BoardChatVO> list = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			//오토 커밋해제
			conn.setAutoCommit(false);
			
			System.out.println(user_num + "," + chatroom_num);
			
			//내가 보낸 메세지가 아닌 상대방이 보낸 메세지를 읽었을 때 
			//read_check를 1에서 0으로 변경
			sql = "UPDATE zboard_chat SET read_check=0 "
					+ "WHERE mem_num!= ? AND chatroom_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, user_num);
			pstmt.setLong(2, chatroom_num);
			pstmt.executeUpdate();
			
			sql="SELECT * FROM zboard_chat JOIN zmember USING(mem_num) "
					+ "WHERE chatroom_num=? ORDER BY chat_date ASC";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setLong(1, chatroom_num);
			
			rs = pstmt2.executeQuery();
			list = new ArrayList<BoardChatVO>();
			
			while(rs.next()) {
				BoardChatVO chat = new BoardChatVO();
				chat.setChat_num(rs.getLong("chat_num"));
				chat.setChatroom_num(rs.getLong("chatroom_num"));
				chat.setMem_num(rs.getLong("mem_num"));
				chat.setMessage(rs.getString("message"));
				chat.setChat_date(rs.getString("chat_date"));
				chat.setRead_check(rs.getInt("read_check"));
				chat.setId(rs.getString("id"));
				
				list.add(chat);
			}
			//SQL문이 모두 성공 시 커밋
			conn.commit();
			
		}catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
		
	}
}


