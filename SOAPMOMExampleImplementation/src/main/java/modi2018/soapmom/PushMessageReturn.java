package modi2018.soapmom;

import java.io.Serializable;

public class PushMessageReturn implements Serializable {
        private static final long serialVersionUID = 568571323482520293L;
        public String outcome;
	public PushMessageReturn() {}
	public PushMessageReturn(String outcome) {
		this.outcome = outcome;
	}
}
