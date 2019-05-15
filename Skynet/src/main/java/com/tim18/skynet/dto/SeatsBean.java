package com.tim18.skynet.dto;

import java.util.List;

import com.tim18.skynet.model.Seat;



public class SeatsBean {
	
	private List<Seat> seats;
	private int rows;
	private int columns;
	
	public SeatsBean() {
		// TODO Auto-generated constructor stub
	}

	public SeatsBean(List<Seat> seats, int rows, int columns) {
		super();
		this.seats = seats;
		this.rows = rows;
		this.columns = columns;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	
	
	
	
	

}
