// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Drivetrain

class DriveTime(private val m_speed: Double, time: Double, drive: Drivetrain) : CommandBase() {
    private val m_duration: Double
    private val m_drive: Drivetrain
    private var m_startTime: Long = 0

    /**
     * Creates a new DriveTime. This command will drive your robot for a desired speed and time.
     *
     * @param speed The speed which the robot will drive. Negative is in reverse.
     * @param time How much time to drive in seconds
     * @param drive The drivetrain subsystem on which this command will run
     */
    init {
        m_duration = time * 1000
        m_drive = drive
        addRequirements(drive)
    }

    // Called when the command is initially scheduled.
    override fun initialize() {
        m_startTime = System.currentTimeMillis()
        m_drive.arcadeDrive(0.0, 0.0)
    }

    // Called every time the scheduler runs while the command is scheduled.
    override fun execute() {
        m_drive.arcadeDrive(m_speed, 0.0)
    }

    // Called once the command ends or is interrupted.
    override fun end(interrupted: Boolean) {
        m_drive.arcadeDrive(0.0, 0.0)
    }

    // Returns true when the command should end.
    override fun isFinished(): Boolean {
        return System.currentTimeMillis() - m_startTime >= m_duration
    }
}