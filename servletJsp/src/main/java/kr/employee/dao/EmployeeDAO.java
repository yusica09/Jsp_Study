package kr.employee.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.employee.vo.EmployeeVO;
import kr.util.DBUtil;

public class EmployeeDAO {
	//싱글턴 패턴
	private static EmployeeDAO instance = new EmployeeDAO();
	
	public static EmployeeDAO getInstance() {
		return instance;
	}
	private EmployeeDAO() {}
	
	//사원 등록
	public void insertEmployee(EmployeeVO vo) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int cnt = 0;
		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO semployee(snum,id,name,passwd,salary,job) "
					+ "VALUES (semployee_seq.nextval,?,?,?,?,?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(++cnt, vo.getId());
			pstmt.setString(++cnt, vo.getName());
			pstmt.setString(++cnt, vo.getPasswd());
			pstmt.setInt(++cnt, vo.getSalary());
			pstmt.setString(++cnt, vo.getJob());
			
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//사원 상세정보
	public EmployeeVO getEmployee(long snum) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		EmployeeVO employee = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM semployee WHERE snum=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, snum);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				employee = new EmployeeVO();
				
				employee.setSnum(rs.getLong("snum"));
				employee.setId(rs.getString("id"));
				employee.setName(rs.getString("name"));
				employee.setPasswd(rs.getString("passwd"));
				employee.setSalary(rs.getInt("salary"));
				employee.setJob(rs.getString("job"));
				employee.setReg_date(rs.getDate("reg_date"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return employee;
	}
	
	//아이디 중복체크, 로그인 체크
	public EmployeeVO checkEmployee(String id) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		EmployeeVO employee = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM semployee WHERE id=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				employee = new EmployeeVO();
				employee.setId(rs.getString("id"));
				employee.setPasswd(rs.getString("passwd"));
				employee.setSnum(rs.getLong("snum"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return employee;
	}
	
	//사원정보 수정
	public void updateEmployee(EmployeeVO vo) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int cnt = 0;
		try {
			conn = DBUtil.getConnection();
			sql = "UPDATE semployee SET name=?, passwd=?, salary=?, job=? WHERE snum=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(++cnt, vo.getName());
			pstmt.setString(++cnt, vo.getPasswd());
			pstmt.setInt(++cnt, vo.getSalary());
			pstmt.setString(++cnt, vo.getJob());
			
			pstmt.setLong(++cnt, vo.getSnum());
			
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//사원정보 삭제
	public void deleteEmployee(EmployeeVO vo) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "DELETE FROM semployee WHERE snum=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, vo.getSnum());
			
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
}
