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
        @JvmStatic
        fun instantiate(value: Long): Natural {
            return NaturalImpl(value)
        }

    }

    // Real

    override fun absoluteValue(): Natural {
        return this
    }

}