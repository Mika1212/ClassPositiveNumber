
import java.math.BigInteger;
import java.util.ArrayList;

public class PositiveNumber implements Comparable<PositiveNumber> {

    private ArrayList<Integer> number;

    public static final PositiveNumber ZERO = new PositiveNumber(0);
    public static final PositiveNumber ONE = new PositiveNumber(1);
    public static final PositiveNumber TEN = new PositiveNumber(10);

    public  PositiveNumber(){
        this.number = new ArrayList<>();
    }

    public PositiveNumber(int newNumber) {
        this.number = arrayMaker(newNumber);
        transformation(number);
    }

    public PositiveNumber(String newNumber) {
        this.number = arrayMaker(newNumber);
        transformation(number);
    }

    public PositiveNumber(BigInteger newNumber) {
        this.number = arrayMaker(newNumber);
        transformation(number);
    }

    private ArrayList<Integer> arrayMaker(String anyString) {
        int k = anyString.length();
        ArrayList<Integer> otherNumber = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            char character = anyString.toCharArray()[k-i-1];
            otherNumber.add(Character.getNumericValue(character));
        }
        return otherNumber;
    }

    private ArrayList<Integer> arrayMaker(Integer anyInt) {
        int other1 = anyInt;
        ArrayList<Integer> otherNumber = new ArrayList<>();

        for (int i = 0; i < anyInt.toString().length(); i++) {
            otherNumber.add(other1 % 10);
            other1/=10;
        }
        return otherNumber;
    }

    private ArrayList<Integer> arrayMaker(BigInteger anyInt) {
        BigInteger other1 = anyInt;
        ArrayList<Integer> otherNumber = new ArrayList<>();

        for (int i = 0; i < anyInt.toString().length(); i++) {
            otherNumber.add(other1.mod(BigInteger.TEN).intValue());
            other1= other1.divide(BigInteger.TEN);
        }
        return otherNumber;
    }


    public PositiveNumber plus(PositiveNumber other) {
        ArrayList<Integer> result = new ArrayList<>();
        ArrayList<Integer> smth = other.number;

        for (int i=0; i < Math.min(number.size(), smth.size()); i++) {
            result.add(smth.get(i) + number.get(i));
        }

        for (int i = Math.min(number.size(),smth.size()); i<Math.max(number.size(), smth.size());i++){
            if (Math.max(number.size(), smth.size()) == smth.size())
                result.add(smth.get(i));
            else
                result.add(number.get(i));
        }

        result = transformation(result);
        PositiveNumber res = new PositiveNumber();
        res.number = result;
        return res;
    }

    public PositiveNumber minus(PositiveNumber other) {
        PositiveNumber result = new PositiveNumber();
        ArrayList<Integer> smth = other.number;
        if (smth.size()>number.size())
            throw new IllegalArgumentException();

        for (int i = 0; i < smth.size(); i++) {
            result.number.add(number.get(i) - smth.get(i));
        }

        for (int i = smth.size(); i < number.size(); i++){
            result.number.add(number.get(i));
        }

        transformation(result.number);
        return result;
    }

    public PositiveNumber remainderOfDivision(PositiveNumber other) {
        return  this.minus(this.division(other).multiplication(other));
    }

    public PositiveNumber division(int other) {
        PositiveNumber result = new PositiveNumber();
        result.number.add(0);
        result.number.addAll(number);

        for (int i = number.size(); i > 0; i--) {
            if (result.number.get(i) >= other) {
                result.number.set(i - 1, result.number.get(i - 1) + result.number.get(i) % other * 10);
                result.number.set(i, result.number.get(i) / other);
            } else {
                result.number.set(i - 1, result.number.get(i - 1) + result.number.get(i) * 10);
                result.number.set(i, 0);
            }
        }

        result.number.remove(0);

        transformation(result.number);
        return result;
    }

    public PositiveNumber division(PositiveNumber other) {

        PositiveNumber result = new PositiveNumber();
        PositiveNumber divided = new PositiveNumber();
        divided.number.addAll(this.number);
        result.number.add(0);
        PositiveNumber helper = new PositiveNumber();
        if (this.compareTo(other) < 0) return new PositiveNumber(0);

        while (divided.compareTo(PositiveNumber.ZERO) > 0
                || divided.number.size()+helper.number.size() > other.number.size()) {
            result = result.multiplication(10);

            for (int i = divided.number.size() - 1; i >= 0; i--) {
                helper = helper.reversed();
                helper.number.add(divided.number.get(i));
                helper = helper.reversed();
                helper.zeroDelete();

                if (helper.compareTo(other) >= 0) {
                    while (helper.compareTo(other) >= 0) {
                        result = result.plus(PositiveNumber.ONE);
                        helper = helper.minus(other);
                    }
                    int k = divided.number.size() - i;
                    for (int j = 0; j < k; j++) {
                        divided.number.remove(divided.number.size() - 1);
                    }
                    break;
                }

                if (divided.number.size() != number.size() && divided.number.size() - i > 1) {
                    result = result.multiplication(10);
                }

                if (i == 0) return result;
            }

            int k = divided.number.size();
            if (PositiveNumber.ONE.compareTo(helper) > 0) {
                PositiveNumber test = divided.clone();
                test.zeroDelete();
                if (PositiveNumber.ONE.compareTo(test) > 0) {
                    for (int i = 0; i < k; i++ ) {
                        result = result.multiplication(10);

                    }
                    break;
                }
            }
        }

        return result;
    }


    private PositiveNumber multiplication(int other) {
        PositiveNumber result = new PositiveNumber();
        for (int i = 0; i < number.size(); i++) {
           result.number.add(this.number.get(i) * other);
       }

        transformation(result.number);
        return result;
    }

    public PositiveNumber multiplication(PositiveNumber other) {
        PositiveNumber result = new PositiveNumber();

        for (int i = other.number.size() - 1; i >= 0 ; i--) {
            PositiveNumber helper = this.multiplication(other.number.get(i));
            result = result.plus(helper).multiplication(10);
        }
        return result.division(10);

    }


    public int toInt() {
        if (this.number.size() > 10) throw new IllegalArgumentException();
        int result = 0;
        for (int i = this.number.size() - 1; i > 0; i--) {
            if (result+this.number.get(i) < Integer.MAX_VALUE) result+=this.number.get(i);
            else return 0;
            if (Integer.MAX_VALUE/result > 10) result *= 10;
            else return 0;
        }

        result+=this.number.get(0);
        return result;
    }

    public BigInteger toBigInteger() {
        BigInteger result = new BigInteger("0");

        for (int i = this.number.size() - 1; i >= 0; i--) {
            result = result.add(BigInteger.valueOf(this.number.get(i)));
            result = result.multiply(BigInteger.TEN);
        }

        return result.divide(BigInteger.TEN);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = number.size() - 1; i >= 0; i--) {
            result.append(number.get(i));
        }

        return result.toString();
    }


    @Override
    public int compareTo(PositiveNumber o) {
        try {
            this.minus(o);
        } catch (IllegalArgumentException e) {
            return -1;
        }
        if
            (this.minus(o).number.toString().equals(new PositiveNumber(0).number.toString()))
                return 0;
        else return 1;
    }

    public PositiveNumber clone(){
        PositiveNumber result = new PositiveNumber();
        result.number.addAll(this.number);
        return result;
    }


    private ArrayList<Integer> transformation(ArrayList<Integer> array) {
        for (int i = 0; i < array.size(); i++){
            if (array.get(i) >= 10) {
                if (array.size() < i + 2) {
                    array.add(array.get(i) / 10);
                } else {
                    array.set(i + 1, array.get(i + 1) + (array.get(i) / 10));
                }
                array.set(i, array.get(i) % 10);
            } else {
                if (array.get(i) < 0) {
                    if (array.size() < i + 2) {
                        throw new IllegalArgumentException();
                    }
                    while (array.get(i) < 0) {
                        array.set(i, array.get(i) + 10);
                        array.set(i + 1, array.get(i + 1) - 1);
                    }

                }
            }
        }
        while (array.size() > 1 && array.get(array.size() - 1) == 0) {
            array.remove(array.size() - 1);
        }
        return array;
    }

    private PositiveNumber reversed() {
        PositiveNumber result = new PositiveNumber();
        for (int i = number.size() - 1; i >= 0; i--) {
            result.number.add(number.get(i));
        }
        return result;
    }

    private void zeroDelete(){
        while (this.number.size()>1 && this.number.get(this.number.size()-1) == 0) {
            this.number.remove(this.number.size() - 1);
        }
    }


}

class Main{
    public static void main(String[] args) {
        PositiveNumber x = new PositiveNumber("1219028852");
        PositiveNumber test = new PositiveNumber( "1234567899");
        PositiveNumber test1 = new PositiveNumber("54654165498265456546523154486548797886798451654");

        PositiveNumber a = new PositiveNumber(601);
        PositiveNumber b = new PositiveNumber(600);
        System.out.println(test1.division(test));
        System.out.println(a.remainderOfDivision(b));
    }
}
