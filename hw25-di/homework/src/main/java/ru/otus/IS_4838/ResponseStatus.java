package ru.otus.IS_4838;

/**
 * @author Andrey Sokolov
 */
public enum ResponseStatus {
    OK(0, "response.message.ok"),
    ERROR_VALIDATION(1, "error.validation"),
    SUBSCRIPTION_ALREADY_EXIST(2, "error.subscription.already_exist"),
    SUBSCRIPTION_CREATE_SCRIPT_ERROR(205, "error.subscription.create.script"),                      //F
    SUBSCRIPTION_LIMIT_EXPIRED(206, "error.subscription.limit.expired"),
    SUBSCRIPTION_PERIOD_EXPIRED(207, "error.subscription.period.expired"),
    SUBSCRIPTION_PAYMENT_NOT_FOUND(208, "error.subscription.payment.not.found"),
    FINALLY_PAYMENT_ALREADY_DONE(209, "error.finally.payment.already.done"),
    EVENT_NOT_CONFIGURED(210, "error.event.not.configured"),                                        //F
    PROVIDER_UNKNOWN(3, "error.provider.unknown"),
    UNIDENTIFIED_SUBSCRIBER_NOT_FOUND(4, "error.unidentified_subscriber_not_found"),
    INFOGATE_RESULT_NOT_OK(5, "error.infogate.result"),
    ERROR_NOT_FOUND_PAYMENT_INTERFACE(6, "error.not_found.payment_interface"),
    ERROR_NOT_FOUND_SUBSCRIBER(7, "error.not_found.subscriber"),
    ERROR_NOT_FOUND_SUBSCRIPTION(8, "error.not_found.subscription"),
    ERROR_IDENTITY_FIELD_EDIT(9, "error.identity_field_edit"),
    ERROR_REQUIRED_FIELD_NOT_PRESENT(10, "error.required_field_not_present"),
    ERROR_NO_FIELDS_PASSED(11, "error.no_fields_passed"),
    ERROR_INCORRECT_FIELD_TYPE(12, "error.incorrect_field_type"),
    ERROR_UNKNOWN_FIELD(13, "error.unknown_field"),
    ERROR_EMPTY_FIELD_VALUE(14, "error.empty_field_value"),
    ERROR_NO_SUBSCRIPTION_IDENTIFYING_FIELDS(14, "error.no_subscription_identifying_fields"),
    ERROR_INFOGATE_REQUEST(50, "error.infogate.request"),                                           //F
    ERROR_INFOGATE_VALIDATION(51, "error.infogate.validation"),                                     //F
    STATUS_TCRM_ERROR(110, "error.tcrm"),                                                           //F
    ERROR_SMOG_NO_MAPPING(200, "error.not_found.smog_push_code"),                                   //F
    ERROR_SMOG_UNKNOWN_ERROR(201, "error.unknown.smog"),                                            //F
    ERROR_SMOG_UNKNOWN_RESPONSE(202, "error.unknown_response.smog"),                                //F
    ERROR_SMOG_RESPONSE_NOT_OK(203, "error.response_not_ok.smog"),
    ERROR_SCRIPT_INVOCATION(204, "error.script_invocation"),                                        //F
    ERROR_NOT_FOUND_IDENTITY(211, "error.not.found.identity"),
    SUBSCRIPTION_EXCEEDED_AUTOPAY_MAX_DELAY(212, "error.subscription.autopay.exceeded_max_delay"),
    SUBSCRIPTION_AUTOPAY_NOT_SUPPORTED(213, "error.subscription.autopay.not_supported"),
    SUBSCRIPTION_AUTOPAY_NOT_ACTIVE(214, "error.subscription.autopay.not_active"),
    SUBSCRIPTION_BILL_NOT_FOUND(215, "error.subscription.bill_not_found"),
    SUBSCRIPTION_NO_PAY_SOURCE(216, "error.subscription.autopay.no_pay_source"),
    SUBSCRIPTION_AUTOPAY_BILLS_NOT_RECEIVED(217, "error.subscription.autopay.bills_not_received"),
    ERROR_OFFLINE_SUBSCRIPTION_FIELD_VALIDATION(218, "error.offline_subscription.field_validation"),
    UNKNOWN_BUSINESS_ERROR(500, "error.unknown");                                                   //F

    private final int status;
    private final String message;

    ResponseStatus(final int status, final String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
