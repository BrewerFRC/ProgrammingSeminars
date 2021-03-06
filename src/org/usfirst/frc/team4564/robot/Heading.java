package org.usfirst.frc.team4564.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

/**
 * A full-featured heading hold and Gyro controller class.  Built in PID control when using {@link Heading#turnRate()} for turn output to drivetrain.
 * 
 * @author Brewer FIRST Robotics Team 4564
 * @author Evan McCoy
 *
 */

public class Heading {
	public static final double P = 0.08;
	public static final double I = 0;
	public static final double D = 1;//250.0;
	
	//You can change the class type (ADXRS450_Gyro) to a more general class such as AnalogGyro if you are not using this specific SPI gyro model.
	private ADXRS450_Gyro gyro;
	//PID takes cumulative angles
	private PID pid;
	private boolean headingHold;
	
	/**
	 * Initializes heading with the specified PID values.
	 * 
	 * @param p the p scaler.
	 * @param i the integral scaler.
	 * @param d the derivative scaler.
	 */
	public Heading(double p, double i, double d) {
		pid = new PID(p, i, d, false, "gyro");
		//PID is dealing with error; an error of 0 is always desired.
		pid.setTarget(0.0);
		pid.setMin(0.0);
		pid.setOutputLimits(-1, 1);
		gyro = new ADXRS450_Gyro();	
	}
	
	/**
	 * Resets gyro PID and gyro
	 */
	public void reset() {
		gyro.reset();
		resetPID();
	}
	
	/**
	 * Resets gyro PID
	 */
	public void resetPID() {
		pid.reset();
	}
	
	/**
	 * Sets new PID values to the gyro PID.
	 * 
	 * @param p the p scaler.
	 * @param i the integral scaler.
	 * @param d the derivative scaler.s
	 */
	public void setPID(double p, double i, double d) {
		pid.setP(p);
		pid.setI(i);
		pid.setD(d);
	}
	
	/*
	 * Returns the targeted angle.
	 * 
	 * @return double the angle in degrees.
	 */
	public double getTargetAngle() {
		return pid.getTarget(); 
	}
	
	/**
	 * Returns the targeted heading.
	 * 
	 * @return double the heading in degrees.
	 */
	public double getTargetHeading(){
		return angleToHeading(pid.getTarget());
	}
	
	/**
	 * Convert angle to heading in partial degrees, 0.01 accuracy
	 * 
	 * @param angle the input angle to convert.
	 * @return double the heading in degrees.
	 */
	public double angleToHeading(double angle) {
		double heading = (angle * 100) % 36000 / 100;
		if (heading < 0) {
			heading += 360;
		}
		return heading; 
	}
	
	/**
	 * Returns current heading.
	 * 
	 * @return double the current heading in degrees.
	 */
	public double getHeading() {
		return angleToHeading(getAngle());
	}
	
	/**
	 * Returns current angle.
	 * 
	 * @return double the current angle in degrees.
	 */
	public double getAngle() {
		return gyro.getAngle();
	}
	
	/**
	 * Sets target angle given a heading, and will turn left or right to target dependent on which is shortest.
	 * 
	 * @param heading the heading to set the target to in degrees.
	 */
	public void setHeading(double heading) {
		//Find the short and long path to the desired heading.
		double changeLeft = (-360 + (heading - getHeading())) % 360;
		double changeRight = (360 - (getHeading() - heading)) % 360;
		double change = (Math.abs(changeLeft) < Math.abs(changeRight)) ? changeLeft : changeRight;
		pid.setTarget(getAngle() + change);
	}
	
	/**
	 * Turn a number of degrees relative to current angle.
	 * 
	 * @param degrees the amount of degrees relative to current angle.
	 */
	public void relTurn(double degrees){
		pid.setTarget(getAngle() + degrees);
	}
	
	/**
	 * Modifies the current target by the defined term.
	 * 
	 * @param degrees the amount of degrees to change the target by.
	 */
	public void incrementTargetAngle(double degrees) {
		pid.setTarget(pid.getTarget() + degrees);
	}
	
	/**
	 * Activates or deactivates heading hold.  If setting heading hold, it will reset the PID and and set target heading to current heading
	 * 
	 * @param headingHold whether or not heading hold should be enabled.
	 */
	public void setHeadingHold(boolean headingHold) {
		if (headingHold) {
			resetPID();
			this.headingHold = true;
			//Set target angle to current heading.
			setHeading(getHeading());
		}
		else {
			resetPID();
			this.headingHold = false;
		}
	}
	
	/**
	 * Returns state of heading hold.
	 * 
	 * @return boolean whether or not heading hold is enabled.
	 */
	public boolean isHeadingHold() {
		return headingHold;
	}
	
	/**
	 * This returns the PID-recommended turn power required to turn to target heading.  If heading hold is off, turn rate is always 0.
	 * 
	 * @return double the PID recommended turn rate.
	 */
	public double turnRate() {
		pid.update();
		if (headingHold) {
			double turnRate = pid.calc(gyro.getAngle());
			//Return the PID calculation of the shorter path.
			return turnRate;
		}
		else {
			return 0.0;
		}
	}
}
