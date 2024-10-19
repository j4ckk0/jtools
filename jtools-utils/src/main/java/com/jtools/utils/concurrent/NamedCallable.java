/**
 * 
 */
package com.jtools.utils.concurrent;

import java.util.concurrent.Callable;

/**
 * @author j4ckk0
 *
 */
public interface NamedCallable<V> extends Callable<V> {

	public String getName();

}
