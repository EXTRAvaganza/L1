import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

class deCipher {
    private char[][] cipherMatrix;
    private char[][] deCipherMatrix;
    deCipher()
    {
        createCipherMatrix();
        createDeCipherMatrix();
        deCipherText(getTextFromFile());
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
            while(true)
            {
                tInt = fileOutputStream.read();
                if(tInt==-1)
                    break;
                if((tInt>=97 && tInt<=122) || (tInt>=65 && tInt<=90))
                    if(tInt <= 90)
                        temp.add ((char)(tInt+32));
                    else
                        temp.add((char)tInt);
            }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return temp;
    }
    private void deCipherText(ArrayList<Character> array)
    {
        ArrayList[] result = new ArrayList[26];
        for(int i=0;i<26;i++)
        {
            result[i] = new ArrayList<Character>();
            result[i].add(deCipherChar(array.get(0),(char)('a'+i)));
        }
        for(int i=0;i<26;i++)
        {
            for(int j=0;j<array.size()-1;j++)
            {
                result[i].add(deCipherChar(array.get(j+1), (Character) result[i].get(j)));
            }
        }
        for (int i=0;i<26;i++)
        {
            for(int j=0;j<result[i].size();j++)
                System.out.print(result[i].get(j));
            System.out.println();
        }
    }
    private char deCipherChar(char cipherChar,char prevChar)
    {
        return deCipherMatrix[(int)cipherChar-97][(int)prevChar-97];
    }

}
