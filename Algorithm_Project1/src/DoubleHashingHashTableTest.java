/**
 * Created by thomas on 10/6/16.
 */

import java.util.Scanner;

/* Class DoubleHashingHashTableTest */
public class DoubleHashingHashTableTest
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("Hash Table Test\n\n");
        System.out.println("Enter size");
        /* Make object of HashTable */
        HashTable ht = new HashTable(scan.nextInt() );

        char ch;
        /*  Perform HashTable operations  */
        do
        {
            System.out.println("\nHash Table Operations\n");
            System.out.println("1. insert ");
            System.out.println("2. remove");
            System.out.println("3. get");
            System.out.println("4. check empty");
            System.out.println("5. clear");
            System.out.println("6. size");

            int choice = scan.nextInt();
            switch (choice)
            {
                case 1 :
                    System.out.println("Enter key and value");
                    ht.insert(scan.next(), scan.nextInt() );
                    break;
                case 2 :
                    System.out.println("Enter key");
                    ht.remove( scan.next() );
                    break;
                case 3 :
                    System.out.println("Enter key");
                    System.out.println("Value = "+ ht.get( scan.next() ));
                    break;
                case 4 :
                    System.out.println("Empty Status " +ht.isEmpty());
                    break;
                case 5 :
                    ht.makeEmpty();
                    System.out.println("Hash Table Cleared\n");
                    break;
                case 6 :
                    System.out.println("Size = "+ ht.getSize() );
                    break;
                default :
                    System.out.println("Wrong Entry \n ");
                    break;
            }
            /* Display hash table */
            ht.printHashTable();

            System.out.println("\nDo you want to continue (Type y or n) \n");
            ch = scan.next().charAt(0);
        } while (ch == 'Y'|| ch == 'y');
    }
}
