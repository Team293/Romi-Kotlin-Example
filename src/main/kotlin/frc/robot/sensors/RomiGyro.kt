// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.sensors

import edu.wpi.first.hal.SimDevice
import edu.wpi.first.hal.SimDouble

class RomiGyro {
    private var mSimRateX: SimDouble? = null
    private var mSimRateY: SimDouble? = null
    private var mSimRateZ: SimDouble? = null
    private var mSimAngleX: SimDouble? = null
    private var mSimAngleY: SimDouble? = null
    private var mSimAngleZ: SimDouble? = null
    private var mAngleXOffset = 0.0
    private var mAngleYOffset = 0.0
    private var mAngleZOffset = 0.0

    /** Create a new RomiGyro.  */
    init {
        val gyroSimDevice = SimDevice.create("Gyro:RomiGyro")
        if (gyroSimDevice != null) {
            gyroSimDevice.createBoolean("init", SimDevice.Direction.kOutput, true)
            mSimRateX = gyroSimDevice.createDouble("rate_x", SimDevice.Direction.kInput, 0.0)
            mSimRateY = gyroSimDevice.createDouble("rate_y", SimDevice.Direction.kInput, 0.0)
            mSimRateZ = gyroSimDevice.createDouble("rate_z", SimDevice.Direction.kInput, 0.0)
            mSimAngleX = gyroSimDevice.createDouble("angle_x", SimDevice.Direction.kInput, 0.0)
            mSimAngleY = gyroSimDevice.createDouble("angle_y", SimDevice.Direction.kInput, 0.0)
            mSimAngleZ = gyroSimDevice.createDouble("angle_z", SimDevice.Direction.kInput, 0.0)
        }
    }

    /**
     * Get the rate of turn in degrees-per-second around the X-axis.
     *
     * @return rate of turn in degrees-per-second
     */
    val rateX: Double
        get() = mSimRateX?.get() ?: 0.0

    /**
     * Get the rate of turn in degrees-per-second around the Y-axis.
     *
     * @return rate of turn in degrees-per-second
     */
    val rateY: Double
        get() {
            return mSimRateY?.get() ?: 0.0
        }

    /**
     * Get the rate of turn in degrees-per-second around the Z-axis.
     *
     * @return rate of turn in degrees-per-second
     */
    val rateZ: Double
        get() {
            return mSimRateZ?.get() ?: 0.0
        }

    /**
     * Get the currently reported angle around the X-axis.
     *
     * @return current angle around X-axis in degrees
     */
    val angleX: Double
        get() {
            return if (mSimAngleX != null) {
                mSimAngleX!!.get() - mAngleXOffset
            } else 0.0
        }

    /**
     * Get the currently reported angle around the X-axis.
     *
     * @return current angle around Y-axis in degrees
     */
    val angleY: Double
        get() {
            return if (mSimAngleY != null) {
                mSimAngleY!!.get() - mAngleYOffset
            } else 0.0
        }

    /**
     * Get the currently reported angle around the Z-axis.
     *
     * @return current angle around Z-axis in degrees
     */
    val angleZ: Double
        get() {
            return if (mSimAngleZ != null) {
                mSimAngleZ!!.get() - mAngleZOffset
            } else 0.0
        }

    /** Reset the gyro angles to 0.  */
    fun reset() {
        if (mSimAngleX != null) {
            mAngleXOffset = mSimAngleX!!.get()
            mAngleYOffset = mSimAngleY!!.get()
            mAngleZOffset = mSimAngleZ!!.get()
        }
    }
}