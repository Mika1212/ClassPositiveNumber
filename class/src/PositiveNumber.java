
import javax.naming.event.ObjectChangeListener;
import java.math.BigInteger;
import java.util.ArrayList;

public class PositiveNumber implements Comparable {

    ArrayList<Integer> number;

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

    public PositiveNumber plus(int other) {
        ArrayList<Integer> result = new ArrayList<>();
        ArrayList<Integer> smth = arrayMaker(other);

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

    public PositiveNumber plus(BigInteger other) {
        ArrayList<Integer> result = new ArrayList<>();
        ArrayList<Integer> smth = arrayMaker(other);

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

    public PositiveNumber minus(int other) {
        ArrayList<Integer> result = new ArrayList<>();
        ArrayList<Integer> smth = arrayMaker(other);
        if (smth.size()>number.size() ||
                (smth.size() == number.size() && smth.get(smth.size()-1) > number.get(smth.size()-1))) {
            System.out.println('1');
            return null;
        }

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

    public PositiveNumber minus(BigInteger other) {
        ArrayList<Integer> result = new ArrayList<>();
        ArrayList<Integer> smth = arrayMaker(other);
        if (smth.size()>number.size() ||
                (smth.size() == number.size() && smth.get(smth.size()-1) > number.get(smth.size()-1))) {
            System.out.println('1');
            return null;
        }

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

    public PositiveNumber minus(PositiveNumber other) {
        ArrayList<Integer> result = new ArrayList<>();
        ArrayList<Integer> smth = other.number;
        if (smth.size()>number.size() ||
                (smth.size() == number.size() && smth.get(smth.size()-1) > number.get(smth.size()-1))) {
            System.out.println("first");
            return null;
        }

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

    public PositiveNumber division(int other) {
        PositiveNumber result = new PositiveNumber();
        result.number.add(0);
        for (int i = 0; i<number.size(); i++){
            result.number.add(number.get(i));
        }

        for (int i = number.size(); i>0; i--) {
            if (result.number.get(i) >= other) {
                result.number.set(i-1, result.number.get(i-1) + result.number.get(i) % other * 10);
                result.number.set(i, result.number.get(i) / other);
            } else {
                result.number.set(i-1, result.number.get(i-1) + result.number.get(i) * 10);
                result.number.set(i, 0);
            }
        }

        result.number.set(2, result.number.get(2) / other);
        result.number.remove(0);

        result.number = transformation(result.number);
        return result;
    }

    public PositiveNumber division(PositiveNumber other) {
        PositiveNumber result = new PositiveNumber();


        result.number = transformation(result.number);
        return result;
    }

    public PositiveNumber multiplication(int other) {
        PositiveNumber result = new PositiveNumber();
        for (int i = 0; i<number.size(); i++) {
           result.number.add(this.number.get(i)* other);
       }

        result.number = transformation(result.number);
        return result;
    }

    @Override
    public int compareTo(Object o) {
        PositiveNumber other = (PositiveNumber) o;
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
                } else
                    array.set(i+1, array.get(i+1) + (array.get(i) / 10));
                array.set(i, array.get(i) % 10);
            }
            if  (array.get(i)<0) {
                if (array.size()<i+2) {
                    System.out.println("second");
                    return null;
                }
                array.set(i+1, (array.get(i+1) / 10)*-1);
                array.set(i, (array.get(i) % 10)*-1);
            }
        }
        while (array.size()>1 && array.get(array.size()-1) == 0) {
            array.remove(array.size() - 1);
        }
        return array;
    }

    public void show() {
        StringBuilder result = new StringBuilder();
        for (int i = number.size()-1; i>=0; i--) {
            result.append(number.get(i));
        }
        System.out.println(result);
    }

}

class Main{
    public static void main(String[] args) {

        PositiveNumber test = new PositiveNumber("1000");


        PositiveNumber test1 = new PositiveNumber(1000);

        System.out.println(test1.compareTo(test));
    }
}
