package com.james.runner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.james.crawler.YahooCrawler;

public class YahooRunner {
	public static void main(String[] args) {
		Date date1 = new Date();

		Map<String, String> input = new HashMap<String, String>();
		for (int i = 0; i < args.length; i++) {
			String[] parts = args[i].trim().split("=");
			input.put(parts[0], parts[1]);
			System.out.println(parts[0] + ": " + parts[1]);
		}

		YahooRunner runner = new YahooRunner();

		switch (input.get("mode")) {
			case "single-thread": runner.getDataSingleThread(input.get("stock-list"), input.get("date"), input.get("output")); break;
			case "multithread": runner.getDataMultiThread(input.get("thread"), input.get("stock-list"), input.get("date"), input.get("output")); break;
			default: break;
		}

		Date date2 = new Date();
		System.out.println(date2.getTime() - date1.getTime());
	}
	
	public void getDataMultiThread(String threadNumber, String stockList, String date, String output) {
		try {
			Queue<String> queue = getStockListFromFile(stockList);

			ExecutorService executor = Executors.newFixedThreadPool(Integer.parseInt(threadNumber));

			while (!queue.isEmpty()) {
				executor.execute(new Runnable() {
					String stock;
					String date;
					String output;
					public void run() {
						YahooCrawler crawler = new YahooCrawler();
						crawler.getData(output + "/" + date, stock);
					}

					private Runnable init(String stock, String date, String output) {
						this.stock = stock;
						this.date = date;
						this.output = output;
						return this;
					}
				}.init(queue.poll(), date, output));
			}

			executor.shutdown();
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void getDataSingleThread(String stockList, String date, String output) {
		Queue<String> queue = getStockListFromFile(stockList);
		while (!queue.isEmpty()) {
			YahooCrawler crawler = new YahooCrawler();
			crawler.getData(output + "/" + date, queue.poll());
		}
	}
	
	public Queue<String> getStockListFromFile(String stockList) {
		Queue<String> stockQueue = new LinkedList<String>();
		
		try {
			File file = new File(stockList);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stockQueue.add(line.split("\t")[0]);
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return stockQueue;
	}

}
