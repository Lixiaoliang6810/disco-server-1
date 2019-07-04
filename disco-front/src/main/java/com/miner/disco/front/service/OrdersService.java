package com.miner.disco.front.service;

import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.model.request.*;
import com.miner.disco.front.model.response.*;
import com.miner.disco.pojo.Orders;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019/1/7
 */
public interface OrdersService {

    Orders queryByPrimaryKey(Long ordersId) throws BusinessException;

    Long purchase(OrdersPurchaseRequest request) throws BusinessException;

    void payment(OrdersPaymentRequest request) throws BusinessException;

    void paymentCallback(OrdersPaymentNotifyRequest request) throws BusinessException;

    OrdersDetailsResponse details(Long ordersId) throws BusinessException;

    List<OrdersListResponse> list(OrdersListRequest request) throws BusinessException;

    void refund(Long ordersId) throws BusinessException;

    void evaluate(OrdersEvaluateRequest request) throws BusinessException;

    void launchAssemble(LaunchOrdersAssembleRequest request) throws BusinessException;

    List<AssembleOrdersListResponse> assembleList(AssembleOrdersListRequest request) throws BusinessException;

    List<ApplyAssembleOrdersListResponse> applyAssembleList(ApplyAssembleOrdersListRequest request) throws BusinessException;

    List<LaunchAssembleOrdersListResponse> launchAssembleList(LaunchAssembleOrdersListRequest request) throws BusinessException;

    void applyAssemble(Long userId, Long ordersId) throws BusinessException;

    OrdersAssembleDetailsResponse assembleDetails(Long ordersId) throws BusinessException;

    void agreeAssemble(Long ordersInvitationId) throws BusinessException;

    void refuseAssemble(Long ordersInvitationId) throws BusinessException;

    boolean cancel(OrdersListRequest request);

    Orders query(OrdersListRequest request);

    boolean updateStatus(Orders orders);
}
