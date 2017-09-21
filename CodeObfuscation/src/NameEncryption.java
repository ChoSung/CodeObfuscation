
import java.util.Arrays;

/*
 * 숫자로 시작하는 class명 불가
 * class의 대소문자가 같으면 불가
 */

public class NameEncryption {
	int num;
	
	public void NameEncryption(){
		num = 0;
	}
	
	public String getEnName(){
		//변수의 수가 3의 8승의 limit를 가진다 추후 수정 필요 
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
