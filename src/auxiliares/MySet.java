package auxiliares;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
 
/**
 * Set of elements with get method.
 * @author Lukáš Závitkovský
 */
public class MySet<T> implements Iterable{
   
    public MySet(){
        map = new HashMap<T,T>();
    }
   
    public void add(T obj){
        map.put(obj, obj);
    }
   
    public boolean isEmpty(){
        return map.isEmpty();
    }
   
    public void remove(T o){
        map.remove(o);
    }
   
    public boolean contains(T o){
        return map.containsKey(o);
    }
   
    public T get(T obj){
        return map.get(obj);
    }
   
    @Override
    public Iterator<T> iterator(){
        return new MyIterator();
    }
   
    private final Map<T, T> map;
   
    // Iterator implementation for MySet
    private class MyIterator implements Iterator<T>{
 
        public MyIterator(){
            it = map.entrySet().iterator();
        }
       
        @Override
        public boolean hasNext() {
            return it.hasNext();
        }
 
        @Override
        public T next() {
            return it.next().getValue();
        }
 
        @Override
        public void remove() {
            it.remove();
        }
       
        private Iterator<Entry<T,T>> it;
       
    }
   
};