package eg.edu.alexu.csd.filestructure.hash;

public class PairImpl implements IPair {
	
	private Integer key;
	private String value;

	public PairImpl() {
		this.key = null;
		this.value = null;
	}

	@Override
	public void setKey(Integer key) {
		this.key = key;
	}

	@Override
	public Integer getKey() {
		return this.key;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return this.value;
	}
}
