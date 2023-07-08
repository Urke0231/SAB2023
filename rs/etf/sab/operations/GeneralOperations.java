// 
// Decompiled by Procyon v0.5.36
// 

package rs.etf.sab.operations;

import java.util.Calendar;

public interface GeneralOperations
{
    void setInitialTime(final Calendar p0);
    
    Calendar time(final int p0);
    
    Calendar getCurrentTime();
    
    void eraseAll();
}
