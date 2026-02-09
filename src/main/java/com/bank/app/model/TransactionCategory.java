package com.bank.app.model;

public enum TransactionCategory {
    FOOD("Food & Dining"),
    TRAVEL("Travel & Transportation"),
    BILLS("Bills & Utilities"),
    SHOPPING("Shopping"),
    ENTERTAINMENT("Entertainment"),
    HEALTHCARE("Healthcare"),
    EDUCATION("Education"),
    SALARY("Salary & Income"),
    INVESTMENT("Investment"),
    TRANSFER("Transfer"),
    OTHER("Other");

    private final String displayName;

    TransactionCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
