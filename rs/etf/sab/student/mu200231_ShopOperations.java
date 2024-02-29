package rs.etf.sab.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import rs.etf.sab.operations.ShopOperations;

public class mu200231_ShopOperations implements ShopOperations {
	private Connection connection=DB.getInstance().getConnection();
	@Override
	public int createShop(String p0, String p1) {ResultSet rs1 = null;
		try(
				PreparedStatement ps2=connection.prepareStatement("select * from Prodavnica where Naziv=?")
				){
					ps2.setString(1, p0);
					
					 rs1=ps2.executeQuery();
					if(rs1.next())return -1;
					
				}
				catch (Exception e) {e.printStackTrace();}
		
		try(
				PreparedStatement ps=connection.prepareStatement("INSERT INTO Prodavnica values(?,0,?)");
						PreparedStatement ps1=connection.prepareStatement("select * from grad where nazivGrada=?");
				){
					ps1.setString(1, p1);
					 rs1=ps1.executeQuery();
					if(rs1.next())ps.setInt(1, rs1.getInt(1));
					else return -1;
					ps.setString(2, p0);
					
					
					ps.execute();
					
				}
				catch (Exception e) {e.printStackTrace();}
		try(PreparedStatement ps1=connection.prepareStatement("select * from grad where nazivGrada=?");
				PreparedStatement ps=connection.prepareStatement("select * from Prodavnica where Naziv=? and IdGrad=?")
				){	ps1.setString(1, p1);
					rs1=ps1.executeQuery();
					rs1.next();
					ps.setString(1, p0);
					ps.setInt(2, rs1.getInt(1));
					ResultSet rs=ps.executeQuery();
					if(rs.next()) {return rs.getInt(1);}
				}
				catch (Exception e) {e.printStackTrace();}
				return -1;
	}

	@Override
	public int setCity(int p0, String p1) {
		ResultSet rs1=null;
		try(
				PreparedStatement ps2=connection.prepareStatement("select * from Grad where NazivGrada=?")
				){
					ps2.setString(1, p1);
					
					 rs1=ps2.executeQuery();
					if(!rs1.next())return -1;
					
				}
				catch (Exception e) {e.printStackTrace();}
		
		try(PreparedStatement ps2=connection.prepareStatement("select * from Grad where NazivGrada=?");
				PreparedStatement ps=connection.prepareStatement("Update Prodavnica set IdGrad=? where idProd=?");
						
				){ps2.setString(1, p1);
				
				 rs1=ps2.executeQuery();
					rs1.next();
					ps.setInt(1, rs1.getInt(1));
					ps.setInt(2, p0);
					
					
					ps.executeUpdate();
					return 1;
					
				}
				catch (Exception e) {e.printStackTrace();}
		return -1;
	}

	@Override
	public int getCity(int p0) {
		ResultSet rs1=null;
		try(
				PreparedStatement ps=connection.prepareStatement("select idgrad from Prodavnica where IDprod=?")
				){
					ps.setInt(1, p0);
					
					ResultSet rs=ps.executeQuery();
					if(rs.next()) {return rs.getInt(1);}
				}
				catch (Exception e) {e.printStackTrace();}
				return -1;}

	@Override
	public int setDiscount(int p0, int p1) {ResultSet rs1=null;
		try(
				PreparedStatement ps=connection.prepareStatement("Update Prodavnica set Popust=? where idProd=?");
						
				){
					
					ps.setInt(1, p1);
					ps.setInt(2, p0);
					
					
					ps.executeUpdate();
					return 1;
					
				}
				catch (Exception e) {e.printStackTrace();}
		return -1;
	}
	

	@Override
	public int increaseArticleCount(int p0, int p1) {ResultSet rs1=null;
		try(
				PreparedStatement ps=connection.prepareStatement("Update Artikal set kolicina=kolicina+? where idProizvod=?");
						
				){
					ps.setInt(2, p0);
					ps.setInt(1, p1);
					ps.executeUpdate();
					
					
				}
				catch (Exception e) {e.printStackTrace();}
		return -1;
		}

	@Override
	public int getArticleCount(int p0) {ResultSet rs1=null;
		try(
				PreparedStatement ps=connection.prepareStatement("select Kolicina from Artikal where IdProizvod=?");
						
				){
					
					ps.setInt(1, p0);
					rs1=ps.executeQuery();
					if(rs1.next())return rs1.getInt(1);
					
				}
				catch (Exception e) {e.printStackTrace();}
		return -1;
		}


	@Override
	public List<Integer> getArticles(int p0) {
		List a = new ArrayList<Integer>();
		try(
				PreparedStatement ps=connection.prepareStatement("select idProizvod from artikal where idProd=?")
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
	public int getDiscount(int p0) {ResultSet rs1=null;
		try(
				PreparedStatement ps=connection.prepareStatement("select Popust from Prodavnica where IdProd=?");
						
				){
					
					ps.setInt(1, p0);
					rs1=ps.executeQuery();
					if(rs1.next())return rs1.getInt(1);
					
				}
				catch (Exception e) {e.printStackTrace();}
		return -1;
	}

}
