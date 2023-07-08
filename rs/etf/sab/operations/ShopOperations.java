// 
// Decompiled by Procyon v0.5.36
// 

package rs.etf.sab.operations;

import java.util.List;

public interface ShopOperations
{
    int createShop(final String p0, final String p1);
    
    int setCity(final int p0, final String p1);
    
    int getCity(final int p0);
    
    int setDiscount(final int p0, final int p1);
    
    int increaseArticleCount(final int p0, final int p1);
    
    int getArticleCount(final int p0);
    
    List<Integer> getArticles(final int p0);
    
    int getDiscount(final int p0);
}
