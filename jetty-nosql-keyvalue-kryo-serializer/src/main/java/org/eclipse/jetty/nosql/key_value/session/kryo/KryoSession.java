package org.eclipse.jetty.nosql.key_value.session.kryo;

import org.eclipse.jetty.nosql.key_value.session.AbstractSerializableSession;

public class KryoSession extends AbstractSerializableSession {
    private static final long serialVersionUID = -4000990196099331646L;

    public KryoSession() {
        setCreationTime(System.currentTimeMillis());
        setAccessed(getCreationTime());
    }
}
