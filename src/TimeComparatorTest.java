import org.junit.Assert;
import org.junit.jupiter.api.Test;

class TimeComparatorTest {
    Order order1 = new Order( 0, 1, 1, 50, 10, true);
    Order order2 = new Order(1, 2, 1, 100, 10, true);
    Order order3 = new Order(2, 0, 1, 15, 26, true);
    Order order4 = new Order( 3, 0, 0, 7, 2, true);
    Pair<Order,Order> pair1 = Pair.of(order1,order2);
    Pair<Order,Order> pair2 = Pair.of(order3,order4);


    @Test
    public void compare() {
        TimeComparator timecomparison = new TimeComparator();
        Assert.assertEquals(timecomparison.compare(pair1,pair2),-1);

    }
}