
public class Polynomial {

    //fields
    double[] coefficients;

    //methods

    public Polynomial() {
        coefficients = new double[] {0.0};
    }

    public Polynomial(double[] coeff){
        this.coefficients = coeff.clone();
    }

    public Polynomial add(Polynomial toadd){

        int maxlength = Math.max(this.coefficients.length, toadd.coefficients.length);

        double[] resultCoeffs = new double[maxlength];

        for(int i = 0; i < maxlength; i++){

            double coeffother = (i<toadd.coefficients.length) ? toadd.coefficients[i] : 0.0;
            double coeffthis = (i<this.coefficients.length) ? this.coefficients[i] : 0.0;

            resultCoeffs[i] = coeffother + coeffthis;

        }

        return new Polynomial(resultCoeffs);
    }

    public double evaluate(double eva){

        int len = this.coefficients.length;
        double result = 0.0;

        for(int i = 0; i < len; i++){
            
            result += this.coefficients[i]*(Math.pow(eva, i));

        }

        return result;
    }

    public boolean hasRoot(double x){

        return Math.abs(evaluate(x)) < 1e-9; 
        // Use a small tolerance to account for floating-point precision
    }
}