import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Polynomial {

    //fields
    double[] coefficients;
    int[] exponents;

    //methods

    public Polynomial() {
        coefficients = new double[] {0.0};
        exponents = new int[] {0};
    }

    public Polynomial(double[] coeff, int[] expos){
        this.coefficients = coeff.clone();
        this.exponents = expos.clone();
    }

    

    public void arrange(){


        int len = this.coefficients.length;

        double[] newcoeff = new double[len];
        int[] newexpo = new int[len];

        

        for(int i = 0; i < len; i++){
            for(int j = 0; j < len; j++){
                if( this.exponents[j] == i){
                    newcoeff[i] = this.coefficients[j];
                    newexpo[i] = i;
                }
            }
        }

        this.coefficients = newcoeff.clone();
        this.exponents = newexpo.clone();

    }

    public Polynomial add(Polynomial poly) {
        // Arrange both polynomials to ensure exponents are in ascending order
        this.arrange();
        poly.arrange();
    
        // Temporary storage for the result
        List<Double> resultCoefficients = new ArrayList<>();
        List<Integer> resultExponents = new ArrayList<>();
    
        int i = 0, j = 0;
        int len1 = this.coefficients.length;
        int len2 = poly.coefficients.length;
    
        // Merge the two polynomials based on exponents
        while (i < len1 && j < len2) {
            if (this.exponents[i] == poly.exponents[j]) {
                // If exponents are equal, add the coefficients
                resultCoefficients.add(this.coefficients[i] + poly.coefficients[j]);
                resultExponents.add(this.exponents[i]);
                i++;
                j++;
            } else if (this.exponents[i] < poly.exponents[j]) {
                // If current term in this polynomial has a smaller exponent, add it to the result
                resultCoefficients.add(this.coefficients[i]);
                resultExponents.add(this.exponents[i]);
                i++;
            } else {
                // If current term in the argument polynomial has a smaller exponent, add it to the result
                resultCoefficients.add(poly.coefficients[j]);
                resultExponents.add(poly.exponents[j]);
                j++;
            }
        }
    
        // Add any remaining terms from the current polynomial
        while (i < len1) {
            resultCoefficients.add(this.coefficients[i]);
            resultExponents.add(this.exponents[i]);
            i++;
        }
    
        // Add any remaining terms from the input polynomial
        while (j < len2) {
            resultCoefficients.add(poly.coefficients[j]);
            resultExponents.add(poly.exponents[j]);
            j++;
        }
    
        // Convert the lists back to arrays
        double[] newCoefficients = new double[resultCoefficients.size()];
        int[] newExponents = new int[resultExponents.size()];
    
        for (int k = 0; k < resultCoefficients.size(); k++) {
            newCoefficients[k] = resultCoefficients.get(k);
            newExponents[k] = resultExponents.get(k);
        }
    
        // Return the new Polynomial
        return new Polynomial(newCoefficients, newExponents);
    }
    
    
    public Polynomial multiply(Polynomial poly) {
    // Temporary storage for the result
    Map<Integer, Double> resultMap = new HashMap<>();

    // Multiply each term in this polynomial by each term in the input polynomial
    for (int i = 0; i < this.coefficients.length; i++) {
        for (int j = 0; j < poly.coefficients.length; j++) {
            int newExpo = this.exponents[i] + poly.exponents[j]; // Sum the exponents
            double newCoeff = this.coefficients[i] * poly.coefficients[j]; // Multiply the coefficients

            // If this exponent already exists, add the new coefficient to the existing one
            resultMap.put(newExpo, resultMap.getOrDefault(newExpo, 0.0) + newCoeff);
        }
    }

    // Convert the map to arrays for the new Polynomial
    int size = resultMap.size();
    double[] newCoefficients = new double[size];
    int[] newExponents = new int[size];

    int index = 0;
    // Extract the combined terms from the map and fill the arrays
    for (Map.Entry<Integer, Double> entry : resultMap.entrySet()) {
        newExponents[index] = entry.getKey();
        newCoefficients[index] = entry.getValue();
        index++;
    }

    // Sort the resulting polynomial by exponents in ascending order
    // You can use the arrange() method here or sort directly
    Polynomial result = new Polynomial(newCoefficients, newExponents);
    result.arrange(); // Arrange the resulting polynomial

    return result;
}


    public double evaluate(double eva){

        int len = this.coefficients.length;
        double result = 0.0;

        for(int i = 0; i < len; i++){
            
            result += this.coefficients[i]*(Math.pow(eva, exponents[i]));

        }

        return result;
    }

    public boolean hasRoot(double x){

        return Math.abs(evaluate(x)) < 1e-9; 
        // Use a small tolerance to account for floating-point precision
    }
}