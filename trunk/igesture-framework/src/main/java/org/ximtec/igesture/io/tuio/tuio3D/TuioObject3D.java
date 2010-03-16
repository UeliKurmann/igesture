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
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/
package org.ximtec.igesture.io.tuio.tuio3D;

import org.ximtec.igesture.io.tuio.TuioTime;
import org.ximtec.igesture.io.tuio.interfaces.AbstractTuioObject;

/**
 * The TuioObject class encapsulates /tuio/3Dobj TUIO objects.
 *
 * @author Martin Kaltenbrunner, Bjorn Puype
 * @version 1.4
 */ 
public class TuioObject3D extends TuioContainer3D implements AbstractTuioObject {
	
	/** XY plane */
	public static final int XY = 0;
	/** XZ plane */
	public static final int XZ = 1;
	/** YZ plane */
	public static final int YZ = 2;
	
	/**
	 * The individual symbol ID number that is assigned to each TuioObject.
	 */ 
	protected int symbol_id;
	/**
	 * The rotation angle value in XY plane.
	 */ 
	protected float angle_XY;
	/**
	 * The rotation angle value in XZ plane.
	 */ 
	protected float angle_XZ;
	/**
	 * The rotation angle value in YZ plane.
	 */ 
	protected float angle_YZ;
	/**
	 * The rotation speed value.
	 */ 
	protected float rotation_speed_XY;
	protected float rotation_speed_XZ;
	protected float rotation_speed_YZ;
	/**
	 * The rotation acceleration value.
	 */ 
	protected float rotation_accel;
	/**
	 * Defines the ROTATING state.
	 */ 
	public static final int TUIO_ROTATING = 5;
	
	/**
	 * This constructor takes a TuioTime argument and assigns it along with the provided 
	 * Session ID, Symbol ID, X, Y and Z coordinate and angles between the different planes to the newly created TuioObject3D.
	 *
	 * @param	ttime	the TuioTime to assign
	 * @param	si	the Session ID  to assign
	 * @param	sym	the Symbol ID  to assign
	 * @param	xp	the X coordinate to assign
	 * @param	yp	the Y coordinate to assign
	 * @param	zp	the Z coordinate to assign
	 * @param	a	the angle of the XY plane to assign
	 * @param	b	the angle of the XZ plane to assign
	 * @param	c	the angle of the YZ plane to assign
	 */
	public TuioObject3D (TuioTime ttime, long si, int sym, float xp, float yp, float zp, float a, float b, float c) {
		super(ttime, si,xp,yp,zp);
		symbol_id = sym;
		angle_XY = a;
		angle_XZ = b;
		angle_YZ = c;
		rotation_speed_XY = 0.0f;
		rotation_speed_XZ = 0.0f;
		rotation_speed_YZ = 0.0f;
		rotation_accel = 0.0f;
	}

	/**
	 * This constructor takes the provided Session ID, Symbol ID, X, Y and Z coordinate 
	 * and angles between the different planes, and assigns these values to the newly created TuioObject3D.
	 *
	 * @param	si	the Session ID  to assign
	 * @param	sym	the Symbol ID  to assign
	 * @param	xp	the X coordinate to assign
	 * @param	yp	the Y coordinate to assign
	 * @param	zp	the Z coordinate to assign
	 * @param	a	the angle of the XY plane to assign
	 * @param	b	the angle of the XZ plane to assign
	 * @param	c	the angle of the YZ plane to assign
	 */	
	public TuioObject3D (long si, int sym, float xp, float yp, float zp, float a, float b, float c) {
		super(si,xp,yp,zp);
		symbol_id = sym;
		angle_XY = a;
		angle_XZ = b;
		angle_YZ = c;
		rotation_speed_XY = 0.0f;
		rotation_speed_XZ = 0.0f;
		rotation_speed_YZ = 0.0f;
		rotation_accel = 0.0f;
	}

	/**
	 * This constructor takes the attributes of the provided TuioObject3D 
	 * and assigns these values to the newly created TuioObject3D.
	 *
	 * @param	tobj	the TuioObject3D to assign
	 */
	public TuioObject3D (TuioObject3D tobj) {
		super(tobj);
		symbol_id = tobj.getSymbolID();
		angle_XY = tobj.getAngle(XY);
		angle_XZ = tobj.getAngle(XZ);
		angle_YZ = tobj.getAngle(YZ);
		rotation_speed_XY = 0.0f;
		rotation_speed_XZ = 0.0f;
		rotation_speed_YZ = 0.0f;		
		rotation_accel = 0.0f;
	}

