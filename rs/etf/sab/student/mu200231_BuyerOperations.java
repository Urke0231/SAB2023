package rs.etf.sab.student;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import rs.etf.sab.operations.BuyerOperations;
import rs.etf.sab.operations.GeneralOperations;
import rs.etf.sab.operations.OrderOperations;

public class mu200231_BuyerOperations implements BuyerOperations{
	private Connection connection=DB.getInstance().getConnection();
	@Override
	public int createBuyer(String p0, int p1) {
		
		try(
				PreparedStatement ps=connection.prepareStatement("INSERT INTO Kupac values(?,?,?,?)");
				PreparedStatement ps1=connection.prepareStatement("select max(IdKupac) from Kupac")
				){	//System.out.println("Usao1 "+p0);
					ResultSet rs=ps1.executeQuery();
					
					ps.setString(2, p0);
					ps.setInt(3, 0);
					ps.setInt(4, p1);
					if(rs.next() && rs.getInt(1)!=0) {
					ps.setInt(1, rs.getInt(1)+1);
					}else{
						ps.setInt(1, 1);
						
					}ps.execute();
					
				}
				catch (Exception e) {e.printStackTrace();}
		try(
				PreparedStatement ps=connection.prepareStatement("select * from Kupac where Ime=? and IdGrad=?")
				){
					ps.setString(1, p0);
					ps.setInt(2, p1);
					ResultSet rs=ps.executeQuery();
					if(rs.next()) {return rs.getInt(1);}
				}
				catch (Exception e) {e.printStackTrace();}
				return -1;
	}

	@Override
	public int setCity(int p0, int p1) {
		ResultSet rs1=null;
				
		try(
				PreparedStatement ps=connection.prepareStatement("Update Kupac set IdGrad=? where idKupac=?");
						
				){
					
					ps.setInt(2, p0);
					ps.setInt(1, p1);
					
					
					ps.executeUpdate();
					return 1;
					
				}
				catch (Exception e) {e.printStackTrace();}
		return -1;
	}

	@Override
	public int getCity(int p0) {ResultSet rs1=null;
		try(
				PreparedStatement ps=connection.prepareStatement("Select IdGrad from kupac where IdKupac=?");
						
				){
					
					ps.setInt(1, p0);
					
					
					
					rs1=ps.executeQuery();
					if(rs1.next()) {
						return rs1.getInt(1);
					}
					
					
				}
				catch (Exception e) {e.printStackTrace();}
		return -1;
	}

	@Override
	public BigDecimal increaseCredit(int p0, BigDecimal p1) {
		ResultSet rs1=null;
		
		try(
				PreparedStatement ps=connection.prepareStatement("Update Kupac set Credit=Credit+? where idKupac=?");
				PreparedStatement ps1=connection.prepareStatement("select credit from Kupac where idKupac=?");
						
				){
					
					ps.setInt(2, p0);
					ps.setBigDecimal(1, p1);
					
					
					ps.executeUpdate();
					ps1.setInt(1, p0);
					rs1=ps1.executeQuery();
					if(rs1.next()) {
						return rs1.getBigDecimal(1).setScale(3);
					}
				}
				catch (Exception e) {e.printStackTrace();}
		
		return null;
	}

	@Override
	public int createOrder(int p0) {
		try(
				PreparedStatement ps=connection.prepareStatement("INSERT INTO Porudzbina values(?,?,'created',?,null)");
				PreparedStatement ps1=connection.prepareStatement("select max(IdPorudzbina) from Porudzbina")
				){	//System.out.println("Usao1 "+p0);
					ResultSet rs=ps1.executeQuery();
					
					
					
					ps.setInt(2, p0);
					ps.setDate(3, null);
					if(rs.next() && rs.getInt(1)!=0) {
					ps.setInt(1, rs.getInt(1)+1);
					}else{
						ps.setInt(1, 1);
						
					}ps.execute();
					
				}
				catch (Exception e) {e.printStackTrace();}
		try(
				PreparedStatement ps=connection.prepareStatement("select * from Porudzbina where IdKupac=? order by idPorudzbina desc")
				){
					ps.setInt(1, p0);
					
					ResultSet rs=ps.executeQuery();
					if(rs.next()) {return rs.getInt(1);}
				}
				catch (Exception e) {e.printStackTrace();}
				return -1;
	}

	@Override
	public List<Integer> getOrders(int p0) {
		List a = new ArrayList<Integer>();
		try(
				PreparedStatement ps=connection.prepareStatement("select idPorudzbina from Porudzbina where idKupac=?")
				){	ps.setInt(1, p0);
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
	public BigDecimal getCredit(int p0) {
ResultSet rs1=null;
		
		try(
				//PreparedStatement ps=connection.prepareStatement("Update Kupac set Credit=Credit+? where idKupac=?");
				PreparedStatement ps1=connection.prepareStatement("select credit from Kupac where idKupac=?");
						
				){
					
					
					ps1.setInt(1, p0);
					rs1=ps1.executeQuery();
					if(rs1.next()) {
						return rs1.getBigDecimal(1).setScale(3);
					}
				}
				catch (Exception e) {e.printStackTrace();}
		
		return null;
	}
	public static void main(String[] args) {
	
	}	
}
