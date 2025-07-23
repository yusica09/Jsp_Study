package kr.order.vo;

import java.sql.Date;

public class OrderVO {
	private long order_num;
	private int order_total; //총 구매금액
	private int payment; //지불방식
	private int status; //배송상태
	private String receive_name; //받는 사람
	private String receive_post; //우편번호
	private String receive_address1; //주소
	private String receive_address2; //상세주소
	private String receive_phone; //전화번호
	private String notice; //남기실 말씀
	private Date reg_date; //주문일
	private Date modify_date;
	private long mem_num; //구매자

	private String item_name; //구매 상품명
	private String id; //구매자 id
	
	//배송상태 case별 문자열 반환
	public String getStatusName() {
		String name;
		switch(status) {
		case 1: name = "배송대기"; break;
		case 2: name = "배송준비중"; break;
		case 3: name = "배송중"; break;
		case 4: name = "배송완료"; break;
		case 5: name = "주문취소"; break;
		default : name = "상태오류";
		}
		
		return name;
	}

	public long getOrder_num() {
		return order_num;
	}

	public void setOrder_num(long order_num) {
		this.order_num = order_num;
	}

	public int getOrder_total() {
		return order_total;
	}

	public void setOrder_total(int order_total) {
		this.order_total = order_total;
	}

	public int getPayment() {
		return payment;
	}

	public void setPayment(int payment) {
		this.payment = payment;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getReceive_name() {
		return receive_name;
	}

	public void setReceive_name(String receive_name) {
		this.receive_name = receive_name;
	}

	public String getReceive_post() {
		return receive_post;
	}

	public void setReceive_post(String receive_post) {
		this.receive_post = receive_post;
	}

	public String getReceive_address1() {
		return receive_address1;
	}

	public void setReceive_address1(String receive_address1) {
		this.receive_address1 = receive_address1;
	}

	public String getReceive_address2() {
		return receive_address2;
	}

	public void setReceive_address2(String receive_address2) {
		this.receive_address2 = receive_address2;
	}

	public String getReceive_phone() {
		return receive_phone;
	}

	public void setReceive_phone(String receive_phone) {
		this.receive_phone = receive_phone;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public Date getReg_date() {
		return reg_date;
	}

	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}

	public Date getModify_date() {
		return modify_date;
	}

	public void setModify_date(Date modify_date) {
		this.modify_date = modify_date;
	}

	public long getMem_num() {
		return mem_num;
	}

	public void setMem_num(long mem_num) {
		this.mem_num = mem_num;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	
}
