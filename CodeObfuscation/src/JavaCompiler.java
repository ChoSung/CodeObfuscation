import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class JavaCompiler {
	public void JavaCompiler(){}
	public void compile(File selectJavaFile){
		String filePath =selectJavaFile.getParent(); //부모경로
		String fileName = selectJavaFile.getName(); //파일이름
		ProcessBuilder builder = new ProcessBuilder(
				"cmd.exe", "/c", " cd "+ filePath +" && javac "+fileName);
		builder.redirectErrorStream(true);
		Process p=null;
		try {
			p = builder.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line="";
		while (true) {
			try {
				line = r.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (line == null) { break; }
			System.out.println(line);
		}
	}
}
