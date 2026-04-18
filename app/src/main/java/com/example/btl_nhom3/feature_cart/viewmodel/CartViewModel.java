package com.example.btl_nhom3.feature_cart.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.btl_nhom3.feature_cart.model.CartItem;
import com.example.btl_nhom3.feature_cart.repository.CartRepository;

import java.util.List;

public class CartViewModel extends ViewModel {

	private final CartRepository repository = CartRepository.getInstance();

	private final MutableLiveData<List<CartItem>> cartItems = new MutableLiveData<>();
	private final MutableLiveData<Integer> total = new MutableLiveData<>(0);
	private final MutableLiveData<Boolean> canCheckout = new MutableLiveData<>(false);

	public LiveData<List<CartItem>> getCartItems() {
		return cartItems;
	}

	public LiveData<Integer> getTotal() {
		return total;
	}

	public LiveData<Boolean> getCanCheckout() {
		return canCheckout;
	}

	public void loadCart(Context context) {
		refreshState(context);
	}

	public void increaseQuantity(Context context, CartItem item) {
		repository.updateQuantity(context, item.getId(), item.getQuantity() + 1);
		refreshState(context);
	}

	public void decreaseQuantity(Context context, CartItem item) {
		repository.updateQuantity(context, item.getId(), item.getQuantity() - 1);
		refreshState(context);
	}

	public void removeItem(Context context, CartItem item) {
		repository.removeItem(context, item.getId());
		refreshState(context);
	}

	public void setQuantity(Context context, CartItem item, int quantity) {
		repository.updateQuantity(context, item.getId(), quantity);
		refreshState(context);
	}

	private void refreshState(Context context) {
		List<CartItem> items = repository.getCart(context);
			cartItems.setValue(items);
		total.setValue(repository.calculateTotal(context));
		canCheckout.setValue(!items.isEmpty());
	}
}
