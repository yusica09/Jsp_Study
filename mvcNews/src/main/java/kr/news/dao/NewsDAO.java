package kr.news.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.news.vo.NewsVO;
import kr.util.DBUtil;

public class NewsDAO {
	
	//싱글턴 패턴
	private static NewsDAO instance = new NewsDAO();
	public static NewsDAO getInstance() {
		return instance;
	}
	private NewsDAO() {}
	
	//뉴스 등록
	public void registerNews(NewsVO vo) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO dailynews (num,title,writer,passwd,"
					+ "email,article,filename) VALUES (dailynews_seq.nextval,"
					+ "?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getWriter());
			pstmt.setString(3, vo.getPasswd());
			pstmt.setString(4, vo.getEmail());
			pstmt.setString(5, vo.getArticle());
			pstmt.setString(6, vo.getFilename());
			
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//뉴스 총 레코드 수
	public int getCount() throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT COUNT(*) FROM dailynews";
			pstmt = conn.prepareStatement(sql);
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
	//뉴스 목록
	public List<NewsVO> getList(int startRow, int endRow) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<NewsVO> list = null;
		
		try {
			conn = DBUtil.getConnection();
			sql =  "SELECT * FROM (SELECT a.*, rownum rnum "
					+ "FROM (SELECT * FROM dailynews ORDER BY "
					+ "num DESC)a) WHERE rnum >= ? AND rnum <= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<NewsVO>();
			while(rs.next()) {
				NewsVO news = new NewsVO();
				news.setNum(rs.getLong("num"));
				news.setTitle(rs.getString("title"));
				news.setWriter(rs.getString("writer"));
				news.setReg_date(rs.getDate("reg_date"));
				
				list.add(news);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	//뉴스 상세 정보
	public NewsVO getNews(long num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		NewsVO news = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM dailynews WHERE num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				news = new NewsVO();
				news.setNum(rs.getLong("num"));
				news.setTitle(rs.getString("title"));
				news.setWriter(rs.getString("writer"));
				news.setPasswd(rs.getString("passwd"));
				news.setEmail(rs.getString("email"));
				news.setArticle(rs.getString("article"));
				news.setFilename(rs.getString("filename"));
				news.setReg_date(rs.getDate("reg_date"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return news;
	}
	//뉴스 수정 - 파일 추가 시 업로드 가변적 처리
	public void updateNews(NewsVO vo) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		try {
			conn = DBUtil.getConnection();
			if(vo.getFilename() != null && !vo.getFilename().isEmpty()) {
				sub_sql += ",filename=?";
			}
			sql = "UPDATE dailynews SET title=?,writer=?,email=?,article=?" + sub_sql + "WHERE num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(++cnt,vo.getTitle());
			pstmt.setString(++cnt,vo.getWriter());
			pstmt.setString(++cnt,vo.getEmail());
			pstmt.setString(++cnt,vo.getArticle());
			if(vo.getFilename() != null && !vo.getFilename().isEmpty()) {
				pstmt.setString(++cnt,vo.getFilename());
			}
			pstmt.setLong(++cnt,vo.getNum());
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//뉴스 삭제
	public void deleteNews(long num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			conn = DBUtil.getConnection();
			sql = "DELETE FROM dailynews WHERE num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, num);

			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
}
