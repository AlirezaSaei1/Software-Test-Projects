import org.example.impl.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.example.*;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

public class Tests {

    private TicketReservationSystem trs;
    City origin;
    City destination;

    @BeforeEach
    public void setUp() {
        // Set up the default ZoneId and create a TicketReservationSystem
        ZoneId zoneId = ZoneId.systemDefault();
        trs = new TicketReservationSystemImpl(zoneId);
        origin = new CityImpl("City A");
        destination = new CityImpl("City B");
    }

    // Scenario 1: City and Train Management
    @Test
    public void addNewCity(){
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
    public void bookingATicket() throws TripException, ReservationException {
        Train train = new TrainImpl("Express Train", 200);
        Instant departureTime = Instant.parse("2023-11-25T10:00:00Z");
        Instant arrivalTime = Instant.parse("2023-11-28T14:00:00Z");

        Trip trip = trs.createTrip(origin, destination, train, departureTime, arrivalTime);
        trip.bookTicket("Alireza");

        List<Ticket> tickets = trs.getAllBookedTickets();
        List<Ticket> actual_tickets = trip.getBookedTickets();

        assertEquals(tickets, actual_tickets);
    }

    @Test
    public void cancelATicket() throws TripException, ReservationException{
        Train train = new TrainImpl("Express Train", 200);
        Instant departureTime = Instant.parse("2023-11-25T10:00:00Z");
        Instant arrivalTime = Instant.parse("2023-11-28T14:00:00Z");

        Trip trip = trs.createTrip(origin, destination, train, departureTime, arrivalTime);
        trip.bookTicket("Alireza");

        List<Ticket> tickets = trs.getAllBookedTickets();
        trip.cancelTicket(tickets.get(0));

        boolean ticketIsCancelled = trip.getBookedTickets().isEmpty();

        List<Ticket> canceled = trs.getAllCancelledTickets();

        assertTrue(ticketIsCancelled);
        assertFalse(canceled.isEmpty());
    }

    @Test
    public void bookAnInvalidTicket() throws TripException, ReservationException{
        Train train = new TrainImpl("Express Train", 3);
        Instant departureTime = Instant.parse("2023-11-25T10:00:00Z");
        Instant arrivalTime = Instant.parse("2023-11-28T14:00:00Z");

        Trip trip = trs.createTrip(origin, destination, train, departureTime, arrivalTime);
        trip.bookTicket("Alireza Saei");
        trip.bookTicket("David Goggins");
        trip.bookTicket("Donald Trump");

        assertThrows(ReservationException.class, () -> {
            trip.bookTicket("Andrew Tate");
        });
    }

    // Scenario 4: Delay Management
    @Test
    public void addDepartureDelayToATrip() throws TripException{
        Train train = new TrainImpl("Express Train", 3);
        Instant departureTime = Instant.parse("2023-11-25T10:00:00Z");
        Instant arrivalTime = Instant.parse("2023-11-28T14:00:00Z");

        Trip trip = trs.createTrip(origin, destination, train, departureTime, arrivalTime);
        Duration dur_day = Duration.ofDays(1);
        Duration dur_hour = Duration.ofHours(2);
        Duration dur_merged = dur_day.plus(dur_hour);
        trip.addDepartureDelay(dur_merged);

        assertTrue(trs.getAllTrips().get(0).isDelayed());
        assertEquals(trip.getDepartureDelay(), dur_merged);
        assertEquals(trip.findRealDepartureTime(), Instant.parse("2023-11-26T12:00:00Z"));
        assertEquals(trip.getPlannedDepartureTime(), Instant.parse("2023-11-25T10:00:00Z"));
    }

    @Test
    public void addArrivalDelayToATrip() throws TripException{
        Train train = new TrainImpl("Express Train", 10);
        Instant departureTime = Instant.parse("2023-11-25T10:00:00Z");
        Instant arrivalTime = Instant.parse("2023-11-28T14:00:00Z");

        Trip trip = trs.createTrip(origin, destination, train, departureTime, arrivalTime);
        Duration dur_day = Duration.ofDays(1);
        Duration dur_hour = Duration.ofHours(2);
        Duration dur_merged = dur_day.plus(dur_hour);
        trip.addArrivalDelay(dur_merged);

        // There is an error in isDelayed() for checking delay for arrivalTime
        assertTrue(trs.getAllTrips().get(0).isDelayed());
        assertEquals(trip.getArrivalDelay(), dur_merged);
        assertEquals(trip.findRealArrivalTime(), Instant.parse("2023-11-29T16:00:00Z"));
        assertEquals(trip.getPlannedArrivalTime(), Instant.parse("2023-11-28T14:00:00Z"));
    }

    @Test
    public void addDepartureDelayToATripMoreThanDuration() throws TripException{
        Train train = new TrainImpl("Express Train", 10);
        Instant departureTime = Instant.parse("2023-11-25T10:00:00Z");
        Instant arrivalTime = Instant.parse("2023-11-27T10:00:00Z");

        Trip trip = trs.createTrip(origin, destination, train, departureTime, arrivalTime);
        Duration dur = Duration.ofDays(4);
        trip.addDepartureDelay(dur);

        assertTrue(trs.getAllTrips().get(0).isDelayed());
        assertEquals(trip.getDepartureDelay(), dur);
        assertEquals(trip.findRealDepartureTime(), Instant.parse("2023-11-29T10:00:00Z"));
        assertEquals(trip.getPlannedDepartureTime(), Instant.parse("2023-11-25T10:00:00Z"));
        // Now departure time is after arrival time that is not true
        assertTrue(trip.findRealArrivalTime().isAfter(trip.findRealDepartureTime()));
    }

    // Scenario 5: Exchange Management
}
