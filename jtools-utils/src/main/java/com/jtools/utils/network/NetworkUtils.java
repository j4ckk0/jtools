/**
 * 
 */
package com.jtools.utils.network;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author j4ckk0
 *
 */
public class NetworkUtils {

	public static int getFreePort(int[] ports) throws IOException {
		for (int port : ports) {
			ServerSocket socket;
			try {
				socket = new ServerSocket(port);
			} catch (IOException ex) {
				continue; // try next port
			}

			socket.close();
			return port;
		}

		// if the program gets here, no port in the range was found
		throw new IOException("No free port found");
	}

	public static int getFreePort(int firstPort, int lastPort) throws IOException {
		for (int port = firstPort; port <= lastPort; port++) {
			ServerSocket socket;
			try {
				socket = new ServerSocket(port);
			} catch (IOException ex) {
				continue; // try next port
			}

			socket.close();
			return port;
		}

		// if the program gets here, no port in the range was found
		throw new IOException("No free port found");
	}

}
