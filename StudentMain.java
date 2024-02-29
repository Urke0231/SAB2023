
import org.junit.Test;
import java.util.Calendar;
import rs.etf.sab.operations.*;
import rs.etf.sab.tests.*;
import rs.etf.sab.student.*;
public class StudentMain {

    public static void main(String[] args) {

        ArticleOperations articleOperations = new mu200231_ArticleOperations(); // Change this for your implementation (points will be negative if interfaces are not implemented).
        BuyerOperations buyerOperations = new mu200231_BuyerOperations();
        CityOperations cityOperations = new mu200231_CityOperations();
        GeneralOperations generalOperations = new mu200231_GeneralOperations();
        OrderOperations orderOperations = new mu200231_OrderOperations();
        ShopOperations shopOperations = new mu200231_ShopOperations();
        TransactionOperations transactionOperations = new mu200231_TransactionOperations();
        generalOperations.eraseAll();
        TestHandler.createInstance(
                articleOperations,
                buyerOperations,
                cityOperations,
                generalOperations,
                orderOperations,
                shopOperations,
                transactionOperations
        );

        TestRunner.runTests();
    }
}
