import org.example.impl.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.example.*;

import java.time.ZoneId;
import java.util.List;

public class Tests {
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
}
