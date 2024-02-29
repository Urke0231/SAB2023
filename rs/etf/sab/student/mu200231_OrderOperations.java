package rs.etf.sab.student;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import rs.etf.sab.operations.CityOperations;
import rs.etf.sab.operations.GeneralOperations;
import rs.etf.sab.operations.OrderOperations;
import rs.etf.sab.operations.ShopOperations;
import rs.etf.sab.student.mu200231_CityOperations.Pomocni;

public class mu200231_OrderOperations implements OrderOperations {
	private Connection connection=DB.getInstance().getConnection();
	@Override
	public int addArticle(int p0, int p1, int p2) {
		ResultSet rs1 = null;
		try(
				PreparedStatement ps=connection.prepareStatement("select kolicina from artikal where idProizvod=?");
						
				){
					ps.setInt(1, p1);
					 rs1=ps.executeQuery();
					if(rs1.next()) {
						if(rs1.getInt(1)>=p2) {}
						else return -1;
					}					
				}
				catch (Exception e) {e.printStackTrace();}
		try(	PreparedStatement ps1=connection.prepareStatement("Insert into jePorucen values(?,?,?)");
				PreparedStatement ps2=connection.prepareStatement("select  * from JePorucen where idPorudzbina=? and idProizvod=?");
				
				PreparedStatement ps3=connection.prepareStatement("Update JePorucen set kolicina=kolicina+? where idPorudzbina=? and idproizvod=?");
				PreparedStatement ps4=connection.prepareStatement("Update Artikal set kolicina=kolicina-? where idproizvod=?");
				
				){	//da li vec postoji
					ps2.setInt(1, p0);
					ps2.setInt(2, p1);
					//uvecati jeporucen
					ps3.setInt(1, p2);
					ps3.setInt(2, p0);
					ps3.setInt(3, p1);
					//umanjiti artikal
					ps4.setInt(1, p1);
					ps4.setInt(2, p2);
					
					rs1=ps2.executeQuery();
					if(rs1.next()) {// ako postoji
						ps3.executeUpdate();
						ps4.executeUpdate();
					}
					else {
						ps1.setInt(1, p1);
						ps1.setInt(2, p0);
						ps1.setInt(3, p2);
						ps1.execute();
						rs1=ps2.executeQuery();
						if(rs1.next())return rs1.getInt(1);
					}
					
				}
				catch (Exception e) {e.printStackTrace();}
		
				return -1;
	}

	@Override
	public int removeArticle(int p0, int p1) {
		ResultSet rs1 = null;
		try(	
				PreparedStatement ps2=connection.prepareStatement("select  * from JePorucen where idPorudzbina=? and idArtikal=?");
				
				PreparedStatement ps3=connection.prepareStatement("delete from JePorucen where idPorudzbina=? and idproizvod=?"
						+ "\n go \n Update Artikal set kolicina=kolicina-? where idproizvod=?");
				
				){
					ps2.setInt(1, p0);
					ps2.setInt(2, p1);
					rs1=ps2.executeQuery();
					if(rs1.next()) {
						
						ps3.setInt(1, p0);
						ps3.setInt(2, p1);
						ps3.setInt(4, p1);
						ps3.setInt(3, rs1.getInt(4));
						ps3.execute();
						return 1;
					}
					
					
					
					
					
				}
				catch (Exception e) {e.printStackTrace();}
		
				return -1;
	}

