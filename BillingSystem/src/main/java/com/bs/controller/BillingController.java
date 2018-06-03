package com.bs.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.bs.domain.ExpenditureBean;
import com.bs.domain.InComeBean;
import com.bs.mapper.BillingMapper;

@RestController
public class BillingController {

	@Autowired
	BillingMapper billingMapper;

	@RequestMapping(value = "/saveInCome", method = RequestMethod.POST)
	String saveInCome(@RequestParam("userId") String userId, @RequestParam("inCome") String inCome) {
		int userIdTmp = Integer.parseInt(userId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String inComeDate = sdf.format(new Date());
		int result = billingMapper.saveInCome(userIdTmp, inCome, inComeDate);
		System.out.println(result);
		return "SUCCESS";
	}

	@RequestMapping(value = "/saveExpenditure", method = RequestMethod.POST)
	String saveExpenditure(@RequestParam("userId") String userId, @RequestParam("expenditure") String expenditure,
			@RequestParam("useage") String useage) {
		int userIdTmp = Integer.parseInt(userId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String payDate = sdf.format(new Date());
		int result = billingMapper.saveExpenditure(userIdTmp, expenditure, useage, payDate);
		System.out.println(result);
		return "SUCCESS";
	}

	@RequestMapping(value = "/loadExpenditureList", method = RequestMethod.POST)
	String loadExpenditureList(@Param("useageSearch") String useageSearch, @Param("userIdSearch") String userIdSearch,
			@Param("pageIndex") String pageIndex, @Param("pageSize") String pageSize) {

		int pgIndex = Integer.parseInt(pageIndex);
		int pgSize = Integer.parseInt(pageSize);
		pgIndex = (pgIndex - 1) * pgSize;
		int cnt = billingMapper.loadExpenditureCnt(useageSearch, userIdSearch);
		JSONObject json = new JSONObject();
		json.put("totalCnt", cnt);
		List<ExpenditureBean> expenditureList = billingMapper.loadExpenditureList(useageSearch, userIdSearch, pgIndex,
				pgSize);
		json.put("Result", expenditureList);
		return json.toString();
	}

	@RequestMapping(value = "/loadInComeList", method = RequestMethod.POST)
	List<InComeBean> loadInComeList() {

		return billingMapper.loadInComeList();
	}

}
