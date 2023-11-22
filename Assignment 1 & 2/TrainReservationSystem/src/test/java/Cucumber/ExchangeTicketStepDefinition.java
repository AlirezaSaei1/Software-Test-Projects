package Cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import static org.junit.jupiter.api.Assertions.*;

import org.example.*;
import org.example.impl.CityImpl;
import org.example.impl.TicketReservationSystemImpl;
import org.example.impl.TrainImpl;

import java.time.Instant;
import java.time.ZoneId;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/Cucumber/exchange_ticket_check.feature"},
        glue = "Cucumber"
)
public class ExchangeTicketStepDefinition {
    ZoneId zoneId = ZoneId.systemDefault();
    TicketReservationSystem trs;
    Trip trip;
    Ticket t;
    Ticket exchanged_ticket;

    @Given("Exchanging requirements are met")
    public void exchanging_requirements_are_met() throws TripException, ReservationException{
        trs = new TicketReservationSystemImpl(zoneId);

        City origin1 = new CityImpl("City A");
        City destination1 = new CityImpl("City B");
        Train train1 = new TrainImpl("Express Train", 20);
        Instant departureTime1 = Instant.parse("2023-11-25T10:00:00Z");
        Instant arrivalTime1 = Instant.parse("2023-11-28T14:00:00Z");
        trip = trs.createTrip(origin1, destination1, train1, departureTime1, arrivalTime1);

        t = trip.bookTicket("Alireza Saei");
        trip.bookTicket("Jeff Bezos");
        trip.bookTicket("Arthur Morgan");
    }

    @When("Request to exchange a ticket from a trip")
    public void request_to_exchange_a_ticket_from_a_trip() throws ReservationException {
        exchanged_ticket = t.exchangeTicket(trip);
    }

    @Then("Exchange must be done for a trip")
    public void exchange_must_be_done_for_a_trip() {
        assertTrue(t.isCancelled());
    }
}
