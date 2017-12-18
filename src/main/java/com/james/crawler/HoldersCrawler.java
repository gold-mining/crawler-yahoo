package com.james.crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.james.modal.MajorHolder;
import com.james.modal.TopInstitutionalHolder;
import com.james.modal.TopMutualFundHolder;

import us.codecraft.xsoup.Xsoup;

public class HoldersCrawler {
		
	private Document document;
	
	public HoldersCrawler() {
	}
	
	public HoldersCrawler(String ticker) {
		try {
			this.document = Jsoup.connect("https://finance.yahoo.com/quote/" + ticker + "/holders").get();
		} catch (IOException e) {
			System.err.println(ticker);
			e.printStackTrace();
		}
	}
	
//	public void getStatistics(String ticker) {
//			MajorHolder majorHolder = getMajorHolder();
//			List<TopInstitutionalHolder> institutionalHolder = getTopInstitutionalHolder();
//			List<TopMutualFundHolder> mutualFundHolder = getTopMutualFundHolder();
//	}

	public MajorHolder getMajorHolder() {
		try {
			MajorHolder majorHolder = new MajorHolder();
			String majorHoldersXPath = "//*[@id=\"Col1-1-Holders-Proxy\"]/section/div[2]/div[2]/div/table/tbody";
			Element majorHoldersSection = Xsoup.compile(majorHoldersXPath).evaluate(document).getElements().get(0);
			
			
			majorHolder.Insider_Shares = Xsoup.compile("tbody/tr[1]/td[1]").evaluate(majorHoldersSection).getElements().get(0).text();
			majorHolder.Institutions_Shares = Xsoup.compile("tbody/tr[2]/td[1]").evaluate(majorHoldersSection).getElements().get(0).text();
			majorHolder.Institutions_Float = Xsoup.compile("tbody/tr[3]/td[1]").evaluate(majorHoldersSection).getElements().get(0).text();
			majorHolder.Institutions_Number = Xsoup.compile("tbody/tr[4]/td[1]").evaluate(majorHoldersSection).getElements().get(0).text();
			
			return majorHolder;
		} catch (Exception e) {
			return null;
		}
	}

	public List<TopInstitutionalHolder> getTopInstitutionalHolder() {
		List<TopInstitutionalHolder> institutionalHolders = new ArrayList<TopInstitutionalHolder>();
		String institutionalHoldersXPath = "//*[@id=\"Col1-1-Holders-Proxy\"]/section/div[2]/div[3]/table/tbody/tr";
		Elements institutionalHoldersSection = Xsoup.compile(institutionalHoldersXPath).evaluate(document).getElements();
		
		for(int i = 0; i < institutionalHoldersSection.size(); i++) {
			TopInstitutionalHolder institutionalHolder = new TopInstitutionalHolder();
			 institutionalHolder.Holder = Xsoup.compile("tr/td[1]").evaluate(institutionalHoldersSection.get(i)).getElements().get(0).text();
			 institutionalHolder.Shares = Xsoup.compile("tr/td[2]").evaluate(institutionalHoldersSection.get(i)).getElements().get(0).text();
			 institutionalHolder.Date_Reported = Xsoup.compile("tr/td[3]").evaluate(institutionalHoldersSection.get(i)).getElements().get(0).text();
			 institutionalHolder.Percentage = Xsoup.compile("tr/td[4]").evaluate(institutionalHoldersSection.get(i)).getElements().get(0).text();
			 institutionalHolder.Value = Xsoup.compile("tr/td[5]").evaluate(institutionalHoldersSection.get(i)).getElements().get(0).text();
			 institutionalHolders.add(institutionalHolder);
		}
		
		return institutionalHolders;
	}

	public List<TopMutualFundHolder> getTopMutualFundHolder() {
		List<TopMutualFundHolder> mutualFundHolders = new ArrayList<TopMutualFundHolder>();
		
		String institutionalHoldersXPath = "//*[@id=\"Col1-1-Holders-Proxy\"]/section/div[2]/div[4]/table/tbody/tr";
		Elements institutionalHoldersSection = Xsoup.compile(institutionalHoldersXPath).evaluate(document).getElements();
		
		for(int i = 0; i < institutionalHoldersSection.size(); i++) {
			TopMutualFundHolder mutualFundHolder = new TopMutualFundHolder();
			mutualFundHolder.Holder = Xsoup.compile("tr/td[1]").evaluate(institutionalHoldersSection.get(i)).getElements().get(0).text();
			mutualFundHolder.Shares = Xsoup.compile("tr/td[2]").evaluate(institutionalHoldersSection.get(i)).getElements().get(0).text();
			mutualFundHolder.Date_Reported = Xsoup.compile("tr/td[3]").evaluate(institutionalHoldersSection.get(i)).getElements().get(0).text();
			mutualFundHolder.Percentage = Xsoup.compile("tr/td[4]").evaluate(institutionalHoldersSection.get(i)).getElements().get(0).text();
			mutualFundHolder.Value = Xsoup.compile("tr/td[5]").evaluate(institutionalHoldersSection.get(i)).getElements().get(0).text();
			mutualFundHolders.add(mutualFundHolder);
		}
		
		return mutualFundHolders;
	}

}
