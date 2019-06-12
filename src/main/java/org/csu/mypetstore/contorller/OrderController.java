package org.csu.mypetstore.contorller;

import org.csu.mypetstore.domain.*;
import org.csu.mypetstore.service.CatalogService;
import org.csu.mypetstore.service.OrderService;
import org.csu.mypetstore.service.UserActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    UserActionService userActionService;

    @Autowired
    CatalogService catalogService;

    @GetMapping("/catalog/viewOrderList")
    public String viewOrderList(@RequestParam("username")String username, Model model){
        if (username!=null){
            List<Order> orderList=orderService.getOrdersByUsername(username);
            model.addAttribute("orderList",orderList);
        }
        return "order/ListOrders";
    }

    @GetMapping("/catalog/viewOrder")
    public String viewOrder(@RequestAttribute("account")Account account,@RequestAttribute("order")Order order){
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


        return "order/ViewOrder";
    }

    @GetMapping("/catalog/viewCart")
    public String viewCart(@RequestAttribute("cart") Cart cart, Model model){
        if (cart==null){
            cart=new Cart();
            model.addAttribute("cart",cart);
        }
        return "cart/Cart";
    }

    @GetMapping("/catalog/UpdateCartQuantitiesServlet")
    public String UpdateCartQuantitiesServlet(@RequestAttribute("cart")Cart cart,@RequestParam("itemId")String itemId){

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
        return "cart/Cart";
    }

    @GetMapping("/catalog/removeItemFromCart")
    public String removeItemFromCart(@RequestAttribute("cart") Cart cart,@RequestParam("cartItemId")String cartItemId,Model model){
        Item item=cart.removeItemById(cartItemId);
        if (item==null){
            model.addAttribute("message","Attempted to remove null CartItem from Cart.");
            return "common/Error";
        }else {
            return "cart/Cart";
        }
    }

    @GetMapping("/catalog/newOrderServlet")
    public String newOrderServlet(@RequestAttribute("order") Order order,@RequestParam("shippingAddressRequired") String shippingAddressRequired,Model model){
        model.addAttribute("order", order);

        String a=shippingAddressRequired;
        if (a!=null){
            return "order/ShippingForm";
        }else{
            return "order/ConfirmOrder";
        }
    }

    @GetMapping("/catalog/newOrderFormServlet")
    public String newOrderFormServlet(@RequestAttribute("account")Account account,@RequestAttribute("cart")Cart cart,Model model){

        if (account == null) {
            model.addAttribute("message","You must sign on before attempting to check out.  Please sign on and try checking out again.");
            return "order/NewOrderForm";
        } else if (cart != null) {
            Order order=new Order();
            order.initOrder(account, cart);
            model.addAttribute("order",order);
            return "order/NewOrderForm";
        } else {
            model.addAttribute("message","An order could not be created because a cart could not be found.");
            return "common/Error";
        }
    }

    @GetMapping("/catalog/listOrders")
    public String listOrders(@RequestAttribute("account")Account account,Model model){

        List<Order> orderList = orderService.getOrdersByUsername(account.getUsername());

        model.addAttribute("orderList",orderList);
        return "order/ListOrders";
    }

    @GetMapping("/catalog/addItemToCart")
    public String addItemToCart(@RequestParam("workingItemId")String workingItemId,@RequestAttribute("account")Account account,@RequestAttribute("cart")Cart cart,Model model){

        if(cart==null){
            cart=new Cart();
        }

        if (cart.containsItemId(workingItemId)){
            cart.incrementQuantityByItemId(workingItemId);
        }else {
            boolean isInStock = catalogService.isItemInStock(workingItemId);
            Item item = catalogService.getItem(workingItemId);
            cart.addItem(item, isInStock);
        }


        Date currentData=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");
        String date = sdf.format(currentData);
        userActionService.record(account.getUsername(),"add item to cart ",workingItemId,date);


        model.addAttribute("cart",cart);
        return "cart/Cart";
    }
}
