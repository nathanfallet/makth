package me.nathanfallet.makth.numbers

/**
 * Natural representation
 */
interface Natural : Integer {

    // Instantiate

    companion object {

        /**
         * Instantiate a natural from a long value
         * @param value Long value
         * @return Natural
         */
        fun instantiate(value: Long): Natural {
            return NaturalImpl(value)
        }

    }

    // Real

    override val absoluteValue: Natural get() {
        return this
    }

}