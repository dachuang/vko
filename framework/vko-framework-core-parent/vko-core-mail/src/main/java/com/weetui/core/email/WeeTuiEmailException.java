package com.weetui.core.email;

public class WeeTuiEmailException
    extends Exception
{
    private static final long serialVersionUID = -4817386460334501672L;

    public WeeTuiEmailException( String message )
    {
        super( message );
    }

    public WeeTuiEmailException( String message, Throwable throwable )
    {
        super( message, throwable );
    }
}
