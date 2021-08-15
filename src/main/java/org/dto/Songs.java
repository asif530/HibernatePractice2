package org.dto;

import javax.persistence.Embeddable;

/*
* Created this class but did not put embeddable.
* So when implemented this class as collection type(e.g. List<Songs> got errors Saying: {'Element Collection' attribute value type should not be 'Songs'})
* */
@Embeddable
public class Songs {

    private String sName;
    private String source;

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
