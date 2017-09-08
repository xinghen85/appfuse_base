package appfuse_base;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class ConvertUTF {
	private static final String DIR = "/Volumes/WORK/workspace/java/lenovo-cms/src/main/java/com/lenovo/cms/model/";

	public static void main(String[] args) throws IOException {
		Collection<File> listFiles = FileUtils.listFiles(new File(DIR),new String[] {"java"}, false);
		for (File object : listFiles) {
			eee(object.getName());
		}
	}

	private static void eee(String file) throws IOException {
		System.out.println(file);
		File file2 = new File(DIR+file);
		List<String> lines = FileUtils.readLines(file2,"gb2312");
		FileUtils.writeLines(file2,"utf-8", lines);
	}
}
