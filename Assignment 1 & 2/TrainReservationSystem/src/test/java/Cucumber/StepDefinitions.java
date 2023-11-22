package Cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.jupiter.api.Assertions.*;

import org.example.*;
import org.example.impl.CityImpl;
import org.example.impl.TicketReservationSystemImpl;
import org.example.impl.TrainImpl;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

public class StepDefinitions {
    ZoneId zoneId = ZoneId.systemDefault();
    TicketReservationSystem trs;
    Trip trip1;
    Trip trip2;
    List<Trip> tripsList;

    @Given("Exchange requirements are met")
    public void exchange_requirements_are_met() throws TripException{
        trs = new TicketReservationSystemImpl(zoneId);

        City origin1 = new CityImpl("City A");
        City destination1 = new CityImpl("City B");
        Train train1 = new TrainImpl("Express Train", 20);
        Instant departureTime1 = Instant.parse("2023-11-25T10:00:00Z");
        Instant arrivalTime1 = Instant.parse("2023-11-28T14:00:00Z");
        trip1 = trs.createTrip(origin1, destination1, train1, departureTime1, arrivalTime1);

        Train train2 = new TrainImpl("Bullet Train", 20);
        Instant departureTime2 = Instant.parse("2023-11-27T10:00:00Z");
        Instant arrivalTime2 = Instant.parse("2023-11-29T14:00:00Z");
        trip2 = trs.createTrip(origin1, destination1, train2, departureTime2, arrivalTime2);
    }

    @When("Request to see available tickets for exchange")
    public void request_to_see_available_tickets_for_exchange() throws ReservationException{
        Ticket t1 = trip1.bookTicket("Alireza");
        tripsList = trs.findPossibleExchanges(t1);
    }

    @Then("A list of all available tickets are shown")
    public void a_list_of_all_available_tickets_are_shown() {
        assertEquals(tripsList.toArray().length, 1);
    }
}
