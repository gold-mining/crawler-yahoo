package com.james.modal;

public class MajorHolder {
	public String Insider_Shares;
	public String Institutions_Shares;
	public String Institutions_Float;
	public String Institutions_Number;
	
	@Override
	public String toString() {
		return Insider_Shares + "\t" + Institutions_Shares + "\t" + Institutions_Float + "\t" + Institutions_Number;
	}
	
	public static String getLable() {
		return "Insider_Shares" + "\t" + "Institutions_Shares" + "\t" + "Institutions_Float" + "\t" + "Institutions_Number";
	}
}
