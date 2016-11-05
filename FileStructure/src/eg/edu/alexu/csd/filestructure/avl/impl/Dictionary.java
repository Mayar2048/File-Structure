package eg.edu.alexu.csd.filestructure.avl.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import eg.edu.alexu.csd.filestructure.avl.IAVLTree;
import eg.edu.alexu.csd.filestructure.avl.IDictionary;

/**
 * The Class Dictionary.
 */
public class Dictionary implements IDictionary {

	/** The dictionary. */
	private IAVLTree<String> dictionary;

	/** The exist. */
	private boolean exist;

	/** The dictionary size. */
	private int dictionarySize;

	/** The words. */
	private Queue<String> words;

	/**
	 * Instantiates a new dictionary.
	 */
	public Dictionary() {
		this.dictionary = new AVLTree<String>();
		this.words = new LinkedList<String>();
		this.dictionarySize = 0;
		this.exist = false;
	}

	@Override
	public void load(final File file) {
		if (file == null) {
			return;
		}
		this.getContents(file);
		while (!words.isEmpty()) {
			this.insert(words.remove());
		}
	}

	@Override
	public boolean insert(final String word) {
		this.exist = dictionary.search(word);
		if (this.exist) {
			return this.exist;
		} else {
			dictionary.insert(word);
			dictionarySize++;
		}
		return this.exist;
	}

	@Override
	public boolean exists(final String word) {
		this.exist = dictionary.search(word);
		return this.exist;
	}

	@Override
	public boolean delete(final String word) {
		boolean check = dictionary.delete(word);
		if (check) {
			dictionarySize--;
		}
		return check;
	}

	@Override
	public int size() {
		return this.dictionarySize;
	}

	@Override
	public int height() {
		return this.dictionary.height();
	}

	/**
	 * Gets the contents.
	 *
	 * @param aFile
	 *            the a file
	 * @return the contents
	 */
	public void getContents(final File aFile) {
		try {
			BufferedReader input = new BufferedReader(new FileReader(aFile));
			try {
				String line = null; // not declared within while loop
				while ((line = input.readLine()) != null) {
					words.add(line);
				}
			} finally {
				input.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
