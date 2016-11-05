package eg.edu.alexu.csd.filestructure.hash;

import java.util.Arrays;
import java.util.LinkedList;

public class LinearProbingTechnique<K, V> implements IHash<K, V>,
		IHashLinearProbing {
	private LinkedList<Integer> keys_list;
	private IPair[] hash_table;
	private boolean[] deleted;
	private int hash_func;
	private IPair pair;
	private int collisions;
	private int size;
	private int slots;

	public LinearProbingTechnique() {
		this.collisions = 0;
		this.slots = 1200;
		this.size = 0;
		this.hash_table = new PairImpl[this.slots];
		Arrays.fill(hash_table, null);
		this.deleted = new boolean[this.slots];
		Arrays.fill(deleted, false);
		this.keys_list = new LinkedList<Integer>();
	}

	@Override
	public void put(K key, V value) {
		if (size == slots) {
			collisions += slots + 1;
			this.rehash(key, value);
		} else {
			pair = new PairImpl();
			pair.setKey(key.hashCode());
			pair.setValue((String) value);
			boolean flag = true;
			int i = 0;
			for (; flag; i++) {
				hash_func = (pair.getKey().hashCode() + i) % slots;
				if (hash_table[hash_func] == null || deleted[hash_func]) {
					flag = false;
					hash_table[hash_func] = this.pair;
					this.size++;
					this.keys_list.add(pair.getKey().hashCode());
				} else {
					this.collisions++;
				}
			}
			if (i > 1) {
				this.collisions++;
			}
		}
	}

	@Override
	public String get(K key) {
		boolean flag = true;
		for (int i = 0; flag; i++) {
			hash_func = ((key.hashCode() % slots) + i) % slots;
			if (hash_table[hash_func] != null) {
				if (hash_table[hash_func].getKey().equals(key)) {
					return hash_table[hash_func].getValue();
				}
			} else if (hash_table[hash_func] == null && deleted[hash_func]) {
				continue;
			} else {
				flag = false;
			}
		}
		return null;
	}

	@Override
	public void delete(K key) {
		boolean flag = true;
		for (int i = 0; flag; i++) {
			hash_func = ((key.hashCode() % slots) + i) % slots;
			if (hash_table[hash_func] != null) {
				if (hash_table[hash_func].getKey().equals(key)) {
					hash_table[hash_func] = null;
					deleted[hash_func] = true;
					this.keys_list.remove(key);
					this.size--;
				}
			} else if (hash_table[hash_func] == null && deleted[hash_func]) {
				continue;
			} else {
				flag = false;
			}
		}
	}

	@Override
	public boolean contains(K key) {
		boolean flag = true;
		for (int i = 0; flag; i++) {
			hash_func = ((key.hashCode() % slots) + i) % slots;
			if (hash_table[hash_func] != null) {
				if (hash_table[hash_func].getKey().equals(key)) {
					return true;
				}
			} else if (hash_table[hash_func] == null && deleted[hash_func]) {
				continue;
			} else {
				flag = false;
			}
		}
		return false;
	}

	@Override
	public boolean isEmpty() {
		return (!(this.size > 0));
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public int capacity() {
		return this.slots;
	}

	@Override
	public int collisions() {
		return this.collisions;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<K> keys() {
		return (Iterable<K>) this.keys_list;
	}

	public void rehash(K key, V value) {
		this.slots *= 2;
		IPair[] temp_table = new IPair[this.slots];
		boolean[] temp_deleted = new boolean[this.slots];
		for (IPair p : hash_table) {
			if (p != null) {
				boolean flag = true;
				int i = 0;
				for (; flag; i++) {
					hash_func = (p.getKey().hashCode() + i) % slots;
					if (temp_table[hash_func] == null) {
						flag = false;
						temp_table[hash_func] = p;
					} else {
						this.collisions++;
					}
				}
				if (i > 1) {
					this.collisions++;
				}
			}
		}
		hash_table = new IPair[this.slots];
		hash_table = temp_table;
		deleted = temp_deleted;
		pair = new PairImpl();
		pair.setKey(key.hashCode());
		pair.setValue((String) value);
		put(key, value);
	}
}
