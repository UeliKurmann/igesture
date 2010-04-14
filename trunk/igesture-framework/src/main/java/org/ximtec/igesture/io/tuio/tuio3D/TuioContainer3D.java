/*
 TUIO Java backend - part of the reacTIVision project
 http://reactivision.sourceforge.net/
 
 Copyright (c) 2005-2009 Martin Kaltenbrunner <mkalten@iua.upf.edu>
 
 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.
 
 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 GNU General Public License for more details.
 
 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.ximtec.igesture.io.tuio.tuio3D;

import java.util.*;

import org.ximtec.igesture.io.tuio.TuioTime;
import org.ximtec.igesture.io.tuio.interfaces.AbstractTuioContainer;
import org.ximtec.igesture.io.tuio.tuio2D.TuioCursor;
import org.ximtec.igesture.io.tuio.tuio2D.TuioObject;


/**
 * The abstract TuioContainer class defines common attributes that apply to both subclasses {@link TuioObject} and {@link TuioCursor}.
 *
 * @author Martin Kaltenbrunner, Bjorn Puype
 * @version 1.4
 */ 
abstract class TuioContainer3D extends TuioPoint3D implements AbstractTuioContainer {

	/**
	 * The unique session ID number that is assigned to each TUIO object or cursor.
	 */ 
	protected long session_id;
	/**
	 * The X-axis velocity value.
	 */ 
	protected float x_speed;
	/**
	 * The Y-axis velocity value.
	 */ 
	protected float y_speed;
	/**
	 * The Z-axis velocity value.
	 */ 
	protected float z_speed;
	/**
	 * The motion speed value.
	 */ 
	protected float motion_speed;
	/**
	 * The motion acceleration value.
	 */ 
	protected float motion_accel;
	
	/**
	 * A Vector of TuioPoint3Ds containing all the previous positions of the TUIO component.
	 */ 
	protected Vector<TuioPoint3D> path;
	/**
	 * Defines the ADDED state.
	 */ 
	public static final int TUIO_ADDED = 0;
	/**
	 * Defines the ACCELERATING state.
	 */ 
	public static final int TUIO_ACCELERATING = 1;
	/**
	 * Defines the DECELERATING state.
	 */ 
	public static final int TUIO_DECELERATING = 2;
	/**
	 * Defines the STOPPED state.
	 */ 
	public static final int TUIO_STOPPED = 3;
	/**
	 * Defines the REMOVED state.
	 */ 
	public static final int TUIO_REMOVED = 4;
	/**
	 * Reflects the current state of the TuioComponent
	 */ 
	protected int state;
	
	/**
	 * This constructor takes a TuioTime argument and assigns it along with the provided 
	 * Session ID, X, Y and Z coordinate to the newly created TuioContainer3D.
	 *
	 * @param	ttime	the TuioTime to assign
	 * @param	si	the Session ID to assign
	 * @param	xp	the X coordinate to assign
	 * @param	yp	the Y coordinate to assign
	 * @param	zp	the Z coordinate to assign
	 */
	TuioContainer3D(TuioTime ttime, long si, float xp, float yp, float zp) {
		super(ttime,xp,yp,zp);
		
		session_id = si;
		x_speed = 0.0f;
		y_speed = 0.0f;
		z_speed = 0.0f;
		motion_speed = 0.0f;
		motion_accel = 0.0f;
		
		path = new Vector<TuioPoint3D>();
		path.addElement(new TuioPoint3D(currentTime,xpos,ypos,zpos));
		state = TUIO_ADDED;
	}
	
	/**
	 * This constructor takes the provided Session ID, X, Y and 2 coordinate 
	 * and assigns these values to the newly created TuioContainer3D.
	 *
	 * @param	si	the Session ID to assign
	 * @param	xp	the X coordinate to assign
	 * @param	yp	the Y coordinate to assign
	 * @param 	zp	the Z coordinate to assign
	 */
	TuioContainer3D(long si, float xp, float yp, float zp) {
		super(xp,yp,zp);
		
		session_id = si;
		x_speed = 0.0f;
		y_speed = 0.0f;
		z_speed = 0.0f;
		motion_speed = 0.0f;
		motion_accel = 0.0f;
		
		path = new Vector<TuioPoint3D>();
		path.addElement(new TuioPoint3D(currentTime,xpos,ypos,zpos));
		state = TUIO_ADDED;
	}
	
