package mvc.common.util;

import java.util.ArrayList;
import java.util.Collection;

public class DataObservable<T> {

	private T data = null;
	private final Collection<DataListenerInterface> listListeners;

	public DataObservable() {
		super();
		this.listListeners = new ArrayList<DataListenerInterface>();
	}

	public T getValue() {
		return data;
	}

	public void setValue(T value) {
		this.data = value;
		notiffyAllListeners();
	}


	public void addListener(DataListenerInterface listener){
		this.listListeners.add(listener);
	}

	public void removeListener(DataListenerInterface listener){
		this.listListeners.remove(listener);
	}

	public void notiffyAllListeners(){
		for(DataListenerInterface listener : listListeners){
			listener.dataChange(data);
		}
	}

}
