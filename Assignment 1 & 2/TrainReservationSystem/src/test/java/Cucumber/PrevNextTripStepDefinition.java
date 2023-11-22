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
import java.util.Optional;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/Cucumber/prev_next_train_trip.feature"},
        glue = "Cucumber"
)
public class PrevNextTripStepDefinition {
    ZoneId zoneId = ZoneId.systemDefault();
    TicketReservationSystem trs;
    Trip trip1;
    Trip trip2;
    Trip trip3;
    Train train;
    Optional<Trip> previous_trip;
    Optional<Trip> next_trip;

    @Given("Train has more that three trips and has a previous and next trip")
    public void train_has_more_that_three_trips_and_has_a_previous_and_next_trip() throws TripException {
        trs = new TicketReservationSystemImpl(zoneId);
        train = new TrainImpl("Bullet Train", 20);

        City origin1 = new CityImpl("City A");
        City destination1 = new CityImpl("City B");
        Instant departureTime1 = Instant.parse("2023-11-20T10:00:00Z");
        Instant arrivalTime1 = Instant.parse("2023-11-23T14:00:00Z");
        trip1 = trs.createTrip(origin1, destination1, train, departureTime1, arrivalTime1);

        City origin2 = new CityImpl("City C");
        City destination2 = new CityImpl("City D");
        Instant departureTime2 = Instant.parse("2023-11-24T10:00:00Z");
        Instant arrivalTime2 = Instant.parse("2023-11-25T14:00:00Z");
        trip2 = trs.createTrip(origin2, destination2, train, departureTime2, arrivalTime2);

        City origin3 = new CityImpl("City E");
        City destination3 = new CityImpl("City F");
        Instant departureTime3 = Instant.parse("2023-11-27T10:00:00Z");
        Instant arrivalTime3 = Instant.parse("2023-11-29T14:00:00Z");
        trip3 = trs.createTrip(origin3, destination3, train, departureTime3, arrivalTime3);
    }

    @When("Request to see available earlier and later trips of a train")
    public void request_to_see_available_earlier_and_later_trips_of_a_train() throws TripException{
        previous_trip = trs.findPreviousTripOfTrain(train, trip2);
        next_trip = trs.findNextTripOfTrain(train, trip2);
    }

    @Then("The correct successor and predecessor trips are shown")
    public void the_correct_successor_and_predecessor_trips_are_shown() {
        assertTrue(previous_trip.isPresent());
        assertTrue(next_trip.isPresent());

        assertEquals(previous_trip.get(), trip1);
        assertEquals(next_trip.get(), trip3);
    }
}
