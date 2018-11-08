package com.model2.mvc.web.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

@Controller
public class PurchaseController {
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService proService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	public PurchaseController() {
		// TODO Auto-generated constructor stub
		System.out.println();
	}

	@RequestMapping("/addPurchase.do")
	public ModelAndView addPurchase(HttpServletRequest request,HttpServletResponse response) throws Exception {
		System.out.println("addPurchaseAction 시작 ::");

		Purchase purchase=new Purchase();
		purchase.setBuyer(userService.getUser(request.getParameter("buyerId")));
		purchase.setPurchaseProd(proService.getProduct(Integer.parseInt(request.getParameter("prodNo"))));
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setDivyDate(request.getParameter("receiverDate"));
		System.out.println(purchase);
		
		purService.addPurchase(purchase);
		
		System.out.println("purchase vo:::"+purchase);
		System.out.println("addPurchaseAction 끝 ::");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/addPurchase.jsp");
		modelAndView.addObject("purchase", purchase);
		
		return modelAndView;
	}
	
	@RequestMapping("/addPurchaseView.do")
	public ModelAndView addPurchaseView(HttpServletRequest request,HttpServletResponse response) throws Exception {
		System.out.println("addPurchaseView 시작:::::::::::");
		int prodNo = Integer.parseInt(request.getParameter("prod_no"));
		System.out.println("prodNo:::"+prodNo);
		
		Product product=proService.getProduct(prodNo);
		System.out.println("action안에 prodVO"+product);
		
		System.out.println("addPurchaseView 끝:::::::::::");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/addPurchaseView.jsp");
		modelAndView.addObject("product", product);
		
		return modelAndView;
	}
	
	@RequestMapping("/getPurchase.do")
	public ModelAndView getPurchase(HttpServletRequest request,HttpServletResponse response) throws Exception {
		System.out.println("getPurchaseAction 시작");
		int tranNo=Integer.parseInt(request.getParameter("tranNo"));
		
		Purchase purchase=purService.getPurchase(tranNo);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/getPurchase.jsp");
		modelAndView.addObject("purchase", purchase);
		
		System.out.println("getPurchaseAction 끝");
		
		return modelAndView;
	}
	
	@RequestMapping("/listPurchase.do")
	public ModelAndView listPurchase(@ModelAttribute("search") Search search, Model model, HttpServletRequest request,HttpSession session) throws Exception {
		System.out.println("리스트판매엑션 시작");
		User user = (User)session.getAttribute("user");
		
		if(search.getCurrentPage()==0){
			search.setCurrentPage(1);;
		}

		search.setPageSize(pageSize);
		
		// Business logic 수행
		Map<String , Object> map=purService.getPurchaseList(search, user.getUserId());
		
		Page resultPage	= 
					new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListPurchaseAction ::"+resultPage);
		
		// Model 과 View 연결
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/listPurchase.jsp");
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		
		System.out.println("리스트판매엑션 끝");
		
		return modelAndView;
	}
	
	@RequestMapping("/updatePurchase.do")
	public ModelAndView updatePurchase(HttpServletRequest request,HttpServletResponse response) throws Exception {
		int tranNo=Integer.parseInt(request.getParameter("tranNo"));
		
		Purchase purchase = new Purchase();
		purchase.setTranNo(tranNo);
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyRequest(request.getParameter("divyRequest"));
		purchase.setDivyAddr(request.getParameter("divyAddr"));
		purchase.setDivyDate(request.getParameter("divyDate"));
		
		purService.updatePurcahse(purchase);
		System.out.println("업데이트할 purchase"+purchase);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/getPurchase.do?tranNo="+Integer.toString(tranNo));
		
		System.out.println("유펄 끝");
		
		return modelAndView;
	}
	
	@RequestMapping("/updatePurchaseView.do")
	public ModelAndView updatePurchaseView(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		int tranNo=Integer.parseInt(request.getParameter("tranNo"));
		
		Purchase purchase=purService.getPurchase(tranNo);
		System.out.println("업데이트펄뷰에서 purchase::"+purchase);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/updatePurchase.jsp");
		modelAndView.addObject("purchase", purchase);
		
		return modelAndView;
	}
}
