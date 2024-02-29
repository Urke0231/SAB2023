package rs.etf.sab.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import rs.etf.sab.operations.ArticleOperations;
import rs.etf.sab.operations.CityOperations;
import rs.etf.sab.operations.ShopOperations;

import java.util.*;
public class mu200231_CityOperations implements CityOperations {
	private Connection connection=DB.getInstance().getConnection();
	public class Pomocni{
		@Override
		public String toString() {
			return "Pomocni [pocetni=" + pocetni + ", krajnji=" + krajnji + ", daljina=" + daljina + ", dubina="
					+ dubina + ", prethodniPut=" + prethodniPut + "]";
		}
		public int pocetni,krajnji,daljina,dubina;public String prethodniPut;
		public Pomocni(int a,int b,int c,int d,String e) {
			 this.pocetni=a;
			 this.krajnji=b;
			 daljina=c;
			 dubina=d;
			 prethodniPut=e;
		}
	}
	@Override
	
	public int createCity(String p0) {
		try(
				PreparedStatement ps2=connection.prepareStatement("select * from Grad where NazivGrada=?")
				){	System.out.println("Usao "+p0);
					ps2.setString(1, p0);
					
					ResultSet rs1=ps2.executeQuery();
					if(rs1.next()) {
						System.out.println("Vec Postoji "+p0); 
						return -1;}
					
				}
				catch (Exception e) {e.printStackTrace();}
		try(
				PreparedStatement ps=connection.prepareStatement("INSERT INTO Grad values(?,?)");
				PreparedStatement ps1=connection.prepareStatement("select max(IdGrad) from Grad")
				){	System.out.println("Usao1 "+p0);
					ResultSet rs=ps1.executeQuery();
					
					ps.setString(2, p0);
					if(rs.next() && rs.getInt(1)!=0) {
					ps.setInt(1, rs.getInt(1)+1);
					}else{
						ps.setInt(1, 1);
						
					}ps.execute();
					
				}
				catch (Exception e) {e.printStackTrace();}
		try(
				PreparedStatement ps=connection.prepareStatement("select * from Grad where NazivGrada=?")
				){
					ps.setString(1, p0);
					
					ResultSet rs=ps.executeQuery();
					if(rs.next()) {return rs.getInt(1);}
				}
				catch (Exception e) {e.printStackTrace();}
				return -1;
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
				PreparedStatement ps=connection.prepareStatement("select IDUdaljenost,IdGrad,IdGrad2,Udaljenost from UdaljenostGradova where ((IdGrad=? and IdGrad2=?) or (IdGrad2=? and IdGrad=?))")
				){
					ps.setInt(1, p0);
					ps.setInt(2, p1);
					ps.setInt(3, p0);
					ps.setInt(4, p1);
					ResultSet rs=ps.executeQuery();
					if(rs.next()) {
						System.out.println("Vec Postoji "+p0+":"+p1+" sa razdaljinom"+rs.getInt(4)); 
					return -1;}
					
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
						PreparedStatement ps=connection.prepareStatement("select * from UdaljenostGradova where IdGrad=? and IdGrad2=?")
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
				PreparedStatement ps=connection.prepareStatement("select * from UdaljenostGradova where idGrad=? or idGrad2=?")
				){	ps.setInt(1, p0);
					ps.setInt(2, p0);
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
						a.add(rs.getInt(1));
					}
					
