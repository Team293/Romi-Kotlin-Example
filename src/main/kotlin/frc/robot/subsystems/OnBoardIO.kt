package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput
import edu.wpi.first.wpilibj.DigitalOutput
import edu.wpi.first.wpilibj2.command.SubsystemBase


public class OnBoardIO
/**
 * Constructor.
 *
 * @param dio1 Mode for DIO 1 (input = Button B, output = green LED)
 * @param dio2 Mode for DIO 2 (input = Button C, output = red LED)
 */ public constructor(dio1: ChannelMode, dio2: ChannelMode) : SubsystemBase() {
    private val mButtonA = DigitalInput(0)
    private val mYellowLed = DigitalOutput(3)

    // DIO 1
    private lateinit var mButtonB: DigitalInput
    private lateinit var mGreenLed: DigitalOutput

    // DIO 2
    private lateinit var mButtonC: DigitalInput
    private lateinit var mRedLed: DigitalOutput

    private var mNextMessageTime = 0.0

    enum class ChannelMode {
        INPUT, OUTPUT
    }

    init {
        if (dio1 === ChannelMode.INPUT) {
            mButtonB = DigitalInput(1)
        } else {
            mGreenLed = DigitalOutput(1)
        }
        if (dio2 === ChannelMode.INPUT) {
            mButtonC = DigitalInput(2)
        } else {
            mRedLed = DigitalOutput(2)
        }
    }

    /**
     * Returns the state of Button A.
     *
     * @return true if the button is pressed
     */
    fun getButtonAPressed(): Boolean {
        return mButtonA.get()
    }

    /**
     * Returns the state of Button B.
     *
     * @return true if the button is pressed
     */
     fun getButtonBPressed(): Boolean {
        return mButtonB.get()

    }

    /**
     * Returns the state of Button C.
     *
     * @return true if the button is pressed
     */
    fun getButtonCPressed(): Boolean {
        return mButtonC.get()
    }

    /**
     * Sets the state of the yellow LED.
     *
     * @param value true to turn the LED on
     */
    fun setGreenLed(value: Boolean) {
        mGreenLed.set(value)
    }

    /**
     * Sets the state of the yellow LED.
     *
     * @param value true to turn the LED on
     */
    fun setRedLed(value: Boolean) {
        mRedLed.set(value)
    }

    /**
     * Sets the state of the yellow LED.
     *
     * @param value true to turn the LED on
     */
    /** Sets the yellow LED.  */
    fun setYellowLed(value: Boolean) {
        mYellowLed.set(value)
    }

    override fun periodic() {
        // This method will be called once per scheduler run
    }
}