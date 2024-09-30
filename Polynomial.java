import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Polynomial {

    //fields
    double[] coefficients;
    int[] exponents;


    //constructs


    public Polynomial() {
        coefficients = new double[] {0.0};
        exponents = new int[] {0};
    }

    public Polynomial(double[] coeff, int[] expos){
        this.coefficients = coeff.clone();
        this.exponents = expos.clone();
    }

    // New constructor that initializes Polynomial from file
    public Polynomial(File file){
        
        try (Scanner scanner = new Scanner(file)) {

            
            if(scanner.hasNextLine()){
                String content = scanner.nextLine();

                content = content.replace("-", "+-");
                String[] components = content.split("\\+");

                this.coefficients = new double[components.length];
                this.exponents = new int[components.length];

                for(int i = 0; i < components.length; i++){

                    if (!components[i].equals("") && !components[i].equals("0")) {

                        String[] temp = components[i].split("x");
                        double coeff = Double.parseDouble(temp[0]);
                        int expo = Integer.parseInt(temp[1]);

                        this.add_component(coeff, expo);
                    }

                }
            }
            else {
                System.out.println("File is empty or doesn't contain a valid polynomial.");
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format");
            System.out.println(e);
        }
    }


    public void arrange() {
        int len = this.coefficients.length;
        for (int i = 0; i < len - 1; i++) {
            for (int j = 0; j < len - i - 1; j++) {
                if (this.exponents[j] > this.exponents[j + 1]) {
                    // Swap exponents
                    int tempExpo = this.exponents[j];
                    this.exponents[j] = this.exponents[j + 1];
                    this.exponents[j + 1] = tempExpo;
    
                    // Swap coefficients
                    double tempCoeff = this.coefficients[j];
                    this.coefficients[j] = this.coefficients[j + 1];
                    this.coefficients[j + 1] = tempCoeff;
                }
            }
        }
    }

    public void simplify() {
        // Temporary lists to store the new coefficients and exponents
        List<Double> newCoefficients = new ArrayList<>();
        List<Integer> newExponents = new ArrayList<>();
    
        // Iterate through the polynomial and remove terms with zero coefficients
        for (int i = 0; i < this.coefficients.length; i++) {
            if (this.coefficients[i] != 0.0) {
                newCoefficients.add(this.coefficients[i]);
                newExponents.add(this.exponents[i]);
            }
        }
    
        // Convert the lists back to arrays
        this.coefficients = new double[newCoefficients.size()];
        this.exponents = new int[newExponents.size()];
    
        for (int i = 0; i < newCoefficients.size(); i++) {
            this.coefficients[i] = newCoefficients.get(i);
            this.exponents[i] = newExponents.get(i);
        }
    }


    // Add a new term to the polynomial
    public void add_component(double coeff, int expo) {
        // Check if the exponent already exists in the polynomial
        boolean found = false;
        for (int i = 0; i < exponents.length; i++) {
            if (exponents[i] == expo) {
                // If the exponent exists, add the new coefficient to the existing one
                coefficients[i] += coeff;
                found = true;
                break;
            }
        }

        // If the exponent doesn't exist, add the new term to the arrays
        if (!found) {
            // Create new arrays with an additional space for the new term
            double[] newCoefficients = new double[coefficients.length + 1];
            int[] newExponents = new int[exponents.length + 1];

            // Copy existing terms
            System.arraycopy(coefficients, 0, newCoefficients, 0, coefficients.length);
            System.arraycopy(exponents, 0, newExponents, 0, exponents.length);

            // Add the new term
            newCoefficients[newCoefficients.length - 1] = coeff;
            newExponents[newExponents.length - 1] = expo;

            // Update the polynomial arrays
            this.coefficients = newCoefficients;
            this.exponents = newExponents;
        }

        // Arrange and simplify the polynomial after adding the new term
        this.arrange();
        this.simplify();
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
        

        Polynomial resutpoly = new Polynomial(newCoefficients, newExponents);

        //arranging
        resutpoly.arrange();

        //simplify
        resutpoly.simplify();


        // Return the new Polynomial
        return resutpoly;
    }
    

    public Polynomial multiply(Polynomial poly){

        //arrange the polynomials
        this.arrange();
        poly.arrange();

        Polynomial resultpoly = new Polynomial();

        for(int i = 0; i < poly.coefficients.length; i++){

            for(int j = 0; j < this.coefficients.length; j++){

                double[] tempcoeff = new double[] {poly.coefficients[i] * this.coefficients[j]};
                int[] tempexpo = new int[] {poly.exponents[i] + this.exponents[j]};

                Polynomial temppoly = new Polynomial(tempcoeff, tempexpo);

                resultpoly = resultpoly.add(temppoly);
            }

        }

        //arranging
        resultpoly.arrange();

        //simplify
        resultpoly.simplify();

        //returning the result
        return resultpoly;

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


    // Method to save the polynomial to a file in textual format
    public void saveToFile(String fileName){
        try{
            FileWriter writer = new FileWriter(fileName);
        
            // Build the polynomial string
            StringBuilder polyStr = new StringBuilder();

            this.arrange();
            this.simplify();
            
            if(this.coefficients.length == 0){
                polyStr.append("0");
            }
            for (int i = 0; i < coefficients.length; i++) {
                double coeff = coefficients[i];
                int expo = exponents[i];

                if (i > 0 && coeff > 0) {
                    polyStr.append("+");
                }
            
                if (expo == 0) {
                    // Constant term
                    polyStr.append(coeff);
                } else if (expo == 1) {
                    // Linear term
                    if (coeff == 1.0) {
                        polyStr.append("x");
                    } else if (coeff == -1.0) {
                        polyStr.append("-x");
                    } else {
                        polyStr.append(coeff).append("x");
                    }
                } else {
                    // Higher degree term
                    if (coeff == 1.0) {
                        polyStr.append("x").append(expo);
                    } else if (coeff == -1.0) {
                        polyStr.append("-x").append(expo);
                    } else {
                        polyStr.append(coeff).append("x").append(expo);
                    }
                }
            }

            // Write the string to the file
            writer.write(polyStr.toString());
            writer.close();
        }

        catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format");
            System.out.println(e);
        }
    }
}