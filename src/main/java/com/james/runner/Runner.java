package com.james.runner;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.james.crawler.YahooCrawler;
import com.james.dao.ConsoleDAO;
import com.james.dao.FileDAO;
import com.james.dao.FileUtil;
import com.james.modal.YahooData;

public class Runner {
	public static void main(String[] args) {
		Date date1 = new Date();

		Map<String, String> input = new HashMap<String, String>();
		for (int i = 0; i < args.length; i++) {
			String[] parts = args[i].trim().split("=");
			input.put(parts[0], parts[1]);
			System.out.println(parts[0] + ": " + parts[1]);
		}

		Runner runner = new Runner();

		switch (input.get("mode")) {
			case "single-thread": runner.getYahooData(input.get("stock-list"), input.get("output")); break;
			case "multithread": runner.getYahooData(input.get("stock-list"), input.get("output"), input.get("thread")); break;
			default: break;
		}

		Date date2 = new Date();
		System.out.println(date2.getTime() - date1.getTime());
	}
	
	public void getYahooData(String stockList, String output, String threadNumber) {
		try {
			Queue<String> queue = FileUtil.getStockListFromFile(stockList);			
			ExecutorService executor = Executors.newFixedThreadPool(Integer.parseInt(threadNumber));

			while (!queue.isEmpty()) {
				executor.execute(new YahooRunner(queue.poll(), output));
			}

			executor.shutdown();
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void getYahooData(String stockList, String output) {
		Queue<String> queue = FileUtil.getStockListFromFile(stockList);
		while (!queue.isEmpty()) {
			YahooRunner runner = new YahooRunner(queue.poll(), output);
			runner.run();
		}
	}
	
	class YahooRunner implements Runnable {
		
		private String ticker; 
		private String finalPath;
		
		public YahooRunner(String ticker, String output) {
			this.ticker = ticker;
			this.finalPath = output;
		}
		
		@Override
		public void run() {
			if(FileUtil.isFileExist(finalPath + "/" + ticker + ".txt")) return;
			YahooCrawler crawler = new YahooCrawler();
			YahooData data = crawler.getData(ticker);
			ConsoleDAO.output(data, true);
			FileDAO.output(data, finalPath);
		}
	}
}
