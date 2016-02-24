package basic.ant;

import java.io.File;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Jar;
import org.apache.tools.ant.types.FileSet;


public class BuildJar {

	public static void main(String[] args) {
		Project prj = new Project();
		Jar jar = new Jar();
		jar.setProject(prj);
		jar.setDestFile(new File("C:\\Users\\YAO\\Desktop\\test.jar"));
		FileSet fileSet = new FileSet();
		fileSet.setProject(prj);
		fileSet.setDir(new File("D:\\workspace3\\doobetter-basic\\target\\classes"));
		fileSet.setIncludes("**/*.class,**/*.properties");
		// **代表所有目录
		// * 匹配任意字符

		jar.addFileset(fileSet);
		jar.execute();
	}

}
