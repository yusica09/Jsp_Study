package kr.story.vo;

import java.sql.Date;

public class StoryVO {
	//프로퍼티(멤버변수)
	private long story_num;
	private String title;
	private String content;
	private String ip;
	private long snum;
	private Date reg_date;
	
	private String id;

	public long getStory_num() {
		return story_num;
	}

	public void setStory_num(long story_num) {
		this.story_num = story_num;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public long getSnum() {
		return snum;
	}

	public void setSnum(long snum) {
		this.snum = snum;
	}

	public Date getReg_date() {
		return reg_date;
	}

	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	
}
