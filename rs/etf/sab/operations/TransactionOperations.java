// 
// Decompiled by Procyon v0.5.36
// 

package rs.etf.sab.operations;

import java.util.Calendar;
import java.util.List;
import java.math.BigDecimal;

public interface TransactionOperations
{
    BigDecimal getBuyerTransactionsAmmount(final int p0);
    
    BigDecimal getShopTransactionsAmmount(final int p0);
    
    List<Integer> getTransationsForBuyer(final int p0);
    
    int getTransactionForBuyersOrder(final int p0);
    
    int getTransactionForShopAndOrder(final int p0, final int p1);
    
    List<Integer> getTransationsForShop(final int p0);
    
    Calendar getTimeOfExecution(final int p0);
    
    BigDecimal getAmmountThatBuyerPayedForOrder(final int p0);
    
    BigDecimal getAmmountThatShopRecievedForOrder(final int p0, final int p1);
    
    BigDecimal getTransactionAmount(final int p0);
    
    BigDecimal getSystemProfit();
}
