

public class Driver {
    public static void main(String [] args) {

        Polynomial p = new Polynomial();

        System.out.println(p.evaluate(3));

        double [] c1 = {6,0,0,5};
        int [] e1 = {0,1,2,3};
        Polynomial p1 = new Polynomial(c1, e1);

        double [] c2 = {0,-2,0,0,-9};
        int[] e2 = {0,1,2,3,4};
        Polynomial p2 = new Polynomial(c2, e2);

        // double [] c4 = {3,2};
        // int[] e4 = {2,0};
        // Polynomial p4 = new Polynomial(c4, e4);

        // double [] c5 = {1,4};
        // int[] e5 = {3,1};
        // Polynomial p5 = new Polynomial(c5, e5);

        // if(p1 != p4){
        //     System.out.println("not equal");
        // }

        

        
        
        p1.arrange();
        
        p1.simplify();

        int len1 = p1.coefficients.length;

        // for(int i = 0; i < len; i++){
        //     System.out.print(p1.exponents[i]);
        //     System.out.print(p1.coefficients[i]);
        // }

        for(int i = 0; i < len1; i++){
            System.out.println(p1.coefficients[i]);
            System.out.println(p1.exponents[i]);
        }


        // p1.removeRedundancy();

        // int len2 = p1.coefficients.length;

        // for(int i = 0; i < len2; i++){
        //     System.out.println(p1.coefficients[i]);
        //     System.out.println(p1.exponents[i]);
        // }

        Polynomial s = p1.add(p2);

        System.out.println("s(0.1) = " + s.evaluate(0.1));
        if(s.hasRoot(1))
            System.out.println("1 is a root of s");
        else
            System.out.println("1 is not a root of s");

        double [] c7 = {3,2};
        int [] e7 = {2,1};
        Polynomial p7 = new Polynomial(c7, e7);

        double [] c8 = {4,1};
        int [] e8 = {1,0};
        Polynomial p8 = new Polynomial(c8, e8);

        //Polynomial p9 = p7.multiply(p8);

        //int leno = p9.coefficients.length;
        //for(int i = 0; i < leno; i++){
           // System.out.print(p9.exponents[i]);
          //  System.out.print(p9.coefficients[i]);
       // }
        

       Polynomial mult = p7.multiply(p8);

       int len2 = mult.coefficients.length;

       for(int i = 0; i < len2; i++){
            System.out.println(mult.coefficients[i]);
            System.out.println(mult.exponents[i]);
        }


        
		

    }
}