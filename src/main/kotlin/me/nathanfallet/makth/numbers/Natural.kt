package me.nathanfallet.makth.numbers

interface Natural : Integer {

    // Instantiate

    companion object {

        @JvmStatic
        fun instantiate(value: Long): Natural {
            return NaturalImpl(value)
        }

    }

    // AbstractReal

    override fun absoluteValue(): Natural {
        return this
    }

}