	/**
	 * Takes a TuioTime argument and assigns it along with the provided 
	 * X, Y and Z coordinate, angle, X, Y and Z velocity, motion acceleration,
	 * rotation speed and rotation acceleration to the private TuioObject3D attributes.
	 *
	 * @param	ttime	the TuioTime to assign
	 * @param	xp	the X coordinate to assign
	 * @param	yp	the Y coordinate to assign
	 * @param	zp	the Z coordinate to assign
	 * @param	a	the angle coordinate to assign (XY plane)
	 * @param	b	the angle coordinate to assign (XZ plane)
	 * @param	c	the angle coordinate to assign (YZ plane)
	 * @param	xs	the X velocity to assign
	 * @param	ys	the Y velocity to assign
	 * @param	zs	the Z velocity to assign
	 * @param	rsXY	the rotation velocity to assign in the XY plane
	 * @param	rsXZ	the rotation velocity to assign in the XZ plane
	 * @param	rsYZ	the rotation velocity to assign in the YZ plane
	 * @param	ma	the motion acceleration to assign
	 * @param	ra	the rotation acceleration to assign
	 */
	public void update (TuioTime ttime, float xp, float yp, float zp, float a, float b, float c, float xs, float ys, float zs, float rsXY, float rsXZ, float rsYZ, float ma, float ra) {
		super.update(ttime,xp,yp,zp,xs,ys,zs,ma);
		angle_XY = a;
		angle_XZ = b;
		angle_YZ = c;
		rotation_speed_XY = rsXY;
		rotation_speed_XZ = rsXZ;
		rotation_speed_YZ = rsYZ;
		rotation_accel = ra;
		if ((rotation_accel!=0) && (state!=TUIO_STOPPED)) state = TUIO_ROTATING;
	}

	/**
	 * Assigns the provided X, Y and Z coordinate, angle, X, Y and Z velocity, motion acceleration
	 * rotation velocity and rotation acceleration to the private TuioContainer3D attributes.
	 * The TuioTime time stamp remains unchanged.
	 *
	 * @param	xp	the X coordinate to assign
	 * @param	yp	the Y coordinate to assign
	 * @param	zp	the Z coordinate to assign
	 * @param	a	the angle coordinate to assign (XY plane)
	 * @param	b	the angle coordinate to assign (XZ plane)
	 * @param	c	the angle coordinate to assign (YZ plane)
	 * @param	xs	the X velocity to assign
	 * @param	ys	the Y velocity to assign
	 * @param	zs	the Z velocity to assign
	 * @param	rsXY	the rotation velocity to assign in the XY plane
	 * @param	rsXZ	the rotation velocity to assign in the XZ plane
	 * @param	rsYZ	the rotation velocity to assign in the YZ plane
	 * @param	ma	the motion acceleration to assign
	 * @param	ra	the rotation acceleration to assign
	 */
	public void update (float xp, float yp, float zp, float a, float b, float c, float xs, float ys, float zs, float rsXY, float rsXZ, float rsYZ, float ma, float ra) {
		super.update(xp,yp,zp,xs,ys,zs,ma);
		angle_XY = a;
		angle_XZ = b;
		angle_YZ = c;
		rotation_speed_XY = rsXY;
		rotation_speed_XZ = rsXZ;
		rotation_speed_YZ = rsYZ;
		rotation_accel = ra;
		if ((rotation_accel!=0) && (state!=TUIO_STOPPED)) state = TUIO_ROTATING;
	}
	
	/**
	 * Takes a TuioTime argument and assigns it along with the provided 
	 * X, Y and Z coordinate and angles to the private TuioObject3D attributes.
	 * The speed and acceleration values are calculated accordingly.
	 *
	 * @param	ttime	the TuioTime to assign
	 * @param	xp	the X coordinate to assign
	 * @param	yp	the Y coordinate to assign
	 * @param	zp	the Z coordinate to assign
	 * @param	a	the angle coordinate to assign for XY plane
	 * @param	b	the angle coordinate to assign for XZ plane
	 * @param	c	the angle coordinate to assign for YZ plane
	 */
	public void update (TuioTime ttime, float xp, float yp, float zp, float a, float b, float c) {
		TuioPoint3D lastPoint = path.lastElement();
		super.update(ttime,xp,yp,zp);
		TuioTime diffTime = currentTime.subtract(lastPoint.getTuioTime());
		float dt = diffTime.getTotalMilliseconds()/1000.0f;
		//FIXME
		float last_angle_XY = angle_XY;
		float last_angle_XZ = angle_XZ;
		float last_angle_YZ = angle_YZ;
		float last_rotation_speed_XY = rotation_speed_XY;
		float last_rotation_speed_XZ = rotation_speed_XZ;
		float last_rotation_speed_YZ = rotation_speed_YZ;
		angle_XY = a;
		angle_XZ = b;
		angle_YZ = c;
		
		float da = (this.angle_XY-last_angle_XY)/(2.0f*(float)Math.PI);
		if (da>0.75f) da-=1.0f;
		else if (da<-0.75f) da+=1.0f;
		float db = (this.angle_XZ-last_angle_XZ)/(2.0f*(float)Math.PI);
		if (db>0.75f) db-=1.0f;
		else if (db<-0.75f) db+=1.0f;
		float dc = (this.angle_YZ-last_angle_YZ)/(2.0f*(float)Math.PI);
		if (dc>0.75f) dc-=1.0f;
		else if (dc<-0.75f) dc+=1.0f;
		
		float last_rotation_speed = (float) Math.sqrt(last_rotation_speed_XY*last_rotation_speed_XY+last_rotation_speed_XZ*last_rotation_speed_XZ+last_rotation_speed_YZ*last_rotation_speed_YZ);
		rotation_speed_XY = da/dt;
		rotation_speed_XZ = db/dt;
		rotation_speed_YZ = dc/dt;
		float rotation_speed = (float)Math.sqrt(rotation_speed_XY*rotation_speed_XY+rotation_speed_XZ*rotation_speed_XZ+rotation_speed_YZ*rotation_speed_YZ);
		//end FIXME
		rotation_accel = (rotation_speed - last_rotation_speed)/dt;
		if ((rotation_accel!=0) && (state!=TUIO_STOPPED)) state = TUIO_ROTATING;
	}
	
