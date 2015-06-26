package com.epam.autopocreator.settings;

import java.io.File;
import java.io.IOException;
/**
 * Одиночка для каталога сохранения результатов
 * @author Maxim_Mytaryov
 *
 */
public class SavePath {
	private String path;
	public static SavePath INSTANCE;
	
	private SavePath() {
		try {
			path = (new File(".")).getCanonicalPath();
		} catch (IOException e) {
			// do nothing
		}
	}
	
	public static SavePath getSavePath() {
		if (INSTANCE == null) {
			INSTANCE = new SavePath();
		}
		return INSTANCE;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String newPath) {
		path = newPath;
	}
}
