// 
// Decompiled by Procyon v0.5.36
// 

package rs.etf.sab.operations;

import java.util.Calendar;
import java.math.BigDecimal;
import java.util.List;

public interface OrderOperations
{
    int addArticle(final int p0, final int p1, final int p2);
    
    int removeArticle(final int p0, final int p1);
    
    List<Integer> getItems(final int p0);
    
    int completeOrder(final int p0);
    
    BigDecimal getFinalPrice(final int p0);
    
    BigDecimal getDiscountSum(final int p0);
    
    String getState(final int p0);
    
    Calendar getSentTime(final int p0);
    
    Calendar getRecievedTime(final int p0);
    
    int getBuyer(final int p0);
    
    int getLocation(final int p0);
}
