package rs.etf.sab.student;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import rs.etf.sab.operations.TransactionOperations;

public class mu200231_TransactionOperations implements TransactionOperations{
	private Connection connection=DB.getInstance().getConnection();

	@Override
	public BigDecimal getBuyerTransactionsAmmount(int p0) {
		ResultSet rs1 = null;
		try(	
				PreparedStatement ps2=connection.prepareStatement("select sum(vrednost) from Transakcija where idKupac=?");
				
				
				){
					ps2.setInt(1, p0);
//					ps2.setInt(3, p0);
//					ps2.setDate(2,  new Date(new mu200231_GeneralOperations().getCurrentTime().getTimeInMillis()));
					rs1=ps2.executeQuery();
		
					if(rs1.next()) {BigDecimal q=rs1.getBigDecimal(1);System.out.println(q);return q;
				}}
				catch (Exception e) {e.printStackTrace();}
		
				return new BigDecimal(0);
	
	}

	@Override
	public BigDecimal getShopTransactionsAmmount(int p0) {
		ResultSet rs1 = null;ResultSet rs2 = null;ResultSet rs3 = null;
		try(	PreparedStatement ps1=connection.prepareStatement("select count(*) from Transakcija where idprod=? and status='received'");
				PreparedStatement ps2=connection.prepareStatement("select sum(vrednost*(100-popust)*0.01) from Transakcija where idprod=? and status='received'");
				PreparedStatement ps3=connection.prepareStatement("select sum(vrednost) from Transakcija where idprod!=? and status='received' and DATEDIFF(day,datum,?)<=30 and DATEDIFF(day,datum,?)>0");

				
				){	ps1.setInt(1, p0);
				rs2=ps1.executeQuery();
				if(rs2.next()) {if(rs2.getInt(1)>0) {
					ps2.setInt(1, p0);
					ps3.setInt(1, p0);
//					ps2.setInt(3, p0);
					ps3.setDate(2,  new Date(new mu200231_GeneralOperations().getCurrentTime().getTimeInMillis()));
					ps3.setDate(3,  new Date(new mu200231_GeneralOperations().getCurrentTime().getTimeInMillis()));
					rs1=ps2.executeQuery();
					rs3=ps3.executeQuery();
					//if(rs3.next())if(rs3.)
					if(rs1.next()) {BigDecimal q=rs1.getBigDecimal(1);
					rs3.next();
					//System.out.println("SvePrethodno :"+rs3.getBigDecimal(1).setScale(3));
					//System.out.println(q);
					q=q.setScale(3);
					q=q.setScale(3);
					return q;
				}}else return new BigDecimal(0).setScale(3);}}
				catch (Exception e) {e.printStackTrace();}
		
				return new BigDecimal(0).setScale(3);
	}

	@Override
	public List<Integer> getTransationsForBuyer(int p0) {
		List a = new ArrayList<Integer>();
		try(
				PreparedStatement ps=connection.prepareStatement("select * from Transakcija where idKupac=?")
				){
					ps.setInt(1, p0);
					ResultSet rs=ps.executeQuery();
					while(rs.next()) {
						a.add(rs.getInt(1));
					}
					
					return a;
					
				}
				catch (Exception e) {e.printStackTrace();}
				return null;
	}

