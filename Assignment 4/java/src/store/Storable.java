package store;

public abstract class Storable {
	protected final String subject;
	protected final String verb;
	
	public Storable(final String subject, final String verb) {
		this.subject = subject;
		this.verb = verb;
	}
	
	public final String getSubject() {
		return subject;
	}
	public final String getVerb() {
		return verb;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		result = prime * result + ((verb == null) ? 0 : verb.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Storable other = (Storable) obj;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		if (verb == null) {
			if (other.verb != null)
				return false;
		} else if (!verb.equals(other.verb))
			return false;
		return true;
	}
}
