package eg.edu.alexu.csd.filestructure.hash;

public class Main {

	public static void main(String[] args) {
		IHash<Integer, String> hash = new DoubleHashingImpl<Integer, String>();
		for (int i = 0; i < 10000; i++) {
			int key = (i + 100000) * 12345;
				hash.put(key, String.valueOf(i));
		}
		System.out.println(hash.collisions());
		System.out.println(hash.capacity());
		System.out.println(hash.size());
	}

}
