package Server;
import java.util.ArrayList;
import Server.Event;

public class Match {
	private ArrayList<Event> eventList = new ArrayList<Event>();
	private ArrayList<Event> priorityEventList = new ArrayList<Event>();

	public Match(){
		//Constructor, setup here
	}

	public void update(){
		//Gather input from connections

		//Construct new events

		//If there is a priority event, execute that

		//Else, execute other events

		//Package game state

		//Send state to client
	}
}