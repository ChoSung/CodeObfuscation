
import java.util.Arrays;

/*
 * ���ڷ� �����ϴ� class�� �Ұ�
 * class�� ��ҹ��ڰ� ������ �Ұ�
 */

public class NameEncryption {
	int num;
	
	public void NameEncryption(){
		num = 0;
	}
	
	public String getEnName(){
		//������ ���� 3�� 8���� limit�� ������ ���� ���� �ʿ� 
		String StrEncryption="";
		int[] forChange = new int[8];
		Arrays.fill(forChange, 0);
		char[] ternaryChar = new char[3];
		ternaryChar[0] = 'l';
		ternaryChar[1] = 'I';
		ternaryChar[2] = 'J';
		int num_temp = num;
		for(int i=0; i<forChange.length; i++){
			if(num_temp<3){
				forChange[i] = num_temp;
				break;
			}
			forChange[i] = num_temp%3;
			num_temp = num_temp/3;
		}
		
		for(int i=0; i<forChange.length; i++)
			StrEncryption += ternaryChar[forChange[i]];
		
		num++;
		return StrEncryption;
	}
}
