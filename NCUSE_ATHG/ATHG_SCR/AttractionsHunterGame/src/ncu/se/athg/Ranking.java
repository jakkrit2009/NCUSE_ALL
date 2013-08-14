package ncu.se.athg;

import android.graphics.drawable.Drawable;


public class Ranking {
	
	private Drawable drawable;
	private String nTeam,sumCity,sumAmount;
	public Drawable getDrawable() {
		return drawable;
	}
	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
	}
	public String getnTeam() {
		return nTeam;
	}
	public void setnTeam(String nTeam) {
		this.nTeam = nTeam;
	}
	public String getSumCity() {
		return sumCity;
	}
	public void setSumCity(String sumCity) {
		this.sumCity = sumCity;
	}
	public String getSumAmount() {
		return sumAmount;
	}
	public void setSumAmount(String sumAmount) {
		this.sumAmount = sumAmount;
	}
	
	
}
