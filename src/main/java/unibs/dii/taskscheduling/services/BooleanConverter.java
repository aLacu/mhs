package unibs.dii.taskscheduling.services;

import org.springframework.stereotype.Service;


@Service
public class BooleanConverter {

    public byte[] toByteArray(boolean[] array){
        return booleanArrayToString(array).getBytes();
    }

    public boolean[] fromByteArray(byte[] array){
        return stringToBooleanArray(new String(array));
    }

    public String booleanArrayToString(boolean[] array){
        final StringBuilder stringBuilder = new StringBuilder();
        for (boolean b: array
        ) {
            stringBuilder.append(b?1:0);
        }
        return stringBuilder.toString();
    }

    public boolean[] stringToBooleanArray(String string){
        final char[] chars = string.toCharArray();
        final boolean[] booleans = new boolean[chars.length];
        for (int i=0; i<chars.length; i++){
            booleans[i]=chars[i]=='1';
        }
        return booleans;
    }
}
