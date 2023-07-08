package rs.etf.sab.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import rs.etf.sab.operations.ArticleOperations;
import rs.etf.sab.operations.CityOperations;

public class mu200231_CityOperations implements CityOperations {
	private Connection connection=DB.getInstance().getConnection();
	@Override
	public int createCity(String p0) {
		try(
				PreparedStatement ps2=connection.prepareStatement("select from Grad where Naziv=?")
				){
					ps2.setString(1, p0);
					
					ResultSet rs1=ps2.executeQuery();
					if(rs1.next())return -1;
					
				}
				catch (Exception e) {e.printStackTrace();}
		try(
				PreparedStatement ps=connection.prepareStatement("INSERT INTO Grad values(?)")
				){
					ps.setString(1, p0);
					
					ps.execute();
					
				}
				catch (Exception e) {e.printStackTrace();}
		try(
				PreparedStatement ps=connection.prepareStatement("select * from Grad where NazivGrada=?)")
				){
					ps.setString(1, p0);
					
					ResultSet rs=ps.executeQuery();
					if(rs.next()) {return rs.getInt(1);}
				}
				catch (Exception e) {e.printStackTrace();}
				return 0;
	}

	@Override
	public List<Integer> getCities() {
		List a = new ArrayList<Integer>();
		try(
				PreparedStatement ps=connection.prepareStatement("select idGrad from Grad")
				){
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
	public int connectCities(int p0, int p1, int p2) {
		
		try(
				PreparedStatement ps=connection.prepareStatement("select * UdaljenostGradova where (IdGrad1=? and IdGrad2=?) or (IdGrad2=? and IdGrad1=?) )")
				){
					ps.setInt(1, p0);
					ps.setInt(2, p1);
					ps.setInt(4, p0);
					ps.setInt(3, p1);
					ResultSet rs=ps.executeQuery();
					if(rs.next()) {return -1;}
					
				}
				catch (Exception e) {e.printStackTrace();}
				
		try(
				PreparedStatement ps=connection.prepareStatement("INSERT INTO UdaljenostGradova values(?,?,?)")
				){
					ps.setInt(1, p0);
					ps.setInt(2, p1);
					ps.setInt(3, p2);
					
					ps.execute();
					
				}
				catch (Exception e) {e.printStackTrace();}
				
				try(
						PreparedStatement ps=connection.prepareStatement("select * UdaljenostGradova where IdGrad1=? and IdGrad2=?)")
						){
							ps.setInt(1, p0);
							ps.setInt(2, p1);
							
							ResultSet rs=ps.executeQuery();
							if(rs.next()) {return rs.getInt(1);}
							
						}
						catch (Exception e) {e.printStackTrace();}
				return -1;
	}

	@Override
	public List<Integer> getConnectedCities(int p0) {
		List a = new ArrayList<Integer>();
		try(
				PreparedStatement ps=connection.prepareStatement("select * from UdaljenostGradova where idGrad1=? or idGrad2=?")
				){
					ResultSet rs=ps.executeQuery();
					while(rs.next()) {
						if(rs.getInt(2)==p0) {
						a.add(rs.getInt(3));}
						else {
							a.add(rs.getInt(2));
						}
					}
					
					return a;
					
				}
				catch (Exception e) {e.printStackTrace();}
				return null;
	}

	@Override
	public List<Integer> getShops(int p0) {
		List a = new ArrayList<Integer>();
		try(
				PreparedStatement ps=connection.prepareStatement("select idProd from Prodavnica where idgrad=?")
				){
					ps.setInt(1,p0);
					ResultSet rs=ps.executeQuery();
					while(rs.next()) {
						a.add(rs.getInt(0));
					}
					
					return a;
					
				}
				catch (Exception e) {e.printStackTrace();}
				return null;
	}
	public static void main( String args[]) {
		CityOperations a=new mu200231_CityOperations();
		a.createCity("A");
	}

}
