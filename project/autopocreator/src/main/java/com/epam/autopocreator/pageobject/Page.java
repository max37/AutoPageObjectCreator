package com.epam.autopocreator.pageobject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;

import com.epam.autopocreator.navigation.AddressOperations;
import com.epam.autopocreator.settings.SavePath;

public class Page {
	private String name;
	// Пустой файл Page Object
	private final String BLANK_FILE = "package com.autopocreator.pageobject;\r\n"
			+ "import ru.yandex.qatools.htmlelements.element.*;\r\n"
			+ "import org.openqa.selenium.support.FindBy;\r\n"
			+ "public class %sPage {\r\n"
			+ "\r\n"
			+ "}";
	
	public Page(String URL) {
		this.name = AddressOperations.getPageName(URL);
	}
	
	public String getName() {
		return name;
	}
	
	public boolean equals(Page obj) {
		return name.equals(obj.getName());
	}
	
	/**
	 * Проверяет, существует ли в выбранном каталоге данная страница
	 * @return
	 */
	public boolean isExists() {
		return (new File(SavePath.getSavePath().getPath() + "\\" + getName() + ".java")).exists();
	}
	
	/**
	 * Дописывает элемент на страницу
	 * @param node
	 * @return
	 */
	public boolean addWebElement(ChosenNode node) {
		if (!this.isExists()) {
			create();
		}
		return writeElement(node);
	}
	
	/**
	 * Создает новый файл-заготовку
	 */
	private void create() {
		try {
			OutputStream os = new FileOutputStream(SavePath.getSavePath().getPath() + "\\" + getName() + ".java");
			os.write(String.format(BLANK_FILE, name).getBytes());
			os.close();
		} catch (FileNotFoundException e1) {
			System.out.println("File not found: " + SavePath.getSavePath().getPath() + "\\" + getName() + ".java");
		} catch (IOException e) {
			System.out.println("Can't read file: " + SavePath.getSavePath().getPath() + "\\" + getName() + ".java");
		}
	}
	
	/**
	 * Дописывает в конец файла элемент
	 * @param node
	 * @return
	 */
	private boolean writeElement(ChosenNode node) {
		if (!checkSameElement(node)) {
			try {
				RandomAccessFile out = new RandomAccessFile(SavePath.getSavePath().getPath() + "\\" + getName() + ".java", "rw");
				try {
					System.out.println(out.length());
					out.seek(out.length() - 1);
					out.write((node.getFullDescription() + "}").getBytes());
					out.close();
					return true;
				} catch (IOException e) {
					System.out.println("Can't read file: " + SavePath.getSavePath().getPath() + "\\" + getName() + ".java");
				}
			} catch (FileNotFoundException e) {
				System.out.println("File not found: " + SavePath.getSavePath().getPath() + "\\" + getName() + ".java");
			}
		} else {
			System.out.println("Element already written");
		}
		return false;
	}
	
	/**
	 * Проверяет, есть ли в файле страницы данный элемент
	 * @param node
	 * @return
	 */
	public boolean checkSameElement(ChosenNode node) {
		File file = new File(SavePath.getSavePath().getPath() + "\\" + getName() + ".java");
		boolean found = false;
		try {
			found = searchWordInFile(file, node.getLastName());
		} catch (IOException e) {
			System.out.println("Can't read file: " + SavePath.getSavePath().getPath() + "\\" + getName() + ".java");
		}
		return found;
	}
	
	/**
	 * Поиск слов в файле, нужен для проверки повторов
	 * @param file
	 * @param searchWord
	 * @return
	 * @throws IOException
	 */
	private boolean searchWordInFile(File file, String searchWord) throws IOException {
	        FileInputStream fis = new FileInputStream(file); 
	        byte[] content = new byte[fis.available()];
	        fis.read(content);
	        fis.close();
	        String[] lines = new String(content, "Cp1251").split("\n"); // кодировку указать нужную
	        for (String line : lines) {
	            if (line.contains(searchWord)) {
	                return true;
	            }
	        }
	        return false;
	}
}
