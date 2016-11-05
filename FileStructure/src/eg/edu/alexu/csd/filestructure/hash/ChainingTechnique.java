package eg.edu.alexu.csd.filestructure.hash;

import java.util.LinkedList;

public class ChainingTechnique<K, V> implements IHash<K, V>, IHashChaining {
	private LinkedList<IPair>[] chained_list;
	private LinkedList<Integer> keys_list;
	private IPair pair;
	private int hash_func;
	private int collisions;
	private int slots;
	private int size;

	public ChainingTechnique() {
		this.slots = 1200;
		this.collisions = 0;
		this.size = 0;
		this.chained_list = new LinkedList[this.slots];
		for (int i = 0; i < chained_list.length; i++) {
			if (chained_list[i] == null) {
				chained_list[i] = new LinkedList<IPair>();
			}
		}
		this.keys_list = new LinkedList<Integer>();
	}

	@Override
	public void put(K key, V value) {
		if (!this.contains(key)) {
			this.size++;
			this.pair = new PairImpl();
			this.pair.setKey((Integer) key);
			this.pair.setValue((String) value);
			this.hash_func = key.hashCode() % slots;
			if (chained_list[hash_func].size() != 0) {
				this.collisions += chained_list[hash_func].size();
			}
			chained_list[hash_func].addFirst(this.pair);
			this.keys_list.add(key.hashCode());
		}
	}

	@Override
	public String get(K key) {
		hash_func = key.hashCode() % slots;
		LinkedList<IPair> list = chained_list[hash_func];
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getKey().equals(key)) {
				return list.get(i).getValue();
			}
		}
		return null;
	}

	@Override
	public void delete(K key) {
		hash_func = key.hashCode() % slots;
		LinkedList<IPair> list = chained_list[hash_func];
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getKey().equals(key)) {
				this.size--;
				list.remove(i);
				this.keys_list.remove(key);
			}
		}
	}

	@Override
	public boolean contains(K key) {
		hash_func = key.hashCode() % slots;
		LinkedList<IPair> list = chained_list[hash_func];
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getKey().equals(key)) {
				return true;
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

	@Override
	public Iterable<K> keys() {
		return (Iterable<K>) this.keys_list;
	}
}
