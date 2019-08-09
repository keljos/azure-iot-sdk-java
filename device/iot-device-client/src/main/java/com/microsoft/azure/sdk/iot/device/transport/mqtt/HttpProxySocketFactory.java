/*
 *  Copyright (c) Microsoft. All rights reserved.
 *  Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package com.microsoft.azure.sdk.iot.device.transport.mqtt;

import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.net.*;

public class HttpProxySocketFactory extends SSLSocketFactory
{
    private final SSLSocketFactory delegate;
    private final Proxy proxy;

    public HttpProxySocketFactory(String proxyHostname, int proxyPort) {
        this((SSLSocketFactory) SSLSocketFactory.getDefault(), proxyHostname, proxyPort);
    }

    public HttpProxySocketFactory(SSLSocketFactory delegate, String proxyHost, int proxyPort) {
        this.delegate = delegate;
        this.proxy = buildProxy(proxyHost, proxyPort);
    }

    private Proxy buildProxy(String proxyHost, int proxyPort) {
        return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
    }

    @Override
    public Socket createSocket() throws IOException {
        return new ProxiedSSLSocket(delegate, new Socket(proxy));
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return delegate.getDefaultCipherSuites();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return delegate.getSupportedCipherSuites();
    }

    @Override
    public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
        return delegate.createSocket(s, host, port, autoClose);
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
        return delegate.createSocket(host, port);
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localHost, int localPort)
            throws IOException, UnknownHostException
    {
        return delegate.createSocket(host, port, localHost, localPort);
    }

    @Override
    public Socket createSocket(InetAddress host, int port) throws IOException {
        return delegate.createSocket(host, port);
    }

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort)
            throws IOException
    {
        return delegate.createSocket(address, port, localAddress, localPort);
    }
}
