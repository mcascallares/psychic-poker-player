package org.matias.poker;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class FileLineReader {

	private final String fileName;

	public FileLineReader(String fileName) {
		this.fileName = fileName;
	}

	public String[] read() throws IOException {
		List<String> ret = new LinkedList<String>();
		DataInputStream in = new DataInputStream(new FileInputStream(fileName));
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line;
		while ((line = reader.readLine()) != null) {
			ret.add(line.trim());
		}
		in.close();
		return ret.toArray(new String[ret.size()]);
	}

}
