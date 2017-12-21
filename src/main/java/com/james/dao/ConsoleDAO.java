package com.james.dao;

import com.james.modal.YahooData;

public class ConsoleDAO {
	public static void output(YahooData data, boolean brief) {
		if (brief) System.out.println(data.ticker); 
		else System.out.println(data);
	}
}
