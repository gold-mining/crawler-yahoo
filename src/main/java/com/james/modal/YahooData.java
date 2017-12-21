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
		
		output +=" \t" + "statistics" + "\n";
		output +=" \t" + "\t" + Statistics.getLable() + "\n";
		output +=" \t" + "\t" + statistics + "\n";
		
		output +=" \t" + "major holder" + "\n";
		output +=" \t" + "\t" + MajorHolder.getLable() + "\n";
		output +=" \t" + "\t" + majorHolder + "\n";
		
		if (institutionalHolders.size() > 0) {
			output +=" \t" + "institutional holder" + "\n";
			output +=" \t" + "\t" + TopInstitutionalHolder.getLable() + "\n";
			for (TopInstitutionalHolder institutionalHolder : institutionalHolders) output +=" \t" + "\t" + institutionalHolder + "\n";
		}
		
		if (mutualFundHolders.size() > 0) {
			output +=" \t" + "mutual fund holder" + "\n";
			output +=" \t" + "\t" + TopMutualFundHolder.getLable() + "\n";
			for (TopMutualFundHolder mutualFundHolder : mutualFundHolders) output +=" \t" + "\t" + mutualFundHolder + "\n";
		}
		
		return output;
	}
}
