package entity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import entity.enums.CartingState;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;


public class PassengerTest {
	private Passenger pas;

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testWrongPassengerID() throws Exception {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Wrong passenger ID");
		pas = new Passenger(0, CartingState.COMPLETED, 1, 2);
	}

	@Test
	public void testWrongPassengerInitialStory() throws Exception {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Wrong passenger initialStory");
		pas = new Passenger(1, CartingState.COMPLETED, -1, 2);
	}

	@Test
	public void testWrongPassengerDestinationStory() throws Exception {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Wrong passenger destinationStory");
		pas = new Passenger(2, CartingState.COMPLETED, 1, 0);
	}

	@Test
	public void testAllGettersAndSetters() throws Exception {
		pas = new Passenger(99, CartingState.COMPLETED, 1, 2);
		assertThat(99, is(equalTo(pas.getPassengerID())));
		assertThat(CartingState.COMPLETED, is(equalTo(pas.getState())));
		assertThat(1, is(equalTo(pas.getInitialStory())));
		assertThat(2, is(equalTo(pas.getDestinationStory())));
		pas.setState(CartingState.ABORTED);
		assertThat(CartingState.ABORTED, is(equalTo(pas.getState())));
	}

	@Test
	public void testToString() throws Exception {
		pas = new Passenger(10, CartingState.COMPLETED, 5, 6);
		String passengerString = "ID = 10. Initial = 5. Destination = 6. State = COMPLETED;";
		assertThat(passengerString, is(equalTo(pas.toString())));
	}
}