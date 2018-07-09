package com.punchingwood.minechat.formatting;

import java.util.Calendar;

public class TimestampedMessage implements Message
{
    //-------------------------------------------------------------------------
    // Members
    //-------------------------------------------------------------------------
    
    final String format;

    //-------------------------------------------------------------------------
    // Constructors
    //-------------------------------------------------------------------------

    public TimestampedMessage( final String format )
    {
        this.format = format;
    }

    //-------------------------------------------------------------------------
    // Observers
    //-------------------------------------------------------------------------

    @Override
    public String getMessage()
    {
        String result = this.format;

        final Calendar calendar = Calendar.getInstance();

        final int second = calendar.get(Calendar.SECOND);
        final int minute = calendar.get(Calendar.MINUTE);
        final int hour12 = calendar.get(Calendar.HOUR);
        final int hour24 = calendar.get(Calendar.HOUR_OF_DAY);
        
        result = result.replace(TemplateToken.TIME_SECOND.getToken(), String.valueOf(second));
        result = result.replace(TemplateToken.TIME_SECOND_PADDED.getToken(), String.format("%02d", second));
        result = result.replace(TemplateToken.TIME_MINUTE.getToken(),  String.valueOf(minute));
        result = result.replace(TemplateToken.TIME_MINUTE_PADDED.getToken(), String.format("%02d", minute));
        result = result.replace(TemplateToken.TIME_12HOUR.getToken(),  String.valueOf(hour12));
        result = result.replace(TemplateToken.TIME_12HOUR_PADDED.getToken(), String.format("%02d", hour12));
        result = result.replace(TemplateToken.TIME_24HOUR.getToken(),  String.valueOf(hour24));
        result = result.replace(TemplateToken.TIME_24HOUR_PADDED.getToken(), String.format("%02d", hour24));
        result = result.replace(TemplateToken.TIME_AM_PM_LOWER.getToken(),  (calendar.get(Calendar.AM_PM) == 0) ? "am" : "pm");
        result = result.replace(TemplateToken.TIME_AM_PM_UPPER.getToken(),  (calendar.get(Calendar.AM_PM) == 0) ? "AM" : "PM");

        return result;
    }
}
