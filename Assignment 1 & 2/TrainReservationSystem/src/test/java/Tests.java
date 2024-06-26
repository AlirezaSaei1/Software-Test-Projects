import org.example.impl.*;
import org.example.util.TimeManagement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.example.*;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

public class Tests {

    ZoneId zoneId;
    private TicketReservationSystem trs;
    City origin;
    City destination;

    @BeforeEach
    public void setUp() {
        // Set up the default ZoneId and create a TicketReservationSystem
        zoneId = ZoneId.systemDefault();
        trs = new TicketReservationSystemImpl(zoneId);
        origin = new CityImpl("City A");
        destination = new CityImpl("City B");
    }

    // Group 1: City and Train Management
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
        Train train1 = new TrainImpl("Bullet Train 1", 100);
        trs.addTrain(train1);
        Train train2 = new TrainImpl("Bullet Train 2", 100);
        trs.addTrain(train2);
        Train train3 = new TrainImpl("Bullet Train 3", 100);
        trs.addTrain(train3);

        Train train = new TrainImpl("Bullet Train 4", 100);
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

    // Group 2: Trip Management
    @Test
    public void createNewValidTrip() throws TripException {
        City origin1 = new CityImpl("City A");
        City destination1 = new CityImpl("City B");
        Train train1 = new TrainImpl("Express Train", 200);
        Instant departureTime1 = TimeManagement.createInstant("2023-11-25 10:00", zoneId);
        Instant arrivalTime1 = TimeManagement.createInstant("2023-11-28 14:00", zoneId);

        Trip trip1 = trs.createTrip(origin1, destination1, train1, departureTime1, arrivalTime1);


        City origin2 = new CityImpl("City C");
        City destination2 = new CityImpl("City D");
        Train train2 = new TrainImpl("Local Train", 100);
        Instant departureTime2 = TimeManagement.createInstant("2023-12-02 08:00", zoneId);
        Instant arrivalTime2 = TimeManagement.createInstant("2023-12-02 10:00", zoneId);

        Trip trip2 = trs.createTrip(origin2, destination2, train2, departureTime2, arrivalTime2);

        List<Trip> trips = trs.getAllTrips();
        assertEquals(trips.toArray().length, 2);
    }

    @Test
    public void cancelAnExistingTrip() throws TripException, ReservationException{
        Train train = new TrainImpl("Express Train", 200);
        Instant departureTime = TimeManagement.createInstant("2023-11-25 10:00", zoneId);
        Instant arrivalTime = TimeManagement.createInstant("2023-11-28 14:00", zoneId);

        Trip trip = trs.createTrip(origin, destination, train, departureTime, arrivalTime);

        trip.bookTicket("Alireza Saei");
        trip.bookTicket("Martin Luther King");

        trs.cancelTrip(trip);
        List<Trip> trips = trs.getAllTrips();
        List<Trip>canceled_trips = trs.getAllCancelledTrips();

        assertEquals(trips.toArray().length, 0);
        assertFalse(canceled_trips.isEmpty());
        assertEquals(trip.getCancelledTickets().size(), 2);
    }

    @Test
    public void createATripWithConflict() throws TripException{
        City origin1 = new CityImpl("City A");
        City destination1 = new CityImpl("City B");
        Train train = new TrainImpl("Express Train", 200);
        Instant departureTime1 = TimeManagement.createInstant("2023-11-25 10:00", zoneId);
        Instant arrivalTime1 = TimeManagement.createInstant("2023-11-28 14:00", zoneId);

        Trip trip1 = trs.createTrip(origin1, destination1, train, departureTime1, arrivalTime1);


        City origin2 = new CityImpl("City C");
        City destination2 = new CityImpl("City D");
        Instant departureTime2 = TimeManagement.createInstant("2023-11-26 08:30", zoneId);
        Instant arrivalTime2 = TimeManagement.createInstant("2023-12-01 12:30", zoneId);

        assertThrows(TripException.class, () -> {
            Trip trip2 = trs.createTrip(origin2, destination2, train, departureTime2, arrivalTime2);
        });
    }

    @Test
    public void createATripButArrivalIsBeforeDeparture(){
        City origin = new CityImpl("City A");
        City destination = new CityImpl("City B");
        Train train = new TrainImpl("Express Train", 200);
        Instant departureTime = TimeManagement.createInstant("2023-11-25 10:00", zoneId);
        Instant arrivalTime = TimeManagement.createInstant("2023-10-25 14:00", zoneId);

        assertThrows(TripException.class, ()->{
            Trip trip = trs.createTrip(origin, destination, train, departureTime, arrivalTime);
        });
    }

