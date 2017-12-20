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
		try {
			if (isFileExist(filePath, ticker)) return;
						
			StatisticsCrawler statisticsCrawler = new StatisticsCrawler();
			Statistics statistics = statisticsCrawler.getStatistics(ticker);
			
			HoldersCrawler holdersCrawler = new HoldersCrawler(ticker);
			MajorHolder majorHolder = holdersCrawler.getMajorHolder();
			List<TopInstitutionalHolder> institutionalHolders = holdersCrawler.getTopInstitutionalHolder();
			List<TopMutualFundHolder> mutualFundHolders = holdersCrawler.getTopMutualFundHolder();
			
			outputToFile(statistics, majorHolder, institutionalHolders, mutualFundHolders, filePath, ticker);
			// outputToConsole(statistics, majorHolder, institutionalHolders, mutualFundHolders, ticker);
		} catch (Exception e) {
			System.out.println(this.ticker);
			System.out.println(e.toString());
		}
	}
	
	public void outputToFile(Statistics statistics, MajorHolder majorHolder, List<TopInstitutionalHolder> institutionalHolders, List<TopMutualFundHolder> mutualFundHolders, String filePath, String ticker) {
		PrintWriter writer = createWirter(filePath, ticker);
		
		writer.println(ticker);
		writer.println("\t" + "statistics");
		writer.println("\t" + "\t" + Statistics.getLable());
		writer.println("\t" + "\t" + statistics);


		writer.println("\t" + "major holder");
		writer.println("\t" + "\t" + MajorHolder.getLable());
		writer.println("\t" + "\t" + majorHolder);
		writer.println("\t" + "institutional holder");
		writer.println("\t" + "\t" + TopInstitutionalHolder.getLable());
		for (TopInstitutionalHolder institutionalHolder : institutionalHolders) writer.println("\t" + "\t" + institutionalHolder);
		writer.println("\t" + "mutual fund holder");
		writer.println("\t" + "\t" + TopMutualFundHolder.getLable());
		for (TopMutualFundHolder mutualFundHolder : mutualFundHolders) writer.println("\t" + "\t" + mutualFundHolder);
		
		writer.close();
	}
	
	public void outputToConsole(Statistics statistics, MajorHolder majorHolder, List<TopInstitutionalHolder> institutionalHolders, List<TopMutualFundHolder> mutualFundHolders, String ticker) {		
		System.out.println(ticker);
		System.out.println("\t" + "statistics");
		System.out.println("\t" + "\t" + Statistics.getLable());
		System.out.println("\t" + "\t" + statistics);


		System.out.println("\t" + "major holder");
		System.out.println("\t" + "\t" + MajorHolder.getLable());
		System.out.println("\t" + "\t" + majorHolder);
		System.out.println("\t" + "institutional holder");
		System.out.println("\t" + "\t" + TopInstitutionalHolder.getLable());
		for (TopInstitutionalHolder institutionalHolder : institutionalHolders) System.out.println("\t" + "\t" + institutionalHolder);
		System.out.println("\t" + "mutual fund holder");
		System.out.println("\t" + "\t" + TopMutualFundHolder.getLable());
		for (TopMutualFundHolder mutualFundHolder : mutualFundHolders) System.out.println("\t" + "\t" + mutualFundHolder);
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
