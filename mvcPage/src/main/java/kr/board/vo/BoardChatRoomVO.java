package kr.board.vo;

import java.sql.Date;

public class BoardChatRoomVO {
	private long chatroom_num;
	private long board_num;
	private long writer_num;
	private long reader_num; 
	private Date room_date; //채팅방 생성일
	
	private String title;
	private String writer_id;
	private String reader_id;
	public long getChatroom_num() {
		return chatroom_num;
	}
	public void setChatroom_num(long chatroom_num) {
		this.chatroom_num = chatroom_num;
	}
	public long getBoard_num() {
		return board_num;
	}
	public void setBoard_num(long board_num) {
		this.board_num = board_num;
	}
	public long getWriter_num() {
		return writer_num;
	}
	public void setWriter_num(long writer_num) {
		this.writer_num = writer_num;
	}
	public long getReader_num() {
		return reader_num;
	}
	public void setReader_num(long reader_num) {
		this.reader_num = reader_num;
	}
	public Date getRoom_date() {
		return room_date;
	}
	public void setRoom_date(Date room_date) {
		this.room_date = room_date;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWriter_id() {
		return writer_id;
	}
	public void setWriter_id(String writer_id) {
		this.writer_id = writer_id;
	}
	public String getReader_id() {
		return reader_id;
	}
	public void setReader_id(String reader_id) {
		this.reader_id = reader_id;
	}
	
	
}
