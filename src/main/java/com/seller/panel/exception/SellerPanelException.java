package com.seller.panel.exception;

import lombok.Getter;

@Getter
public class SellerPanelException extends RuntimeException {

    private static final long serialVersionUID = -2351508053441907928L;
    private final String messageKey;

    public SellerPanelException(String message, String messageKey) {
        super(message);
        this.messageKey = messageKey;
    }

    public SellerPanelException(String message) {
        super(message);
        this.messageKey = null;
    }

    public SellerPanelException(Throwable e) {
        super(e);
        this.messageKey = null;
    }

    public SellerPanelException(String message, Throwable e) {
        super(message, e);
        this.messageKey = null;
    }
}