	/**
	 * Takes the attributes of the provided TuioObject3D 
	 * and assigns these values to this TuioObject3D.
	 * The TuioTime time stamp of this TuioContainer3D remains unchanged.
	 *
	 * @param	tobj	the TuioContainer3D to assign
	 */	
	public void update (TuioObject3D tobj) {
		super.update(tobj);
		angle_XY = tobj.getAngle(TuioObject3D.XY);
		angle_XZ = tobj.getAngle(TuioObject3D.XZ);
		angle_YZ = tobj.getAngle(TuioObject3D.YZ);
		rotation_speed_XY = tobj.getRotationSpeedXY();
		rotation_speed_XZ = tobj.getRotationSpeedXZ();
		rotation_speed_YZ = tobj.getRotationSpeedYZ();
		rotation_accel = tobj.getRotationAccel();
		if ((rotation_accel!=0) && (state!=TUIO_STOPPED)) state = TUIO_ROTATING;
	}
	
	/**
	 * This method is used to calculate the speed and acceleration values of a
	 * TuioObject3D with unchanged position and angle.
	 *
	 * @param	ttime	the TuioTime to assign
	 */
	public void stop (TuioTime ttime) {
		update(ttime,xpos,ypos,zpos,angle_XY,angle_XZ,angle_YZ);
	}

	/**
	 * Returns the symbol ID of this TuioObject3D.
	 * @return	the symbol ID of this TuioObject3D
	 */
	public int getSymbolID() {
		return symbol_id;
	}
		
	/**
	 * Returns the requested rotation angle of this TuioObject3D.
	 * @param	planes	the planes of which to get the rotation angle.
	 * @return	the requested rotation angle of this TuioObject3D
	 */
	public float getAngle(int planes) {
		float angle = 0;
		switch(planes)
		{
		case XY:
			angle = angle_XY;
			break;
		case XZ:
			angle = angle_XZ;
			break;
		case YZ:
			angle = angle_YZ;
			break;
		default:
			System.err.println("Unknown entered planes");
		}
		return angle;
	}

	/**
	 * Returns the rotation angle in degrees of this TuioObject3D.
	 * @param	planes	the planes of which to get the rotation angle. 
	 * @return	the rotation angle in degrees of this TuioObject3D
	 */
	public float getAngleDegrees(int planes) {
		return getAngle(planes)/(float)Math.PI*180.0f;
	}
	
	/**
	 * Returns the rotation speed of this TuioObject3D.
	 * @return	the rotation speed of this TuioObject3D
	 */
	public float getRotationSpeedXY() {
		return rotation_speed_XY;
	}
	public float getRotationSpeedXZ() {
		return rotation_speed_XZ;
	}
	public float getRotationSpeedYZ() {
		return rotation_speed_YZ;
	}
		
	/**
	 * Returns the rotation acceleration of this TuioObject3D.
	 * @return	the rotation acceleration of this TuioObject3D
	 */
	public float getRotationAccel() {
		return rotation_accel;
	}
	
	/**
	 * @return the symbol_id
	 */
	public int getSymbol_id() {
		return symbol_id;
	}

	/**
	 * @return the angle_XY
	 */
	public float getAngleXY() {
		return angle_XY;
	}

	/**
	 * @return the angle_XZ
	 */
	public float getAngleXZ() {
		return angle_XZ;
	}

	/**
	 * @return the angle_YZ
	 */
	public float getAngleYZ() {
		return angle_YZ;
	}

	/**
	 * Returns true of this TuioObject3D is moving.
	 * @return	true of this TuioObject3D is moving
	 */
	public boolean isMoving() { 
		if ((state==TUIO_ACCELERATING) || (state==TUIO_DECELERATING) || (state==TUIO_ROTATING)) return true;
		else return false;
	}
	
}