    // Group 3: Ticket Booking and Cancellation
    @Test
    public void bookingATicket() throws TripException, ReservationException {
        Train train = new TrainImpl("Express Train", 200);
        Instant departureTime = TimeManagement.createInstant("2023-11-25 10:00", zoneId);
        Instant arrivalTime = TimeManagement.createInstant("2023-11-28 14:00", zoneId);

        Trip trip = trs.createTrip(origin, destination, train, departureTime, arrivalTime);
        trip.bookTicket("Alireza");

        List<Ticket> tickets = trs.getAllBookedTickets();
        List<Ticket> actual_tickets = trip.getBookedTickets();

        assertEquals(tickets, actual_tickets);
    }

    @Test
    public void cancelATicket() throws TripException, ReservationException{
        Train train = new TrainImpl("Express Train", 200);
        Instant departureTime = TimeManagement.createInstant("2023-11-25 10:00", zoneId);
        Instant arrivalTime = TimeManagement.createInstant("2023-11-28 14:00", zoneId);

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
        Instant departureTime = TimeManagement.createInstant("2023-11-25 10:00", zoneId);
        Instant arrivalTime = TimeManagement.createInstant("2023-11-28 14:00", zoneId);


        Trip trip = trs.createTrip(origin, destination, train, departureTime, arrivalTime);
        trip.bookTicket("Alireza Saei");
        trip.bookTicket("David Goggins");
        trip.bookTicket("Donald Trump");

        assertThrows(ReservationException.class, () -> {
            trip.bookTicket("Andrew Tate");
        });
    }

    // Group 4: Delay Management
    @Test
    public void addDepartureDelayToATrip() throws TripException{
        Train train = new TrainImpl("Express Train", 3);
        Instant departureTime = TimeManagement.createInstant("2023-11-25 10:00", zoneId);
        Instant arrivalTime = TimeManagement.createInstant("2023-11-28 14:00", zoneId);

        Trip trip = trs.createTrip(origin, destination, train, departureTime, arrivalTime);
        Duration dur_day = Duration.ofDays(1);
        Duration dur_hour = Duration.ofHours(2);
        Duration dur_merged = dur_day.plus(dur_hour);
        trip.addDepartureDelay(dur_merged);

        assertTrue(trs.getAllTrips().get(0).isDelayed());
        assertEquals(trip.getDepartureDelay(), dur_merged);
        assertEquals(trip.findRealDepartureTime(), TimeManagement.createInstant("2023-11-26 12:00", zoneId));
        assertEquals(trip.getPlannedDepartureTime(), departureTime);
        assertEquals(trip.findPlannedDuration(), Duration.between(departureTime, arrivalTime));
        assertEquals(trip.findRealDuration(), Duration.between(departureTime.plus(dur_merged), arrivalTime));
    }

    @Test
    public void addArrivalDelayToATrip() throws TripException{
        Train train = new TrainImpl("Express Train", 10);
        Instant departureTime = TimeManagement.createInstant("2023-11-25 10:00", zoneId);
        Instant arrivalTime = TimeManagement.createInstant("2023-11-28 14:00", zoneId);

        Trip trip = trs.createTrip(origin, destination, train, departureTime, arrivalTime);
        Duration dur_day = Duration.ofDays(1);
        Duration dur_hour = Duration.ofHours(2);
        Duration dur_merged = dur_day.plus(dur_hour);
        trip.addArrivalDelay(dur_merged);

        // There is an error in isDelayed() for checking delay for arrivalTime
        assertTrue(trs.getAllTrips().get(0).isDelayed());
        assertEquals(trip.getArrivalDelay(), dur_merged);
        assertEquals(trip.findRealArrivalTime(), TimeManagement.createInstant("2023-11-29 16:00", zoneId));
        assertEquals(trip.getPlannedArrivalTime(), arrivalTime);
        assertEquals(trip.findPlannedDuration(), Duration.between(departureTime, arrivalTime));
        assertEquals(trip.findRealDuration(), Duration.between(departureTime, arrivalTime.plus(dur_merged)));
    }

    @Test
    public void addDepartureDelayToATripMoreThanDuration() throws TripException{
        Train train = new TrainImpl("Express Train", 10);
        Instant departureTime = TimeManagement.createInstant("2023-11-25 10:00", zoneId);
        Instant arrivalTime = TimeManagement.createInstant("2023-11-27 10:00", zoneId);

        Trip trip = trs.createTrip(origin, destination, train, departureTime, arrivalTime);
        Duration dur = Duration.ofDays(4);
        trip.addDepartureDelay(dur);

        assertTrue(trs.getAllTrips().get(0).isDelayed());
        assertEquals(trip.getDepartureDelay(), dur);
        assertEquals(trip.findRealDepartureTime(), TimeManagement.createInstant("2023-11-29 10:00", zoneId));
        assertEquals(trip.getPlannedDepartureTime(), departureTime);
        // Now departure time is after arrival time that is not true
        assertTrue(trip.findRealArrivalTime().isAfter(trip.findRealDepartureTime()));
    }

    // Group 5: Exchange Management -> Cucumber
}
