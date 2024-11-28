package org.jtools.utils.network;

/*-
 * #%L
 * Java Tools - Utils
 * %%
 * Copyright (C) 2024 jtools.org
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.io.IOException;
import java.net.ServerSocket;
// TODO: Auto-generated Javadoc

/**
 * The Class NetworkUtils.
 */
public class NetworkUtils {

	/**
	 * Instantiates a new network utils.
	 */
	private NetworkUtils() {
		super();
	}

	/**
	 * Gets the free port.
	 *
	 * @param ports the ports
	 * @return the free port
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
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

	/**
	 * Gets the free port.
	 *
	 * @param firstPort the first port
	 * @param lastPort the last port
	 * @return the free port
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
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
