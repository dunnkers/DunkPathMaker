package com.dunnkers.util;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Fires a ChangeEvent when an element has been added, removed or if the array
 * was cleared.
 * 
 * @author Dunnkers
 * 
 * @param <T>
 *            ArrayList type.
 */
public class ListenedArrayList<T> extends ArrayList<T> {

	private static final long serialVersionUID = 1L;
	private final ChangeListener changeListener;

	public ListenedArrayList(ChangeListener changeListener) {
		super();
		this.changeListener = changeListener;
	}

	public ListenedArrayList(int i, ChangeListener changeListener) {
		super(i);
		this.changeListener = changeListener;
	}

	@Override
	public boolean add(T e) {
		final boolean result = super.add(e);
		changeListener.stateChanged(new ChangeEvent(e));
		return result;
	}

	@Override
	public void add(int index, T element) {
		super.add(index, element);
		changeListener.stateChanged(new ChangeEvent(element));
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		final boolean result = super.addAll(index, c);
		changeListener.stateChanged(new ChangeEvent(c));
		return result;
	}

	@Override
	public T remove(int index) {
		final T result = super.remove(index);
		changeListener.stateChanged(new ChangeEvent(index));
		return result;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		final boolean result = super.addAll(c);
		changeListener.stateChanged(new ChangeEvent(c));
		return result;
	}

	@Override
	public boolean remove(Object o) {
		final boolean result = super.remove(o);
		changeListener.stateChanged(new ChangeEvent(o));
		return result;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		final boolean result = super.removeAll(c);
		changeListener.stateChanged(new ChangeEvent(c));
		return result;
	}

	@Override
	public void clear() {
		final ArrayList<T> arrayList = this;
		super.clear();
		changeListener.stateChanged(new ChangeEvent(arrayList));
	}
}