					return a;
					
				}
				catch (Exception e) {e.printStackTrace();}
				return null;
	}
	public Pomocni funkcija(int pocetni,int krajnji) {
		if(pocetni==krajnji) {return new Pomocni(pocetni,pocetni,0,0,""+pocetni);}
		int minDaljina=100000000;
		Queue<Pomocni> q=new LinkedList<Pomocni>();
		int x=pocetni;
		List<Integer> z=getConnectedCities(pocetni);
		for(int i=0;i<getConnectedCities(pocetni).size();i++) {
			try(
					PreparedStatement ps=connection.prepareStatement("select IdGrad,IdGrad2,Udaljenost from UdaljenostGradova where ((IdGrad=? and IdGrad2=?) or (IdGrad2=? and IdGrad=?))")
					){
						ps.setInt(1, x);
						ps.setInt(2, z.get(i));
						ps.setInt(3, x);
						ps.setInt(4, z.get(i));
						ResultSet rs=ps.executeQuery();
						if(rs.next()) {
							{
								if(rs.getInt(1)==x) {q.add(new Pomocni(rs.getInt(1),rs.getInt(2),rs.getInt(3),0,""+rs.getInt(1)+"-"+rs.getInt(2)));}
								else {q.add(new Pomocni(rs.getInt(2),rs.getInt(1),rs.getInt(3),0,""+rs.getInt(2)+"-"+rs.getInt(1)));}
							}
							}
							
						
					}
					catch (Exception e) {e.printStackTrace();}
		}
		Queue<Pomocni> resenje=new LinkedList<Pomocni>();
		while(!q.isEmpty()) {
			Pomocni p=q.poll();
			if(p.dubina>getCities().size())break;
			if(pocetni==p.pocetni && p.krajnji==krajnji) {
				
				resenje.add(p);
				
				Iterator<Pomocni> itr=q.iterator();
				while(itr.hasNext()) {
					Pomocni r=itr.next();
					if(r.daljina>p.daljina) {
						itr.remove();
					}
				}
				continue;
			}
			x=p.krajnji;
			z=getConnectedCities(x);
			for(int i=0;i<getConnectedCities(x).size();i++) {
				try(
						PreparedStatement ps=connection.prepareStatement("select IdGrad,IdGrad2,Udaljenost from UdaljenostGradova where ((IdGrad=? and IdGrad2=?) or (IdGrad2=? and IdGrad=?))")
						){
							ps.setInt(1, x);
							ps.setInt(2, z.get(i));
							ps.setInt(3, x);
							ps.setInt(4, z.get(i));
							ResultSet rs=ps.executeQuery();
							if(rs.next()) {
								{
									if(rs.getInt(1)==x) {q.add(new Pomocni(p.pocetni,rs.getInt(2),p.daljina+rs.getInt(3),p.dubina+1,p.prethodniPut+"-"+rs.getInt(2)));}
									else {q.add(new Pomocni(p.pocetni,rs.getInt(1),p.daljina+rs.getInt(3),p.dubina+1,p.prethodniPut+"-"+rs.getInt(1)));}
								}
								}
								
							
						}
						catch (Exception e) {e.printStackTrace();}
			}
			//System.out.println(p);
		}
		Pomocni zaReturn=null;
		for(int i=0;i<resenje.size();i++) {
			
			
			Pomocni p=resenje.poll();
			if(p.daljina<minDaljina) {
				minDaljina=p.daljina;
				zaReturn=p;
			}
			//System.out.println(p);
			//if(p.daljina==minDaljina)return p;
		}
		return zaReturn; 
	}
	public static void main( String args[]) {
		mu200231_CityOperations a=new mu200231_CityOperations();
		ShopOperations b=new mu200231_ShopOperations();
		//System.out.println(a.createCity("A"));
		a.createCity("A");//1
		a.createCity("B");
		a.createCity("C1");//3
		a.createCity("C2");
		a.createCity("C3");//5
		a.createCity("C4");
		a.createCity("C5");//7
		a.connectCities(2, 7, 2);
		a.connectCities(2, 3, 8);
		a.connectCities(3, 1, 10);
		a.connectCities(7, 1, 15);
		a.connectCities(1, 4, 3);
		a.connectCities(1, 6, 3);
		a.connectCities(6, 5, 1);
		a.connectCities(4, 5, 2);
		a.funkcija(1,5);
		List<Integer> be=a.getCities();
		for(int i=0;i<be.size();i++) {
			System.out.println("Putevi od 1 do "+a.getShops(be.get(i)));
			System.out.println("Pobednik je "+a.funkcija(1, be.get(i)));
			System.out.println("\n");
		}
	}

}
