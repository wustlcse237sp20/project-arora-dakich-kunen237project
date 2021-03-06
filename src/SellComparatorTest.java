import org.junit.Assert;
import org.junit.jupiter.api.Test;

class SellComparatorTest {
    Order order1 = new Order(0, 1, 1, 200, 10, true);
    Order order2 = new Order(1, 2, 1, 100, 10, true);

    @Test
    public void compare() {
        SellComparator sell = new SellComparator();
        Assert.assertEquals(sell.compare(order1,order2), 1);

    }
}