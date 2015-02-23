package com.ecofactor.qa.automation.platform.page.validate;

import static com.ecofactor.qa.automation.platform.util.CheckJenkinsParamUtil.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.ecofactor.qa.automation.platform.util.LogUtil;
import com.ecofactor.qa.automation.platform.annotation.BindAdminValidation;
import com.ecofactor.qa.automation.platform.annotation.BindDBValidation;

/**
 * The Class ValidationSwitch.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public final class ValidationSwitch {

    /**
     * Validate.
     * @param <T> the
     * @param t the t
     * @param methodName the method name
     * @param params the params
     * @return the boolean
     */
    public static <T> Boolean validate(final T t, final String methodName, final Object... params) {

        Boolean validationResult;
        validationResult = invokeDBValidation(t, methodName, params);
        validationResult = validationResult && invokeAdminValidation(t, methodName, params);
        return validationResult;
    }

    /**
     * Invoke admin validation.
     * @param <T> the
     * @param t the t
     * @param methodName the method name
     * @param params the params
     * @return the boolean
     */
    private static <T> Boolean invokeAdminValidation(final T t, final String methodName, final Object... params) {

        if (!canAdminValidate() || (canAdminValidate() && (t.getClass().isAnnotationPresent(BindAdminValidation.class) == false))) {
            return Boolean.TRUE;
        }
        LogUtil.setLogString("Validation Admin Starts", true);
        return invokeValidation(t, methodName, params);
    }

    /**
     * Invoke db validation.
     * @param <T> the
     * @param t the t
     * @param methodName the method name
     * @param params the params
     * @return the boolean
     */
    private static <T> Boolean invokeDBValidation(final T t, final String methodName, final Object... params) {

        if (!canDBValidate() || (canDBValidate() && (t.getClass().isAnnotationPresent(BindDBValidation.class) == false))) {
            return Boolean.TRUE;
        }
        LogUtil.setLogString("Validation DB Starts", true);
        return invokeValidation(t, methodName, params);
    }

    /**
     * Invoke validation.
     * @param <T> the
     * @param t the t
     * @param methodName the method name
     * @param params the params
     * @return the boolean
     */
    private static <T> Boolean invokeValidation(final T t, final String methodName, final Object... params) {

        LogUtil.setLogString("===================================================================", true);
        try {
            final List<Class<?>> methodParamTypes = new ArrayList<>();
            for (final Object param : params) {
                methodParamTypes.add(param.getClass());
            }
            final Method method = t.getClass().getMethod(methodName, methodParamTypes.toArray(new Class<?>[methodParamTypes.size()]));
            Boolean validation = (Boolean) method.invoke(t, params);
            LogUtil.setLogString("===================================================================", true);
            LogUtil.setLogString("Validation Ends", true);
            return validation;
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            LogUtil.setLogString("Error in invoke validation", true);
            return Boolean.FALSE;
        }
    }
}
