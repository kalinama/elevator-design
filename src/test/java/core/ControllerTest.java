package core;


import entity.Building;
import entity.Passenger;
import org.junit.Before;
import org.junit.Test;
import entity.enums.CartingState;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class ControllerTest {
	private Controller controller;
	private Building building;
	private Passenger pas1 = new Passenger(1, CartingState.NOT_STARTED,1,2);
	private Passenger pas2 = new Passenger(2, CartingState.NOT_STARTED,2,3);


	@Before
	public void initialization() throws Exception{
		String file = "src/test/resources/test-correct.property";
		Generator generator = new Generator(file);
		building = new Building(generator);
		controller = new Controller(building);
	}

	@Test
	public void iCanEnter() throws Exception {
		building.setEnterLatch(1);
		assertThat(controller.iCanEnter(pas1), is(equalTo(true)));
		assertThat(controller.iCanEnter(pas2), is(equalTo(false)));
		controller.moveElevator();
		assertThat(controller.iCanEnter(pas1), is(equalTo(false)));
		assertThat(controller.iCanEnter(pas2), is(equalTo(false)));
	}

	@Test
	public void iCanLeave() throws Exception {
		building.setEnterLatch(1);
		building.setLeaveLatch(1);
		assertThat(controller.iCanEnter(pas1), is(equalTo(true)));
		controller.moveElevator();
		assertThat(controller.iCanLeave(pas1), is(equalTo(true)));
	}

	@Test
	public void moveElevator() throws Exception {
		assertThat(building.getElevatorContainer().getCurrentStory(), is(equalTo(1)));
		controller.moveElevator();
		assertThat(building.getElevatorContainer().getCurrentStory(), is(equalTo(2)));
		controller.moveElevator();
		assertThat(building.getElevatorContainer().getCurrentStory(), is(equalTo(3)));
		controller.moveElevator();
		assertThat(building.getElevatorContainer().getCurrentStory(), is(equalTo(3)));
		controller.moveElevator();
		assertThat(building.getElevatorContainer().getCurrentStory(), is(equalTo(2)));
		controller.moveElevator();
		assertThat(building.getElevatorContainer().getCurrentStory(), is(equalTo(1)));
	}

}