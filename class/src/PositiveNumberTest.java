import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class PositiveNumberTest {

    PositiveNumber hundred = new PositiveNumber(      "100");
    PositiveNumber thousand = new PositiveNumber(     "1000");
    PositiveNumber billion = new PositiveNumber(      "1000000000");
    PositiveNumber test1 = new PositiveNumber(        "123456789");
    PositiveNumber test2 = new PositiveNumber(        "987654321");
    PositiveNumber someNumber = new PositiveNumber(   "54654165498265456546523154486548797886798451654");
    PositiveNumber someBigNumber = new PositiveNumber("645896398753073073978643101894078714658716189186");
    PositiveNumber otherNumber = new PositiveNumber(  "1219028852");

    @org.junit.jupiter.api.Test
    void plus() {
        assertEquals(new PositiveNumber(1100).toString(), hundred.plus(thousand).toString());
        assertEquals(new PositiveNumber(1000000100).toString(), hundred.plus(billion).toString());
        assertEquals(new PositiveNumber("54654165498265456546523154486548797888017480506").toString(),someNumber.plus(otherNumber).toString());
        assertEquals(new PositiveNumber(100).toString(), hundred.plus(PositiveNumber.ZERO).toString());
    }

    @org.junit.jupiter.api.Test
    void minus() {
        assertEquals(new PositiveNumber().toString(), hundred.minus(thousand).toString());
        assertEquals(new PositiveNumber(999999999).toString(), billion.minus(PositiveNumber.ONE).toString());
        assertEquals(new PositiveNumber(864197532).toString(), test2.minus(test1).toString());
        assertEquals(new PositiveNumber("591242233254807617432119947407529916771917737532").toString(), someBigNumber.minus(someNumber).toString());

    }

    @org.junit.jupiter.api.Test
    void remainderOfDivision() {
        assertEquals(new PositiveNumber(0).toString(), billion.remainderOfDivision(thousand).toString());
        assertEquals(new PositiveNumber(12345688).toString(), billion.remainderOfDivision(test1).toString());
        assertEquals(new PositiveNumber("44700578272153051966888402542041937903933220992").toString(), someBigNumber.remainderOfDivision(someNumber).toString());
        assertEquals(new PositiveNumber(0).toString(), billion.remainderOfDivision(PositiveNumber.ONE).toString());
    }

    @org.junit.jupiter.api.Test
    void division() {
        assertEquals(new PositiveNumber(1000000).toString(), billion.division(thousand).toString());
        assertEquals(new PositiveNumber(8).toString(), test2.division(test1).toString());
        assertEquals(new PositiveNumber(11).toString(), someBigNumber.division(someNumber).toString());
        assertEquals(new PositiveNumber(0).toString(), PositiveNumber.ONE.division(PositiveNumber.TEN).toString());
    }

    @org.junit.jupiter.api.Test
    void multiplication() {
        assertEquals( new PositiveNumber(100000).toString(), hundred.multiplication(thousand).toString());
        assertEquals(new PositiveNumber("121932631112635269").toString(), test1.multiplication(test2).toString());
        assertEquals(new PositiveNumber("35300928672184114048741090492352409976456274979277822326383484384834798927020698847568138613644").toString(), someBigNumber.multiplication(someNumber).toString());
    }

    @org.junit.jupiter.api.Test
    void toInt() {
        assertEquals(123456789, test1.toInt());
        assertEquals(987654321, test2.toInt());
        assertEquals(100, hundred.toInt());
        assertEquals(0, someBigNumber.toInt());
    }

    @org.junit.jupiter.api.Test
    void toBigInteger() {
        assertEquals( new BigInteger("123456789"), test1.toBigInteger());
        assertEquals(new BigInteger("987654321") , test2.toBigInteger());
        assertEquals( new BigInteger("645896398753073073978643101894078714658716189186"), someBigNumber.toBigInteger());
    }

    @org.junit.jupiter.api.Test
    void testToString() {
        assertEquals("123456789" , test1.toString());
        assertEquals( "987654321", test2.toString());
        assertEquals("645896398753073073978643101894078714658716189186", someBigNumber.toString());
    }

    @org.junit.jupiter.api.Test
    void compareTo() {
        assertEquals(-1, hundred.compareTo(thousand));
        assertEquals(1, thousand.compareTo(hundred));
        assertEquals(0, thousand.compareTo(thousand));
        assertEquals(-1, test1.compareTo(test2));
        assertEquals(1, someBigNumber.compareTo(someNumber));
    }

    @org.junit.jupiter.api.Test
    void testClone() {
        assertEquals(new PositiveNumber(100).toString(), hundred.clone().toString());
        assertEquals(new PositiveNumber(123456789).toString(), test1.clone().toString());
    }
}