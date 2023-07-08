// 
// Decompiled by Procyon v0.5.36
// 

package rs.etf.sab.operations;

import java.util.List;

public interface CityOperations
{
    int createCity(final String p0);
    
    List<Integer> getCities();
    
    int connectCities(final int p0, final int p1, final int p2);
    
    List<Integer> getConnectedCities(final int p0);
    
    List<Integer> getShops(final int p0);
}
