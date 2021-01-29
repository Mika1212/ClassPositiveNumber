import java.io.PipedOutputStream;
import java.util.ArrayList;

public class PositiveNumber {

    ArrayList<Integer> number;

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

    public void show() {
        System.out.println(number);
    }

    public ArrayList<Integer> plus(int other) {
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
        return result;
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
        PositiveNumber res = new PositiveNumber(0);
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
        PositiveNumber res = new PositiveNumber(0);
        res.number = result;
        return res;
    }

    public PositiveNumber minus(PositiveNumber other) {
        ArrayList<Integer> result = new ArrayList<>();
        ArrayList<Integer> smth = other.number;
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
        PositiveNumber res = new PositiveNumber(0);
        res.number = result;
        return res;
    }


    public PositiveNumber division(int other) {
        PositiveNumber result = new PositiveNumber(0);
        while (true) {
            PositiveNumber sample = this.minus(other);
            if (sample.isBigger(other) || sample.isEqual(other)) {
                result.number.set(0, result.number.get(0)+1);
                this.number = sample.number;
            } else {
                break;
            }
        }
        result.number = transformation(result.number);
        return result;
    }

    public PositiveNumber multiplication(int other) {
        PositiveNumber result = new PositiveNumber(0);
        int i = other;
        while (i>0) {
            i--;
            result = result.plus(this);
            System.out.println(result);
        }
        result.number = transformation(result.number);
        return result;
    }

    public Boolean isBigger(PositiveNumber other){
        return (this.minus(other) != null &&
                !this.minus(other).toString().equals(arrayMaker(0).toString()));
    }

    public Boolean isBigger(int other){
        return (this.minus(other) != null &&
                !this.minus(other).toString().equals(arrayMaker(0).toString()));
    }

    public Boolean isEqual(PositiveNumber other) {
        if (this.minus(other) == null) return false;
        return this.minus(other).toString().equals(arrayMaker(0).toString());
    }

    public Boolean isEqual(int other) {
        if (this.minus(other) == null) return false;
        return this.minus(other).toString().equals(arrayMaker(0).toString());
    }


    public static ArrayList<Integer> transformation(ArrayList<Integer> array) {
        for (int i = 0; i<array.size(); i++){
            while (array.get(i)>=10) {
                array.set(i, array.get(i)-10);
                if (array.size()<i+2) {
                    array.add(1);
                } else
                    array.set(i+1, array.get(i+1)+1);
            }
            while (array.get(i)<0) {
                array.set(i, array.get(i)+10);
                if (array.size()<i+2) {
                    System.out.println('2');
                    return null;
                }
                array.set(i+1, array.get(i+1)-1);
            }
            while (array.size()>1 && array.get(array.size()-1) == 0) {
                array.remove(array.size()-1);
            }
        }
        return array;
    }

}

class Main{
    public static void main(String[] args) {
        PositiveNumber test = new PositiveNumber("200000000000000000000000000000000000000000");

        PositiveNumber a = test.division(2);
        a.show();
    }
}
