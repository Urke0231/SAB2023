// 
// Decompiled by Procyon v0.5.36
// 

package rs.etf.sab.operations;

import java.util.List;
import java.math.BigDecimal;

public interface BuyerOperations
{
    int createBuyer(final String p0, final int p1);
    
    int setCity(final int p0, final int p1);
    
    int getCity(final int p0);
    
    BigDecimal increaseCredit(final int p0, final BigDecimal p1);
    
    int createOrder(final int p0);
    
    List<Integer> getOrders(final int p0);
    
    BigDecimal getCredit(final int p0);
}