	/**
	 * This constructor takes the attributes of the provided TuioContainer3D 
	 * and assigns these values to the newly created TuioContainer3D.
	 *
	 * @param	tcon	the TuioContainer3D to assign
	 */
	TuioContainer3D(TuioContainer3D tcon) {
		super(tcon);
		
		session_id = tcon.getSessionID();
		x_speed = 0.0f;
		y_speed = 0.0f;
		z_speed = 0.0f;
		motion_speed = 0.0f;
		motion_accel = 0.0f;
		
		path = new Vector<TuioPoint3D>();
		path.addElement(new TuioPoint3D(currentTime,xpos,ypos,zpos));
		state = TUIO_ADDED;
	}
	
	/**
	 * Takes a TuioTime argument and assigns it along with the provided 
	 * X, Y and Z coordinate to the private TuioContainer3D attributes.
	 * The speed and acceleration values are calculated accordingly.
	 *
	 * @param	ttime	the TuioTime to assign
	 * @param	xp	the X coordinate to assign
	 * @param	yp	the Y coordinate to assign
	 * @param	zp	the Z coordinate to assign
	 */
	public void update(TuioTime ttime, float xp, float yp, float zp) {
		TuioPoint3D lastPoint = path.lastElement();
		super.update(ttime,xp,yp,zp);
		
		TuioTime diffTime = currentTime.subtract(lastPoint.getTuioTime());
		float dt = diffTime.getTotalMilliseconds()/1000.0f;
		float dx = this.xpos - lastPoint.getX();
		float dy = this.ypos - lastPoint.getY();
		float dz = this.zpos - lastPoint.getZ();
		float dist = (float)Math.sqrt(dx*dx+dy*dy+dz*dz);
		float last_motion_speed = this.motion_speed;
		
		this.x_speed = dx/dt;
		this.y_speed = dy/dt;
		this.z_speed = dz/dt;
		this.motion_speed = dist/dt;
		this.motion_accel = (motion_speed - last_motion_speed)/dt;
		
		path.addElement(new TuioPoint3D(currentTime,xpos,ypos,zpos));

		if (motion_accel>0) state = TUIO_ACCELERATING;
		else if (motion_accel<0) state = TUIO_DECELERATING;
		else state = TUIO_STOPPED;
	}
	
	/**
	 * This method is used to calculate the speed and acceleration values of
	 * TuioContainer3Ds with unchanged positions.
	 */
	public void stop(TuioTime ttime) {
		update(ttime,xpos,ypos,zpos);
	}
	
	/**
	 * Takes a TuioTime argument and assigns it along with the provided 
	 * X, Y and Z coordinate; X, Y and Z velocity and acceleration
	 * to the private TuioContainer3D attributes.
	 *
	 * @param	ttime	the TuioTime to assign
	 * @param	xp	the X coordinate to assign
	 * @param	yp	the Y coordinate to assign
	 * @param	zp	the Z coordinate to assign
	 * @param	xs	the X velocity to assign
	 * @param	ys	the Y velocity to assign
	 * @param	zs	the Z velocity to assign
	 * @param	ma	the acceleration to assign
	 */
	public void update(TuioTime ttime, float xp, float yp, float zp, float xs, float ys, float zs, float ma) {
		super.update(ttime,xp,yp,zp);
		x_speed = xs;
		y_speed = ys;
		z_speed = zs;
		motion_speed = (float)Math.sqrt(x_speed*x_speed+y_speed*y_speed+z_speed*z_speed);
		motion_accel = ma;
		path.addElement(new TuioPoint3D(currentTime,xpos,ypos,zpos));
		
		if (motion_accel>0) state = TUIO_ACCELERATING;
		else if (motion_accel<0) state = TUIO_DECELERATING;
		else state = TUIO_STOPPED;
	}
	
