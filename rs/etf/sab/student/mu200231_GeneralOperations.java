package rs.etf.sab.student;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;

import rs.etf.sab.operations.GeneralOperations;

public class mu200231_GeneralOperations implements GeneralOperations {
	private static Calendar a=null;
	private Connection connection=DB.getInstance().getConnection();
	@Override
	public void setInitialTime(Calendar p0) {
		
		 a = Calendar.getInstance();
	        a.clear();
	        a.set(p0.get(Calendar.YEAR), p0.get(Calendar.MONTH), p0.get(Calendar.DATE));
		
	}

	@Override
	public Calendar time(int p0) {
		a.add(Calendar.DATE, p0);
		ResultSet rs1 = null;
		try(	
				PreparedStatement ps2=connection.prepareStatement("Update Porudzbina set status='received' where Datediff(day,DatumPrimljeno,?)>0");
				
				
				){
					//ps2.setInt(1, p0);
//					ps2.setInt(3, p0);
					ps2.setDate(1,  new Date(new mu200231_GeneralOperations().getCurrentTime().getTimeInMillis()));
					ps2.executeUpdate();
					//BigDecimal b=getFinalPrice(p0);
					//if(rs1.next()) {BigDecimal q=rs1.getBigDecimal(1);q=q.subtract(b);System.out.println(q);return q;
				}
				catch (Exception e) {e.printStackTrace();}
		
				//return new BigDecimal(0);
	
		return a;
	}

	@Override
	public Calendar getCurrentTime() {
		
		return a;
	}

	@Override
	public void eraseAll() {
		String[] ab={"Transakcija","JePorucen","Artikal","Porudzbina","Prodavnica","Kupac","UdaljenostGradova","Grad"};
		for(int i=0;i<ab.length;i++) {
			ResultSet rs1=null;
			try(
					PreparedStatement ps2=connection.prepareStatement("Delete from "+ab[i]+" where 0=0")
					){
						
						ps2.execute();
						 
					}
					catch (Exception e) {e.printStackTrace();}
			
		}
		
		
	}

}
