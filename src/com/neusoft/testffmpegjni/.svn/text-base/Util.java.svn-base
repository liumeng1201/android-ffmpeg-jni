package com.neusoft.testffmpegjni;


public class Util {
	public static String getTimeByInt(int time) {
		String result = "";
		int h = time / 3600;
		time = time % 3600;
		int m = time / 60;
		int s = time % 60;
		if (h > 0) {
			result = String.valueOf(h) + ":";
		}
		if (m > 0) {
			result += (String.valueOf(m) + ":");
		}
		result += String.valueOf(s);
		return result;
	}
}
