package com.diviso.graeshoppe.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import java.io.IOException;
import java.util.List;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import com.diviso.graeshoppe.client.product.model.Product;
import com.diviso.graeshoppe.client.sale.domain.Sale;
import com.diviso.graeshoppe.client.sale.domain.TicketLine;
import com.diviso.graeshoppe.service.SaleQueryService;
import com.diviso.graeshoppe.web.rest.util.ServiceUtility;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SaleQueryServiceImpl implements SaleQueryService {
	@Autowired
	private ServiceUtility serviceUtility;

	private RestHighLevelClient restHighLevelClient;

	
	public SaleQueryServiceImpl( RestHighLevelClient restHighLevelClient) {
	
		this.restHighLevelClient = restHighLevelClient;
	}

	
	/**
	 * @param storeId
	 */
	@Override
	public Page<Sale> findSales(String storeId, Pageable pageable) {
		
		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(termQuery("userId", storeId)).sort("date", SortOrder.DESC);

		SearchRequest searchRequest = serviceUtility.generateSearchRequest("sale", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}
		return serviceUtility.getPageResult(searchResponse, pageable, new Sale());


	}
	
	/**
	 * @param saleId
	 */
	@Override
	public List<TicketLine> findTicketLinesBySaleId(Long saleId) {
		

		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(termQuery("sale.id", saleId));
		
		Pageable pageable = PageRequest.of(2, 20);

		SearchRequest searchRequest = serviceUtility.generateSearchRequest("ticketline", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}
		return serviceUtility.getPageResult(searchResponse, pageable, new TicketLine()).getContent();

	}
	
}
