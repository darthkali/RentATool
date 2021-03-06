package de.rat.model.employee;

import de.rat.model.Rental;
import de.rat.model.common.*;
import de.rat.model.customer.*;
import de.rat.model.logistics.*;
import de.rat.model.billing.*;
import org.junit.jupiter.api.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmployeeTest {

    //Variable declaration
    private Employee empMichael;
    private Employee empJonas;

    private Customer custMartin;

    private Warehouse warehouse;
    private Tool saw;
    private Manufacturer bosch;
    private Station stationOne;
    private Address musterhausen;

    @BeforeEach
    void setUp() {

        empJonas = new Employee("Hecht", "Jonas", LocalDate.of(2019, 12, 15),
                "Johannesstraße", "5", "99084", "Weimar", "Germany", null);
        empMichael = new Employee("Müller", "Michael", LocalDate.of(2017, 2, 5),
                "Michaelistraße", "17", "99086", "Erfurt", "Germany", empJonas);

        custMartin = new Customer("Schmidt", "Martin", LocalDate.of(2005, 8, 29), "maria.schmidt@web.de",
                "Weimarerlandstraße", "53", "99986", "Dresden", "Germany", "561616310651");

        musterhausen = new Address("Musterstrasse", "1", "99099", "Erfurt", "Deutschland");
        bosch = new Manufacturer("Bosch", musterhausen, "Mr Smith", "123456");
        saw = new Tool("1111", bosch, "Säge", Category.HANDTOOL, "1-4-5", ToolStatus.AVAILABLE, new BigDecimal(3));
        stationOne = new Station("S1", 3, musterhausen);

        warehouse = new Warehouse();

    }

    // Main Test --------------------------------
    @Test
    void should_return_null_if_the_employee_has_no_supervisor(){
        Assertions.assertNull(empJonas.getSupervisor());
    }

    @Test
    void should_return_the_correct_supervisor_from_this_employee(){
        Assertions.assertEquals(empJonas, empMichael.getSupervisor());
    }

    @Test
    void create_correct_password_for_customer_and_employee(){
        Assertions.assertEquals("jo151219he", empJonas.getAccount().getPassword());
    }

    @Test
    void create_correct_email_for_employee(){
        Assertions.assertEquals("jonas.hecht@rat.de", empJonas.getAccount().getEmail());
    }

    @Test
    void set_correct_role_for_the_employee(){
        Assertions.assertEquals(Role.EMPLOYEE, empJonas.getAccount().getRole());
    }

    @Test
    void should_set_the_discount_and_move_bills_to_Close_Bills(){

        warehouse.putToolInWarehouse(saw);
        Rental.rentATool(saw, stationOne, custMartin, warehouse);
        Bill bill2 = Billing.findOpenBillFromCustomer(custMartin);
        Rental.returnTool(saw,stationOne,custMartin,warehouse);

        assertTrue(empJonas.setDiscountAndMoveBillsToCloseBills(bill2, 5));
    }

    @Test
    void should_return_false_if_the_Bill_is_null(){
        Bill bill = null;
        assertFalse(empJonas.setDiscountAndMoveBillsToCloseBills(bill, 5));
    }
}