import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

class deCipher {
    private ArrayList<Character> result;
    private ArrayList<Character> sourceText;
    private ArrayList<Character> cipherText;
    private char[][] cipherMatrix;
    private char[][] deCipherMatrix;
    deCipher(ArrayList<Character> sourceText)
    {
        this.sourceText = sourceText;
        createCipherMatrix();
        createDeCipherMatrix();
        cipherText = getTextFromFile();
        deCipherText(cipherText,getFirstKey(cipherText));
    }
    private void createCipherMatrix() {
        cipherMatrix = new char[26][26];
        for(int i=0;i<26;i++)
            cipherMatrix[0][i] = (char)(97+i);
        for(int i=0;i<25;i++)
        {
            System.arraycopy(cipherMatrix[i], 1, cipherMatrix[i+1], 0, 25);
            System.arraycopy(cipherMatrix[i], 0, cipherMatrix[i+1], 25, 1);
        }
    }
    private void createDeCipherMatrix()
    {
        deCipherMatrix = new char[26][26];
        for(int i=0;i<26;i++)
        {
            if(i==0)
            {
                for(int j=0;j<26;j++)
                    deCipherMatrix[j][i] = cipherMatrix[j][i];
            }
            else
                for(int j=0;j<26;j++)
                {
                    deCipherMatrix[j][i] = cipherMatrix[j][26-i];
                }
        }
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
            while (true)
            {
                tInt = fileOutputStream.read();
                if(tInt==-1)
                    break;
                temp.add((char)tInt);
            }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return temp;
    }

    private char getLastChar(ArrayList<Character> temp, int lastInd)
    {
        for(int i=lastInd-1;i>=0;i--)
            if(temp.get(i)>=97 && temp.get(i)<=122)
                return temp.get(i);
        return 'a';
    }
    private char getFirstKey(ArrayList<Character> array)
    {
        ArrayList[] result = new ArrayList[26];
        for(int i=0;i<26;i++)
        {
            result[i] = new ArrayList<Character>();
            for(int j=0;j<20;j++)
            {
                if(array.get(j)>= 97 && array.get(j)<=122)
                {
                    if(result[i].size()==0)
                        result[i].add(deCipherChar(array.get(j), (char) ('a'+i)));
                    else
                        result[i].add(deCipherChar(array.get(j),getLastChar(result[i],result[i].size())));
                }
                else
                    result[i].add(array.get(j));
            }
            if (checkEqual(result[i]))
            {
                System.out.println((char)('a'+i));
                return (char)('a'+i);
            }
        }
        return 'a';
    }
    private void deCipherText(ArrayList<Character> array,char key)
    {

            result = new ArrayList<Character>();
            for(int j=0;j<array.size();j++)
            {
                if(array.get(j)>= 97 && array.get(j)<=122)
                {
                    if(result.size()==0)
                        result.add(deCipherChar(array.get(j), key));
                    else
                        result.add(deCipherChar(array.get(j),getLastChar(result,result.size())));
                }
                else
                    result.add(array.get(j));
            }
                saveResultText(this.result);
    }
    private boolean checkEqual(ArrayList<Character> array)
    {
        for(int i=0;i<array.size();i++)
            if(sourceText.get(i).equals(array.get(i))!=true)
                return false;
            return true;
    }
    private char deCipherChar(char cipherChar,char prevChar)
    {
        return deCipherMatrix[(int)cipherChar-97][(int)prevChar-97];
    }
    private void saveResultText(ArrayList<Character> out) {
        char[] array = new char[out.size()];
        for(int i=0;i<array.length;i++)
        {
            array[i] = out.get(i);
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
