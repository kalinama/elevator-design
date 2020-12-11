package entity;

import core.Generator;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.assertThat;

public class BuildingTest {
	private Building building;

	@Before
	public void initialize() throws Exception {
		Generator generator = new Generator("src/main/resources/config.property");
		building = new Building(generator);
	}

	@Test
	public void getGenerator() throws Exception {
		assertThat(3, is(equalTo(building.getGenerator().getElevatorCapacity())));
		assertThat(10, is(equalTo(building.getGenerator().getStoriesNumber())));
		assertThat(1000, is(equalTo(building.getGenerator().getPassengersNumber())));
	}

	@Test
	public void getPassengerList() throws Exception {
		assertThat(1000, is(equalTo(building.getPassengerList().size())));
	}

	@Test
	public void getElevatorContainer() throws Exception {
		assertThat(building.getElevatorContainer().getElevatorPassengers(), empty());
		assertThat(3, is(equalTo(building.getElevatorContainer().getStorage())));
	}

	@Test
	public void getDispatchStory() throws Exception {
		for (int i = 0; i < building.getElevatorContainer().getStoryNumbers(); i++) {
			assertThat(building.getDispatchStory(i).size(), greaterThanOrEqualTo(0));
		}
	}

	@Test
	public void getArrivalStory() throws Exception {
		for (int i = 0; i < building.getElevatorContainer().getStoryNumbers(); i++) {
			assertThat(building.getArrivalStory(i).size(), greaterThanOrEqualTo(0));
		}
	}

}