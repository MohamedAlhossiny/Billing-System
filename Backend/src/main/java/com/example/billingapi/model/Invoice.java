package com.example.billingapi.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Invoice {
    private int invoiceId;
    private int billId;
    private String pdfPath;
    private Timestamp invoiceDate;
    private Date dueDate;
    private String paymentStatus;

    // Constructors
    public Invoice() {
    }

    public Invoice(int invoiceId, int billId, String pdfPath, Timestamp invoiceDate, Date dueDate, String paymentStatus) {
        this.invoiceId = invoiceId;
        this.billId = billId;
        this.pdfPath = pdfPath;
        this.invoiceDate = invoiceDate;
        this.dueDate = dueDate;
        this.paymentStatus = paymentStatus;
    }

    // Getters and Setters
    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public Timestamp getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Timestamp invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {
        return "Invoice{" +
               "invoiceId=" + invoiceId +
               ", billId=" + billId +
               ", pdfPath='" + pdfPath + '\'' +
               ", invoiceDate=" + invoiceDate +
               ", dueDate=" + dueDate +
               ", paymentStatus='" + paymentStatus + '\'' +
               '}';
    }
} 