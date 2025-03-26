package kr.web.member;


//자바빈
//DTO(Data Transfer Object)
//VO(Value Object)
public class MemberVO {
	//멤버변수(프로퍼티)
	private String id;
	private String password;
	private String name;
	private String email;
	private String address;
	
	//getter
	public String getId() {
		return id;
	}
	//setter
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
	
}
