package ghanam.com.abdo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import ghanam.com.abdo.adapters.CartItemAdapter
import ghanam.com.abdo.databinding.FragmentCartBinding
import ghanam.com.abdo.singletons.DecimalFormatter
import ghanam.com.abdo.singletons.VirtualDB

class CartFragment : Fragment() {
    lateinit var binding: FragmentCartBinding
    private lateinit var itemsAdapter: CartItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val total=VirtualDB.calculateTotal()

        binding= FragmentCartBinding.inflate(inflater, container, false)
        binding.apply {
            deliveryFeeText.text=DecimalFormatter.format(VirtualDB.deliveryFee)
            subtotalText.text=DecimalFormatter.format(total)
            totalText.text=DecimalFormatter.format(total+VirtualDB.deliveryFee)
            itemsAdapter = CartItemAdapter(VirtualDB.items,binding)
            itemsCartRecycler.adapter=itemsAdapter
            itemsCartRecycler.layoutManager = LinearLayoutManager(context)
            continueShoppingButton.setOnClickListener {
                continueShopping(it)
            }
            checkoutButton.setOnClickListener {
                checkOut(it)
            }
        }


        return binding.root
    }



    private fun checkOut(it: View?) {
        if (it != null) {
            if (VirtualDB.getItemsCount()>0){
                VirtualDB.addPrevOrder()
                Navigation.findNavController(it).navigate(R.id.action_cartFragment_to_orderConfirmedFragment)
                VirtualDB.clearCart()
                Toast.makeText(context,"successful order",Toast.LENGTH_SHORT).show()
            }else
                Toast.makeText(context,"add something to cart first!",Toast.LENGTH_SHORT).show()

        }
    }

    private fun continueShopping(it: View?) {
        if (it != null) {
            Navigation.findNavController(it).navigate(R.id.action_cartFragment_to_homeFragment)
        }
    }


}