 

public class StudentsTester
{
    public static void main(String[] args)
    {
        System.out.println("\n=============Constructor and to_string===============\n");
        MatrixList ml = new MatrixList();
        int[][] arr = {{10, 2, 30}, {-16, 20, 2}, {4, 4, 15}}; //
        ml = new MatrixList(arr);

        System.out.println(ml);

        System.out.println("\n=============get and set===============\n");
        int i = ml.getData_i_j(0, 2);
        System.out.println(i);
        System.out.println();
        ml.setData_i_j(0, 2, 1);
        System.out.println(ml);

        System.out.println("\n=============max===============\n");
        i = ml.findMax();

        System.out.println(i);

        System.out.println("\n=============how many x===============\n");

        MatrixList ml3 = new MatrixList();
        int[][] arr3 = {{1, 2, 3}}; // , {2 , 4, 5}, {10 , 11, 12}
        ml3 = new MatrixList(arr3);

        System.out.println(ml3);
        i = ml3.howManyX(3);

        System.out.println(i);

        System.out.println("\n=============negative testing===============\n");
        MatrixList ml2 = new MatrixList();
        int[][] arr2 = {}; //
        ml2 = new MatrixList(arr2);

        System.out.println(ml2);
        i = ml2.findMax();

        System.out.println(i);

        i = ml2.howManyX(3);

        System.out.println(i);

    }
}
