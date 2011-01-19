package edu.kit.cm.kitcampusguide.applicationlogic.coordinatemanager;

import java.util.Scanner;

import edu.kit.cm.kitcampusguide.standardtypes.WorldPosition;

public class Test {

	private static Scanner in = new Scanner(System.in);
	private static CoordinateManager cm = CoordinateManagerImpl.getInstance();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String input = "";
		while (!input.equals("exit")) {
			input = in.nextLine();
			WorldPosition position = cm.stringToCoordinate(input);
			if (position != null) {
			//System.out.println("latitude: " + position.getLatitude() + ", longitude: " + position.getLongitude());		
			System.out.println(cm.coordinateToString(position));
			} else {
				System.out.println("null");
			}
		}
		
	}

}