	@Override
	public int getTransactionForBuyersOrder(int p0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTransactionForShopAndOrder(int p0, int p1) {
		ResultSet rs1 = null;
		try(	
				PreparedStatement ps2=connection.prepareStatement("select * from Transakcija  where idprod=? and idporudzbina=?");
				//PreparedStatement ps1=connection.prepareStatement("select count(Datum) from porudzbina  where idporudzbina=? and status='sent'");
				
				){	
					ps2.setInt(2, p0);
					ps2.setInt(1, p1);
					rs1=ps2.executeQuery();
					
					//ps1.setInt(1, p0);
					//ResultSet rs2=ps1.executeQuery();
					//if(rs2.next()) {if(rs2.getInt(1)>0) {
					if(rs1.next()) {int a=rs1.getInt(1);return a;}
				}
				catch (Exception e) {e.printStackTrace();}
				System.out.println("Nema vreme"+p0);
				return -1;
	}

	@Override
	public List<Integer> getTransationsForShop(int p0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Calendar getTimeOfExecution(int p0) {
		ResultSet rs1 = null;
		try(	
				PreparedStatement ps2=connection.prepareStatement("select p.DatumPoslato,p.DatumPrimljeno,'nesto'=case when t.status='received' then 1 else 0 end from Transakcija t join porudzbina p on p.idporudzbina=t.idporudzbina where idTransakcija=?");
				//PreparedStatement ps1=connection.prepareStatement("select count(Datum) from porudzbina  where idporudzbina=? and status='sent'");
				
				){	//System.out.println("Pokusavam vreme"+p0);
					ps2.setInt(1, p0);
					rs1=ps2.executeQuery();
					Calendar cal =new GregorianCalendar();
					
					if(rs1.next()) {
						int q=rs1.getInt(3);
						//System.out.println(q+""+"sent");
					if(q==1) {
						cal.setTime((rs1.getDate(2)));return cal;
						}
							
					else {
						cal.setTime(rs1.getDate(1));return cal;
					}
						
					}
					
		}
				catch (Exception e) {e.printStackTrace();}
				System.out.println("Nema vreme"+p0);
				return null;
	}

	@Override
	public BigDecimal getAmmountThatBuyerPayedForOrder(int p0) {
		ResultSet rs1 = null;ResultSet rs2 = null;

		try(	PreparedStatement ps1=connection.prepareStatement("select sum(vrednost) from Transakcija where idporudzbina=?");
				PreparedStatement ps2=connection.prepareStatement("select sum(vrednost*popust*0.01) from Transakcija where status='received'");
				){	
				ps1.setInt(1, p0);
				rs2=ps1.executeQuery();
				if(rs2.next()) {					
//					ps2.setInt(3, p0);
//					ps2.setDate(2,  new Date(new mu200231_GeneralOperations().getCurrentTime().getTimeInMillis()));
					//rs1=ps2.executeQuery();
		
					BigDecimal q=rs2.getBigDecimal(1);q=q.setScale(3);return q;
				}else return new BigDecimal(0).setScale(3);}
				catch (Exception e) {e.printStackTrace();}
		
				return new BigDecimal(0);
	}

	@Override
	public BigDecimal getAmmountThatShopRecievedForOrder(int p0, int p1) {
		ResultSet rs1 = null;ResultSet rs2 = null;

		try(	PreparedStatement ps1=connection.prepareStatement("select vrednost*(100-popust)*0.01 from Transakcija where idporudzbina=? and idprod=?");
				PreparedStatement ps2=connection.prepareStatement("select sum(vrednost*popust*0.01) from Transakcija where status='received'");
				){	
				ps1.setInt(2, p0);
				ps1.setInt(1, p1);
				rs2=ps1.executeQuery();
				if(rs2.next()) {					
//					ps2.setInt(3, p0);
//					ps2.setDate(2,  new Date(new mu200231_GeneralOperations().getCurrentTime().getTimeInMillis()));
					//rs1=ps2.executeQuery();
		
					BigDecimal q=rs2.getBigDecimal(1);q=q.setScale(3);return q;
				}else return new BigDecimal(0).setScale(3);}
				catch (Exception e) {e.printStackTrace();}
		
				return new BigDecimal(0);
		
	}

	@Override
	public BigDecimal getTransactionAmount(int p0) {
		ResultSet rs1 = null;ResultSet rs2 = null;

		try(	PreparedStatement ps1=connection.prepareStatement("select vrednost from Transakcija where idTransakcija=?");
				PreparedStatement ps2=connection.prepareStatement("select sum(vrednost*popust*0.01) from Transakcija where status='received'");
				){	
				ps1.setInt(1, p0);
				rs2=ps1.executeQuery();
				if(rs2.next()) {					
//					ps2.setInt(3, p0);
//					ps2.setDate(2,  new Date(new mu200231_GeneralOperations().getCurrentTime().getTimeInMillis()));
					//rs1=ps2.executeQuery();
		
					BigDecimal q=rs2.getBigDecimal(1);q=q.setScale(3);return q;
				}else return new BigDecimal(0).setScale(3);}
				catch (Exception e) {e.printStackTrace();}
		
				return new BigDecimal(0);
	}

	@Override
	public BigDecimal getSystemProfit() {
		ResultSet rs1 = null;ResultSet rs2 = null;

		try(	PreparedStatement ps1=connection.prepareStatement("select count(*) from Transakcija where status='received'");
				PreparedStatement ps2=connection.prepareStatement("select sum(vrednost*popust*0.01) from Transakcija where status='received'");
				){	
				rs2=ps1.executeQuery();
				if(rs2.next()) {if(rs2.getInt(1)>0) {
					
//					ps2.setInt(3, p0);
//					ps2.setDate(2,  new Date(new mu200231_GeneralOperations().getCurrentTime().getTimeInMillis()));
					rs1=ps2.executeQuery();
		
					if(rs1.next()) {BigDecimal q=rs1.getBigDecimal(1);q=q.setScale(3);return q;
				}}else return new BigDecimal(0).setScale(3);}}
				catch (Exception e) {e.printStackTrace();}
		
				return new BigDecimal(0);
	}

}
