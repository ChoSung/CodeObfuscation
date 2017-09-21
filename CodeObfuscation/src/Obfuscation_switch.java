
public class Obfuscation_switch {
	Obfuscation_Debug obDebug;
	Obfuscation_Class obClass;
	Obfuscation_Method obMethod;
	Obfuscation_LastCheck obCheck;
	Obfuscation_Package obPackage;
	Obfuscation_String obString;
	Obfuscation_Javafile obJava;
	
	public Obfuscation_switch(){
		
	}
	
	public void changeDebug(){
		obDebug = new Obfuscation_Debug();
		obDebug.removeDebug();
	}
	
	public void changeClass(){
		obClass = new Obfuscation_Class();
		obClass.obfuscationClass();
	}
	
	public void changeMethod(){
		obMethod = new Obfuscation_Method();
		obMethod.obfuscationMethod();
	}
	
	public void changeCheck(){
		obCheck = new Obfuscation_LastCheck();
		obCheck.lastCheck();
	}
	
	public void changePackage(){
		obPackage = new Obfuscation_Package();
		obPackage.obfuscationPackage();
	}
	
	public void changeString(){
		obString = new Obfuscation_String();
		obString.obfuscationString();
	}
	
	public void changeJavaFile(){
		obJava = new Obfuscation_Javafile();
		obJava.obfuscationJavaFile();
	}
}
