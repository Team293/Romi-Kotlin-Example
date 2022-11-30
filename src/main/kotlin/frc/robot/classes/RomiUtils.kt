package frc.robot.classes

import kotlin.math.abs

class RomiUtils private constructor() {
    init {
        throw AssertionError("utility class")
    }

    companion object {
        /**
         *
         * @param input The input to be deadbanded
         * @param deadband The deadband to be applied
         * @return deadband applied output
         */
        fun applyDeadband(input: Double, deadband: Double): Double {
            var retval = input
            if (abs(input) >= deadband) {
                retval = (abs(input) - deadband) / (1 - deadband)
                // Check if value is supposed to be negative
                if (input < 0) {
                    retval *= -1
                }
            }
            return retval
        }

        /**
         *
         * @param val The value to be clamped
         * @param min The minimum value
         * @param max The maximum value
         * @return clamped output
         */
        fun clamp(`val`: Double, min: Double, max: Double): Double {
            return min.coerceAtLeast(max.coerceAtMost(`val`))
        }
    }
}