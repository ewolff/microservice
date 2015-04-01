package com.ewolff.microservice.order.logic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class OrderLine {

	@Column(name = "F_COUNT")
	private int count;

	private long itemId;

	@Id
	@GeneratedValue
	private long id;

	public void setCount(int count) {
		this.count = count;
	}

	public void setItemId(long item) {
		this.itemId = item;
	}

	public OrderLine() {
	}

	public OrderLine(int count, long item) {
		this.count = count;
		this.itemId = item;
	}

	public int getCount() {
		return count;
	}

	public long getItemId() {
		return itemId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + count;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (int) (itemId ^ (itemId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderLine other = (OrderLine) obj;
		if (count != other.count)
			return false;
		if (id != other.id)
			return false;
		if (itemId != other.itemId)
			return false;
		return true;
	}

}
