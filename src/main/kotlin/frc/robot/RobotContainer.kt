// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot

import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.PrintCommand
import edu.wpi.first.wpilibj2.command.button.Button
import frc.robot.commands.ArcadeDrive
import frc.robot.commands.AutonomousDistance
import frc.robot.commands.AutonomousTime
import frc.robot.subsystems.Drivetrain
import frc.robot.subsystems.OnBoardIO
import frc.robot.subsystems.OnBoardIO.ChannelMode

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the [Robot]
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
class RobotContainer {
    // The robot's subsystems and commands are defined here...
    private val m_drivetrain = Drivetrain()
    private val m_onboardIO = OnBoardIO(ChannelMode.INPUT, ChannelMode.INPUT)

    // Assumes a gamepad plugged into channnel 0
    private val m_XboxController = XboxController(0)

    // Create SmartDashboard chooser for autonomous routines
    private val m_chooser = SendableChooser<Command>()
    // NOTE: The I/O pin functionality of the 5 exposed I/O pins depends on the hardware "overlay"
    // that is specified when launching the wpilib-ws server on the Romi raspberry pi.
    // By default, the following are available (listed in order from inside of the board to outside):
    // - DIO 8 (mapped to Arduino pin 11, closest to the inside of the board)
    // - Analog In 0 (mapped to Analog Channel 6 / Arduino Pin 4)
    // - Analog In 1 (mapped to Analog Channel 2 / Arduino Pin 20)
    // - PWM 2 (mapped to Arduino Pin 21)
    // - PWM 3 (mapped to Arduino Pin 22)
    //
    // Your subsystem configuration should take the overlays into account
    /** The container for the robot. Contains subsystems, OI devices, and commands.  */
    init {
        // Configure the button bindings
        configureButtonBindings()
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a [GenericHID] or one of its subclasses ([ ] or [XboxController]), and then passing it to a [ ].
     */
    private fun configureButtonBindings() {
        // Default command is arcade drive. This will run unless another command
        // is scheduled over it.
        m_drivetrain.defaultCommand = arcadeDriveCommand

        // Example of how to use the onboard IO
        val onboardButtonA = Button { m_onboardIO.getButtonAPressed() }
        onboardButtonA
            .whenActive(PrintCommand("Button A Pressed"))
            .whenInactive(PrintCommand("Button A Released"))

        // Setup SmartDashboard options
        m_chooser.setDefaultOption("Auto Routine Distance", AutonomousDistance(m_drivetrain))
        m_chooser.addOption("Auto Routine Time", AutonomousTime(m_drivetrain))
        SmartDashboard.putData(m_chooser)
    }

    /**
     * Use this to pass the autonomous command to the main [Robot] class.
     *
     * @return the command to run in autonomous
     */
    val autonomousCommand: Command
        get() = m_chooser.selected

    /**
     * Use this to pass the teleop command to the main [Robot] class.
     *
     * @return the command to run in teleop
     */
    val arcadeDriveCommand: Command
        get() = ArcadeDrive(m_drivetrain, m_XboxController)
}