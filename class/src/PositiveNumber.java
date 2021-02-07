
import java.math.BigInteger;
import java.util.ArrayList;

public class PositiveNumber implements Comparable<PositiveNumber> {

    ArrayList<Integer> number;

    public static final PositiveNumber ZERO = new PositiveNumber(0);
    public static final PositiveNumber ONE = new PositiveNumber(1);
    public static final PositiveNumber TEN = new PositiveNumber(10);

    public  PositiveNumber(){
        this.number = new ArrayList<>();
    }

    public PositiveNumber(int newNumber) {
        this.number = arrayMaker(newNumber);
        this.number = transformation(number);
    }

    public PositiveNumber(String newNumber) {
        this.number = arrayMaker(newNumber);
        this.number = transformation(number);
    }

    private ArrayList<Integer> arrayMaker(String anyString) {
        int k = anyString.length();
        ArrayList<Integer> otherNumber = new ArrayList<>();
        for (int i = 0; i<k; i++) {
            char character = anyString.toCharArray()[k-i-1];
            otherNumber.add(Character.getNumericValue(character));
        }
        return otherNumber;
    }

    private ArrayList<Integer> arrayMaker(int anyInt) {
        int k = 0;
        int other1 = anyInt;
        do  {
            other1 /= 10;
            k++;
        } while (other1 > 0);

        ArrayList<Integer> otherNumber = new ArrayList<>();

        other1 = anyInt;
        for (int i = 0; i < k; i++) {
            otherNumber.add(other1 % 10);
            other1/=10;
        }
        return otherNumber;
    }

    private ArrayList<Integer> arrayMaker(BigInteger anyInt) {
        int k = 0;
        BigInteger other1 = anyInt;
        BigInteger zero = new BigInteger("0");
        BigInteger divider = new BigInteger("10");
        do  {
            other1 = other1.divide(divider);
            k++;
        } while (other1.compareTo(zero) != 0);

        ArrayList<Integer> otherNumber = new ArrayList<>();

        other1 = anyInt;
        for (int i = 0; i < k; i++) {
            otherNumber.add(other1.mod(divider).intValue());
            other1= other1.divide(divider);
        }
        return otherNumber;
    }

    public PositiveNumber plus(PositiveNumber other) {
        ArrayList<Integer> result = new ArrayList<>();
        ArrayList<Integer> smth = other.number;

        for (int i=0; i<Math.min(number.size(),smth.size()); i++) {
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



    public PositiveNumber remainderOfDivision(int other) {
        PositiveNumber result = new PositiveNumber();
        result.number.add(0);
        result.number.addAll(number);

        for (int i = number.size(); i>0; i--) {
            if (result.number.get(i) >= other) {
                result.number.set(i-1, result.number.get(i-1) + result.number.get(i) % other * 10);
                result.number.set(i, result.number.get(i) / other);
            } else {
                result.number.set(i-1, result.number.get(i-1) + result.number.get(i) * 10);
                result.number.set(i, 0);
            }
        }

        return new PositiveNumber(result.number.get(0) /10 % other);
    }

    public PositiveNumber division(int other) {
        PositiveNumber result = new PositiveNumber();
        result.number.add(0);
        result.number.addAll(number);

        for (int i = number.size(); i>0; i--) {
            if (result.number.get(i) >= other) {
                result.number.set(i-1, result.number.get(i-1) + result.number.get(i) % other * 10);
                result.number.set(i, result.number.get(i) / other);
            } else {
                result.number.set(i-1, result.number.get(i-1) + result.number.get(i) * 10);
                result.number.set(i, 0);
            }
        }

        result.number.remove(0);

        result.number = transformation(result.number);
        return result;
    }



    /*
    public PositiveNumber division(BigInteger other) {
        PositiveNumber some = new PositiveNumber();
        some.number = arrayMaker(other);
        return this.division(some);
    }

     */

    public static ArrayList<Integer> factoring(PositiveNumber some) {
        ArrayList<Integer> result = new ArrayList<>();
        int divider = 2;
        int stop = 1000000;
        while (some.compareTo(1)>0 && divider<stop) {
            while (some.remainderOfDivision(divider).number.equals(new PositiveNumber(0).number)) {
                some = some.division(divider);
                result.add(divider);
            }
            divider++;
        }
        if (some.compareTo(1)>0) result.add(some.toInt());
        return result;
    }

    public PositiveNumber multiplication(int other) {
        PositiveNumber result = new PositiveNumber();
        for (int i = 0; i<number.size(); i++) {
           result.number.add(this.number.get(i) * other);
       }

        result.number = transformation(result.number);
        return result;
    }

    public PositiveNumber multiplication(PositiveNumber other) {
        PositiveNumber result = new PositiveNumber();

        for (int i = other.number.size()-1; i >= 0 ; i--) {
            PositiveNumber helper = this.multiplication(other.number.get(i));
            result = result.plus(helper).multiplication(10);
        }
        return result.division(10);

    }

    public int toInt() {
        if (this.number.size() > 10) return 0;
        int result = 0;
        for (int i = this.number.size()-1; i > 0; i--) {
            if (result+this.number.get(i) < Integer.MAX_VALUE) result+=this.number.get(i);
            else return 0;
            if (Integer.MAX_VALUE/result > 10) result*=10;
            else return 0;
        }

        result+=this.number.get(0);
        return result;
    }

    public BigInteger toBigInteger() {
        BigInteger result = new BigInteger("0");

        for (int i = this.number.size()-1; i>=0; i--) {
            result = result.add(BigInteger.valueOf(this.number.get(i)));
            result = result.multiply(BigInteger.TEN);
        }

        return result.divide(BigInteger.TEN);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = number.size()-1; i>=0; i--) {
            result.append(number.get(i));
        }

        return result.toString();
    }

    @Override
    public int compareTo(PositiveNumber o) {

        if (this.minus(o).number.toString().equals(new PositiveNumber().number.toString())) return -1;
        else if
            (this.minus(o).number.toString().equals(new PositiveNumber(0).number.toString()))
                return 0;
        else return 1;
    }

    public int compareTo(int o) {

        PositiveNumber other = new PositiveNumber(o);
        if (this.minus(other) == null) return -1;
        else if
        (this.minus(other).number.toString().equals(new PositiveNumber(0).number.toString()))
            return 0;
        else return 1;
    }


    public static ArrayList<Integer> transformation(ArrayList<Integer> array) {
        for (int i = 0; i<array.size(); i++){
            if (array.get(i)>=10) {
                if (array.size()<i+2) {
                    array.add(array.get(i) / 10);
                    array.set(i, array.get(i) % 10);
                } else {
                    array.set(i + 1, array.get(i + 1) + (array.get(i) / 10));
                    array.set(i, array.get(i) % 10);
                }
            } else {
                if (array.get(i) < 0) {
                    if (array.size() < i + 2) {
                        System.out.println("second");
                        return new ArrayList<>();
                    }
                    while (array.get(i)<0) {
                        array.set(i, array.get(i)+10);
                        array.set(i+1, array.get(i+1) - 1);
                    }

                }
            }
        }
        while (array.size()>1 && array.get(array.size()-1) == 0) {
            array.remove(array.size() - 1);
        }
        return array;
    }

    public ArrayList<Integer> reversed() {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = number.size()-1; i>=0; i--) {
            result.add(number.get(i));
        }
        return result;
    }

