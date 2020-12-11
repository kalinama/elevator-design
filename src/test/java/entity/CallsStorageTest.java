package entity;

import org.junit.Before;
import org.junit.Test;
import entity.enums.CartingState;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class CallsStorageTest {
	private CallsStorage callsStorage;
	private Passenger pas1;
	private Passenger pas2;
	private Passenger pas3;

	@Before
	public void initialize() {
		pas1 = new Passenger(1, CartingState.NOT_STARTED, 1, 2);
		pas2 = new Passenger(2, CartingState.NOT_STARTED, 1, 3);
		pas3 = new Passenger(3, CartingState.NOT_STARTED, 2, 3);
		List<Story> dispatches = new ArrayList<>();
		List<Story> arrivals = new ArrayList<>();
		Story story1 = new Story();
		Story story2 = new Story();
		story1.addPassenger(pas1);
		story1.addPassenger(pas2);
		story2.addPassenger(pas3);
		dispatches.add(story1);
		dispatches.add(story2);
		arrivals.add(story2);
		arrivals.add(story1);
		callsStorage = new CallsStorage(dispatches, arrivals);
	}

	@Test
	public void getDispatches() throws Exception {
		List<Story> storyDispatchList = callsStorage.getDispatches();
		assertThat(storyDispatchList, hasSize(2));
		assertThat(storyDispatchList.get(0).getStoryPassengers(), allOf(hasItems(pas1, pas2), not(hasItem(pas3))));
		assertThat(storyDispatchList.get(1).getStoryPassengers(), allOf(not(hasItems(pas1, pas2)), hasItem(pas3)));
	}

	@Test
	public void getArrivals() throws Exception {
		List<Story> storyArrivalList = callsStorage.getDispatches();
		assertThat(storyArrivalList, hasSize(2));
		assertThat(storyArrivalList.get(0).getStoryPassengers(), allOf(hasItems(pas1, pas2), not(hasItem(pas3))));
		assertThat(storyArrivalList.get(1).getStoryPassengers(), allOf(not(hasItems(pas1, pas2)), hasItem(pas3)));
	}

}