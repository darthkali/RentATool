package de.rat;

import de.rat.common.*;
import de.rat.customer.*;
import de.rat.employee.Department;
import de.rat.employee.Employee;
import de.rat.employee.EmployeeNotification;
import de.rat.logistics.*;
import de.rat.billing.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.GregorianCalendar;

class RentalTest {

    //Variable declaration
    private Customer custMaria;
    private Customer custLudwig;

    private Department deptRental;
    private Department deptLogistics;
    private Department deptManagement;




    private Address musterhausen;
    private Station stationOne;
    private Warehouse warehouse;
    private Manufacturer bosch;

    private Rental rental;

    private Tool drill;
    private Tool hammer;
    private Tool welder;
    private Tool welder2;

    private Employee empDanny;
    private Employee empMichael;
    private Employee empJonas;


    @BeforeEach
    void setUp() {
        rental = new Rental();

        musterhausen = new Address("Musterstrasse", 1, 99099, "Erfurt", "Deutschland");
        bosch = new Manufacturer("Bosch", musterhausen, "Mr Smith", "123456");
        stationOne = new Station("S1", 3, musterhausen);

        deptRental = new Department("Verleih");
        deptLogistics = new Department("Logistik");
        deptManagement = new Department("Geschaeftsleitung");

        drill = new Tool("123", bosch, "Bohrer", Category.HANDTOOL, "1-4-5", ToolStatus.AVAILABLE, 3.0);
        hammer = new Tool("12553", bosch, "Hammer", Category.HANDTOOL, "1-4-6", ToolStatus.AVAILABLE, 2.5);
        welder = new Tool("ewv133", bosch, "Schweißgerät", Category.HANDTOOL, "1-4-7", ToolStatus.ISRENTED, 3.5);
        welder2 = new Tool("ewv133", bosch, "Schweißgerät", Category.HANDTOOL, "1-4-7", ToolStatus.ISRENTED, 3.5);

        custMaria = new Customer("Schmidt", "Maria", new GregorianCalendar(2005, GregorianCalendar.AUGUST, 29), "maria.schmidt@web.de",
                "Weimarerlandstraße", 53, 99986, "Dresden", "Germany", "561616310651");
        custLudwig = new Customer("Ebert", "Ludwig", new GregorianCalendar(1937, GregorianCalendar.DECEMBER, 17), "crazyemail@web.de",
                "Bahnhofsstraße", 16, 99067, "Gotha", "Germany", "01236/465854");


        empDanny = new Employee("Steinbrecher", "Danny", new GregorianCalendar(2019, GregorianCalendar.DECEMBER, 15),
                "Johannesstraße", 5, 99084, "Erfurt", "Germany", deptManagement, null);
        empMichael = new Employee("Müller", "Michael", new GregorianCalendar(2017, GregorianCalendar.FEBRUARY, 5),
                "Michaelistraße", 17, 99086, "Erfurt", "Germany", deptLogistics, empDanny);
        empJonas = new Employee("Casio", "Jonas", new GregorianCalendar(2000, GregorianCalendar.OCTOBER, 30),
                "Leipzigerstraße", 99, 99084, "Weimar", "Germany", deptRental, empDanny);

        EmployeeNotification.addEmployee(empDanny);
        EmployeeNotification.addEmployee(empMichael);
        EmployeeNotification.addEmployee(empJonas);

        warehouse = new Warehouse();
    }


    @Test
    void should_rent_a_tool(){
        warehouse.putToolInWarehouse(drill);
        assertTrue(rental.rentATool(drill, stationOne, custMaria, warehouse));
    }

    @Test
    void should_return_false_if_the_station_is_full(){

        stationOne.addToolToBox(hammer);
        stationOne.addToolToBox(welder);
        stationOne.addToolToBox(welder2);

        warehouse.putToolInWarehouse(drill);

        assertFalse(rental.rentATool(drill, stationOne, custMaria, warehouse));
    } 

    @Test
    void should_return_false_if_the_tool_is_not_in_this_warehouse(){
        assertFalse(rental.rentATool(hammer,stationOne,custLudwig,warehouse));
    }

    @Test
    void should_return_a_tool()  {

        warehouse.putToolInWarehouse(drill);
        warehouse.putToolInWarehouse(hammer);
        assertTrue(rental.rentATool(drill, stationOne, custMaria, warehouse));
        assertTrue(rental.rentATool(hammer, stationOne, custMaria, warehouse));

        GregorianCalendar today = new GregorianCalendar();
        assertTrue(rental.returnTool(drill,stationOne,custMaria,warehouse, today));
        assertTrue(rental.returnTool(hammer,stationOne,custMaria,warehouse, today));

    }

    @Test
    void should_return_false_if_the_tool_is_not_in_this_station(){
        GregorianCalendar today = new GregorianCalendar();
        boolean checkReturn=rental.returnTool(drill,stationOne,custMaria,warehouse, today);
        assertFalse(checkReturn);
    }

    @Test
    void should_return_false_if_there_is_no_open_bill_from_this_customer(){
        GregorianCalendar today = new GregorianCalendar();
        boolean toolInReturnStation=stationOne.addToolToBox(drill);
        boolean checkReturn=rental.returnTool(drill,stationOne,custMaria,warehouse, today);
        assertFalse(checkReturn);
    }

    @Test
    void should_return_false_if_there_is_no_open_rentProcess_with_this_tool(){
        GregorianCalendar today = new GregorianCalendar();
        boolean result= warehouse.putToolInWarehouse(drill);
        boolean checkRent = rental.rentATool(drill, stationOne, custMaria, warehouse);

        boolean toolInReturnStation=stationOne.addToolToBox(drill);

        boolean checkReturn=rental.returnTool(drill,stationOne,custLudwig,warehouse, today);
        assertFalse(checkReturn);
    }


}