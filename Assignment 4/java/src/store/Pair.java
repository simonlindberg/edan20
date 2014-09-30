package store;

public final class Pair extends Storable {

	public Pair(final String subject, final String verb) {
		super(subject, verb);
	}

	@Override
	public String toString() {
		return "Pair [subject=" + subject + ", verb=" + verb + "]";
	}
}
