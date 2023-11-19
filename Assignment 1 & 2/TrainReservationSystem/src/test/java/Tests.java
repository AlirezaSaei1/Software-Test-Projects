import org.example.impl.CityImpl;
import org.example.impl.TicketImpl;
import org.example.impl.TicketReservationSystemImpl;
import org.example.impl.TripImpl;
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

}
