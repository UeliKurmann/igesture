/**
 * 
 */
package org.ximtec.igesture.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Björn Puypepuype@gmail.com
 *
 */
public class MultiValueMap<T,S> {

	private Map<T,List<S>> map;
	
	public MultiValueMap()
	{
		map = new LinkedHashMap<T,List<S>>();
	}
	
	public void add(T key, S value)
	{
		if(map.containsKey(key))
		{
			List<S> list = map.get(key);
			list.add(value);
		}
		else
		{
			List<S> list = new ArrayList<S>();
			list.add(value);
			map.put(key, list);
		}
	}
	
	public void removeKey(T key)
	{
		map.remove(key);
	}
	
	public void removeValue(T key, S value)
	{
		if(map.containsKey(key))
		{
			List<S> list = map.get(key);
			list.remove(value);
		}
	}
	
	public Set<T> keySet()
	{
		return map.keySet();
	}
	
	public List<S> getValues(T key)
	{
		return map.get(key);
	}
	
	public void clear()
	{
		map.clear();
	}
	
	public boolean isEmpty()
	{
		return map.isEmpty();
	}
	
	public boolean containsKey(T key)
	{
		return map.containsKey(key);
	}
	
	public int size()
	{
		return map.size();
	}
}
