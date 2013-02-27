package com.example.freespot.database;

public class Logging {
	private long id;
	private String type;
	private String date;
	private int time;
	private int costs;
	private int totalCosts;

	// Empty constructor
	public Logging() {

	}

	// Constructor
	public Logging(long id, String type, String date, int time, int costs, int totalCosts) {
		this.id = id;
		this.type = type;
		this.date = date;
		this.time = time;
		this.costs = costs;
		this.totalCosts = totalCosts;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getTotalCosts() {
		return totalCosts;
	}

	public void setTotalCosts(int totalCosts) {
		this.totalCosts = totalCosts;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getCosts() {
		return costs;
	}

	public void setCosts(int costs) {
		this.costs = costs;
	}

	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return type;
	}
}