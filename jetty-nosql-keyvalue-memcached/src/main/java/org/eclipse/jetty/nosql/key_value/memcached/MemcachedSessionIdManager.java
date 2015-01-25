package org.eclipse.jetty.nosql.key_value.memcached;

import org.eclipse.jetty.nosql.key_value.AbstractKeyValueStoreClient;
import org.eclipse.jetty.nosql.key_value.KeyValueStoreSessionIdManager;
import org.eclipse.jetty.nosql.key_value.memcached.spymemcached.SpyMemcachedClientFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

import java.io.IOException;

public class MemcachedSessionIdManager extends KeyValueStoreSessionIdManager {



    private final static Logger log = Log.getLogger("MemcachedSessionIdManager");
    private AbstractMemcachedClientFactory _clientFactory = null;

    public MemcachedSessionIdManager(Server server) throws IOException {
        this(server, "127.0.0.1:11211");
    }

    public MemcachedSessionIdManager(Server server, String serverString) throws IOException {
        this(server, serverString, null);
    }

    public MemcachedSessionIdManager(Server server, String serverString, AbstractMemcachedClientFactory cf) throws IOException {
        super(server, serverString);
        _clientFactory = cf;
    }

    @Override
    protected void doStart() throws Exception {
        log.info("starting...");
        super.doStart();
        log.info("started.");
    }

    @Override
    protected void doStop() throws Exception {
        log.info("stopping...");
        super.doStop();
        log.info("stopped.");
    }

    @Override
    protected AbstractKeyValueStoreClient newClient(String serverString) {
        synchronized (this) {
            if (_clientFactory == null) {
                _clientFactory = new SpyMemcachedClientFactory(); // default client
            }
        }
        AbstractKeyValueStoreClient client = _clientFactory.create(serverString);
        client.setTimeout(getTimeout());
        return client;
    }

    public AbstractMemcachedClientFactory getClientFactory() {
        return _clientFactory;
    }

    public void setClientFactory(AbstractMemcachedClientFactory cf) {
        synchronized (this) {
            _clientFactory = cf;
        }
    }
}
