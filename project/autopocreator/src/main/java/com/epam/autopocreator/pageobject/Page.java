package com.epam.autopocreator.pageobject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.epam.autopocreator.navigation.AddressOperations;
import com.epam.autopocreator.settings.SavePath;

public class Page {
	private String name;
	private final String BLANK_FILE = "package \n"
			+ "import ru.yandex.qatools.htmlelements.element.*\n"
			+ "public class %sPage {\n"
			+ "}";
	
	public Page(String URL) {
		AddressOperations.getPageName(URL);
	}
	
	public String getName() {
		return name;
	}
	
	public boolean equals(Page obj) {
		return name.equals(obj.getName());
	}
	
	public boolean isExists() {
		return new File(SavePath.getSavePath().getPath()).exists();
	}
	
	public void addWebElement(ChosenNode node) {
		if (!this.isExists()) {
			create();
		}
		writeElement(node);
	}
	
	private void create() {
		File file = new File(SavePath.getSavePath().getPath());
		FileWriter fw;
		try {
			fw = new FileWriter(file);
			fw.write(String.format(BLANK_FILE, name));
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void writeElement(ChosenNode node) {
		File file = new File(SavePath.getSavePath().getPath());
		try {
			RandomAccessFile out = new RandomAccessFile(file, "rw");
			try {
				out.seek(out.length() - 1);
				out.writeUTF(WebElementOperations.getFullDescription(node));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
