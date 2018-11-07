package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseDao;

@Repository("purchaseDaoImpl")
public class PurchaseDaoImpl implements PurchaseDao {
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlsession;
	
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}

	public PurchaseDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Purchase findPurchase(int tranNo) throws Exception {
		// TODO Auto-generated method stub
		return sqlsession.selectOne("PurchaseMapper.getPurchase", tranNo);
	}

	@Override
	public Purchase findPurchase2(int prodNo) throws Exception {
		// TODO Auto-generated method stub
		return sqlsession.selectOne("PurchaseMapper.getPurchase2", prodNo);
	}

	@Override
	public Map<String, Object> getPurchase(Search search, String buyerId) throws Exception {
		// TODO Auto-generated method stub
		Map<String , Object>  map = new HashMap<String , Object>();
		
		map.put("search", search);
		map.put("buyerId", buyerId);
	
		
		List<Purchase> list = sqlsession.selectList("PurchaseMapper.getPurchaseList", map); 
		
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setBuyer((User)sqlsession.selectOne("UserMapper.getUser", list.get(i).getBuyer().getUserId()));
			list.get(i).setPurchaseProd((Product)sqlsession.selectOne("ProductMapper.getProduct", list.get(i).getPurchaseProd().getProdNo()));
		}
		
		map.put("totalCount", sqlsession.selectOne("PurchaseMapper.getTotalCount", buyerId));

		map.put("list", list);

		return map;
	}

	@Override
	public Map<String, Object> getSaleList(Search search) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertPurchase(Purchase purchase) throws Exception {
		// TODO Auto-generated method stub
		sqlsession.insert("PurchaseMapper.addPurchase",purchase);
	}

	@Override
	public void updatePurchase(Purchase purchase) throws Exception {
		// TODO Auto-generated method stub
		sqlsession.update("PurchaseMapper.updatePurchase", purchase);
	}

	@Override
	public void updateTranCode(Purchase purchase) throws Exception {
		// TODO Auto-generated method stub
		sqlsession.update("PurchaseMapper.updateTranCode", purchase);
	}
	
	@Override
	public int getTotalCount(Search search) throws Exception {
		// TODO Auto-generated method stub
		return sqlsession.selectOne("PurchaseMapper.getTotalCount", search);
	}

}
