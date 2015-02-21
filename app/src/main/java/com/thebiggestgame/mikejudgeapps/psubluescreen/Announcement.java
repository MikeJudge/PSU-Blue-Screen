package com.thebiggestgame.mikejudgeapps.psubluescreen;

public class Announcement {
	private String title;
	private String message;

	public Announcement() {
		this("","");
	}

	public Announcement(String title, String message) {
		this.title = title;
		this.message = message;
	}

	public String getTitle() {
		return title;
	}

	public String getMessage() {
		return message;
	}

    //returns the title for the listview item
    public String toString() {
        return getTitle();
    }

}