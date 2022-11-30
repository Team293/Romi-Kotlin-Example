// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.commands

import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.Constants.Companion.DEFAULT_ARCADE_JOY_DEADBAND
import frc.robot.Constants.Companion.DEFAULT_FORZA_DEADBAND
import frc.robot.Constants.Companion.DEFAULT_FORZA_MODE
import frc.robot.Constants.Companion.DEFAULT_MAX_TURNING_SPEED
import frc.robot.Constants.Companion.DEFAULT_MAX_VELOCITY_PERCENTAGE
import frc.robot.classes.RomiUtils
import frc.robot.subsystems.Drivetrain

class ArcadeDrive(
    private val m_drivetrain: Drivetrain, private val m_XboxController: XboxController
) : CommandBase() {
    private var m_arcadeDeadband: Double
    private var m_forzaEnabled: Boolean
    private var m_forzaDeadband: Double
    private var m_velocityLimitPercentage: Double
    private var m_turningLimitPercentage: Double

    /**
     * Creates a new ArcadeDrive. This command will drive your robot according to the speed supplier
     * lambdas. This command does not terminate.
     *
     * @param drivetrain The drivetrain subsystem on which this command will run
     * @param xboxController A controller that this command takes inputs from
     */
    init {
        addRequirements(m_drivetrain)
        m_velocityLimitPercentage = DEFAULT_MAX_VELOCITY_PERCENTAGE
        m_turningLimitPercentage = DEFAULT_MAX_TURNING_SPEED
        m_arcadeDeadband = DEFAULT_ARCADE_JOY_DEADBAND
        m_forzaDeadband = DEFAULT_FORZA_DEADBAND
        m_forzaEnabled = DEFAULT_FORZA_MODE
        // Add constants to smart dashbord
        setDashbord()
    }

    // Called when the command is initially scheduled.
    override fun initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    override fun execute() {
        // Get values from smart dashboard
        dashbord
        var speed: Double
        // Get turning. Note that the controls are inverted!
        var turning = m_XboxController.leftX
        // Apply turning deadband
        turning = RomiUtils.applyDeadband(turning, m_arcadeDeadband)

        // Forza drive vs Arcade drive
        if (true == m_forzaEnabled) {
            // In Forza Mode
            // Calculate speed using triggers

            // Get trigger values
            // Want to go forward
            var triggerRight = m_XboxController.rightTriggerAxis
            // Apply trigger deadband
            triggerRight = RomiUtils.applyDeadband(triggerRight, m_forzaDeadband)
            // Want to go backward
            var triggerLeft = m_XboxController.leftTriggerAxis
            // Apply trigger deadband
            triggerLeft = RomiUtils.applyDeadband(triggerLeft, m_forzaDeadband)

            // Forward vs Backward
            if (triggerRight >= triggerLeft) {
                // Driver wants to go forward
                // Set speed to forward trigger (right)
                speed = triggerRight
            } else {
                // Driver wants to go backward
                // Set speed to backward trigger (left)
                // Multiply the value by negative 1 to make it go backward
                speed = -triggerLeft
                // Invert turning
                turning = -turning
            }
        } else {
            // In Arcade Mode
            // Calculate speed using y axis of joystick

            // Get y axis value
            speed = m_XboxController.leftY
            // Apply arcade deadband
            speed = RomiUtils.applyDeadband(speed, m_arcadeDeadband)
        }


        // Clamp speed and turning to make sure they are within [-1, 1]
        speed = RomiUtils.clamp(speed, -1.0, 1.0)
        turning = RomiUtils.clamp(turning, -1.0, 1.0)

        // Apply speed and turning limiting percents
        speed *= m_velocityLimitPercentage
        turning *= m_turningLimitPercentage

        // Drive the robot
        m_drivetrain.arcadeDrive(speed, turning)
    }

    // Called once the command ends or is interrupted.
    override fun end(interrupted: Boolean) {}

    // Returns true when the command should end.
    override fun isFinished(): Boolean {
        return false
    }

    fun setDashbord() {
        SmartDashboard.putNumber("Arcade Joy Deadband", m_arcadeDeadband)
        SmartDashboard.putNumber("Forza Deadband", m_forzaDeadband)
        SmartDashboard.putBoolean("Forza Mode", m_forzaEnabled)
        SmartDashboard.putNumber("Max Velocity Percentage", m_velocityLimitPercentage)
        SmartDashboard.putNumber("Max Turning Percentage", m_turningLimitPercentage)
    }
    // Get deadband values set in SmartDashboard


    // Get limit percantages set in SmartDashboard
    val dashbord: Unit
        get() {
            // Get deadband values set in SmartDashboard
            m_arcadeDeadband = SmartDashboard.getNumber("Arcade Joy Deadband", DEFAULT_ARCADE_JOY_DEADBAND)
            m_arcadeDeadband = RomiUtils.clamp(m_arcadeDeadband, 0.0, 1.0)
            SmartDashboard.putNumber("Arcade Joy Deadband", m_arcadeDeadband)
            m_forzaDeadband = SmartDashboard.getNumber("Forza Deadband", DEFAULT_FORZA_DEADBAND)
            m_forzaDeadband = RomiUtils.clamp(m_forzaDeadband, 0.0, 1.0)
            SmartDashboard.putNumber("Forza Deadband", m_forzaDeadband)
            m_forzaEnabled = SmartDashboard.getBoolean("Forza Mode", DEFAULT_FORZA_MODE)


            // Get limit percantages set in SmartDashboard
            m_velocityLimitPercentage =
                SmartDashboard.getNumber("Max Velocity Percentage", DEFAULT_MAX_VELOCITY_PERCENTAGE)
            m_velocityLimitPercentage = RomiUtils.clamp(m_velocityLimitPercentage, 0.0, 1.0)
            SmartDashboard.putNumber("Max Velocity Percentage", m_velocityLimitPercentage)
            m_turningLimitPercentage = SmartDashboard.getNumber("Max Turning Percentage", DEFAULT_MAX_TURNING_SPEED)
            m_turningLimitPercentage = RomiUtils.clamp(m_turningLimitPercentage, 0.0, 1.0)
            SmartDashboard.putNumber("Max Turning Percentage", m_turningLimitPercentage)
        }
}