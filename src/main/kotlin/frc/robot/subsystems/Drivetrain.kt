// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.subsystems

import edu.wpi.first.wpilibj.BuiltInAccelerometer
import edu.wpi.first.wpilibj.Encoder
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj.motorcontrol.Spark
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.sensors.RomiGyro

class Drivetrain : SubsystemBase() {
    // The Romi has the left and right motors set to
    // PWM channels 0 and 1 respectively
    private val mLeftMotor = Spark(0)
    private val mRightmotor = Spark(1)

    // The Romi has onboard encoders that are hardcoded
    // to use DIO pins 4/5 and 6/7 for the left and right
    private val mLeftencoder = Encoder(4, 5)
    private val mRightencoder = Encoder(6, 7)

    // Set up the differential drive controller
    private val mDiffdrive = DifferentialDrive(mLeftMotor, mRightmotor)

    // Set up the RomiGyro
    private val mGyro = RomiGyro()

    // Set up the BuiltInAccelerometer
    private val mAccelerometer = BuiltInAccelerometer()

    /** Creates a new Drivetrain.  */
    init {
        // We need to invert one side of the drivetrain so that positive voltages
        // result in both sides moving forward. Depending on how your robot's
        // gearbox is constructed, you might have to invert the left side instead.
        mRightmotor.inverted = true

        // Use inches as unit for encoder distances
        mLeftencoder.distancePerPulse = Math.PI * kWheelDiameterInch / kCountsPerRevolution
        mRightencoder.distancePerPulse = Math.PI * kWheelDiameterInch / kCountsPerRevolution
        resetEncoders()
    }

    fun arcadeDrive(xaxisSpeed: Double, zaxisRotate: Double) {
        mDiffdrive.arcadeDrive(xaxisSpeed, zaxisRotate)
    }

    fun resetEncoders() {
        mLeftencoder.reset()
        mRightencoder.reset()
    }

    val leftEncoderCount: Int
        get() = mLeftencoder.get()
    val rightEncoderCount: Int
        get() = mRightencoder.get()
    val leftDistanceInch: Double
        get() = mLeftencoder.distance
    val rightDistanceInch: Double
        get() = mRightencoder.distance
    val averageDistanceInch: Double
        get() = (leftDistanceInch + rightDistanceInch) / 2.0

    /**
     * The acceleration in the X-axis.
     *
     * @return The acceleration of the Romi along the X-axis in Gs
     */
    val accelX: Double
        get() = mAccelerometer.x

    /**
     * The acceleration in the Y-axis.
     *
     * @return The acceleration of the Romi along the Y-axis in Gs
     */
    val accelY: Double
        get() = mAccelerometer.y

    /**
     * The acceleration in the Z-axis.
     *
     * @return The acceleration of the Romi along the Z-axis in Gs
     */
    val accelZ: Double
        get() = mAccelerometer.z

    /** Reset the gyro.  */
    fun resetGyro() {
        mGyro.reset()
    }

    override fun periodic() {
        // This method will be called once per scheduler run
    }

    companion object {
        private const val kCountsPerRevolution = 1440.0
        private const val kWheelDiameterInch = 2.75591 // 70 mm
    }
}