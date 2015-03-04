package com.thebiggestgame.mikejudgeapps.psubluescreen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.net.URL;
import java.util.Scanner;

public class BlueScreenTracker {
	private final String EVENTS_URL        = "http://www2.hn.psu.edu/hn/bluescreen/events.asp";
	private final String CANCELLATIONS_URL = "http://www2.hn.psu.edu/hn/bluescreen/cancellations.asp";

	private LinkedList<Announcement> cancellations;
	private LinkedList<Announcement> events;

	public BlueScreenTracker() {
		cancellations = new LinkedList<>();
		events = new LinkedList<>();
	}

	//this method pulls the class cancellations and events from the PSU
	//Blue screen website and stores them in the cancellations and events
	//linkedlists
	public void refreshFeed() {
		events = getAnnouncements(EVENTS_URL, 26, 4);
		cancellations = getAnnouncements (CANCELLATIONS_URL, 29, 3);
	}

    public LinkedList<Announcement> getCancellations() {
        return cancellations;
    }

    public LinkedList<Announcement> getEvents() {
        return events;
    }


	private LinkedList<Announcement> getAnnouncements(String url, int initialSkips, int postSkips) {

		LinkedList<Announcement> list = new LinkedList<>();
		try {
			URL site = new URL(url);
			BufferedReader scanner = new BufferedReader(new InputStreamReader(site.openStream()));
			skipLines(initialSkips, scanner);

			String title;
			String message;
			String temp;
			while (scanner.ready()) {
				skipLines(2, scanner);

				temp = scanner.readLine();
                title = getSubstring(temp, "<b>", "</b>");

				temp = scanner.readLine();
                message = getSubstring(temp, "=2>", "</font>");
				message = message.replaceAll("<br>", " ");

				list.add(new Announcement(title, message));
				skipLines(postSkips, scanner);
			}
			scanner.close();
		} catch (Exception io) {io.printStackTrace(); }

		return list;
	}

    private static String getSubstring(String line, String startTag, String endTag) {
        int startIndex = line.indexOf(startTag);
        int endIndex = line.indexOf(endTag);
        if (startIndex < 0 || endIndex < 0)
            return "";
        else if (startIndex + startTag.length() >= endIndex)
            return "";

        return line.substring(startIndex + startTag.length(), endIndex);

    }

	private static void skipLines(int n, BufferedReader scanner) throws IOException {
		for (int i = 0; i < n; i++) {
			if (scanner.ready())
				scanner.readLine();
		}
	}

}