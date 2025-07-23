package kr.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.member.vo.MemberVO;
import kr.util.DBUtil;

public class MemberDAO {
	//싱글턴 패턴
	private static MemberDAO instance = new MemberDAO();
	
	public static MemberDAO getInstance() {
		return instance;
	}
	private MemberDAO() {}
	
	//회원가입
	public void insertMember(MemberVO member)
			                          throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rs = null;
		String sql = null;
		long num = 0; //시퀀스 번호 저장
		int cnt = 0;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//오토 커밋 해제
			conn.setAutoCommit(false);
			
			//회원번호(mem_num) 생성
			sql = "SELECT zmember_seq.nextval FROM dual";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				num = rs.getLong(1);
			}
			
			sql = "INSERT INTO zmember (mem_num,id) VALUES (?,?)";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setLong(1, num);//회원번호
			pstmt2.setString(2, member.getId());
			pstmt2.executeUpdate();
			
			sql = "INSERT INTO zmember_detail (mem_num,name,passwd,"
				+ "phone,email,zipcode,address1,address2) VALUES ("
				+ "?,?,?,?,?,?,?,?)";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setLong(++cnt, num);//회원번호
			pstmt3.setString(++cnt, member.getName());
			pstmt3.setString(++cnt, member.getPasswd());
			pstmt3.setString(++cnt, member.getPhone());
			pstmt3.setString(++cnt, member.getEmail());
			pstmt3.setString(++cnt, member.getZipcode());
			pstmt3.setString(++cnt, member.getAddress1());
			pstmt3.setString(++cnt, member.getAddress2());
			pstmt3.executeUpdate();
			
			//SQL 실행시 모두 성공하면 commit
			conn.commit();
		}catch(Exception e) {
			//SQL문이 하나라도 실패하면 rollback
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(rs, pstmt, conn);
		}
	}
	//ID,Email 중복 체크
	public boolean checkUniqueInfo(String id, String email)
	                                     throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		
		if(id!=null && email == null) {
			sub_sql += "id=?";
		}else if(id==null && email != null) {
			sub_sql += "email=?";
		}
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM zmember LEFT OUTER JOIN "
				+ "zmember_detail USING(mem_num) WHERE " + sub_sql;
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			if(id!=null && email == null) {
				pstmt.setString(1, id);
			}else if(id==null && email != null) {
				pstmt.setString(1, email);
			}
			//SQL문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return true; //id 또는 email 중복
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return false; //id 또는 email 미중복
	}
	//로그인 처리
	public MemberVO checkMember(String id)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO member = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM zmember JOIN zmember_detail "
				+ "USING(mem_num) WHERE id=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1, id);
			//SQL문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				member = new MemberVO();
				member.setMem_num(rs.getLong("mem_num"));
				member.setId(rs.getString("id"));
				member.setAuth(rs.getInt("auth"));
				member.setPasswd(rs.getString("passwd"));
				member.setPhoto(rs.getString("photo"));
				member.setEmail(rs.getString("email"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}		
		return member;
	}
	//회원상세정보
	public MemberVO getMember(long mem_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO member = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM zmember JOIN zmember_detail "
				+ "USING(mem_num) WHERE mem_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setLong(1, mem_num);
			//SQL문을 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				member = new MemberVO();
				member.setMem_num(rs.getLong("mem_num"));
				member.setId(rs.getString("id"));
				member.setAuth(rs.getInt("auth"));
				member.setPasswd(rs.getString("passwd"));
				member.setName(rs.getString("name"));
				member.setPhone(rs.getString("phone"));
				member.setEmail(rs.getString("email"));
				member.setZipcode(rs.getString("zipcode"));
				member.setAddress1(rs.getString("address1"));
				member.setAddress2(rs.getString("address2"));
				member.setPhoto(rs.getString("photo"));
				member.setReg_date(rs.getDate("reg_date"));
				member.setModify_date(rs.getDate("modify_date"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}		
		return member;
	}
	//회원정보수정
	public void updateMember(MemberVO member)
			                            throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int cnt = 0;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE zmember_detail SET name=?,phone=?,"
				+ "zipcode=?,address1=?,address2=?,modify_date=SYSDATE "
				+ "WHERE mem_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(++cnt, member.getName());
			pstmt.setString(++cnt, member.getPhone());
			pstmt.setString(++cnt, member.getZipcode());
			pstmt.setString(++cnt, member.getAddress1());
			pstmt.setString(++cnt, member.getAddress2());
			pstmt.setLong(++cnt, member.getMem_num());
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//비밀번호수정
	public void updatePassword(String passwd,long mem_num)
	                                     throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE zmember_detail SET passwd=? WHERE mem_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1, passwd);//새비밀번호
			pstmt.setLong(2, mem_num);//회원번호
			//SQL문 실행
			pstmt.executeUpdate();			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//프로필 사진 수정
	public void updateMyPhoto(String photo, long mem_num)
	                                     throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE zmember_detail SET photo=? WHERE mem_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1, photo);
			pstmt.setLong(2, mem_num);
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}		
	}
	//회원탈퇴(회원정보삭제)
	public void deleteMember(long mem_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//auto commit 해제
			conn.setAutoCommit(false);
			
			//zmember의 auth 값 변경
			sql = "UPDATE zmember SET auth=0 WHERE mem_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, mem_num);
			pstmt.executeUpdate();
			
			//zmember_detail의 레코드 삭제
			sql = "DELETE FROM zmember_detail WHERE mem_num=?";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setLong(1, mem_num);
			pstmt2.executeUpdate();
			
			//모든 SQL문의 실행이 성공하면 커밋
			conn.commit();
		}catch(Exception e) {
			//SQL문이 하나라도 실패하면 롤백
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//관리자
	//전체 내용 개수,검색 내용 개수
	public int getMemberCoutByAdmin(String keyfield,
			                        String keyword)
	                                 throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			if(keyword != null && !"".equals(keyword)) {
				if(keyfield.equals("1")) sub_sql += "WHERE id LIKE '%' || ? || '%'";
				else if(keyfield.equals("2")) sub_sql += "WHERE name LIKE '%' || ? || '%'";
				else if(keyfield.equals("3")) sub_sql += "WHERE email LIKE '%' || ? || '%'";
			}
			
			sql = "SELECT COUNT(*) FROM zmember LEFT OUTER JOIN "
				+ "zmember_detail USING(mem_num) " + sub_sql;
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
	//목록,검색 목록
	public List<MemberVO> getListMemberByAdmin(int start,
			                    int end, String keyfield,
			                    String keyword)
	                                  throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MemberVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			if(keyword != null && !"".equals(keyword)) {
				if(keyfield.equals("1")) sub_sql += "WHERE id LIKE '%' || ? || '%'";
				else if(keyfield.equals("2")) sub_sql += "WHERE name LIKE '%' || ? || '%'";
				else if(keyfield.equals("3")) sub_sql += "WHERE email LIKE '%' || ? || '%'";
			}
			
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
				+ "(SELECT * FROM zmember LEFT OUTER JOIN zmember_detail "
				+ "USING(mem_num) " + sub_sql 
				+ " ORDER BY reg_date DESC NULLS LAST)a) "
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
			
			list = new ArrayList<MemberVO>();
			while(rs.next()) {
				MemberVO member = new MemberVO();
				member.setMem_num(rs.getLong("mem_num"));
				member.setId(rs.getString("id"));
				member.setAuth(rs.getInt("auth"));
				member.setName(rs.getString("name"));
				member.setPhone(rs.getString("phone"));
				member.setEmail(rs.getString("email"));
				member.setReg_date(rs.getDate("reg_date"));
				
				list.add(member);
			}			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}		
		return list;
	}
	
	//회원등급 수정
	public void updateMemberByAdmin(int auth,long mem_num)
	                                    throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE zmember SET auth=? WHERE mem_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, auth);
			pstmt.setLong(2, mem_num);
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
}



















