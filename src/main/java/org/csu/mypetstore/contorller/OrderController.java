package org.csu.mypetstore.contorller;

import org.csu.mypetstore.domain.*;
import org.csu.mypetstore.service.CatalogService;
import org.csu.mypetstore.service.OrderService;
import org.csu.mypetstore.service.UserActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private Account account;

    @Autowired
    private org.csu.mypetstore.domain.test test;
    private Cart cart=new Cart();

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Autowired
    OrderService orderService;

    @Autowired
    UserActionService userActionService;

    @Autowired
    CatalogService catalogService;

    @GetMapping("/catalog/viewOrderList")
    public String viewOrderList(@RequestParam("username")String username, Model model){
        if (account==null)
            test.setAuthenticated(false);
        else test.setAuthenticated(true);
        model.addAttribute("authenticated",test.getAuthenticated());
        model.addAttribute("account",account);
        if (username!=null){
            List<Order> orderList=orderService.getOrdersByUsername(username);
            model.addAttribute("orderList",orderList);
        }
        return "order/o_ListOrders";
    }

    @GetMapping("/catalog/viewOrder")
    public String viewOrder(@RequestAttribute("account")Account account,@RequestAttribute("order")Order order,Model model){
        if (account==null)
            test.setAuthenticated(false);
        else test.setAuthenticated(true);
        model.addAttribute("authenticated",test.getAuthenticated());
        model.addAttribute("account",account);
        orderService.setOrderId(order);

        try{
            orderService.insertOrder(order);
        }catch (Exception e){
            e.printStackTrace();
        }

        Date currentData=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");
        String date = sdf.format(currentData);
        userActionService.record(account.getUsername(),"new order","",date);


        return "order/o_ViewOrder";
    }

    @GetMapping("/catalog/viewCart")
    public String viewCart(Model model){
        if (account==null)
            test.setAuthenticated(false);
        else test.setAuthenticated(true);
        model.addAttribute("authenticated",test.getAuthenticated());
        model.addAttribute("account",account);
        model.addAttribute("cart",cart);
        return "cart/c_Cart";
    }

    @PostMapping("/catalog/updateCartQuantities")
    public String updateCartQuantities(@RequestParam("name")String itemId,Model model){
        if (account==null)
            test.setAuthenticated(false);
        else test.setAuthenticated(true);
        model.addAttribute("authenticated",test.getAuthenticated());
        model.addAttribute("account",account);

        Iterator<CartItem> cartItems = cart.getAllCartItems();
        while (cartItems.hasNext()) {
            CartItem cartItem = (CartItem) cartItems.next();
            String _itemId = cartItem.getItem().getItemId();
            try {
                int quantity = Integer.parseInt((String) itemId);
                cart.setQuantityByItemId(_itemId, quantity);
                if (quantity < 1) {
                    cartItems.remove();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("cart",cart);
        return "cart/c_Cart";
    }

    @GetMapping("/catalog/removeItemFromCart")
    public String removeItemFromCart(@RequestParam("cartItemId")String cartItemId,Model model){
        if (account==null)
            test.setAuthenticated(false);
        else test.setAuthenticated(true);
        model.addAttribute("authenticated",test.getAuthenticated());
        model.addAttribute("account",account);

        Item item=cart.removeItemById(cartItemId);
        if (item==null){
            model.addAttribute("message","Attempted to remove null CartItem from Cart.");
            model.addAttribute("cart",cart);
            return "common/Error";
        }else {
            cart.setNumberOfItem(cart.getCartItemList().size()-1);
            model.addAttribute("cart",cart);
            return "cart/c_Cart";
        }
    }

    @PostMapping("/catalog/newOrder")
    public String newOrder(@RequestParam("shippingAddressRequired") String shippingAddressRequired,Model model){
        if (account==null)
            test.setAuthenticated(false);
        else test.setAuthenticated(true);
        model.addAttribute("authenticated",test.getAuthenticated());
        model.addAttribute("account",account);

        //model.addAttribute("order", order);

        String a=shippingAddressRequired;
        if (a!=null){
            return "order/o_ShippingForm";
        }else{
            return "order/o_ConfirmOrder";
        }
    }

    @GetMapping("/catalog/newOrderForm")
    public String newOrderForm(Model model){
        if (account==null)
            test.setAuthenticated(false);
        else test.setAuthenticated(true);
        model.addAttribute("authenticated",test.getAuthenticated());
        model.addAttribute("account",account);

        if (account == null) {
            model.addAttribute("message","You must sign on before attempting to check out.  Please sign on and try checking out again.");
            return "order/o_NewOrderForm";
        } else if (cart != null) {
            Order order=new Order();
            order.initOrder(account, cart);
            model.addAttribute("order",order);
            return "order/o_NewOrderForm";
        } else {
            model.addAttribute("message","An order could not be created because a cart could not be found.");
            return "common/Error";
        }
    }

    @GetMapping("/catalog/listOrders")
    public String listOrders(@RequestAttribute("account")Account account,Model model){
        if (account==null)
            test.setAuthenticated(false);
        else test.setAuthenticated(true);
        model.addAttribute("authenticated",test.getAuthenticated());
        model.addAttribute("account",account);

        List<Order> orderList = orderService.getOrdersByUsername(account.getUsername());

        model.addAttribute("orderList",orderList);
        return "order/o_ListOrders";
    }

    @GetMapping("/catalog/addItemToCart")
    public String addItemToCart(@RequestParam("workingItemId")String workingItemId,Model model){
        if (account==null)
            test.setAuthenticated(false);
        else test.setAuthenticated(true);
        model.addAttribute("authenticated",test.getAuthenticated());
        model.addAttribute("account",account);

        if (cart.containsItemId(workingItemId)){
            cart.incrementQuantityByItemId(workingItemId);
        }else {
            boolean isInStock = catalogService.isItemInStock(workingItemId);
            Item item = catalogService.getItem(workingItemId);
            cart.addItem(item, isInStock);
            cart.setNumberOfItem(cart.getCartItemList().size()+1);
        }


        Date currentData=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");
        String date = sdf.format(currentData);
       // userActionService.record(account.getUsername(),"add item to cart ",workingItemId,date);


        model.addAttribute("cart",cart);
        return "cart/c_Cart";
    }
}
