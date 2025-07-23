package kr.board.vo;

public class BoardChatVO {
	private long chat_num; //채팅 번호
	private long chatroom_num; //채팅방 번호
	private long mem_num; //채팅메세지 작성자 번호
	private String message; //채팅메세지
	private String chat_date; //채팅 날짜
	private int read_check;  // 읽음:0 읽지않음:1
	
	private String id;
	private int unread_cnt; //읽지 않은 채팅 메세지 수
	
	public long getChat_num() {
		return chat_num;
	}
	public void setChat_num(long chat_num) {
		this.chat_num = chat_num;
	}
	public long getChatroom_num() {
		return chatroom_num;
	}
	public void setChatroom_num(long chatroom_num) {
		this.chatroom_num = chatroom_num;
	}
	public long getMem_num() {
		return mem_num;
	}
	public void setMem_num(long mem_num) {
		this.mem_num = mem_num;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getChat_date() {
		return chat_date;
	}
	public void setChat_date(String chat_date) {
		this.chat_date = chat_date;
	}
	public int getRead_check() {
		return read_check;
	}
	public void setRead_check(int read_check) {
		this.read_check = read_check;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getUnread_cnt() {
		return unread_cnt;
	}
	public void setUnread_cnt(int unread_cnt) {
		this.unread_cnt = unread_cnt;
	}
	
	
}
