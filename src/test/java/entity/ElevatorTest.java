package entity;

import core.Generator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import entity.enums.Direction;
import entity.enums.CartingState;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;


public class ElevatorTest {
	private Elevator elevatorContainer;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void wrongElevatorCapacity() throws Exception {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Elevator storage must be greater than 0 and storyNumbers must be great than 1");
		elevatorContainer = new Elevator(0, 2);
	}

	@Test
	public void wrongStoryNumbers() throws Exception {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Elevator storage must be greater than 0 and storyNumbers must be great than 1");
		elevatorContainer = new Elevator(1, 1);
	}

	@Test
	public void testGettersAndSetters() throws Exception {
		Generator generator = new Generator("src/test/resources/test-correct-data.property");
		elevatorContainer = new Elevator(generator.getElevatorCapacity(), generator.getStoriesNumber());
		assertThat(0, is(equalTo(elevatorContainer.getElevatorPassengers().size())));
		elevatorContainer.enterElevator(new Passenger(1, CartingState.NOT_STARTED, 1,2));
		assertThat(1, is(equalTo(elevatorContainer.getElevatorPassengers().size())));
		assertThat(1, is(equalTo(elevatorContainer.getStorage())));
		assertThat(8, is(equalTo(elevatorContainer.getStoryNumbers())));
		assertThat(1, is(equalTo(elevatorContainer.getCurrentStory())));
		assertThat(Direction.UP, is(equalTo(elevatorContainer.getDirection())));
		elevatorContainer.setDirection(Direction.DOWN);
		assertThat(Direction.DOWN, is(equalTo(elevatorContainer.getDirection())));
	}

	@Test
	public void correctElevatorMove() throws Exception {
		elevatorContainer = new Elevator(1,2);
		assertThat(elevatorContainer.getCurrentStory(), is(equalTo(1)));
		elevatorContainer.up();
		assertThat(elevatorContainer.getCurrentStory(), is(equalTo(2)));
		elevatorContainer.down();
		assertThat(elevatorContainer.getCurrentStory(), is(equalTo(1)));
	}

	@Test
	public void wrongElevatorMove() throws Exception {
		thrown.expect(IllegalStateException.class);
		thrown.expectMessage("Elevator can't move");
		elevatorContainer = new Elevator(1,2);
		assertThat(elevatorContainer.getCurrentStory(), is(equalTo(1)));
		elevatorContainer.up();
		assertThat(elevatorContainer.getCurrentStory(), is(equalTo(2)));
		elevatorContainer.up();
	}

	@Test
	public void prohibitedDownMovingElevator() throws Exception {
		thrown.expect(IllegalStateException.class);
		thrown.expectMessage("Elevator can't move");
		elevatorContainer = new Elevator(1,2);
		assertThat(elevatorContainer.getCurrentStory(), is(equalTo(1)));
		elevatorContainer.down();
	}

	@Test
	public void letThemEnterInElevator() throws Exception {
		elevatorContainer = new Elevator(1, 2);
		Passenger pas1 = new Passenger(1, CartingState.NOT_STARTED, 1,2);
		Passenger pas2 = new Passenger(2, CartingState.NOT_STARTED, 1,2);
		assertThat(elevatorContainer.letMeEnter(), is(equalTo(true)));
		elevatorContainer.enterElevator(pas1);
		assertThat(elevatorContainer.letMeEnter(), is(not(equalTo(true))));
		elevatorContainer.enterElevator(pas2);
	}

	@Test
	public void enteringElevator() throws Exception {
		elevatorContainer = new Elevator(2, 2);
		Passenger pas1 = new Passenger(1, CartingState.NOT_STARTED, 1,2);
		Passenger pas2 = new Passenger(2, CartingState.NOT_STARTED, 1,2);
		Passenger pas3 = new Passenger(3, CartingState.NOT_STARTED, 1,2);
		elevatorContainer.enterElevator(pas1);
		elevatorContainer.enterElevator(pas2);
		elevatorContainer.enterElevator(pas3);
		assertThat(elevatorContainer.getElevatorPassengers(), allOf(hasItems(pas1, pas2), not(hasItems(pas3))));
	}

	@Test
	public void leaveElevatorEnteredPassengers() throws Exception {
		elevatorContainer = new Elevator(2, 2);
		Passenger pas1 = new Passenger(1, CartingState.NOT_STARTED, 1,2);
		Passenger pas2 = new Passenger(2, CartingState.NOT_STARTED, 1,2);
		elevatorContainer.enterElevator(pas1);
		elevatorContainer.enterElevator(pas2);
		elevatorContainer.leaveElevator(pas2);
		assertThat(elevatorContainer.getElevatorPassengers(), allOf(hasItems(pas1), not(hasItems(pas2))));
	}

	@Test
	public void leaveElevatorNotEnteredPassengers() throws Exception {
		elevatorContainer = new Elevator(2, 2);
		Passenger pas1 = new Passenger(1, CartingState.NOT_STARTED, 1,2);
		elevatorContainer.leaveElevator(pas1);
		assertThat(elevatorContainer.getElevatorPassengers(), hasSize(0));
	}

}