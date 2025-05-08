package com.sboss.hexing.producer.service;

import org.springframework.stereotype.Service;
import za.co.eskom.nrs.xmlvend.base._2_1.schema.*;
import za.co.eskom.nrs.xmlvend.revenue._2_1.schema.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author weibb
 */
@Service
public class CreditVendServiceImpl implements CreditVendService {

    @Override
    public CreditVendResp processCreditVendRequest(CreditVendReq request) {
        try {
            // 创建响应对象
            CreditVendResp response = new CreditVendResp();

            // 设置基本字段
            response.setClientID(request.getClientID());

            // 创建服务器ID
            EANDeviceID serverID = new EANDeviceID();
            serverID.setEan("3210987654321");
            response.setServerID(serverID);

            // 设置终端ID
            response.setTerminalID(request.getTerminalID());

            // 设置请求消息ID
            MsgID reqMsgID = new MsgID();
            request.setMsgID(reqMsgID);
            reqMsgID.setDateTime(request.getMsgID().getDateTime());
            reqMsgID.setUniqueNumber(request.getMsgID().getUniqueNumber());
            response.setReqMsgID(reqMsgID);

            // 设置响应时间
            LocalDateTime now = LocalDateTime.now();
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            response.setRespDateTime(now.toString());

            // 设置显示头
            response.setDispHeader("CREDIT VEND – TAX INVOICE|0|0|0");

            // 设置客户端状态
            ClientStatus clientStatus = new ClientStatus();
            CurrencyAmount availCredit = new CurrencyAmount();
            availCredit.setValue("994599");
            availCredit.setSymbol("USD");
            clientStatus.setAvailCredit(availCredit);
            response.setClientStatus(clientStatus);

            // 设置公用事业
            Organization utility = new Organization();
            utility.setName("MDC Tenant");
            utility.setAddress(" ");
            response.setUtility(utility);

            // 设置供应商
            Organization vendor = new Organization();
            vendor.setName("lu0001");
            vendor.setAddress("");
            response.setVendor(vendor);

            // 设置客户详情
            CustomerDetails custVendDetail = new CustomerDetails();
            custVendDetail.setName(getMeterNumber(request));
            custVendDetail.setAddress("null");
            custVendDetail.setAccNo("20000059");
            custVendDetail.setDaysLastPurchase("1");
            custVendDetail.setLocRef("Saint-Louis");
            response.setCustVendDetail(custVendDetail);

            // 创建收据
            CreditVendReceipt receipt = new CreditVendReceipt();
            String receiptNo = generateReceiptNumber();
            receipt.setReceiptNo(receiptNo);

            // 创建交易
            Transactions transactions = new Transactions();

            // 创建信用卡交易
            CreditVendTx creditTx = new CreditVendTx();
            creditTx.setReceiptNo(receiptNo);

            CurrencyAmount creditAmt = new CurrencyAmount();
            creditAmt.setValue("186.00");
            creditAmt.setSymbol("USD");
            creditTx.setAmt(creditAmt);

            // 创建令牌发行
            SaleCredTokenIssue tokenIssue = new SaleCredTokenIssue();
            tokenIssue.setDesc("SaleCredTokenIssue");

            // 设置仪表详情
            MeterDetail meterDetail = new MeterDetail();
            meterDetail.setMsno(getMeterNumber(request));
            meterDetail.setSgc("990458");
            meterDetail.setKrn("2");
            meterDetail.setTi("01");

            MeterType meterType = new MeterType();
            meterType.setAt("07");
            meterType.setTt("02");
            meterDetail.setMeterType(meterType);

            meterDetail.setMaxVendAmt("null");
            meterDetail.setMinVendAmt("null");
            meterDetail.setMaxVendEng("null");
            meterDetail.setMinVendEng("null");
            tokenIssue.setMeterDetail(meterDetail);

            // 设置令牌
            STS1Token token = new STS1Token();
            token.setStsCipher("58865811050730809683");
            tokenIssue.setToken(token);

            // 设置单位
            tokenIssue.setUnits(createUnits());

            // 设置资源
            Electricity electricity = new Electricity();
            tokenIssue.setResource(electricity);

            creditTx.setCreditTokenIssue(tokenIssue);

            // 设置费率
            Tariff tariff = new Tariff();
            tariff.setName("xxx2023");
            creditTx.setTariff(tariff);

            transactions.getTx().add(creditTx);

            // 添加其他服务费用交易
            addServiceChargeTx(transactions, "86.00", "FER");
            addServiceChargeTx(transactions, "5.00", "TCO");
            addServiceChargeTx(transactions, "23.00", "TVA");

            // 设置总金额相关字段
            CurrencyAmount lessRound = new CurrencyAmount();
            lessRound.setValue("300");
            lessRound.setSymbol("USD");
            transactions.setLessRound(lessRound);

            CurrencyAmount tenderAmt = new CurrencyAmount();
            tenderAmt.setValue("300");
            tenderAmt.setSymbol("USD");
            transactions.setTenderAmt(tenderAmt);

            CurrencyAmount change = new CurrencyAmount();
            change.setValue("0");
            change.setSymbol("USD");
            transactions.setChange(change);

            receipt.setTransactions(transactions);
            response.setCreditVendReceipt(receipt);

            return response;
        } catch (Exception e) {
            throw new RuntimeException("处理信用卡充值请求时出错", e);
        }
    }

    private String getMeterNumber(CreditVendReq request) {
        if (request.getIdMethod() instanceof VendIDMethod idMethod) {
            if (idMethod.getMeterIdentifier() instanceof MeterNumber) {
                return ((MeterNumber) idMethod.getMeterIdentifier()).getMsno();
            }
        }
        return "Unknown";
    }

    private String generateReceiptNumber() {
        // 生成格式为 YYMMDDHHmmssXXXX 的收据号
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");
        return formatter.format(now) + "0003";
    }

    private void addServiceChargeTx(Transactions transactions, String amount, String accDesc) {
        ServiceChrgTx tx = new ServiceChrgTx();

        CurrencyAmount amt = new CurrencyAmount();
        amt.setValue(amount);
        amt.setSymbol("USD");
        tx.setAmt(amt);

        tx.setAccDesc(accDesc);
        tx.setAccNo("20000059");

        Tariff tariff = new Tariff();
        tariff.setName("xxx2023");
        tx.setTariff(tariff);

        transactions.getTx().add(tx);
    }

    private SaleCredTokenIssue.Units createUnits() {
        SaleCredTokenIssue.Units units = new SaleCredTokenIssue.Units();
        units.setValue("122.6");
        units.setSiUnit("KWH");
        return units;
    }
}
