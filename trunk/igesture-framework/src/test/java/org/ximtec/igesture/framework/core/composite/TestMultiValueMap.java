/**
 * 
 */
package org.ximtec.igesture.framework.core.composite;

import junit.framework.Assert;

import org.junit.Test;
import org.ximtec.igesture.util.MultiValueMap;

/**
 * @author Björn Puypeype, bpuype@gmail.com
 *
 */
public class TestMultiValueMap {
	
	@Test
	public void test(){
		MultiValueMap<Integer,String> mvm = new MultiValueMap<Integer,String>();
		mvm.add(1, "een");
		mvm.add(1, "one");
		mvm.add(2, "deux");
		
		Assert.assertFalse(mvm.containsKey(3));
		Assert.assertEquals(2, mvm.size());
		
		mvm.removeValue(1, "een");
		
		Assert.assertEquals("one", mvm.getValues(1).toArray()[0]);
	
	}

}
