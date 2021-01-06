package com.beitech.store.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorMessages {

    public static final String INSUFFICIENT_PRODUCTS = "You must add at least one product per detail.";
    public static final String NOT_ALLOWED_QUANTITY = String
            .format("You must add %s or less elements.",GeneralConstants.MAXIMUM_QUANTITY);
    public static final String SQL_TRANSACTION = "Error when executing the SQL transaction";
    public static final String SAVE_ERROR = "Error, could not save order";
    public static final String NOT_CONTROLLED_ERROR = "Error no controlled";


}
