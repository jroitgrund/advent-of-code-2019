package day4

import kotlin.test.Test
import kotlin.test.assertEquals

class Day4Test {
    @Test
    fun testNumberMetadata() {
        assertEquals(
                NumberMetadata(
                        hasDecreased = false,
                        hasTwoConsecutiveIdenticalNumbers = true,
                        hasTwoConsecutiveIdenticalNumbersNotPartOfGroup = false),
                numberMetadata(123444))

        assertEquals(
                NumberMetadata(
                        hasDecreased = false,
                        hasTwoConsecutiveIdenticalNumbers = true,
                        hasTwoConsecutiveIdenticalNumbersNotPartOfGroup = true),
                numberMetadata(112233))

        assertEquals(
                NumberMetadata(
                        hasDecreased = false,
                        hasTwoConsecutiveIdenticalNumbers = true,
                        hasTwoConsecutiveIdenticalNumbersNotPartOfGroup = true),
                numberMetadata(111122))

        assertEquals(
                NumberMetadata(
                        hasDecreased = false,
                        hasTwoConsecutiveIdenticalNumbers = true,
                        hasTwoConsecutiveIdenticalNumbersNotPartOfGroup = false),
                numberMetadata(5556789))

        assertEquals(
                NumberMetadata(
                        hasDecreased = false,
                        hasTwoConsecutiveIdenticalNumbers = true,
                        hasTwoConsecutiveIdenticalNumbersNotPartOfGroup = false),
                numberMetadata(455556789))

        assertEquals(
                NumberMetadata(
                        hasDecreased = false,
                        hasTwoConsecutiveIdenticalNumbers = true,
                        hasTwoConsecutiveIdenticalNumbersNotPartOfGroup = false),
                numberMetadata(1234555))

        assertEquals(
                NumberMetadata(
                        hasDecreased = false,
                        hasTwoConsecutiveIdenticalNumbers = true,
                        hasTwoConsecutiveIdenticalNumbersNotPartOfGroup = true),
                numberMetadata(556789))

        assertEquals(
                NumberMetadata(
                        hasDecreased = false,
                        hasTwoConsecutiveIdenticalNumbers = true,
                        hasTwoConsecutiveIdenticalNumbersNotPartOfGroup = true),
                numberMetadata(4556789))

        assertEquals(
                NumberMetadata(
                        hasDecreased = false,
                        hasTwoConsecutiveIdenticalNumbers = true,
                        hasTwoConsecutiveIdenticalNumbersNotPartOfGroup = true),
                numberMetadata(123455))

        assertEquals(
                NumberMetadata(
                        hasDecreased = true,
                        hasTwoConsecutiveIdenticalNumbers = false,
                        hasTwoConsecutiveIdenticalNumbersNotPartOfGroup = false),
                numberMetadata(921234567))

        assertEquals(
                NumberMetadata(
                        hasDecreased = true,
                        hasTwoConsecutiveIdenticalNumbers = false,
                        hasTwoConsecutiveIdenticalNumbersNotPartOfGroup = false),
                numberMetadata(123498))
    }
}