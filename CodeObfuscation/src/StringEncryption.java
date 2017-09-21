
public class StringEncryption {
	static String changedStr;
	public static String getStr(String input){
		String[] a = null;
		a = input.split("l");
		changedStr = "";
		for(int i=0; i<a.length; i++){
			changedStr += (char)Integer.parseInt(a[i]);
		}
		return changedStr;
	}
}
