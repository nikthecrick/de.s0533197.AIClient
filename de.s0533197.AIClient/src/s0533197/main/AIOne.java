package s0533197.main;

import java.awt.Point;

import lenz.htw.ai4g.ai.AI;
import lenz.htw.ai4g.ai.Info;

public class AIOne extends AI {

	private final float ANGULAR_ACCELERATION = 1;
	private final float ANGLE_TOLERANCE = 0.1f;

	public AIOne(Info info) {
		super(info);
		System.out.println("First Checkpoint: " + info.getCurrentCheckpoint());
		System.out.println("Startposition: [" + info.getX() + "; "
				+ info.getY() + "]");
		System.out.println("Start Orientation: " + info.getOrientation());
	}

	@Override
	public float getAcceleration() {
		return info.getMaxAcceleration();
	}

	@Override
	public float getAngularAcceleration() {

		return seek(info.getCurrentCheckpoint());
	}

	private float seek(Point goal) {
		
		// the casting is okay because it doesnt have to be 100% exact
		float goalAngle = getAngle(new Point((int) info.getX(), (int) info.getY()), goal);
		
		float currentAngle = getOrientationAsGrade();

		// TODO remove
		printSpeed(new Point((int) info.getX(), (int) info.getY()), goal, goalAngle, currentAngle);
		

		if (goalAngle > currentAngle
				&& info.getAngularVelocity() <= ANGLE_TOLERANCE) {
			return ANGULAR_ACCELERATION;
		} else if (goalAngle < currentAngle
				&& info.getAngularVelocity() >= -ANGLE_TOLERANCE) {
			return -ANGULAR_ACCELERATION;
		}

		return 0;
	}
	
	private float getAngle(Point p1, Point p2) {
		
		float angle = (float) Math.toDegrees(Math.atan2(p2.x - p1.x, p2.y - p1.y));
		
		if(angle < 0) {
			angle += 360;
		}
		
		return angle;
	}

	@Override
	public String getName() {
		return "Riptor XXX";
	}

	@Override
	public String getTextureResourceName() {
		return "/s0533197/alternate_texture.png";
	}

	/**
	 * Transforms the double pi values that you get from
	 * <code>info.getOrientation()</code> to grade values
	 * 
	 * @return
	 */
	private float getOrientationAsGrade() {
		// get positive value of the orientation
		float posOrientation = info.getOrientation();
		if (posOrientation < 0) {
			posOrientation = posOrientation * -1;
		}

		// convert positive value
		float output = (float) ((posOrientation / Math.PI) * 180);

		// if it was negative add 180
		if (info.getOrientation() < 0) {
			output = 180 + (180 - output);
		}

		return output;
	}
	

	@Override
	public void drawDebugStuff() {
		super.drawDebugStuff();
		
	}

	private void printSpeed(Point car, Point goal, float goalAngle, float currentAngle) {
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
		System.out.println("Carposition: " + car);
		System.out.println("Current goal: " + goal);
		System.out.println("Goal angle: " + goalAngle);
		System.out.println("Current angle: " + currentAngle);
		System.out.println("Current angular velocity: " + info.getAngularVelocity());
	}
}
