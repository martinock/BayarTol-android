package com.upiki.bayartol.model;

/**
 * A class representing payment item.
 * @author Martino Christanto Khuangga <martino.aksel.11@gmail.com>
 * @since 2017.07.01
 */

public class Payment {
    private String paymentLocation;
    private long paymentCost;
    private String paymentDate;
    private boolean isBusinessTrip;

    /**
     * Default constructor to create payment object.
     * @param paymentLocation the location of payment
     * @param paymentCost the cost user need to pay
     * @param paymentDate the time of payment
     * @param isBusinessTrip user's business trip mark
     */
    public Payment(
            String paymentLocation,
            long paymentCost,
            String paymentDate,
            boolean isBusinessTrip) {
        this.paymentLocation = paymentLocation;
        this.paymentCost = paymentCost;
        this.paymentDate = paymentDate;
        this.isBusinessTrip = isBusinessTrip;
    }

    /**
     * A method to get the payment location.
     * @return payment location
     */
    public String getPaymentLocation() {
        return paymentLocation;
    }

    /**
     * A method to get the payment cost.
     * @return payment cost
     */
    public long getPaymentCost() {
        return paymentCost;
    }

    /**
     * A method to get the payment date.
     * @return payment date
     */
    public String getPaymentDate() {
        return paymentDate;
    }

    /**
     * A method to get the business trip mark.
     * @return business trip mark
     */
    public boolean isBusinessTrip() {
        return isBusinessTrip;
    }

    /**
     * A method to set the payment location with
     * value gotten from API.
     * @param paymentLocation the payment location
     */
    public void setPaymentLocation(String paymentLocation) {
        this.paymentLocation = paymentLocation;
    }

    /**
     * A method to set payment cost with value
     * gotten from API.
     * @param paymentCost the payment cost
     */
    public void setPaymentCost(long paymentCost) {
        this.paymentCost = paymentCost;
    }

    /**
     * A method to set payment date with value
     * gotten from API.
     * @param paymentDate the payment date
     */
    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    /**
     * A method to set business trip mark with value
     * gotten from API.
     * @param isBusinessTrip business trip mark
     */
    public void setBusinessTrip(boolean isBusinessTrip) {
        this.isBusinessTrip = isBusinessTrip;
    }
}