	/**
	 * Assigns the provided X, Y and Z coordinate; X, Y and Z velocity and acceleration
	 * to the private TuioContainer attributes. The TuioTime time stamp remains unchanged.
	 *
	 * @param	xp	the X coordinate to assign
	 * @param	yp	the Y coordinate to assign
	 * @param	zp	the Z coordinate to assign
	 * @param	xs	the X velocity to assign
	 * @param	ys	the Y velocity to assign
	 * @param	zs	the Z velocity to assign
	 * @param	ma	the acceleration to assign
	 */
	public void update(float xp, float yp, float zp, float xs,float ys, float zs, float ma) {
		super.update(xp,yp,zp);
		x_speed = xs;
		y_speed = ys;
		z_speed = zs;
		motion_speed = (float)Math.sqrt(x_speed*x_speed+y_speed*y_speed+z_speed*z_speed);
		motion_accel = ma;
		path.addElement(new TuioPoint3D(currentTime,xpos,ypos,zpos));
		
		if (motion_accel>0) state = TUIO_ACCELERATING;
		else if (motion_accel<0) state = TUIO_DECELERATING;
		else state = TUIO_STOPPED;
	}
	
	/**
	 * Takes the attributes of the provided TuioContainer3D 
	 * and assigns these values to this TuioContainer3D.
	 * The TuioTime time stamp of this TuioContainer3D remains unchanged.
	 *
	 * @param	tcon	the TuioContainer3D to assign
	 */
	public void update (TuioContainer3D tcon) {
		super.update(tcon);
		x_speed = tcon.getXSpeed();
		y_speed = tcon.getYSpeed();
		z_speed = tcon.getZSpeed();
		motion_speed = tcon.getMotionSpeed();
		motion_accel= tcon.getMotionAccel();
		path.addElement(new TuioPoint3D(currentTime,xpos,ypos,zpos));
		
		if (motion_accel>0) state = TUIO_ACCELERATING;
		else if (motion_accel<0) state = TUIO_DECELERATING;
		else state = TUIO_STOPPED;
	}
	
	/**
	 * Assigns the REMOVE state to this TuioContainer3D and sets
	 * its TuioTime time stamp to the provided TuioTime argument.
	 *
	 * @param	ttime	the TuioTime to assign
	 */
	public void remove(TuioTime ttime) {
		currentTime = new TuioTime(ttime);
		state = TUIO_REMOVED;
	}
	
	/**
	 * Returns the Session ID of this TuioContainer3D.
	 * @return	the Session ID of this TuioContainer3D
	 */
	public long getSessionID() {
		return session_id;
	}
	
	/**
	 * Returns the X velocity of this TuioContainer3D.
	 * @return	the X velocity of this TuioContainer3D
	 */
	public float getXSpeed() {
		return x_speed;
	}
	
	/**
	 * Returns the Y velocity of this TuioContainer3D.
	 * @return	the Y velocity of this TuioContainer3D
	 */
	public float getYSpeed() {
		return y_speed;
	}
	
	/**
	 * Returns the Z velocity of this TuioContainer3D.
	 * @return	the Z velocity of this TuioContainer3D
	 */
	public float getZSpeed() {
		return z_speed;
	}
	
	/**
	 * Returns the position of this TuioContainer3D.
	 * @return	the position of this TuioContainer3D
	 */
	public TuioPoint3D getPosition() {
		return new TuioPoint3D(xpos,ypos,zpos);
	}
	
	/**
	 * Returns the path of this TuioContainer3D.
	 * @return	the path of this TuioContainer3D
	 */
	public Vector<TuioPoint3D> getPath() {
		return path;
	}
	
	/**
	 * Returns the motion speed of this TuioContainer3D.
	 * @return	the motion speed of this TuioContainer3D
	 */
	public float getMotionSpeed() {
		return motion_speed;
	}
	
	/**
	 * Returns the motion acceleration of this TuioContainer3D.
	 * @return	the motion acceleration of this TuioContainer3D
	 */
	public float getMotionAccel() {
		return motion_accel;
	}
	
	/**
	 * Returns the TUIO state of this TuioContainer3D.
	 * @return	the TUIO state of this TuioContainer3D
	 */
	public int getTuioState() {
		return state;
	}
	
	/**
	 * Returns true of this TuioContainer3D is moving.
	 * @return	true of this TuioContainer3D is moving
	 */
	public boolean isMoving() { 
		if ((state==TUIO_ACCELERATING) || (state==TUIO_DECELERATING)) return true;
		else return false;
	}
	
}