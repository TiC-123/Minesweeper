import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GenerateBomb {
    private List<Integer> number = new ArrayList<>();
    private int limit, bound, temp, rNumber[];
    private boolean check;

    public void setLimit( int limit, int bound ) {
        this.limit = limit;
        this.bound = bound;
        rNumber = new int[bound];
    }

    public int[] getRandomNumberList() {
        for( int i = 1; i <= limit; i++ )
            number.add(i);

        rNumber[0] = number.get( ThreadLocalRandom.current().nextInt( number.size() ) % limit );

        for( int j = 1; j < bound; j++ ) {
            check = false;
            temp = number.get( ThreadLocalRandom.current().nextInt( number.size() ) % limit );

            for( int k = 0; k < j && check != true ; k++ ) {
                if( temp == rNumber[k] ) {
                    check = true;
                }
            }

            if( check == true ) 
                j--;
            else
                rNumber[j] = temp;
        }

        return rNumber;
    } 

    public void showBomb() {
        for( int i = 0; i < bound; i++ )
            System.out.println( rNumber[i] );
    }
}