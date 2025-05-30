package org.digitalLibrary.enums;

import lombok.Getter;

@Getter
public enum Plantype {
    THREE_MONTHS(3, 99, "THREE_MONTHS"),
    SIX_MONTHS(6, 199, "SIX_MONTHS"),
    ONE_YEAR(12, 299, "ONE_YEAR");


    private final int durationinMonhts;
    private final int price;
    private final String plan;


    Plantype(int durationinMonhts, int price, String plan) {
        this.durationinMonhts = durationinMonhts;
        this.price = price;
        this.plan = plan;
    }

    public static Plantype formPlan(String plan) {
        for (Plantype type : values()) {
           if(type.plan.equalsIgnoreCase(plan)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid plan " + plan);
    }

}


