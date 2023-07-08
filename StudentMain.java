
import org.junit.Test;
import java.util.Calendar;
import rs.etf.sab.operations.*;
import rs.etf.sab.tests.*;

public class StudentMain {

    public static void main(String[] args) {

        ArticleOperations articleOperations = null; // Change this for your implementation (points will be negative if interfaces are not implemented).
        BuyerOperations buyerOperations = null;
        CityOperations cityOperations = null;
        GeneralOperations generalOperations = null;
        OrderOperations orderOperations = null;
        ShopOperations shopOperations = null;
        TransactionOperations transactionOperations = null;

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
