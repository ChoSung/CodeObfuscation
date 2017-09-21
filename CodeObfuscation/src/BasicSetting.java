import java.io.File;

public class BasicSetting {
	
	private static File selectFile;
	private static NameEncryption naemEn;
	private static String classList[] ;
	private static int count_of_class;
	private static String methodList[];
	private static int count_of_method;
	private static String packageList[];
	private static int count_of_package;
	
	public BasicSetting(){
		naemEn = new NameEncryption();
	}
	
	public void setSelectFile(File selectFile){
		this.selectFile = selectFile;
	}
	
	public static File getSelectFile(){
		return selectFile;
	}
	
	public static String getNameEn(){
		return naemEn.getEnName();
	}
	
	public static void setClassList(String[] classlist2, int count_of_class2){
		classList = classlist2;
		count_of_class = count_of_class2;
	}
	
	public static String[] getClassList(){
		return classList;
	}
	
	public static int getCountOfClass(){
		return count_of_class;
	}
	
	public void setMethodList(String[] methodList, int count_of_method){
		this.methodList = methodList;
		this.count_of_method = count_of_method;
	}
	
	public void setPackageList(String[] packageList, int count_of_package){
		this.packageList = packageList;
		this.count_of_package = count_of_package;
	}
}
