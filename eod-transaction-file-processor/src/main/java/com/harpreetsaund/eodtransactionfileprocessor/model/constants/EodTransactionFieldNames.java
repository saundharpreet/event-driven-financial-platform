package com.harpreetsaund.eodtransactionfileprocessor.model.constants;

public enum EodTransactionFieldNames {
    TRANSACTION_ID("transactionId"), ACCOUNT_NUMBER("accountNumber"), TYPE("type"), AMOUNT("amount"),
    CURRENCY("currency"), TIMESTAMP("timestamp"), MERCHANT("merchant"), CHANNEL("channel");

    private final String fieldName;

    EodTransactionFieldNames(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public static String[] getFieldNames() {
        EodTransactionFieldNames[] values = EodTransactionFieldNames.values();
        String[] fieldNames = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            fieldNames[i] = values[i].getFieldName();
        }
        return fieldNames;
    }
}
