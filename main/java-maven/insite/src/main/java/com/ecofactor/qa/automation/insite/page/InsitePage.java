/*
 * ConsumerPage.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.page;

/**
 * The Interface ConsumerPage.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface InsitePage {

    /**
     * Load page.
     */
    public void loadPage();
    
    public abstract void clickAboutEcofactor();
    /**
     * End.
     */
    public void end();
}
