package rs.etf.sab.student;

import java.sql.*;

import rs.etf.sab.operations.ArticleOperations;

public class mu200231_ArticleOperations implements ArticleOperations {
	private Connection connection=DB.getInstance().getConnection();
	@Override
	public int createArticle(int p0, String p1, int p2) {
		try(
		PreparedStatement ps=connection.prepareStatement("INSERT INTO artikal(IdProd,Naziv,Cena,kolicina) values(?,?,?,0)");
		PreparedStatement ps1=connection.prepareStatement("Select * from artikal where idprod=? and Naziv=? and cena=?")
		){
			ps.setInt(1, p0);
			ps.setString(2, p1);
			ps.setInt(3, p2);

			ps1.setInt(1, p0);
			ps1.setString(2, p1);
			ps1.setInt(3, p2);
			ps.execute();
			ResultSet rs =ps1.executeQuery();
			if(rs.next())return rs.getInt(1);
			
		}
		catch (Exception e) {e.printStackTrace();}
		return -1;
	}
	public static void main( String args[]) {
		ArticleOperations a=new mu200231_ArticleOperations();
		a.createArticle(0, "SMOKI", 60);
	}
}
