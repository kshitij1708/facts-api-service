package com.example.facts.util;

import java.math.BigInteger;

/**
 * Utility class for encoding values using Base62 encoding.
 */
public class Base62Encoder {

    private static final String BASE62 = "b138KztOiFUZqldmanTHSQjGcM7khAuIfye4vpNDgY50LrwBX2CRo9P6JVEWsx";

    /**
     * Encodes the given value to a Base62 string.
     *
     * @param value the value to encode
     * @return the Base62 encoded string
     */
    public static String encode(BigInteger value) {
        StringBuilder sb = new StringBuilder();

        while (value.compareTo(BigInteger.ZERO) > 0) {
            int remainder = value.mod(BigInteger.valueOf(62)).intValue();
            value = value.divide(BigInteger.valueOf(62));
            sb.append(BASE62.charAt(remainder));
        }

        return sb.reverse().toString();
    }
}
