package com.model2.mvc.service.purchase.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:config/context-*.xml" })
@ContextConfiguration	(locations = {	"classpath:config/context-common.xml",
																	"classpath:config/context-aspect.xml",
																	"classpath:config/context-mybatis.xml",
																	"classpath:config/context-transaction.xml" })
//@ContextConfiguration(locations = { "classpath:config/context-common.xml" })
public class PurchaseServiceTest {
	
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purService;
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService proService;
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	
	public PurchaseServiceTest() {
		// TODO Auto-generated constructor stub
	}
	
	//@Test
	public void testAddPurchase() throws Exception {
		
		Purchase purchase = new Purchase();
		purchase.setPurchaseProd(proService.getProduct(123));
		purchase.setBuyer(userService.getUser("user15"));
		purchase.setPaymentOption("1");
		purchase.setReceiverName("ÀÌÀÎÈ£");
		purchase.setReceiverPhone("12345678");
		purchase.setDivyAddr("¼­¿ï½Ã");
		purchase.setDivyRequest("»¡¸®¿ä¤Ì¤Ì");
		purchase.setDivyDate("20181010");
		purService.addPurchase(purchase);
		
		System.out.println(purchase);
		
		purchase = purService.getPurchase2(123);
		//==> API È®ÀÎ
		Assert.assertEquals(123, purchase.getPurchaseProd().getProdNo());
		Assert.assertEquals("user15", purchase.getBuyer().getUserId());
		Assert.assertEquals("1  ", purchase.getPaymentOption());
		Assert.assertEquals("ÀÌÀÎÈ£", purchase.getReceiverName());
		Assert.assertEquals("12345678", purchase.getReceiverPhone());
		Assert.assertEquals("¼­¿ï½Ã", purchase.getDivyAddr());
		Assert.assertEquals("»¡¸®¿ä¤Ì¤Ì", purchase.getDivyRequest());
	}
	
	//@Test
	public void testGetPurchase() throws Exception {
		
		Purchase purchase = new Purchase();
	
		purchase = purService.getPurchase2(123);
		System.out.println(purchase);
		//==> console È®ÀÎ
		//System.out.println(user);
		
		Assert.assertEquals(123, purchase.getPurchaseProd().getProdNo());
		Assert.assertEquals("user15", purchase.getBuyer().getUserId());
		Assert.assertEquals("1  ", purchase.getPaymentOption());
		Assert.assertEquals("ÀÌÀÎÈ£", purchase.getReceiverName());
		Assert.assertEquals("12345678", purchase.getReceiverPhone());
		Assert.assertEquals("¼­¿ï½Ã", purchase.getDivyAddr());
		Assert.assertEquals("»¡¸®¿ä¤Ì¤Ì", purchase.getDivyRequest());
	}
	
	//@Test
	public void testUpdatePurchase() throws Exception{
		 
		Purchase purchase = purService.getPurchase2(123);
		Assert.assertNotNull(purchase);
		
		Assert.assertEquals(123, purchase.getPurchaseProd().getProdNo());
		Assert.assertEquals("user15", purchase.getBuyer().getUserId());
		Assert.assertEquals("1  ", purchase.getPaymentOption());
		Assert.assertEquals("ÀÌÀÎÈ£", purchase.getReceiverName());
		Assert.assertEquals("12345678", purchase.getReceiverPhone());
		Assert.assertEquals("¼­¿ï½Ã", purchase.getDivyAddr());
		Assert.assertEquals("»¡¸®¿ä¤Ì¤Ì", purchase.getDivyRequest());
		
		//==> console È®ÀÎ
		System.out.println("purchase::"+purchase);
		//System.out.println(user);
		
		purchase.setPaymentOption("2");
		purchase.setReceiverName("·òÀÎÈ«");
		purchase.setReceiverPhone("12121234");
		purchase.setDivyAddr("¼º³²½Ã");
		purchase.setDivyRequest("»¡¸®¹è");
		purchase.setDivyDate("20181024");
		
		purService.updatePurcahse(purchase);
		
		purchase=purService.getPurchase2(123);
		System.out.println("¹Ù²ïÈÄ purchase :: "+purchase);
		
		//==> API È®ÀÎ
		Assert.assertEquals(123, purchase.getPurchaseProd().getProdNo());
		Assert.assertEquals("user15", purchase.getBuyer().getUserId());
		Assert.assertEquals("2  ", purchase.getPaymentOption());
		Assert.assertEquals("·òÀÎÈ«", purchase.getReceiverName());
		Assert.assertEquals("12121234", purchase.getReceiverPhone());
		Assert.assertEquals("¼º³²½Ã", purchase.getDivyAddr());
		Assert.assertEquals("»¡¸®¹è", purchase.getDivyRequest());
	 }
	 
	 //@Test
	 public void testGetPurchaseListAll() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	String buyerId = "user15";
	 	Map<String,Object> map = purService.getPurchaseList(search, buyerId);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
		//==> console È®ÀÎ
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("");
	 	map = purService.getPurchaseList(search, buyerId);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
	 	//==> console È®ÀÎ
	 	System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 
	 //@Test
	 public void testUpdateTranCode()throws Exception{
		 Purchase purchase = new Purchase();
		 purchase = purService.getPurchase2(123);
		 
		 System.out.println("::::"+purchase);
		 
		 Assert.assertEquals("1  ", purchase.getTranCode());
		 
		 purchase.setTranCode("2");
		 
		 purService.updateTranCode(purchase);
		 
		 purchase=purService.getPurchase2(123);
		 
		 Assert.assertEquals("2  ", purchase.getTranCode());
	 }
}
