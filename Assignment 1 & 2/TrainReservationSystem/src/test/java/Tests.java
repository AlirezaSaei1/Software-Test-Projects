import org.example.impl.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.example.*;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

public class Tests {
    // Scenario 1: City and Train Management
    @Test
    public void addNewCity(){
        ZoneId zoneId = ZoneId.systemDefault();
        TicketReservationSystem trs = new TicketReservationSystemImpl(zoneId);

        // Add a city to the system before executing the test
        City existingCity1 = new CityImpl("Los Angeles");
        trs.addCity(existingCity1);
        City existingCity2 = new CityImpl("Washington");
        trs.addCity(existingCity2);
        City existingCity3 = new CityImpl("Texas");
        trs.addCity(existingCity3);

        City city = new CityImpl("California");
        trs.addCity(city);
        List<City> cityList = trs.getCities();

        boolean cityExists = false;
        for (City c : cityList) {
            if (c.getName().equals(city.getName())) {
                cityExists = true;
                break;
            }
        }
        assertTrue(cityExists);
    }

    @Test
    public void AddNewTrain(){
        ZoneId zoneId = ZoneId.systemDefault();
        TicketReservationSystem trs = new TicketReservationSystemImpl(zoneId);

        Train train1 = new TrainImpl("Bullet Train", 100);
        trs.addTrain(train1);
        Train train2 = new TrainImpl("Bullet Train", 100);
        trs.addTrain(train2);
        Train train3 = new TrainImpl("Bullet Train", 100);
        trs.addTrain(train3);

        Train train = new TrainImpl("Bullet Train", 100);
        trs.addTrain(train);

        List<Train> trains = trs.getAllTrains();

        boolean trainExists = false;
        for (Train t : trains) {
            if (t.getName().equals(train.getName())) {
                trainExists = true;
                break;
            }
        }
        assertTrue(trainExists);
    }

    // Scenario 2: Trip Management
    @Test
    public void createNewValidTrip() throws TripException {
        ZoneId zoneId = ZoneId.systemDefault();
        TicketReservationSystem trs = new TicketReservationSystemImpl(zoneId);

        City origin1 = new CityImpl("City A");
        City destination1 = new CityImpl("City B");
        Train train1 = new TrainImpl("Express Train", 200);
        Instant departureTime1 = Instant.parse("2023-11-25T10:00:00Z");
        Instant arrivalTime1 = Instant.parse("2023-11-28T14:00:00Z");

        Trip trip1 = trs.createTrip(origin1, destination1, train1, departureTime1, arrivalTime1);


        City origin2 = new CityImpl("City C");
        City destination2 = new CityImpl("City D");
        Train train2 = new TrainImpl("Local Train", 100);
        Instant departureTime2 = Instant.parse("2023-12-02T08:00:00Z");
        Instant arrivalTime2 = Instant.parse("2023-12-02T10:00:00Z");

        Trip trip2 = trs.createTrip(origin2, destination2, train2, departureTime2, arrivalTime2);

        List<Trip> trips = trs.getAllTrips();
        assertEquals(trips.toArray().length, 2);
    }

    @Test
    public void cancelAnExistingTrip() throws TripException {
        ZoneId zoneId = ZoneId.systemDefault();
        TicketReservationSystem trs = new TicketReservationSystemImpl(zoneId);

        City origin = new CityImpl("City A");
        City destination = new CityImpl("City B");
        Train train = new TrainImpl("Express Train", 200);
        Instant departureTime = Instant.parse("2023-11-25T10:00:00Z");
        Instant arrivalTime = Instant.parse("2023-11-28T14:00:00Z");

        Trip trip = trs.createTrip(origin, destination, train, departureTime, arrivalTime);

        trs.cancelTrip(trip);
        List<Trip> trips = trs.getAllTrips();
        assertEquals(trips.toArray().length, 0);
    }

    // Scenario 3: Ticket Booking and Cancellation
    @Test
    public void bookingATicket(){
        ZoneId zoneId = ZoneId.systemDefault();
        TicketReservationSystem trs = new TicketReservationSystemImpl(zoneId);


    }
}
