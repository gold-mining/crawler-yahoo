package com.james.modal;

import java.util.List;

public class YahooData {
	public String ticker;
	public Statistics statistics;
	public MajorHolder majorHolder;
	public List<TopInstitutionalHolder> institutionalHolders;
	public List<TopMutualFundHolder> mutualFundHolders;
	
	@Override
	public String toString() {
		String output = "";
		
		output += this.ticker + "\n";
		
		output +=" \t" + "statistics";
		output +=" \t" + "\t" + Statistics.getLable();
		output +=" \t" + "\t" + statistics;
		
		output +=" \t" + "major holder";
		output +=" \t" + "\t" + MajorHolder.getLable();
		output +=" \t" + "\t" + majorHolder;
		
		if (institutionalHolders.size() > 0) {
			output +=" \t" + "institutional holder";
			output +=" \t" + "\t" + TopInstitutionalHolder.getLable();
			for (TopInstitutionalHolder institutionalHolder : institutionalHolders) output +=" \t" + "\t" + institutionalHolder;
		}
		
		if (mutualFundHolders.size() > 0) {
			output +=" \t" + "mutual fund holder";
			output +=" \t" + "\t" + TopMutualFundHolder.getLable();
			for (TopMutualFundHolder mutualFundHolder : mutualFundHolders) output +=" \t" + "\t" + mutualFundHolder;
		}
		
		return output;
	}
}
