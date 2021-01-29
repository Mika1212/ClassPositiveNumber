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
        while (other1 > 0) {
            other1 /= 10;
            k++;
        }

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
        ArrayList<Integer> smth = arrayMaker(other);
        for (int i=0; i<Math.min(number.size(),smth.size()); i++) {
            number.set(i, smth.get(i) + number.get(i));
        }
        if (number.size()<smth.size()) {
            for (int i = number.size(); i<smth.size();i++){
                number.add(smth.get(i));
            }
        }
        number = transformation(number);
        return number;
    }

    ArrayList<Integer> minus(int other) {
        ArrayList<Integer> smth = arrayMaker(other);
        if (smth.size()>number.size() ||
                (smth.size() == number.size() && smth.get(smth.size()-1) > number.get(smth.size()-1))) {
            System.out.println('1');
            return null;
        }
        for (int i=0; i<Math.min(number.size(),smth.size()); i++) {
            number.set(i, number.get(i) - smth.get(i));
        }
        if (number.size()<smth.size()) {
            for (int i = number.size(); i<smth.size();i++){
                number.add(smth.get(i)*-1);
            }
        }
        number = transformation(number);
        return number;
    }

    public ArrayList<Integer> division(int other) {
        ArrayList<Integer> k = new ArrayList<>();
        k.add(0);
        while (true) {
            ArrayList<Integer> sample = this.minus(other);
            if (sample != null) {
                k.set(0, k.get(0)+1);
                number = sample;
            } else {
                break;
            }
        }
        k = transformation(k);
        return k;
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
                    return null;
                }
                array.set(i+1, array.get(i+1)-1);
            }
        }
        return array;
    }

}

class Main{
    public static void main(String[] args) {
        PositiveNumber test = new PositiveNumber(19);
        test.plus(101);

        PositiveNumber test1 = new PositiveNumber("-100");
        test1.show();
        ArrayList<Integer> k = test1.division(2);
        test1.show();
        System.out.println(test1.number);

    }
}
