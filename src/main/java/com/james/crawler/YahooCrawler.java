package com.james.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.james.modal.YahooData;

public class YahooCrawler {
	public YahooData getData(String ticker) {
		try {
			YahooData data = new YahooData();
			data.ticker = ticker;
			
			HoldersCrawler holdersCrawler = new HoldersCrawler();
			Document holders_document = Jsoup.connect("https://finance.yahoo.com/quote/" + ticker + "/holders").get();
			data.majorHolder = holdersCrawler.getMajorHolder(holders_document, ticker);
			data.institutionalHolders = holdersCrawler.getTopInstitutionalHolder(holders_document, ticker);
			data.mutualFundHolders = holdersCrawler.getTopMutualFundHolder(holders_document, ticker);
			
			StatisticsCrawler statisticsCrawler = new StatisticsCrawler();
			Document statistics_document = Jsoup.connect("https://finance.yahoo.com/quote/" + ticker + "/key-statistics").get();
			data.statistics = statisticsCrawler.getStatistics(statistics_document, ticker);
			
			return data;
		} catch (Exception e) {
			System.out.println("Yahoo crawler has some issue for " + ticker);
			System.out.println(e.toString());
			return null;
		}
	}
}
