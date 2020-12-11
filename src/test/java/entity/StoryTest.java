package entity;

import org.junit.Before;
import org.junit.Test;
import entity.enums.CartingState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class StoryTest {
	private Story story;
	private Passenger pas1;
	private Passenger pas2;
	private Passenger pas3;

	@Before
	public void initialize() throws IOException {
		pas1 = new Passenger(1, CartingState.NOT_STARTED, 1, 2);
		pas2 = new Passenger(2, CartingState.NOT_STARTED, 1, 3);
		pas3 = new Passenger(3, CartingState.NOT_STARTED, 1, 2);
		List<Passenger> passengerList = new ArrayList<>();
		passengerList.add(pas1);
		passengerList.add(pas2);
		passengerList.add(pas3);
		story = new Story(passengerList);
	}

	@Test
	public void getStoryPassengers() throws Exception {
		assertThat(3, is(equalTo(story.getStoryPassengers().size())));
		assertThat(story.getStoryPassengers(), hasItems(pas1, pas2, pas3));
	}

	@Test
	public void addPassenger() throws Exception {
		Passenger pas4 = new Passenger(4, CartingState.NOT_STARTED, 1,4);
		assertThat(story.getStoryPassengers(), allOf(hasItems(pas1, pas2, pas3), not(hasItem(pas4))));
		story.addPassenger(pas4);
		assertThat(story.getStoryPassengers(), allOf(hasItems(pas1, pas2, pas3), hasItem(pas4)));
	}

	@Test
	public void removePassenger() throws Exception {
		assertThat(story.getStoryPassengers(), hasItems(pas1, pas2, pas3));
		story.removePassenger(pas2);
		assertThat(story.getStoryPassengers(), allOf(hasItems(pas1, pas3), not(hasItem(pas2))));
	}

	@Test
	public void size() throws Exception {
		assertThat(3, is(equalTo(story.size())));
		story.removePassenger(pas2);
		assertThat(2, is(equalTo(story.size())));
	}

}