package com.james.crawler;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.james.modal.Statistics;

import us.codecraft.xsoup.Xsoup;

public class StatisticsCrawler {
		
	public Statistics getStatistics(String ticker) {
		try {
			Document document = Jsoup.connect("https://finance.yahoo.com/quote/" + ticker + "/key-statistics").get();
			
			Statistics statistics = new Statistics();
			getValuationMeasures(document, statistics);
			getFinancialHighlights(document, statistics);
			getTradingInformation(document, statistics);
			
			return statistics;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private void getValuationMeasures(Document document, Statistics statistics) {
		String valuationMeasuresXPath = "//*[@id=\"Col1-0-KeyStatistics-Proxy\"]/section/div[2]/div[1]/div[1]/div/table/tbody";
		Element valuationMeasuresSection = Xsoup.compile(valuationMeasuresXPath).evaluate(document).getElements().get(0);
		
		statistics.Market_Cap = Xsoup.compile("tbody/tr[1]/td[2]").evaluate(valuationMeasuresSection).getElements().get(0).text();
		statistics.Enterprise_Value = Xsoup.compile("tbody/tr[2]/td[2]").evaluate(valuationMeasuresSection).getElements().get(0).text();
		statistics.Trailing_P_E = Xsoup.compile("tbody/tr[3]/td[2]").evaluate(valuationMeasuresSection).getElements().get(0).text();
		statistics.Forward_P_E = Xsoup.compile("tbody/tr[4]/td[2]").evaluate(valuationMeasuresSection).getElements().get(0).text();
		statistics.PEG_Ratio = Xsoup.compile("tbody/tr[5]/td[2]").evaluate(valuationMeasuresSection).getElements().get(0).text();
		statistics.Price_Sales = Xsoup.compile("tbody/tr[6]/td[2]").evaluate(valuationMeasuresSection).getElements().get(0).text();
		statistics.Price_Book = Xsoup.compile("tbody/tr[7]/td[2]").evaluate(valuationMeasuresSection).getElements().get(0).text();
		statistics.Enterprise_Value_Revenue = Xsoup.compile("tbody/tr[8]/td[2]").evaluate(valuationMeasuresSection).getElements().get(0).text();
		statistics.Enterprise_Value_EBITDA = Xsoup.compile("tbody/tr[9]/td[2]").evaluate(valuationMeasuresSection).getElements().get(0).text();
	}
	
	private void getFinancialHighlights(Document document, Statistics statistics) {
		String financialHighlightsXPath = "//*[@id=\"Col1-0-KeyStatistics-Proxy\"]/section/div[2]/div[1]/div[2]";
		Element financialHighlightsSection = Xsoup.compile(financialHighlightsXPath).evaluate(document).getElements().get(0);
		
		// Fiscal Year
		statistics.Fiscal_Year_Ends = Xsoup.compile("div/div[1]/table/tbody/tr[1]/td[2]").evaluate(financialHighlightsSection).getElements().get(0).text();
		statistics.Most_Recent_Quarter = Xsoup.compile("div/div[1]/table/tbody/tr[2]/td[2]").evaluate(financialHighlightsSection).getElements().get(0).text();
		
		// Profitability
		statistics.Profit_Margin = Xsoup.compile("div/div[2]/table/tbody/tr[1]/td[2]").evaluate(financialHighlightsSection).getElements().get(0).text();
		statistics.Operating_Margin = Xsoup.compile("div/div[2]/table/tbody/tr[2]/td[2]").evaluate(financialHighlightsSection).getElements().get(0).text();
				
		// Management Effectiveness
		statistics.Return_on_Assets = Xsoup.compile("div/div[3]/table/tbody/tr[1]/td[2]").evaluate(financialHighlightsSection).getElements().get(0).text();
		statistics.Return_on_Equity = Xsoup.compile("div/div[3]/table/tbody/tr[2]/td[2]").evaluate(financialHighlightsSection).getElements().get(0).text();
		
		// Income Statement
		statistics.Revenue = Xsoup.compile("div/div[4]/table/tbody/tr[1]/td[2]").evaluate(financialHighlightsSection).getElements().get(0).text();
		statistics.Revenue_Per_Share = Xsoup.compile("div/div[4]/table/tbody/tr[2]/td[2]").evaluate(financialHighlightsSection).getElements().get(0).text();
		statistics.Quarterly_Revenue_Growth = Xsoup.compile("div/div[4]/table/tbody/tr[3]/td[2]").evaluate(financialHighlightsSection).getElements().get(0).text();
		statistics.Gross_Profit = Xsoup.compile("div/div[4]/table/tbody/tr[4]/td[2]").evaluate(financialHighlightsSection).getElements().get(0).text();
		statistics.EBITDA = Xsoup.compile("div/div[4]/table/tbody/tr[5]/td[2]").evaluate(financialHighlightsSection).getElements().get(0).text();
		statistics.Net_Income_Avi_to_Common = Xsoup.compile("div/div[4]/table/tbody/tr[6]/td[2]").evaluate(financialHighlightsSection).getElements().get(0).text();
		statistics.Diluted_EPS = Xsoup.compile("div/div[4]/table/tbody/tr[7]/td[2]").evaluate(financialHighlightsSection).getElements().get(0).text();
		statistics.Quarterly_Earnings_Growth = Xsoup.compile("div/div[4]/table/tbody/tr[8]/td[2]").evaluate(financialHighlightsSection).getElements().get(0).text();
		
		// Balance Sheet
		statistics.Total_Cash = Xsoup.compile("div/div[5]/table/tbody/tr[1]/td[2]").evaluate(financialHighlightsSection).getElements().get(0).text();
		statistics.Total_Cash_Per_Share = Xsoup.compile("div/div[5]/table/tbody/tr[2]/td[2]").evaluate(financialHighlightsSection).getElements().get(0).text();
		statistics.Total_Debt = Xsoup.compile("div/div[5]/table/tbody/tr[3]/td[2]").evaluate(financialHighlightsSection).getElements().get(0).text();
		statistics.Total_Debt_Equity = Xsoup.compile("div/div[5]/table/tbody/tr[4]/td[2]").evaluate(financialHighlightsSection).getElements().get(0).text();
		statistics.Current_Ratio = Xsoup.compile("div/div[5]/table/tbody/tr[5]/td[2]").evaluate(financialHighlightsSection).getElements().get(0).text();
		statistics.Book_Value_Per_Share = Xsoup.compile("div/div[5]/table/tbody/tr[6]/td[2]").evaluate(financialHighlightsSection).getElements().get(0).text();
		
		// Cash Flow Statement
		statistics.Operating_Cash_Flow = Xsoup.compile("div/div[6]/table/tbody/tr[1]/td[2]").evaluate(financialHighlightsSection).getElements().get(0).text();
		statistics.Levered_Free_Cash_Flow = Xsoup.compile("div/div[6]/table/tbody/tr[2]/td[2]").evaluate(financialHighlightsSection).getElements().get(0).text();
	}
	
	private void getTradingInformation(Document document, Statistics statistics) {
		String tradingInformationXPath = "//*[@id=\"Col1-0-KeyStatistics-Proxy\"]/section/div[2]/div[2]/div";
		Element tradingInformationSection = Xsoup.compile(tradingInformationXPath).evaluate(document).getElements().get(0);
		
		// Stock Price History
		statistics.Beta = Xsoup.compile("div/div[1]/table/tbody/tr[1]/td[2]").evaluate(tradingInformationSection).getElements().get(0).text();
		statistics.Change_52_Week = Xsoup.compile("div/div[1]/table/tbody/tr[2]/td[2]").evaluate(tradingInformationSection).getElements().get(0).text();
		statistics.SP500_Change_52_Week = Xsoup.compile("div/div[1]/table/tbody/tr[3]/td[2]").evaluate(tradingInformationSection).getElements().get(0).text();
		statistics.Week_High_52 = Xsoup.compile("div/div[1]/table/tbody/tr[4]/td[2]").evaluate(tradingInformationSection).getElements().get(0).text();
		statistics.Week_Low_52 = Xsoup.compile("div/div[1]/table/tbody/tr[5]/td[2]").evaluate(tradingInformationSection).getElements().get(0).text();
		statistics.Moving_Average_50 = Xsoup.compile("div/div[1]/table/tbody/tr[6]/td[2]").evaluate(tradingInformationSection).getElements().get(0).text();
		statistics.Moving_Average_200 = Xsoup.compile("div/div[1]/table/tbody/tr[7]/td[2]").evaluate(tradingInformationSection).getElements().get(0).text();
		
		// Share Statistics
		statistics.Avg_Vol_3_month = Xsoup.compile("div/div[2]/table/tbody/tr[1]/td[2]").evaluate(tradingInformationSection).getElements().get(0).text();
		statistics.Avg_Vol_10_day = Xsoup.compile("div/div[2]/table/tbody/tr[2]/td[2]").evaluate(tradingInformationSection).getElements().get(0).text();
		statistics.Shares_Outstanding = Xsoup.compile("div/div[2]/table/tbody/tr[3]/td[2]").evaluate(tradingInformationSection).getElements().get(0).text();
		statistics.Float = Xsoup.compile("div/div[2]/table/tbody/tr[4]/td[2]").evaluate(tradingInformationSection).getElements().get(0).text();
		statistics.Held_by_Insiders = Xsoup.compile("div/div[2]/table/tbody/tr[5]/td[2]").evaluate(tradingInformationSection).getElements().get(0).text();
		statistics.Held_by_Institutions = Xsoup.compile("div/div[2]/table/tbody/tr[6]/td[2]").evaluate(tradingInformationSection).getElements().get(0).text();
		statistics.Shares_Short = Xsoup.compile("div/div[2]/table/tbody/tr[7]/td[2]").evaluate(tradingInformationSection).getElements().get(0).text();
		statistics.Short_Ratio = Xsoup.compile("div/div[2]/table/tbody/tr[8]/td[2]").evaluate(tradingInformationSection).getElements().get(0).text();
		statistics.Short_Float = Xsoup.compile("div/div[2]/table/tbody/tr[9]/td[2]").evaluate(tradingInformationSection).getElements().get(0).text();
		statistics.Shares_Short_Pre_Month = Xsoup.compile("div/div[2]/table/tbody/tr[10]/td[2]").evaluate(tradingInformationSection).getElements().get(0).text();
		
		//Dividends & Splits
		statistics.Forward_Annual_Dividend_Rate = Xsoup.compile("div/div[3]/table/tbody/tr[1]/td[2]").evaluate(tradingInformationSection).getElements().get(0).text();
		statistics.Forward_Annual_Dividend_Yield = Xsoup.compile("div/div[3]/table/tbody/tr[2]/td[2]").evaluate(tradingInformationSection).getElements().get(0).text();
		statistics.Trailing_Annual_Dividend_Rate = Xsoup.compile("div/div[3]/table/tbody/tr[3]/td[2]").evaluate(tradingInformationSection).getElements().get(0).text();
		statistics.Trailing_Annual_Dividend_Yield = Xsoup.compile("div/div[3]/table/tbody/tr[4]/td[2]").evaluate(tradingInformationSection).getElements().get(0).text();
		statistics.Average_Dividend_Yield_5_Year = Xsoup.compile("div/div[3]/table/tbody/tr[5]/td[2]").evaluate(tradingInformationSection).getElements().get(0).text();
		statistics.Payout_Ratio = Xsoup.compile("div/div[3]/table/tbody/tr[6]/td[2]").evaluate(tradingInformationSection).getElements().get(0).text();
		statistics.Dividend_Date = Xsoup.compile("div/div[3]/table/tbody/tr[7]/td[2]").evaluate(tradingInformationSection).getElements().get(0).text();
		statistics.Ex_Dividend_Date = Xsoup.compile("div/div[3]/table/tbody/tr[8]/td[2]").evaluate(tradingInformationSection).getElements().get(0).text();
		statistics.Last_Split_Factor = Xsoup.compile("div/div[3]/table/tbody/tr[9]/td[2]").evaluate(tradingInformationSection).getElements().get(0).text();
		statistics.Last_Split_Date = Xsoup.compile("div/div[3]/table/tbody/tr[10]/td[2]").evaluate(tradingInformationSection).getElements().get(0).text();
	}
	
}
