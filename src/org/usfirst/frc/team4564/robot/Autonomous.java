package org.usfirst.frc.team4564.robot;

import java.util.Calendar;

public class Autonomous {
	public static final int READY = 0, START = 1, TIME_SPIN = 2, DISTANCE_DRIVE = 3;
	private DriveTrain dt;
	private int state = 0;
	
	private long timer = 0;
	private double targetDistance = 0;
	
	//Prepare auto to start.
	public void init() {
		dt = DriveTrain.instance();
		state = READY;
	}
	
	//Launch the autonomous actions by setting the state.
	public void start() {
		state = START;
	}
	
	//Update 
	public void update() {
		//Check the state against a variety of values.
		switch (state) {
			case START:
				//Start by creating a timer aimed three seconds in the future.
				timer = time() + 3000;
				//Advance to the next state to start acting.
				state = TIME_SPIN;
				break;
			case TIME_SPIN:
				//If the time hasn't reached the timer value, spin.
				if (time() < timer) {
					dt.arcadeDrive(0, 0.4);
				}
				//When the time reaches the timer, shut off motors and set up for next state.
				else {
					dt.arcadeDrive(0, 0);
					//Create a target distance 2 feet in front of the bot.
					targetDistance = dt.getDistance() + 24;
					state = DISTANCE_DRIVE;
				}
				break;
			case DISTANCE_DRIVE:
				//If the robot has not reached the target distance, drive forward.
				if (dt.getDistance() < targetDistance) {
					dt.arcadeDrive(0.5, 0);
				}
				//When the robot reaches the target distance, shut off motors and return to ready state.
				else {
					dt.arcadeDrive(0, 0);
					state = READY;
				}
				break;
			//If the provided state is not listed, shut off the motors and put into ready state.
			default:
				dt.arcadeDrive(0, 0);
				state = READY;
				break;
		}
	}
	
	//Return the time in milliseconds since the Epoch.
	public long time() {
		return Calendar.getInstance().getTimeInMillis();
	}
}
