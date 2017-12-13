package com.james.crawler;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

import com.james.modal.MajorHolder;
import com.james.modal.Statistics;
import com.james.modal.TopInstitutionalHolder;
import com.james.modal.TopMutualFundHolder;

public class YahooCrawler {
	public void getData(String filePath, String ticker) {
		if (isFileExist(filePath, ticker)) return;
		
		PrintWriter writer = createWirter(filePath, ticker);
		
		StatisticsCrawler statisticsCrawler = new StatisticsCrawler();
		Statistics statistics = statisticsCrawler.getStatistics(ticker);
		writer.println("statistics");
		writer.println("\t" + Statistics.getLable());
		writer.println("\t" + statistics);
		
		HoldersCrawler holdersCrawler = new HoldersCrawler(ticker);
		MajorHolder majorHolder = holdersCrawler.getMajorHolder();
		List<TopInstitutionalHolder> institutionalHolders = holdersCrawler.getTopInstitutionalHolder();
		List<TopMutualFundHolder> mutualFundHolders = holdersCrawler.getTopMutualFundHolder();
		writer.println("major holder");
		writer.println("\t" + MajorHolder.getLable());
		writer.println("\t" + majorHolder);
		writer.println("institutional holder");
		writer.println("\t" + TopInstitutionalHolder.getLable());
		for (TopInstitutionalHolder institutionalHolder : institutionalHolders) writer.println("\t" + institutionalHolder);
		writer.println("mutual fund holder");
		writer.println("\t" + TopMutualFundHolder.getLable());
		for (TopMutualFundHolder mutualFundHolder : mutualFundHolders) writer.println("\t" + mutualFundHolder);
		
		writer.close();
	}

	public PrintWriter createWirter(String filePath, String ticker) {
		try {
			new File(filePath).mkdirs();
			PrintWriter writer = new PrintWriter(filePath + "/" + ticker + ".txt", "UTF-8");
			return writer;
		} catch (Exception e) {
			return  null;
		}
	}

	public boolean isFileExist(String filePath, String ticker) {
		File file = new File(filePath + "/" + ticker + ".txt");
		return file.exists() && file.length() != 0;
	}
}
