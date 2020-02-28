import javax.swing.*;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

class Cipher
{
    private ArrayList<Character> text;
    private char[][] matrix;
    Cipher() {
        createMatrix();
        for(int i=0;i<26;i++)
            print(matrix[i]);
        saveCipherText(cipherText(getTextFromFile()));
    }
    private void createMatrix() {
        matrix = new char[26][26];
        for(int i=0;i<26;i++)
            matrix[0][i] = (char)(97+i);
        for(int i=0;i<25;i++)
        {
            System.arraycopy(matrix[i], 1, matrix[i+1], 0, 25);
            System.arraycopy(matrix[i], 0, matrix[i+1], 25, 1);
        }
    }
    public ArrayList<Character> getText()
    {
        return text;
    }
    private String cipherText(ArrayList<Character> temp)
    {
        text=temp;
        StringBuilder out = new StringBuilder();
        for(int i=0;i<temp.size();i++)
        {
            if(temp.get(i)>= 97 && temp.get(i)<=122)
                out.append(cipherChar(temp.get(i),getLastChar(temp,i)));
            else out.append(temp.get(i));
            /*if(i==0)
                out.append(cipherChar(temp.get(i), 'a'));
            else
                out.append(cipherChar(temp.get(i), temp.get(i - 1)));*/
        }
        return out.toString();
    }
    private char getLastChar(ArrayList<Character> temp, int lastInd)
    {
        for(int i=lastInd-1;i>=0;i--)
            if(temp.get(i)>=97 && temp.get(i)<=122)
                return temp.get(i);
        return 'a';
    }
    private char cipherChar(char part,char key)
    {
        return matrix[(int)(part-97)][(int)(key-97)];
    }
    private void print(char[] matrix)
    {
        System.out.println();
        for(int i=0;i<26;i++)
            System.out.print(matrix[i]+ " ");
    }
    private ArrayList<Character> getTextFromFile()
    {
        JFileChooser file = new JFileChooser();
        file.setCurrentDirectory(new File("C:\\Users\\Рафаэль\\Desktop"));
        file.showOpenDialog(file);
        int tInt=0;
        ArrayList<Character> temp = new ArrayList<>();
        try{
            FileInputStream fileOutputStream = new FileInputStream(file.getSelectedFile());
            while(true)
            {
                tInt = fileOutputStream.read();
                if(tInt==-1)
                    break;
                else
                    temp.add((char)tInt);
            }
            fileOutputStream.close();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return temp;
    }
    private void saveCipherText(String out) {
        char[] array = new char[out.length()];
        for(int i=0;i<array.length;i++)
        {
            array[i] = out.charAt(i);
        }
        JFileChooser file = new JFileChooser();
        file.setCurrentDirectory(new File("C:\\Users\\Рафаэль\\Desktop"));
        file.showSaveDialog(file);
        File file1 = new File(String.valueOf(file.getSelectedFile()));
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file1);
            for (char c : array) {
                fileOutputStream.write(c);
            }
            fileOutputStream.close();
        }
        catch (IOException ex)
        {
            System.out.print("Exception happened");
        }
    }
}