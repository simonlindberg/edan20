package store;

public final class Triple extends Storable {
	@Override
	public String toString() {
		return "Triple [subject=" + subject + ", verb=" + verb + ", object=" + object + "]\n";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((object == null) ? 0 : object.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!super.equals(obj)) return false;
		if (getClass() != obj.getClass()) return false;
		
		Triple other = (Triple) obj;
		if (object == null && other.object != null) {
				return false;
		} else if (!object.equals(other.object)){
			return false;
		} else if (!verb.equals(other.verb)){
			return false;
		} else if(!subject.equals(other.subject)){
			return false;
		}
			
		return true;
	}

	private final String object;
	
	public Triple(final String subject, final String verb, final String object) {
		super(subject, verb);
		this.object = object;
	}
	
	public String getObject() {
		return object;
	}
	
	
}
