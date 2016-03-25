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

    void solve(ArrayList<String> input) {
        System.out.println("Hammine Code:");
        ArrayList<String> parityList = new ArrayList();
        String[] inputSplit;
        String parityKind, binInput;
        for (String str : input) {
            boolean hasError = false;
            inputSplit = str.split(" ");
            parityKind = inputSplit[0];
            binInput = inputSplit[1];
            System.out.println("Parity: " + parityKind);
            System.out.println("Input: " + binInput);
            int changeThisPos = 0;
            String codeWord = addCheckBits(binInput, parityKind);
            System.out.println("CodeWord: " + codeWord);
            for (int i = 0, j = 1; i < codeWord.length(); i++, j++) {
                changeThisPos = 0;
                if ((j & (j - 1)) == 0) {
                    if (!checkParity(takeNthPower(codeWord, j), parityKind)) {
                        changeThisPos += j;
                        if (codeWord.charAt(changeThisPos - 1) == '1') {
                            codeWord = changeCharInPosition(changeThisPos - 1, '0', codeWord);
                        } else {
                            codeWord = changeCharInPosition(changeThisPos - 1, '1', codeWord);
                        }
                        hasError = true;
                    }
                }
            }
            if (hasError) {
                System.out.println("New CodeWord: " + codeWord);
            } else {
                System.out.println("NO Error");
            }
        }
    }

    String addCheckBits(String str, String parity) {
        String codeWord = "";
        double noOfCheckBits = Math.log(str.length());
        noOfCheckBits /= Math.log(2);
        noOfCheckBits++;
        for(int i = 0 ,j = 1; j<=str.length()+noOfCheckBits;j++){
              if ((j & (j - 1)) == 0){
                  codeWord+="0";
              }else{
                codeWord += str.charAt(i);
                i++;
              }
        }
        str = codeWord;
        codeWord = "";
        for (int i = 0, j = 1; j <= str.length() + noOfCheckBits; j++) {
            if ((j & (j - 1)) == 0) {
                if (StringUtils.equalsIgnoreCase(parity, "even")) {
                    if (StringUtils.countMatches(takeNthPower(str, j), "1") % 2 == 0) {
                        codeWord += "0";
                    } else {
                        codeWord += "1";
                    }
                } else if (StringUtils.equalsIgnoreCase(parity, "odd")) {
                    if (StringUtils.countMatches(takeNthPower(str, j), "1") % 2 == 0) {
                        codeWord += "1";
                    } else {
                        codeWord += "0";
                    }
                }
            } else {
                codeWord += str.charAt(i);
                i++;
            }
        }
        return codeWord;
    }

    public String changeCharInPosition(int position, char ch, String str) {
        char[] charArray = str.toCharArray();
        charArray[position] = ch;
        return new String(charArray);
    }

    String takeNthPower(String str, int power) {
        String parityStr = "";
        for (int i = power - 1; i < str.length();) {
            int pos = power + i;
            while (i < pos) {
                if (i < str.length()) {
                    parityStr += Character.toString(str.charAt(i));
                }
                i++;
            }
            i += power;
        }
        return parityStr;
    }

    Boolean checkParity(String parityString, String parityKind) {
        int ones = StringUtils.countMatches(parityString, "1");
        if (StringUtils.equalsIgnoreCase(parityKind, "even")) {
            if (ones % 2 == 0) {
                return true;
            }
            return false;
        } else if (StringUtils.equalsIgnoreCase(parityKind, "odd")) {
            if (ones % 2 == 1) {
                return true;
            }
            return false;
        }
        return null;
    }
}
