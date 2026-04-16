package com.example.btl_nhom3.feature_cart.viewmodel;

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

	public void loadCart() {
		refreshState();
	}

	public void increaseQuantity(CartItem item) {
		repository.updateQuantity(item.getId(), item.getQuantity() + 1);
		refreshState();
	}

	public void decreaseQuantity(CartItem item) {
		repository.updateQuantity(item.getId(), item.getQuantity() - 1);
		refreshState();
	}

	public void removeItem(CartItem item) {
		repository.removeItem(item.getId());
		refreshState();
	}

	public void setQuantity(CartItem item, int quantity) {
		repository.updateQuantity(item.getId(), quantity);
		refreshState();
	}

	private void refreshState() {
		List<CartItem> items = repository.getCart();
		cartItems.setValue(items);
		total.setValue(repository.calculateTotal());
		canCheckout.setValue(!items.isEmpty());
	}
}
