package kr.cart.vo;

import java.sql.Date;

import kr.item.vo.ItemVO;

public class CartVO {
	private long cart_num;
	private long item_num;
	private int order_quantity;
	private Date reg_date;
	private long mem_num;
	private int sub_total; //동일 상품의 총 구매 금액
	
	private ItemVO itemVO; //상품정보제공

	public long getCart_num() {
		return cart_num;
	}

	public void setCart_num(long cart_num) {
		this.cart_num = cart_num;
	}

	public long getItem_num() {
		return item_num;
	}

	public void setItem_num(long item_num) {
		this.item_num = item_num;
	}

	public int getOrder_quantity() {
		return order_quantity;
	}

	public void setOrder_quantity(int order_quantity) {
		this.order_quantity = order_quantity;
	}

	public Date getReg_date() {
		return reg_date;
	}

	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}

	public long getMem_num() {
		return mem_num;
	}

	public void setMem_num(long mem_num) {
		this.mem_num = mem_num;
	}

	public int getSub_total() {
		return sub_total;
	}

	public void setSub_total(int sub_total) {
		this.sub_total = sub_total;
	}

	public ItemVO getItemVO() {
		return itemVO;
	}

	public void setItemVO(ItemVO itemVO) {
		this.itemVO = itemVO;
	}

	
}
