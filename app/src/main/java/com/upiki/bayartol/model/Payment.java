package com.upiki.bayartol.model;

/**
 * A class representing payment item.
 * @author Martino Christanto Khuangga <martino.aksel.11@gmail.com>
 * @since 2017.07.01
 */

public class Payment {

    private String toll_name;
    private Long cost;
    private String datetime;
    private String tid;
    private boolean is_business;

    /**
     * Default constructor to create payment object.
     * @param paymentLocation the location of payment
     * @param paymentCost the cost user need to pay
     * @param paymentDate the time of payment
     * @param isBusinessTrip user's business trip mark
     */
    public Payment(
            String paymentLocation,
            Long paymentCost,
            String paymentDate,
            boolean isBusinessTrip) {
        this.toll_name = paymentLocation;
        this.cost = paymentCost;
        this.datetime = paymentDate;
        this.is_business = isBusinessTrip;
    }

    /**
     * A method to get the payment location.
     * @return payment location
     */
    public String getToll_name() {
        return toll_name;
    }

    /**
     * A method to get the payment cost.
     * @return payment cost
     */
    public Long getCost() {
        return cost;
    }

    /**
     * A method to get the payment date.
     * @return payment date
     */
    public String getDatetime() {
        return datetime;
    }

    /**
     * A method to get the business trip mark.
     * @return business trip mark
     */
    public boolean isIs_business() {
        return is_business;
    }

    public String getTid() {
        return tid;
    }

    /**
     * A method to set the payment location with
     * value gotten from API.
     * @param paymentLocation the payment location
     */
    public void setToll_name(String paymentLocation) {
        this.toll_name = paymentLocation;
    }

    /**
     * A method to set payment cost with value
     * gotten from API.
     * @param paymentCost the payment cost
     */
    public void setCost(Long paymentCost) {
        this.cost = paymentCost;
    }

    /**
     * A method to set payment date with value
     * gotten from API.
     * @param paymentDate the payment date
     */
    public void setDatetime(String paymentDate) {
        this.datetime = paymentDate;
    }

    /**
     * A method to set business trip mark with value
     * gotten from API.
     * @param isBusinessTrip business trip mark
     */
    public void setIs_business(boolean isBusinessTrip) {
        this.is_business = isBusinessTrip;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

}