	@Override
	public List<Integer> getItems(int p0) {
		List a = new ArrayList<Integer>();
		try(
				PreparedStatement ps=connection.prepareStatement("select idProizvod from jePorucen where idPorudzbina=?")
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
	public int completeOrder(int p0) {System.out.println("POKUSAVAM DA KOMPLETIRAM"+ p0);
		ResultSet rs1 = null;ResultSet rs2 = null;
		try(	PreparedStatement ps4=connection.prepareStatement("select k.idgrad from porudzbina p join kupac k on p.idkupac=k.idkupac where idporudzbina=?");

				PreparedStatement ps3=connection.prepareStatement("select sum(a.cena*b.kolicina*(1-p.popust*0.01)),a.idprod from Artikal a join jePorucen b on a.idProizvod=b.idProizvod join prodavnica p on p.idprod=a.idprod where b.idPorudzbina=? and a.idProizvod=? group by a.idprod");
				PreparedStatement ps5=connection.prepareStatement("Update Porudzbina set DatumPrimljeno=? where idPorudzbina=?");

				PreparedStatement ps2=connection.prepareStatement("Update Porudzbina set status=?,DatumPoslato=? where idPorudzbina=?");
				PreparedStatement ps1=connection.prepareStatement("Insert into transakcija values(?,(select idKupac from porudzbina where idPorudzbina=?),?,(select idprod from artikal where idproizvod=?),?,'sent',0)");
				
				){  
					
					mu200231_CityOperations q=new mu200231_CityOperations();
					mu200231_ShopOperations shop=new mu200231_ShopOperations();
					ps3.setInt(1, p0);
					List<Integer> a=getItems(p0);int maxDaljina=0;
					for(int j=0;j<a.size();j++) {
						ShopOperations b=new mu200231_ShopOperations();
						mu200231_CityOperations ae=new mu200231_CityOperations();
						
												
						ps4.setInt(1, p0);						
						ps3.setInt(1, p0);
						ps3.setInt(2, a.get(j));
						rs1=ps3.executeQuery();
						rs1.next();
						ps1.setBigDecimal(1, rs1.getBigDecimal(1));
						int grad;rs2=ps4.executeQuery();
						rs2.next();
						grad=rs2.getInt(1);
						
						int gradArtikla=shop.getCity(rs1.getInt(2));
						int MINLENGTH=200000;
						List<Integer> be=ae.getCities();
						for(int i=0;i<be.size();i++) {
							if(be.get(i)==grad)continue;
						System.out.println("U gradu"+be.get(i)+" ima prodavnica:"+!ae.getShops(be.get(i)).isEmpty());
						if(!ae.getShops(be.get(i)).isEmpty()) {
							Pomocni f=ae.funkcija(grad, be.get(i));
							if(f.daljina<MINLENGTH) {
								MINLENGTH=f.daljina;
							}
						}}
						int idGradSaProdavnicom=0;
						Pomocni t=null;
						for(int i=0;i<be.size();i++) {
							if(be.get(i)==grad)continue;
							//System.out.println("U gradu"+be.get(i)+" ima prodavnica:"+!ae.getShops(be.get(i)).isEmpty());
							if(!ae.getShops(be.get(i)).isEmpty()) {
								Pomocni f=ae.funkcija(grad, be.get(i));
								if(f.daljina==MINLENGTH) {
									idGradSaProdavnicom=f.krajnji;t=f;
								}
							}
						}
						System.out.println(t);
						int IdGradCilj=t.krajnji;
						Pomocni Resenje=ae.funkcija(gradArtikla, idGradSaProdavnicom);
						
						System.out.println(Resenje+" Grad PrveDostave:"+idGradSaProdavnicom);
						if(Resenje.daljina+t.daljina>maxDaljina) {maxDaljina=Resenje.daljina+t.daljina;}
						ps2.setString(1, "sent");
						ps2.setInt(3, p0);
						ps2.setDate(2, new Date(new mu200231_GeneralOperations().getCurrentTime().getTimeInMillis()));
						//ps2.setDate(3, new Date(new mu200231_GeneralOperations().getCurrentTime().getTimeInMillis()));
						ps2.executeUpdate();
						
						ps1.setInt(2, p0);
						ps1.setInt(3, p0);
						ps1.setInt(4, a.get(j));
						ps1.setDate(5, new Date(new mu200231_GeneralOperations().getCurrentTime().getTimeInMillis()));
						ps1.execute();
						
					}
					
					ps5.setDate(1,new Date(new mu200231_GeneralOperations().getCurrentTime().getTimeInMillis()+86400000*maxDaljina));
					ps5.setInt(2, p0);
					ps5.executeUpdate();
					
				}
				catch (Exception e) {e.printStackTrace();}
		
				return -1;
	}

	@Override
	public BigDecimal getFinalPrice(int p0) {
		ResultSet rs1 = null;
		try(	CallableStatement cs =connection.prepareCall("{call SP_FINAL_PRICE(?)}");
				PreparedStatement ps2=connection.prepareStatement("select sum(t.vrednost) from transakcija t join prodavnica p on t.idprod=p.idprod where t.idPorudzbina=?");
				
				
				){
//					ps2.setInt(1, p0);
//					rs1=ps2.executeQuery();
					BigDecimal p=new BigDecimal(0);
					cs.setInt(1, p0);//cs.setBigDecimal(2,p);
					rs1=cs.executeQuery();
					if(rs1.next()) {BigDecimal q=rs1.getBigDecimal(1);//.multiply(new BigDecimal(1.05).subtract(getDiscountSum(p0)));
					System.out.println(q.setScale(3));
					return q.setScale(3);}
				}
				catch (Exception e) {e.printStackTrace();}
		
				return null;
	}

	@Override
	public BigDecimal getDiscountSum(int p0) {
		ResultSet rs1 = null;
		try(	
				PreparedStatement ps2=connection.prepareStatement("select sum(a.cena*b.kolicina) from Artikal a join jePorucen b on a.idProizvod=b.idProizvod join prodavnica p on p.idprod=a.idprod where b.idPorudzbina=?");
				
				
				){
					ps2.setInt(1, p0);
//					ps2.setInt(3, p0);
//					ps2.setDate(2,  new Date(new mu200231_GeneralOperations().getCurrentTime().getTimeInMillis()));
					rs1=ps2.executeQuery();
					BigDecimal b=getFinalPrice(p0);
					if(rs1.next()) {BigDecimal q=rs1.getBigDecimal(1);q=q.subtract(b);System.out.println(q);return q;
				}}
				catch (Exception e) {e.printStackTrace();}
		
				return new BigDecimal(0);
	
		
	}

	@Override
	public String getState(int p0) {
		ResultSet rs1 = null;
		try(	
				PreparedStatement ps2=connection.prepareStatement("select status from porudzbina  where idporudzbina=?");
				PreparedStatement ps1=connection.prepareStatement("select count(status) from porudzbina  where idporudzbina=?");
				
				){	System.out.println("Pokusavam vreme"+p0);
					ps2.setInt(1, p0);
					rs1=ps2.executeQuery();
					
					ps1.setInt(1, p0);
					ResultSet rs2=ps1.executeQuery();
					if(rs2.next()) {if(rs2.getInt(1)>0) {
					if(rs1.next()) {System.out.println(rs1.getString(1));return rs1.getString(1);}}}
				}
				catch (Exception e) {e.printStackTrace();}
				System.out.println("Nema vreme"+p0);
				return null;
	}

	@Override
	public Calendar getSentTime(int p0) {
		ResultSet rs1 = null;
		try(	
				PreparedStatement ps2=connection.prepareStatement("select DatumPoslato from porudzbina  where idporudzbina=?");
				PreparedStatement ps1=connection.prepareStatement("select count(DatumPoslato) from porudzbina  where idporudzbina=? and status='sent'");
				
				){	System.out.println("Pokusavam vreme"+p0);
					ps2.setInt(1, p0);
					rs1=ps2.executeQuery();
					Calendar cal =new GregorianCalendar();
					ps1.setInt(1, p0);
					ResultSet rs2=ps1.executeQuery();
					if(rs2.next()) {if(rs2.getInt(1)>0) {
					if(rs1.next()) {cal.setTime((rs1.getDate(1)));return cal;}}}
				}
				catch (Exception e) {e.printStackTrace();}
				System.out.println("Nema vreme"+p0);
				return null;
	}

	@Override
	public Calendar getRecievedTime(int p0) {
		ResultSet rs1 = null;
		try(	
				PreparedStatement ps2=connection.prepareStatement("select DatumPrimljeno from porudzbina  where idporudzbina=?");
				PreparedStatement ps1=connection.prepareStatement("select count(DatumPrimljeno) from porudzbina  where idporudzbina=? and status='received'");
				
				){	System.out.println("Pokusavam vreme"+p0);
					ps2.setInt(1, p0);
					rs1=ps2.executeQuery();
					Calendar cal =new GregorianCalendar();
					ps1.setInt(1, p0);
					ResultSet rs2=ps1.executeQuery();
					if(rs2.next()) {if(rs2.getInt(1)>0) {
					if(rs1.next()) {cal.setTime((rs1.getDate(1)));return cal;}}}
				}
				catch (Exception e) {e.printStackTrace();}
				System.out.println("Nema vreme"+p0);
				return null;
	}

	@Override
	public int getBuyer(int p0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLocation(int p0) {
		ResultSet rs1 = null;ResultSet rs2 = null;
		try(	PreparedStatement ps4=connection.prepareStatement("select k.idgrad,p.datumposlato,p.datumprimljeno from porudzbina p join kupac k on p.idkupac=k.idkupac where idporudzbina=?");

				PreparedStatement ps3=connection.prepareStatement("select sum(a.cena*b.kolicina*(1-p.popust*0.01)),a.idprod from Artikal a join jePorucen b on a.idProizvod=b.idProizvod join prodavnica p on p.idprod=a.idprod where b.idPorudzbina=? and a.idProizvod=? group by a.idprod");
				PreparedStatement ps5=connection.prepareStatement("Update Porudzbina set DatumPrimljeno=? where idPorudzbina=?");

				PreparedStatement ps2=connection.prepareStatement("Update Porudzbina set status=?,DatumPoslato=? where idPorudzbina=?");
				PreparedStatement ps1=connection.prepareStatement("Insert into transakcija values(?,(select idKupac from porudzbina where idPorudzbina=?),?,(select idprod from artikal where idproizvod=?),?,'sent')");
				
				){  
					
					mu200231_CityOperations q=new mu200231_CityOperations();
					mu200231_ShopOperations shop=new mu200231_ShopOperations();
					ShopOperations b=new mu200231_ShopOperations();
					mu200231_CityOperations ae=new mu200231_CityOperations();
					ps3.setInt(1, p0);Date p1=null,p2 = null;Pomocni prviDeo=null;
					List<Integer> a=getItems(p0);int maxDaljina=0;
					for(int j=0;j<a.size();j++) {
						
						
						
												
						ps4.setInt(1, p0);						
						ps3.setInt(1, p0);
						ps3.setInt(2, a.get(j));
						rs1=ps3.executeQuery();
						rs1.next();
						ps1.setBigDecimal(1, rs1.getBigDecimal(1));
						int grad;rs2=ps4.executeQuery();
						rs2.next();
						grad=rs2.getInt(1);
						 p1=rs2.getDate(2);
						 p2=rs2.getDate(3);
						
//						  System.out.println((p2.getTime()-p1.getTime())/86400000);
						
						 int gradArtikla=shop.getCity(rs1.getInt(2));
						int MINLENGTH=200000;
						List<Integer> be=ae.getCities();
						for(int i=0;i<be.size();i++) {
							if(be.get(i)==grad)continue;
						//System.out.println("U gradu"+be.get(i)+" ima prodavnica:"+!ae.getShops(be.get(i)).isEmpty());
						if(!ae.getShops(be.get(i)).isEmpty()) {
							Pomocni f=ae.funkcija(grad, be.get(i));
							if(f.daljina<MINLENGTH) {
								MINLENGTH=f.daljina;
							}
						}}
						int idGradSaProdavnicom=0;
						Pomocni t=null;
						for(int i=0;i<be.size();i++) {
							if(be.get(i)==grad)continue;
							//System.out.println("U gradu"+be.get(i)+" ima prodavnica:"+!ae.getShops(be.get(i)).isEmpty());
							if(!ae.getShops(be.get(i)).isEmpty()) {
								Pomocni f=ae.funkcija(grad, be.get(i));
								if(f.daljina==MINLENGTH) {
									idGradSaProdavnicom=f.krajnji;t=f;
								}
							}
						}
						//System.out.println(t);
						 prviDeo=t;
						int IdGradCilj=t.krajnji;
						Pomocni Resenje=ae.funkcija(gradArtikla, idGradSaProdavnicom);
						
						//System.out.println(Resenje+" Grad PrveDostave:"+idGradSaProdavnicom);
						if(Resenje.daljina+t.daljina>maxDaljina) {maxDaljina=Resenje.daljina+t.daljina;}
						
					}
					mu200231_GeneralOperations pag=new mu200231_GeneralOperations();
					Date p3=new Date(new mu200231_GeneralOperations().getCurrentTime().getTimeInMillis());
					System.out.println((p2.getTime()-p3.getTime())/86400000);
					long poggers=(p2.getTime()-p3.getTime())/86400000;
					if(poggers>prviDeo.daljina) {
						String[] SviDelovi=prviDeo.prethodniPut.split("-");
						return Integer.parseInt(SviDelovi[SviDelovi.length-1]);
					}
					if(poggers<=0) {
						String[] SviDelovi=prviDeo.prethodniPut.split("-");
						return Integer.parseInt(SviDelovi[0]);
					}
					else {
						String[] SviDelovi=prviDeo.prethodniPut.split("-");
						long pomoc=0;
						
						for(int i=0;i<SviDelovi.length-1;i++) {
							Pomocni staka=ae.funkcija(Integer.parseInt(SviDelovi[i]), Integer.parseInt(SviDelovi[i+1]));
							pomoc+=staka.daljina;
							System.out.println("Pomoc ="+pomoc);
							if(poggers<=pomoc) {
								return Integer.parseInt(SviDelovi[i+1]);
							}
						}
					}
					
				}
				catch (Exception e) {e.printStackTrace();}
		
				return -1;
	}
	public static void main(String[] args) {
		GeneralOperations a=new mu200231_GeneralOperations();
		a.eraseAll();
	}
}