    public PositiveNumber minus(PositiveNumber other) {
        ArrayList<Integer> result = new ArrayList<>();
        ArrayList<Integer> smth = other.number;
        if (smth.size()>number.size())
            return new PositiveNumber();

        for (int i = 0; i< smth.size(); i++) {
            result.add(number.get(i) - smth.get(i));
        }

        for (int i = smth.size(); i<number.size();i++){
            result.add(number.get(i));
        }

        result = transformation(result);
        PositiveNumber res = new PositiveNumber();
        res.number = result;
        return res;
    }

    public PositiveNumber clone(){
        PositiveNumber result = new PositiveNumber();
        for (int i = 0; i < this.number.size()-1; i++) {
            result.number.add(this.number.get(i));
        }
        return result;
    }

    public void zeroDelete(){
        while (this.number.size()>1 && this.number.get(this.number.size()-1) == 0) {
            this.number.remove(this.number.size() - 1);
        }
    }


    public PositiveNumber division(PositiveNumber other) {
        PositiveNumber result = new PositiveNumber();
        PositiveNumber divided = new PositiveNumber();
        divided.number.addAll(this.number);
        result.number.add(0);
        PositiveNumber helper = new PositiveNumber();
        if (this.compareTo(other) < 0) return new PositiveNumber(0);
        int o =0;


        while (divided.compareTo(PositiveNumber.ZERO) > 0
                || divided.number.size()+helper.number.size() > other.number.size()) {

            System.out.println("r with mult " + result);
            result = result.multiplication(10);

            for (int i = divided.number.size() - 1; i >= 0; i--) {
                helper.number.add(0, divided.number.get(i));
                helper.zeroDelete();

                System.out.println("                                               Helper now = " + helper.number);
                if (helper.compareTo(other) >= 0) {
                    System.out.println("entrance " + helper.number);
                    while (helper.compareTo(other) >= 0) {
                        result = result.plus(PositiveNumber.ONE);
                        helper = helper.minus(other);
                    }
                    int k = divided.number.size() - i;
                    for (int j = 0; j < k; j++) {
                        divided.number.remove(divided.number.size() - 1);
                    }

                    System.out.println("divided = " + divided + " helper = " + helper.number);
                    System.out.println(divided.number + " f f " + helper.number);
                    System.out.println("result = " + result);

                    break;
                }

                if (divided.number.size() != number.size() && divided.number.size() - i > 1) {
                    result = result.multiplication(10);
                }
                System.out.println("result = " + result);

                if (i == 0) return result;
            }

            int k = divided.number.size();
            if (PositiveNumber.ONE.compareTo(helper) > 0) {
                PositiveNumber test = divided.clone();
                test.zeroDelete();
                if (PositiveNumber.ONE.compareTo(test) > 0) {
                for (int i = 0; i<k; i++ ) {
                    System.out.println("success");
                    result = result.multiplication(10);

                   }
                break;
                }
            }
            System.out.println("------------------------");
            o++;
        }

        return result;
    }

}

class Main{
    public static void main(String[] args) {
        PositiveNumber x = new PositiveNumber("1219028852");
        PositiveNumber test = new PositiveNumber( "1234567899");
        PositiveNumber test1 = new PositiveNumber("54654165498265456546523154486548797886798451654");

        PositiveNumber a = new PositiveNumber(600);
        PositiveNumber b = new PositiveNumber(601);

        System.out.println(test1.multiplication(test));
        System.out.println(test.toInt());
    }
}
