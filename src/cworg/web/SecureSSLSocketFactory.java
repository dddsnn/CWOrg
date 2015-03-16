package cworg.web;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * I don't trust the default cipher suite selection.
 */
public class SecureSSLSocketFactory extends SSLSocketFactory {
	private SSLSocketFactory f;
	// TODO might not be completely portable
	private String[] suites = { "TLS_RSA_WITH_AES_256_CBC_SHA",
			"TLS_DHE_RSA_WITH_AES_256_CBC_SHA256",
			"TLS_DHE_DSS_WITH_AES_256_CBC_SHA256",
			"TLS_DHE_RSA_WITH_AES_256_GCM_SHA384",
			"TLS_DHE_DSS_WITH_AES_256_GCM_SHA384",
			"TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA384",
			"TLS_ECDH_ECDSA_WITH_AES_256_GCM_SHA384",
			"TLS_ECDH_RSA_WITH_AES_256_CBC_SHA384",
			"TLS_ECDH_RSA_WITH_AES_256_GCM_SHA384",
			"TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384",
			"TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384",
			"TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384",
			"TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384" };

	public SecureSSLSocketFactory() {
		try {
			SSLContext sslctx = SSLContext.getInstance("TLS");
			try {
				sslctx.init(null, null, null);
			} catch (KeyManagementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			f = sslctx.getSocketFactory();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String[] getDefaultCipherSuites() {
		return suites;
	}

	@Override
	public String[] getSupportedCipherSuites() {
		return suites;
	}

	@Override
	public Socket createSocket(Socket arg0, String arg1, int arg2, boolean arg3)
			throws IOException {
		SSLSocket s = (SSLSocket) f.createSocket(arg0, arg1, arg2, arg3);
		s.setEnabledCipherSuites(suites);
		return s;
	}

	@Override
	public Socket createSocket(String arg0, int arg1) throws IOException,
			UnknownHostException {
		SSLSocket s = (SSLSocket) f.createSocket(arg0, arg1);
		s.setEnabledCipherSuites(suites);
		return s;
	}

	@Override
	public Socket createSocket(InetAddress arg0, int arg1) throws IOException {
		SSLSocket s = (SSLSocket) f.createSocket(arg0, arg1);
		s.setEnabledCipherSuites(suites);
		return s;
	}

	@Override
	public Socket createSocket(String arg0, int arg1, InetAddress arg2, int arg3)
			throws IOException, UnknownHostException {
		SSLSocket s = (SSLSocket) f.createSocket(arg0, arg1, arg2, arg3);
		s.setEnabledCipherSuites(suites);
		return s;
	}

	@Override
	public Socket createSocket(InetAddress arg0, int arg1, InetAddress arg2,
			int arg3) throws IOException {
		SSLSocket s = (SSLSocket) f.createSocket(arg0, arg1, arg2, arg3);
		s.setEnabledCipherSuites(suites);
		return s;
	}

}
