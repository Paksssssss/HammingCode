/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hammingcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author paks
 */
public class HammingCode {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        HammingCode hc = new HammingCode();
        ArrayList<String> inputs = hc.readFile("/home/paks/NetBeansProjects/HammingCode/src/hammingcode/input.txt");
        hc.solve(inputs);
    }
    
    //Reads input file
    ArrayList<String> readFile(String filename) throws FileNotFoundException {
        Scanner input = new Scanner(new File(filename));
        ArrayList<String> inputList = new ArrayList();
        while (input.hasNext()) {
            inputList.add(input.nextLine());
        }
        return inputList;
    }
    
    void solve(ArrayList<String> input){
        ArrayList<String> parityList = new ArrayList();
        String[] inputSplit;
        String parityKind, binInput;
        for(String str  : input){
            inputSplit = str.split(" ");
            parityKind = inputSplit[0];
            binInput = inputSplit[1];
            System.out.println(binInput);
            for(int i = 0, j =1 ; i<binInput.length(); i++, j++){
                if((j & (j - 1)) == 0){
                    parityList.add(takeNthPower(binInput,j));
                }
            }
            System.out.println(checkParity(parityList, parityKind));
        }
        System.out.println(parityList);
    }
    
    String addCheckBits(String str){
        double noOfCheckBits = Math.log(str.length());
        noOfCheckBits /= Math.log(2);
        noOfCheckBits++;
    }
    
    String takeNthPower(String str, int power){
        String parityStr = "";
        for(int i  = power-1 ; i<str.length(); ){
            int pos = power +i;
            while(i<pos ){
                if(i<str.length()){
                    parityStr += Character.toString(str.charAt(i));
                }
                i++;
            }
            i+=power;
        }
        return parityStr;
    }
    ArrayList<Boolean> checkParity(ArrayList<String> parityList, String parityKind){
        ArrayList<Boolean> parityCheck = new ArrayList();
        for(String str : parityList){
            int ones = StringUtils.countMatches(str, "1");
            if(StringUtils.equalsIgnoreCase(parityKind, "even")){
                if(ones%2 == 0)
                    parityCheck.add(true);
                else
                    parityCheck.add(false);
            }else if(StringUtils.equalsIgnoreCase(parityKind, "odd")){
                if(ones%2 == 1)
                    parityCheck.add(true);
                else
                    parityCheck.add(false);
            }
        }
        return parityCheck;
    }
}
