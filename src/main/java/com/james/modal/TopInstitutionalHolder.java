package com.james.modal;

public class TopInstitutionalHolder {
	public String Holder;
	public String Shares;
	public String Date_Reported;
	public String Percentage;
	public String Value;
	
	@Override
	public String toString() {
		return Holder + "\t" + Shares + "\t" + Date_Reported + "\t" + Percentage + "\t" + Value;
	}
	
	public static String getLable() {
		return "Holder" + "\t" + "Shares" + "\t" + "Date_Reported" + "\t" + "Percentage" + "\t" + "Value";
	}
}
