package com.vko.core.captcha;

public class VkoCaptchaException
    extends Exception
{
    private static final long serialVersionUID = 1339439433313285858L;

    public VkoCaptchaException( String message )
    {
        super( message );
    }

    public VkoCaptchaException( String message, Throwable throwable )
    {
        super( message, throwable );
    }
}
